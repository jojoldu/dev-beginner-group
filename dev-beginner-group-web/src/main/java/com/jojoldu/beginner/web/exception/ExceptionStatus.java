package com.jojoldu.beginner.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

    BAD_REQUEST,
    SERVER_ERROR

}
