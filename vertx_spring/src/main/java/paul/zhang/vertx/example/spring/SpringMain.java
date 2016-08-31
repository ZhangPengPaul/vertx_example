package paul.zhang.vertx.example.spring;

import io.vertx.core.Vertx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import paul.zhang.vertx.example.spring.verticle.ServerVerticle;
import paul.zhang.vertx.example.spring.verticle.SpringVerticle;

/**
 * Created by PaulZhang on 2016/8/31.
 */
public class SpringMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("paul.zhang.vertx.example.spring");
        ctx.refresh();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SpringVerticle(ctx));
        vertx.deployVerticle(ServerVerticle.class.getName());
    }
}
