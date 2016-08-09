package paul.zhang.vertx.example.core.net.http.sharing;

import io.vertx.core.AbstractVerticle;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class HttpServerVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> request.response().putHeader("content-type", "text/html")
                .end("<html><body><h1>Hello from " + this + "</h1></body></html>")).listen(8888);
    }
}
