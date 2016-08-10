package paul.zhang.vertx.example.core.eventbus.p2p;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class Receiver extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runClusteredExample(Receiver.class);
    }

    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("ping-address", message -> {
            System.out.println("Receive message: " + message.body());

            message.reply("pong!");
        });

        System.out.println("receive ready!");
    }
}
