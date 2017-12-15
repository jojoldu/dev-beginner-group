package com.jojoldu.beginner.batch.subscribe.job;

import com.jojoldu.beginner.domain.letter.LetterRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 11.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Component
public class MailCacheComponent {

    @Autowired
    private LetterRepository letterRepository;

    private String subject;
    private String content;
    private String from;

    public void init(Long letterId) {

    }
}
