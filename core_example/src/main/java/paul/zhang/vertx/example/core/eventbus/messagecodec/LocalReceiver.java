package paul.zhang.vertx.example.core.eventbus.messagecodec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import paul.zhang.vertx.example.core.eventbus.messagecodec.util.CustomMessage;

/**
 * Created by PaulZhang on 2016/8/10.
 */
public class LocalReceiver extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        EventBus eb = vertx.eventBus();

        eb.consumer("local-message-receiver", message -> {
            CustomMessage customMessage = (CustomMessage) message.body();

            System.out.println("Custom message received: " + customMessage.getSummary());
            // Replying is same as publishing
            CustomMessage replyMessage = new CustomMessage(200, "a00000002", "Message sent from local receiver!");
            message.reply(replyMessage);
        });
    }
}
