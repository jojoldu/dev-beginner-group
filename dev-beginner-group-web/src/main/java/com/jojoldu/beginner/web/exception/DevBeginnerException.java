package com.jojoldu.beginner.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@ResponseStatus(HttpStatus.OK)
@AllArgsConstructor
@Getter
public class DevBeginnerException extends RuntimeException {
    protected ExceptionStatus status;
    protected String message;
}
