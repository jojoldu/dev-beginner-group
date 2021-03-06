package com.jojoldu.beginner.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import java.util.Collections;
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
public class LetterAdminSendRequestDto {

    @NotBlank(message = "제목이 누락되었습니다.")
    private Long letterId;
    private String email;

    public List<String> getEmails(){
        return StringUtils.isEmpty(email)? null : Collections.singletonList(email);
    }
}
