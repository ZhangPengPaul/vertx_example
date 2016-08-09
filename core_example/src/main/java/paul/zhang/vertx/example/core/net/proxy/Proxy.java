package paul.zhang.vertx.example.core.net.proxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class Proxy extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Proxy.class);
    }

    @Override
    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient(new HttpClientOptions());
        vertx.createHttpServer().requestHandler(request -> {
            System.out.println("proxying request: " + request.uri());

            HttpClientRequest clientRequest = client.request(request.method(), 8282, "localhost", request.uri(), response -> {
                System.out.println("proxying response: " + response.statusCode());
                request.response().setChunked(true);
                request.response().setStatusCode(response.statusCode());
                request.response().headers().setAll(response.headers());
                response.handler(data -> {
                    System.out.println("proxying response body: " + data.toString("UTF-8"));
                    request.response().write(data);
                });
                response.endHandler(v -> request.response().end());
            });
            clientRequest.setChunked(true);
            clientRequest.headers().setAll(request.headers());
            request.handler(data -> {
                System.out.println("proxying request body: " + data.toString("UTF-8"));
                clientRequest.write(data);
            });
            request.endHandler(v -> clientRequest.end());
        }).listen(8888);
    }
}
