package com.jojoldu.admin.dto;

import com.jojoldu.beginner.domain.letter.LetterContent;
import com.jojoldu.beginner.util.Decoder;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 18.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
public class MailLinkRedirectDto {

    private static final String REDIRECT_FORM = "%s/mail/statistics/link-click?subscriberId=%d&letterContentId=%d&redirectUrl=%s";

    private Long id;
    private String title;
    private String link;
    private String img;
    private String content;

    @Builder
    public MailLinkRedirectDto(Long subscriberId, String baseUrl, LetterContent letterContent){
        id = letterContent.getId();
        title = Decoder.decode(letterContent.getTitle());
        link = templateLink(baseUrl, subscriberId, letterContent.getId(), letterContent.getLink());
        img = letterContent.getImg();
        content = Decoder.decode(letterContent.getContent());
    }

    private String templateLink(String baseUrl, Long subscriberId, Long letterContentId, String link) {
        return String.format(REDIRECT_FORM, baseUrl, subscriberId, letterContentId, link);
    }
}
