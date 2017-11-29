package com.jojoldu.admin.dto;

import com.jojoldu.beginner.domain.letter.Letter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 24.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class LetterAdminRequestDto {

    private String subject;
    private String sender;
    private String content;
    private String markdown;

    public Letter toEntity(){
        Letter letter = Letter.builder()
                .sender(sender)
                .subject(decode(subject))
                .content(decode(content))
                .markdown(decode(markdown))
                .build();

        letter.sending();

        return letter;
    }

    private String decode(String origin){
        try {
            return URLDecoder.decode(origin, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            final String message = "Decode Exception, Subject: " + subject;
            log.error(message, e);
            throw new RuntimeException(message);
        }
    }
}
