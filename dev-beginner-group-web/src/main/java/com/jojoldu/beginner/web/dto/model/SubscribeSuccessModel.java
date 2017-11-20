package com.jojoldu.beginner.web.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 20.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@NoArgsConstructor
public class SubscribeSuccessModel {
    private String message;

    public static SubscribeSuccessModel createInvite(String email){
        SubscribeSuccessModel model = new SubscribeSuccessModel();
        model.message = String.format("초대장이 발송되었습니다. %s 를 확인해보세요", email);
        return model;
    }

    public static SubscribeSuccessModel createCertify(String email){
        SubscribeSuccessModel model = new SubscribeSuccessModel();
        model.message = String.format("%s 인증이 성공하였습니다. 구독 감사합니다!", email);
        return model;
    }
}
