package paul.zhang.vertx.example.core.net.http.proxy;

import io.vertx.core.AbstractVerticle;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class EndpointServer extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(EndpointServer.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            System.out.println("Got request: " + request.uri());

            for (String name : request.headers().names()) {
                System.out.println(name + " : " + request.headers().get(name));
            }

            request.handler(data -> System.out.println("Got data: " + data.toString("UTF-8")));

            request.endHandler(v -> {
                request.response().setChunked(true);

                for (int i = 0; i < 10; i++) {
                    request.response().write("server-data-chunk-" + i);
                }

                request.response().end();
            });
        }).listen(8282);
    }
}
