package paul.zhang.vertx.example.core.eventbus.messagecodec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import paul.zhang.vertx.example.core.eventbus.messagecodec.util.CustomMessage;
import paul.zhang.vertx.example.core.eventbus.messagecodec.util.CustomMessageCodec;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/10.
 */
public class ClusterReceiver extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runClusteredExample(ClusterReceiver.class);
    }

    @Override
    public void start() throws Exception {
        EventBus eb = vertx.eventBus();

        eb.registerDefaultCodec(CustomMessage.class, new CustomMessageCodec());

        eb.consumer("cluster-message-receiver", message -> {
            CustomMessage customMessage = (CustomMessage) message.body();

            System.out.println("Custom message received: " + customMessage.getSummary());

            // Replying is same as publishing
            CustomMessage replyMessage = new CustomMessage(200, "a00000002", "Message sent from cluster receiver!");
            message.reply(replyMessage);
        });
    }
}
