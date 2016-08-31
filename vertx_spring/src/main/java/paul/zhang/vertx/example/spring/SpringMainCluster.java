package paul.zhang.vertx.example.spring;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import paul.zhang.vertx.example.spring.verticle.ServerVerticle;
import paul.zhang.vertx.example.spring.verticle.SpringVerticle;

/**
 * Created by PaulZhang on 2016/8/31.
 */
public class SpringMainCluster {

    private static Vertx vertx = Vertx.vertx();
    private static AnnotationConfigApplicationContext ctx;

    public static void main(String[] args) {
        ctx = new AnnotationConfigApplicationContext();
        ctx.scan("paul.zhang.vertx.example.spring");
        ctx.refresh();
        Config config = new Config();
        GroupConfig groupConfig = new GroupConfig();
        groupConfig.setName("paul-dev");
        groupConfig.setPassword("paul-dev");
        config.setGroupConfig(groupConfig);

        ClusterManager clusterManager = new HazelcastClusterManager(config);
        VertxOptions options = new VertxOptions().setClusterManager(clusterManager);

        Vertx.clusteredVertx(options, result -> {
            if (result.succeeded()) {
                vertx = result.result();
                deploy(vertx);
            } else {
                deploy(vertx);
            }
        });
    }

    private static void deploy(Vertx vertx) {
        vertx.deployVerticle(new SpringVerticle(ctx));
        vertx.deployVerticle(ServerVerticle.class.getName(), new DeploymentOptions().setInstances(5));
    }

}
