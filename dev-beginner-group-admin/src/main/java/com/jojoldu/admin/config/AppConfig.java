package com.jojoldu.admin.config;

import com.jojoldu.beginner.mail.template.HandlebarsFactory;
import com.jojoldu.beginner.mail.template.TemplateComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

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
}
