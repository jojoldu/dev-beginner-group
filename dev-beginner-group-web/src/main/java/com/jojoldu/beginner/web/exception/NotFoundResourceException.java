package com.jojoldu.beginner.web.exception;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 18.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class NotFoundResourceException extends DevBeginnerException {
    private String field;

    public NotFoundResourceException(String field) {
        super(ExceptionStatus.BAD_REQUEST, String.format("해당 %s은 존재하지 않습니다.", field));
        this.field = field;
    }
}
