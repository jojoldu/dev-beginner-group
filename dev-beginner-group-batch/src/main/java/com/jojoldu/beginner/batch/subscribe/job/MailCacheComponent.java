package com.jojoldu.beginner.batch.subscribe.job;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 11.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Component
@Getter
public class MailCacheComponent {
    private String subject;
    private String content;
    private String from;

    public void init(String subject, String content, String from) {
        this.subject = subject;
        this.content = content;
        this.from = from;
    }
}
