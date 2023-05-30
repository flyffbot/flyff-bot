package flyffbot.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class ExecutorConfiguration {

    @Bean
    public ScheduledExecutorService executorService(){
        return Executors.newScheduledThreadPool(20);
    }
}
