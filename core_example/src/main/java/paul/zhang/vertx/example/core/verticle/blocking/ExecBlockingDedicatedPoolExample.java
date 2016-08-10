package paul.zhang.vertx.example.core.verticle.blocking;

import io.vertx.core.DeploymentOptions;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/10.
 */
public class ExecBlockingDedicatedPoolExample {
    public static void main(String[] args) {
        Runner.runExample(ExecBlockingExample.class, new DeploymentOptions()
                .setWorkerPoolName("worker-pool")
                .setMaxWorkerExecuteTime(12000)
                .setWorkerPoolSize(5));
    }
}
