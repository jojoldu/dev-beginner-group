package com.jojoldu.beginner.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 18.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
public class MailLinkClickRequestDto {

    private Long subscriberId;
    private Long letterContentId;
    private String redirectUrl;

    @Builder
    public MailLinkClickRequestDto(Long subscriberId, Long letterContentId, String redirectUrl) {
        this.subscriberId = subscriberId;
        this.letterContentId = letterContentId;
        this.redirectUrl = redirectUrl;
    }
}
