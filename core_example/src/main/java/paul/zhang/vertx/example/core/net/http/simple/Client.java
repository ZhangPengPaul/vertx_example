package paul.zhang.vertx.example.core.net.http.simple;

import io.vertx.core.AbstractVerticle;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class Client extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Client.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpClient().getNow(8888, "localhost", "/", response -> {
            System.out.println("Got response: " + response.statusCode());
            response.bodyHandler(body -> System.out.println("Got data: " + body.toString("UTF-8")));
        });
    }
}
