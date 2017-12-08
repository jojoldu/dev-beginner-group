package com.jojoldu.admin.config;

import com.jojoldu.beginner.mail.template.HandlebarsFactory;
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

    @Bean
    public HandlebarsFactory handlebarsFactory() throws IOException {
        HandlebarsFactory factory = new HandlebarsFactory();
        factory.put("newsletter");
        return factory;
    }
}
