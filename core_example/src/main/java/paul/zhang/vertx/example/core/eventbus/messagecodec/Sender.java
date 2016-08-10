package paul.zhang.vertx.example.core.eventbus.messagecodec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import paul.zhang.vertx.example.core.eventbus.messagecodec.util.CustomMessage;
import paul.zhang.vertx.example.core.eventbus.messagecodec.util.CustomMessageCodec;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/10.
 */
public class Sender extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runClusteredExample(Sender.class);
    }

    @Override
    public void start() throws Exception {
        EventBus eb = vertx.eventBus();

        eb.registerDefaultCodec(CustomMessage.class, new CustomMessageCodec());

        // custom messages
        CustomMessage clusterMessage = new CustomMessage(200, "a00000001", "Message sent from publisher!");
        CustomMessage localMessage = new CustomMessage(200, "a0000001", "Local message!");

        // send to cluster receiver
        vertx.setPeriodic(1000, l -> eb.send("cluster-message-receiver", clusterMessage, reply -> {
            if (reply.succeeded()) {
                CustomMessage replyMessage = (CustomMessage) reply.result().body();
                System.out.println("Received reply: " + replyMessage.getSummary());
            } else {
                System.out.println("No reply from cluster receiver");
            }
        }));

        // deploy local receiver
        vertx.deployVerticle(LocalReceiver.class.getName(), result -> {
            if (result.succeeded()) {
                // send to local receiver
                vertx.setPeriodic(2000, l -> eb.send("local-message-receiver", localMessage, reply -> {
                    if (reply.succeeded()) {
                        CustomMessage replyMessage = (CustomMessage) reply.result().body();
                        System.out.println("Received local reply: " + replyMessage.getSummary());
                    } else {
                        System.out.println("No reply from local receiver");
                    }
                }));
            } else {
                result.cause().printStackTrace();
            }
        });

    }
}
