package paul.zhang.vertx.example.core.net.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetSocket;
import paul.zhang.vertx.example.core.util.Runner;

import java.nio.charset.Charset;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class Client extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Client.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createNetClient().connect(1234, "localhost", handler -> {
            if (handler.succeeded()) {
                NetSocket netSocket = handler.result();
                netSocket.handler(buffer -> {
                    System.out.println("Net client receiving : " + buffer.toString(Charset.forName("UTF-8")));
                });

                // sending data
                for (int i = 0; i < 10; i++) {
                    String str = "Hello" + i + "\n";
                    System.out.println("Now client sending : " + str);
                    netSocket.write(str);
                }
            } else {
                System.out.println("Failed to connect " + handler.cause());
            }
        });
    }
}
