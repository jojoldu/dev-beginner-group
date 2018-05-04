package com.jojoldu.beginner.admin.dto.mail;

import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 2. 28.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
public class ArchiveDto {
    private String subject;
    private MailContentGroupDto contentGroupDto;

    @Builder
    public ArchiveDto(@Nonnull String subject, @Nonnull List<MailContentDto> contentDtos) {
        this.subject = subject.replaceAll(" ", "_");
        this.contentGroupDto = new MailContentGroupDto(contentDtos);
    }
}
