package com.jojoldu.beginner.mail.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 18.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@AllArgsConstructor
public enum MailFromType {

    ADMIN("admin@devbeginner.com");

    private String email;

}
