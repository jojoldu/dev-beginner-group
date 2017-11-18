package com.jojoldu.beginner.web.exception;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 18.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class DuplicateException extends DevBeginnerException {
    private String field;

    public DuplicateException(String field) {
        super(ExceptionStatus.BAD_REQUEST, String.format("%s는 이미 존재하는 값입니다", field));
        this.field = field;
    }
}
