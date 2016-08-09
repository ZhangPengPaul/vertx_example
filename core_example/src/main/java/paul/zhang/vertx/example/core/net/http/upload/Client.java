package paul.zhang.vertx.example.core.net.http.upload;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.streams.Pump;
import paul.zhang.vertx.example.core.util.Runner;

/**
 * Created by PaulZhang on 2016/8/9.
 */
public class Client extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Client.class);
    }

    @Override
    public void start() throws Exception {
        HttpClientRequest request = vertx.createHttpClient(new HttpClientOptions())
                .put(8888, "localhost", "/someurl", response -> System.out.println("Got response: " + response.statusCode()));
        String fileName = "upload.txt";
        FileSystem fs = vertx.fileSystem();

        fs.props(fileName, ares -> {
            FileProps fileProps = ares.result();
            System.out.println("props is: " + fileProps);

            long size = fileProps.size();
            request.headers().set("content-length", String.valueOf(size));
            fs.open(fileName, new OpenOptions(), ares2 -> {
                AsyncFile file = ares2.result();
                Pump pump = Pump.pump(file, request);
                file.endHandler(v -> request.end());
                pump.start();
            });
        });
    }
}
