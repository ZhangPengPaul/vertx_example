package paul.zhang.vertx.example.core.verticle.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/10.
 */
public class MainVerticle extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(MainVerticle.class);
    }

    @Override
    public void start() throws Exception {
        System.out.println("[Main] Running in " + Thread.currentThread().getName());
        vertx.deployVerticle(WorkerVerticle.class.getName(), new DeploymentOptions().setWorker(true));

        vertx.eventBus().send("sample.data", "hello vert.x", reply -> {
            if (reply.succeeded()) {
                System.out.println("[Main] Receiving reply: " + reply.result().body()
                        + " in " + Thread.currentThread().getName());
            } else {
                reply.cause().printStackTrace();
            }
        });
    }
}
