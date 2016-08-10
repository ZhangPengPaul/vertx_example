package paul.zhang.vertx.example.core.verticle.deploy;

import io.vertx.core.AbstractVerticle;

/**
 * Created by PaulZhang on 2016/8/10.
 */
public class OtherVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        // call this method when deployed
        System.out.println("In OtherVerticle.start()");

        System.out.println("Config is :" + config());
    }

    @Override
    public void stop() throws Exception {
        // call this method when undeployed
        System.out.println("In OtherVerticle.stop()");
    }
}
