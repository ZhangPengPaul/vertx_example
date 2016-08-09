package paul.zhang.vertx.example.core.net.proxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
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
        HttpClientRequest request = vertx.createHttpClient(new HttpClientOptions())
                .put(8888, "localhost", "/", response -> {
                    System.out.println("Got response: " + response.statusCode());
                    response.handler(data -> System.out.println("Got response body: " + data.toString("UTF-8")));
                });

        request.setChunked(true);
        for (int i = 0; i < 10; i++) {
            request.write("client-chunk-" + i);
        }

        request.end();
    }
}
