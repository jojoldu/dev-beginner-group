package com.jojoldu.beginner.admin.dto;

import com.jojoldu.beginner.domain.letter.Letter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 24.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class LetterAdminSaveRequestDto {

    @NotBlank(message = "제목이 누락되었습니다.")
    private String subject;
    private String sender;
    private List<Long> contentIds = new ArrayList<>();

    @Builder
    public LetterAdminSaveRequestDto(@Nonnull String subject, @Nonnull String sender, List<Long> contentIds) {
        this.subject = subject;
        this.sender = sender;
        this.contentIds = contentIds;
    }

    public Letter toEntity(){
        Letter letter = Letter.builder()
                .sender(sender)
                .subject(subject)
                .build();

        letter.sending();

        return letter;
    }



}
