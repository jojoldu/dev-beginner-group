package com.jojoldu.beginner.web.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeResponseDto {

    private boolean isSuccess;

    public boolean getIsSuccess() {
        return isSuccess;
    }
}
