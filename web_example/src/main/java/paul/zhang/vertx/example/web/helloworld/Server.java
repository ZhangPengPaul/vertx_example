package paul.zhang.vertx.example.web.helloworld;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import paul.zhang.vertx.example.web.util.Runner;

/**
 * Created by PaulZhang on 2016/8/22.
 */
public class Server extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(routingContext -> routingContext.response().putHeader("content-type", "text/html").end("Hello World!"));

        vertx.createHttpServer().requestHandler(router::accept).listen(8888);
    }

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }
}
