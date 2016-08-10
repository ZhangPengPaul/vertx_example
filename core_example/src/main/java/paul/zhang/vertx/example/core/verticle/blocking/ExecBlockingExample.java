package paul.zhang.vertx.example.core.verticle.blocking;

import io.vertx.core.AbstractVerticle;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/10.
 */
public class ExecBlockingExample extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(ExecBlockingExample.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> vertx.<String>executeBlocking(future -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }

            String r = "armadillos";
            future.complete(r);
        }, result -> {
            if (result.succeeded()) {
                request.response().putHeader("content-type", "text/plain")
                        .end(result.result());
            } else {
                result.cause().printStackTrace();
            }
        })).listen(8888);
    }
}
