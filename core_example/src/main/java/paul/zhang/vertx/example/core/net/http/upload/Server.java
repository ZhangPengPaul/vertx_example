package paul.zhang.vertx.example.core.net.http.upload;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.streams.Pump;
import paul.zhang.vertx.example.core.util.Runner;

import java.util.UUID;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            request.pause();
            String fileName = "D:\\workspace\\vertx_example\\core_example\\target\\classes\\" + UUID.randomUUID() + ".uploaded";
            vertx.fileSystem().open(fileName, new OpenOptions(), ares -> {
                AsyncFile file = ares.result();
                Pump pump = Pump.pump(request, file);
                request.endHandler(v -> file.close(v2 -> {
                    System.out.println("Uploaded to: " + fileName);
                    request.response().end();
                }));
                pump.start();
                request.resume();
            });
        }).listen(8888);
    }
}
