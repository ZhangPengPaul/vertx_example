package paul.zhang.vertx.example.core.verticle.async;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * Created by PaulZhang on 2016/8/10.
 */
public class OtherVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        System.out.println("In OtherVerticle.start (async)");

        vertx.setTimer(2000, l -> {
            System.out.println("Startup tasks are now complete, OtherVerticle is now started!");

            startFuture.complete();
        });

    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        vertx.setTimer(2000, l -> {
            System.out.println("Cleanup tasks are now complete, OtherVerticle is now stopped!");

            stopFuture.complete();
        });
    }
}
