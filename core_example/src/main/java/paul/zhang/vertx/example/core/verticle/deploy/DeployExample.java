package paul.zhang.vertx.example.core.verticle.deploy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/10.
 */
public class DeployExample extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(DeployExample.class);
    }

    @Override
    public void start() throws Exception {

        System.out.println("Main verticle has started, let's deploy some others...");

        // deploy and don't wait for it to start
        vertx.deployVerticle(OtherVerticle.class.getName());

        // deploy and wait for it to start
        vertx.deployVerticle(OtherVerticle.class.getName(), result -> {
            if (result.succeeded()) {
                String deployId = result.result();

                System.out.println("deployment id: " + deployId);

            } else {
                result.cause().printStackTrace();
            }
        });

        // deploy with config
        JsonObject config = new JsonObject();
        config.put("foo", "bar");
        vertx.deployVerticle(OtherVerticle.class.getName(), new DeploymentOptions().setConfig(config));

        // deploy 10 instances
        vertx.deployVerticle(OtherVerticle.class.getName(), new DeploymentOptions().setInstances(10));

        // deploy as worker
        vertx.deployVerticle(OtherVerticle.class.getName(), new DeploymentOptions().setWorker(true));
    }
}
