package paul.zhang.vertx.example.core.net.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.streams.Pump;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createNetServer().connectHandler(netSocket -> {
            // Create pump（泵 -_-|||）
            Pump.pump(netSocket, netSocket).start();
        }).listen(1234);

        System.out.println("Echo server is now listing.");
    }
}
