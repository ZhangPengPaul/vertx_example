package paul.zhang.vertx.example.spring.verticle;

import io.vertx.core.AbstractVerticle;
import org.springframework.context.ApplicationContext;
import paul.zhang.vertx.example.spring.service.SpringService;

/**
 * Created by PaulZhang on 2016/8/31.
 */
public class SpringVerticle extends AbstractVerticle {

    private SpringService springService;

    public static final String GET_HELLO_MSG_SERVICE_ADDRESS = "get_hello_msg_service";

    public SpringVerticle(ApplicationContext ctx) {
        this.springService = (SpringService) ctx.getBean("springService");
    }

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer(GET_HELLO_MSG_SERVICE_ADDRESS).handler(msg -> {
            System.out.println("msg body: " + msg.body());
            String helloMsg = springService.hello();
            System.out.println("msg from hello service: " + helloMsg);
            msg.reply(helloMsg);
        });
    }
}
