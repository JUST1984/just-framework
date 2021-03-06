package priv.just.framework.spring.cloud.consumer;

import com.alibaba.fastjson.support.spring.messaging.MappingFastJsonMessageConverter;
import feign.RequestInterceptor;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import priv.just.framework.spring.cloud.provider.api.channel.TestChannel;

import java.time.Duration;

@EnableBinding(Source.class)
@EnableFeignClients(basePackages = "priv.just.framework.spring.cloud.provider.api.feign")
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    public RequestInterceptor globalRequestInterceptor() {
        return template -> System.out.println("globalRequestInterceptor ...");
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> resilience4JCircuitBreakerFactoryCustomizer() {
        return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory.configure(resilience4JConfigBuilder -> {
            CircuitBreakerConfig circuitBreakerConfig = new CircuitBreakerConfig.Builder()
                    .slowCallDurationThreshold(Duration.ofSeconds(5)).build();
            resilience4JConfigBuilder.circuitBreakerConfig(circuitBreakerConfig).build();
            TimeLimiterConfig timeLimiterConfig = new TimeLimiterConfig.Builder()
                    .timeoutDuration(Duration.ofSeconds(10)).build();
            resilience4JConfigBuilder.timeLimiterConfig(timeLimiterConfig);
        }, "getUserInfo");
    }

    @Bean
    @StreamMessageConverter
    public MappingFastJsonMessageConverter mappingFastJsonMessageConverter() {
        return new MappingFastJsonMessageConverter();
    }

}
