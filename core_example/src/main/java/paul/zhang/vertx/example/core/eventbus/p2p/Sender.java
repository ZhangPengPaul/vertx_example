package paul.zhang.vertx.example.core.eventbus.p2p;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class Sender extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runClusteredExample(Sender.class);
    }

    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();

        // send message every second
        vertx.setPeriodic(1000, v -> eventBus.send("ping-address", "ping!", reply -> {
            if (reply.succeeded()) {
                System.out.println("Receive reply: " + reply.result().body());
            } else {
                System.out.println("No reply!");
            }
        }));
    }
}
