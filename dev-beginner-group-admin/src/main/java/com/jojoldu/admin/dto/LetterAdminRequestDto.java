package com.jojoldu.admin.dto;

import com.jojoldu.beginner.domain.letter.Letter;
import com.jojoldu.beginner.domain.letter.LetterContentMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Long> contentIds = new ArrayList<>();

    @Builder
    public LetterAdminRequestDto(@Nonnull String subject, @Nonnull String sender, List<Long> contentIds) {
        this.subject = subject;
        this.sender = sender;
        this.contentIds = contentIds;
    }

    public Letter toEntity(){
        Letter letter = Letter.builder()
                .sender(sender)
                .subject(subject)
                .build();

        letter.sending();

        return letter;
    }



}
