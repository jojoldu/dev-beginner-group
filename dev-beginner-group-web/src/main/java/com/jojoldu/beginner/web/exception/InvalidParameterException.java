package com.jojoldu.beginner.web.exception;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 18.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class InvalidParameterException extends DevBeginnerException {
    private String field;

    public InvalidParameterException(String field) {
        super(ExceptionStatus.BAD_REQUEST, String.format("해당 %s가 유요하지 않습니다.", field));
        this.field = field;
    }
}
