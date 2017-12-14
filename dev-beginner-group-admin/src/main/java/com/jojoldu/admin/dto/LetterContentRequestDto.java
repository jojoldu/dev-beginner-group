package com.jojoldu.admin.dto;

import com.jojoldu.beginner.domain.letter.LetterContent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@Setter
@Getter
@NoArgsConstructor
public class LetterContentRequestDto {
    private String title;
    private String link;
    private String img;
    private String content;
    private String contentMarkdown;

    @Builder
    public LetterContentRequestDto(@Nonnull String title, @Nonnull String link, @Nonnull String img, @Nonnull String content, @Nonnull String contentMarkdown) {
        this.title = title;
        this.link = link;
        this.img = img;
        this.content = content;
        this.contentMarkdown = contentMarkdown;
    }

    public LetterContent toEntity(){
        return LetterContent.builder()
                .title(title)
                .link(link)
                .img(img)
                .content(decode(content))
                .contentMarkdown(contentMarkdown)
                .build();
    }

    private String decode(String origin){
        try {
            return URLDecoder.decode(origin, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            final String message = "Decode Exception, title: " + title;
            log.error(message, e);
            throw new RuntimeException(message);
        }
    }

}
