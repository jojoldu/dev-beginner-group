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
public class MailOpenRequestDto {

    private Long letterId;
    private Long subscriberId;

    @Builder
    public MailOpenRequestDto(Long letterId, Long subscriberId) {
        this.letterId = letterId;
        this.subscriberId = subscriberId;
    }
}
