package com.jojoldu.admin.dto.letter.save;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jojoldu.beginner.domain.letter.LetterContent;
import com.jojoldu.beginner.util.Decoder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.jojoldu.beginner.util.LocalDateTimeUtil.toStringDate;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@Getter
@NoArgsConstructor
public class LetterContentResponseDto {

    private Long id;
    private Long letterId;
    private String title;
    private String link;
    private String img;
    private String content;
    private String contentMarkdown;
    @JsonIgnore private LocalDateTime createdDate;
    @JsonIgnore private LocalDateTime modifiedDate;

    public LetterContentResponseDto(LetterContent entity) {
        id = entity.getId();
        letterId = entity.getFirstLetterId();
        title = Decoder.decode(entity.getTitle());
        link = Decoder.decode(entity.getLink());
        img = entity.getImg();
        content = Decoder.decode(entity.getContent());
        contentMarkdown = entity.getContentMarkdown();
        createdDate = entity.getCreatedDate();
        modifiedDate = entity.getModifiedDate();
    }

    public String getCreatedDate() {
        return toStringDate(createdDate);
    }

    public String getModifiedDate() {
        return toStringDate(modifiedDate);
    }
}
