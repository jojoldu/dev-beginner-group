package com.jojoldu.admin.dto.mail;

import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.List;

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
    private MailContentGroupDto contentGroup;

    @Builder
    public LetterSendMailDto(@Nonnull Long letterId, @Nonnull String subject, @Nonnull String email, @Nonnull List<MailContentDto> redirectDtos) {
        this.letterId = letterId;
        this.subject = subject;
        this.email = email;
        this.contentGroup = new MailContentGroupDto(redirectDtos);
    }
}
