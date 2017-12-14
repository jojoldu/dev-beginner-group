package com.jojoldu.admin.dto;

import com.jojoldu.beginner.domain.letter.LetterContent;
import lombok.Getter;

import javax.persistence.Column;

import static com.jojoldu.beginner.util.LocalDateTimeUtil.toStringDate;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
public class LetterContentResponseDto {

    private Long id;
    private String title;
    private String link;
    private String img;
    private String content;
    private String contentMarkdown;
    private String createdDate;
    private String modifiedDate;

    public LetterContentResponseDto(LetterContent entity) {
        id = entity.getId();
        title = entity.getTitle();
        link = entity.getLink();
        img = entity.getImg();
        content = entity.getContent();
        contentMarkdown = entity.getContentMarkdown();
        createdDate = toStringDate(entity.getCreatedDate());
        modifiedDate = toStringDate(entity.getModifiedDate());
    }
}
