package com.jojoldu.beginner.web.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 18.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Component
@Getter
public class WebProperties {

    @Value("${devbeginner.web.url}")
    private String webUrl;

}
