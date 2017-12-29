package com.jojoldu.admin.dto;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 19.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
public class LetterSendMailDto {

    private Long letterId;
    private String subject;
    private String email;
    private List<MailLinkRedirectDto> redirectDtos;

    @Builder
    public LetterSendMailDto(@Nonnull Long letterId, @Nonnull String subject, @Nonnull String email, @Nonnull List<MailLinkRedirectDto> redirectDtos) {
        this.letterId = letterId;
        this.subject = subject;
        this.email = email;
        this.redirectDtos = redirectDtos;
    }

    public Map<String, Object> getModel() {
        return ImmutableMap.of("posts", redirectDtos);
    }
}
