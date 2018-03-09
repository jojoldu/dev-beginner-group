package com.jojoldu.beginner.web.dto.archive;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 9.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@NoArgsConstructor
public class LetterArchiveResponseDto {
    private String subject;
    private String archiveUrl;
    private LocalDate sendDate;

    @Builder
    public LetterArchiveResponseDto(String subject, String archiveUrl, LocalDate sendDate) {
        this.subject = subject;
        this.archiveUrl = archiveUrl;
        this.sendDate = sendDate;
    }
}
