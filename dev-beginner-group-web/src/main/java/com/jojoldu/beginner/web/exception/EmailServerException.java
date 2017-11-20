package com.jojoldu.beginner.web.exception;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 20.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class EmailServerException extends DevBeginnerException {

    public EmailServerException() {
        super(ExceptionStatus.SERVER_ERROR, "메일 서버에 문제가 있어 메일 발송이 실패하였습니다.");
    }
}
