package paul.zhang.vertx.example.web.sessions;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import paul.zhang.vertx.example.web.util.Runner;

import java.util.Objects;

/**
 * Created by PaulZhang on 2016/8/24.
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        router.route().handler(routingContext -> {
            Session session = routingContext.session();
            Integer cnt = session.get("hitcount");
            cnt = (Objects.isNull(cnt) ? 0 : cnt) + 1;

            session.put("hitcount", cnt);
            routingContext.response().putHeader("content-type", "text/html").end("<html><body><h1>Hitcount: " + cnt + "</h1></body></html>");
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8888);
    }
}
