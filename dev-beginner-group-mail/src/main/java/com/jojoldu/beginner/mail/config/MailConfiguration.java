package com.jojoldu.beginner.mail.config;

import com.jojoldu.beginner.mail.aws.Sender;
import com.jojoldu.beginner.mail.template.HandlebarsFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 11.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Configuration
@ConditionalOnProperty(prefix = "devbeginner.mail", name = "enabled", havingValue = "true")
public class MailConfiguration {

    @Bean
    public Sender sender() {
        return new Sender();
    }
}
