package paul.zhang.vertx.example.core.verticle.async;

import io.vertx.core.AbstractVerticle;
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

        vertx.deployVerticle(OtherVerticle.class.getName(), result -> {
            if (result.succeeded()) {
                String deploymentId = result.result();

                System.out.println("Other verticle deployed ok, deploymentID = " + deploymentId);

                vertx.undeploy(deploymentId, result2 -> {
                    if (result2.succeeded()) {
                        System.out.println("Undeployed ok!");
                    } else {
                        result2.cause().printStackTrace();
                    }
                });
            } else {
                result.cause().printStackTrace();
            }
        });
    }
}
