package com.jojoldu.beginner.web.config;

import com.jojoldu.beginner.mail.template.HandlebarsFactory;
import com.jojoldu.beginner.mail.template.TemplateComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 8.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Configuration
public class AppConfig {

    @Autowired
    private HandlebarsFactory handlebarsFactory;

    @Bean
    public HandlebarsFactory handlebarsFactory() throws IOException {
        HandlebarsFactory factory = new HandlebarsFactory();
        factory.put("certify");
        return factory;
    }

    @Bean
    public TemplateComponent templateComponent() {
        return new TemplateComponent(handlebarsFactory);
    }

    @Bean
    @ConfigurationProperties("devbeginner")
    public WebProperties webProperties(){
        return new WebProperties();
    }
}
