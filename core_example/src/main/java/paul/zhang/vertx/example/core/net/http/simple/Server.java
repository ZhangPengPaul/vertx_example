package paul.zhang.vertx.example.core.net.http.simple;

import io.vertx.core.AbstractVerticle;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            request.response().putHeader("content-type", "text/html")
                    .end("<html><body><h1>Hello from vert.x!</h1></body></html>");
        }).listen(8888);
    }
}
