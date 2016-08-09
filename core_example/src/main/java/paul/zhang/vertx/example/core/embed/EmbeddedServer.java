package paul.zhang.vertx.example.core.embed;

import io.vertx.core.Vertx;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class EmbeddedServer {

    public static void main(String[] args) {
        // simple http server
        Vertx.vertx().createHttpServer().
                requestHandler(request -> request.response().end("Hello World!")).listen(8888);
    }
}
