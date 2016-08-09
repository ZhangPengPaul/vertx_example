package paul.zhang.vertx.example.core.net.http.sharing;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
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
        vertx.deployVerticle("paul.zhang.vertx.example.core.net.http.sharing.HttpServerVerticle",
                new DeploymentOptions().setInstances(4));
    }
}
