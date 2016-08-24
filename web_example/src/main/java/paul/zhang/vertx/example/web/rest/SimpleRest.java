package paul.zhang.vertx.example.web.rest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import paul.zhang.vertx.example.web.util.Runner;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by PaulZhang on 2016/8/22.
 */
public class SimpleRest extends AbstractVerticle {

    private Map<String, JsonObject> products = new HashMap<>();

    public static void main(String[] args) {
        Runner.runExample(SimpleRest.class);
    }

    @Override
    public void start() throws Exception {
        initData();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.get("/products/:productID").handler(this::handlerGetProduct);
        router.post("/products/:productID").handler(this::handlerAddProduct);
        router.get("/products").handler(this::handlerGetProducts);

        vertx.createHttpServer().requestHandler(router::accept).listen(8888);
    }

    private void handlerGetProduct(RoutingContext routingContext) {
        String productId = routingContext.request().getParam("productID");
        HttpServerResponse response = routingContext.response();
        if (Objects.isNull(productId)) {
            sendError(400, response);
        } else {
            JsonObject product = products.get(productId);
            if (Objects.isNull(product)) {
                sendError(404, response);
            } else {
                response.putHeader("content-type", "application/json").end(product.encodePrettily());
            }
        }
    }

    private void handlerAddProduct(RoutingContext routingContext) {
        String productID = routingContext.request().getParam("productID");
        HttpServerResponse response = routingContext.response();
        if (Objects.isNull(productID)) {
            sendError(400, response);
        } else {
            JsonObject product = routingContext.getBodyAsJson();
            if (Objects.isNull(product)) {
                sendError(400, response);
            } else {
                products.put(productID, product);
                response.end();
            }
        }
    }

    private void handlerGetProducts(RoutingContext routingContext) {
        JsonArray array = new JsonArray();
        products.forEach((k, v) -> array.add(v));
        routingContext.response().putHeader("content-type", "application/json").end(array.encodePrettily());
    }

    private void sendError(int code, HttpServerResponse response) {
        response.setStatusCode(code).end();
    }

    private void initData() {
        addProduct(new JsonObject().put("id", "prod3568").put("name", "Egg Whisk").put("price", 3.99).put("weight", 150));
        addProduct(new JsonObject().put("id", "prod7340").put("name", "Tea Cosy").put("price", 5.99).put("weight", 100));
        addProduct(new JsonObject().put("id", "prod8643").put("name", "Spatula").put("price", 1.00).put("weight", 80));
    }

    private void addProduct(JsonObject product) {
        product.put(product.getString("id"), product);
    }
}
