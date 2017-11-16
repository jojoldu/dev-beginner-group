package com.jojoldu.beginner.web.dto;

import lombok.AllArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@AllArgsConstructor
public class SubscribeResponseDto {

    private boolean isSuccess;

    public boolean getIsSuccess() {
        return isSuccess;
    }
}
