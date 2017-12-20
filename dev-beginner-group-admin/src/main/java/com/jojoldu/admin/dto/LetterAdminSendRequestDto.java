package com.jojoldu.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 24.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class LetterAdminSendRequestDto {

    @NotBlank(message = "제목이 누락되었습니다.")
    private Long letterId;

    public LetterAdminSendRequestDto(Long letterId) {
        this.letterId = letterId;
    }
}
