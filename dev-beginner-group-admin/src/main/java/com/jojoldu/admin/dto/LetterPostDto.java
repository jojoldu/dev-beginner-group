package com.jojoldu.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@NoArgsConstructor
public class LetterPostDto {
    private String title;
    private String link;
    private String img;
    private String content;

    @Builder
    public LetterPostDto(@Nonnull String title, @Nonnull String link, @Nonnull String img, @Nonnull String content) {
        this.title = title;
        this.link = link;
        this.img = img;
        this.content = content;
    }
}
