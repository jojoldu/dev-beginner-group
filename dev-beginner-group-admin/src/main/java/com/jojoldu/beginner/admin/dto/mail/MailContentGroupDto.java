package com.jojoldu.beginner.admin.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 2. 28.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@AllArgsConstructor
public class MailContentGroupDto {

    private List<MailContentDto> posts;

}
