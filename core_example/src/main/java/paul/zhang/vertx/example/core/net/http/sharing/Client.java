package paul.zhang.vertx.example.core.net.http.sharing;

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
        vertx.setPeriodic(1000, l -> vertx.createHttpClient().
                getNow(8888, "localhost", "/", resp -> resp.bodyHandler(body -> System.out.println(body.toString("UTF-8")))));
    }
}
