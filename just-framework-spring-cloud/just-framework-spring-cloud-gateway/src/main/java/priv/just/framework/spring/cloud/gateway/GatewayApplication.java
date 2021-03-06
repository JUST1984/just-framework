package priv.just.framework.spring.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-03-31 16:59
 */
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        // Reactor debug
        ReactorDebugAgent.init();
        ReactorDebugAgent.processExistingClasses();
        SpringApplication.run(GatewayApplication.class, args);
    }

}
