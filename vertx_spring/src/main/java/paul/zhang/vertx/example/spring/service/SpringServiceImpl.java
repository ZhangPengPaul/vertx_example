package paul.zhang.vertx.example.spring.service;

import org.springframework.stereotype.Service;

/**
 * Created by PaulZhang on 2016/8/31.
 */
@Service(value = "springService")
public class SpringServiceImpl implements SpringService {

    @Override
    public String hello() {
        return "Hello World";
    }
}
