package com.jojoldu.staticuploader.config;

import com.jojoldu.staticuploader.aws.StaticUploader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 11.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Configuration
@ConditionalOnProperty(prefix = "devbeginner.static", name = "enabled", havingValue = "true")
public class StaticUploaderConfiguration {

    @Bean
    public StaticUploader staticUploader() {
        return new StaticUploader();
    }
}
