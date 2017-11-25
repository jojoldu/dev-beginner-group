package com.jojoldu.admin.dto;

import com.jojoldu.beginner.domain.letter.Letter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 24.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
public class LetterAdminRequestDto {

    private String subject;
    private String sender;
    private String content;
    private String markdown;

    public Letter toEntity(){
        Letter letter = Letter.builder()
                .subject(subject)
                .sender(sender)
                .content(content)
                .markdown(markdown)
                .build();

        letter.sending();

        return letter;
    }
}
