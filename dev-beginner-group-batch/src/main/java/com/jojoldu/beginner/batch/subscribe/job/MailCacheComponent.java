package com.jojoldu.beginner.batch.subscribe.job;

import com.jojoldu.beginner.domain.posts.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 11.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class MailCacheComponent {
    private String subject;
    private String content;
    private String from;

    public void init(String subject, String from, String templateId){
        this.subject = subject;
        this.from = from;
    }

}
