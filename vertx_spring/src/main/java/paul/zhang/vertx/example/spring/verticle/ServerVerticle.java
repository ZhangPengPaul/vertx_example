package paul.zhang.vertx.example.spring.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * Created by PaulZhang on 2016/8/31.
 */
public class ServerVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route("/spring/hello").handler(routingContext ->
                vertx.eventBus().send(SpringVerticle.GET_HELLO_MSG_SERVICE_ADDRESS, "event bus calls spring service",
                        result -> {
                            if (result.succeeded()) {
                                routingContext.response().putHeader("content-type", "application/json")
                                        .end((String) result.result().body());

                            } else {
                                routingContext.response().setStatusCode(400)
                                        .end(result.cause().toString());
                            }
                        }));

        vertx.createHttpServer().requestHandler(router::accept).listen(8888);

    }
}
