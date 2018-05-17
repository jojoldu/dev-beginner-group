package com.jojoldu.beginner.admin.config;

import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.template.HandlebarsFactory;
import com.jojoldu.beginner.mail.template.TemplateComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 8.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Configuration
@EnableAsync
public class AppConfig {

    @Autowired
    private HandlebarsFactory handlebarsFactory;

    @Bean
    public HandlebarsFactory handlebarsFactory() throws IOException {
        HandlebarsFactory factory = new HandlebarsFactory();
        factory.put("newsletter");
        return factory;
    }

    @Bean
    public TemplateComponent templateComponent() {
        return new TemplateComponent(handlebarsFactory);
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Sender sender() {
        return new Sender();
    }

    @Bean
    @ConfigurationProperties("devbeginner.web")
    public WebProperties webProperties(){
        return new WebProperties();
    }
}
