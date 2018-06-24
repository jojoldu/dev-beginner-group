package com.jojoldu.beginner.admin.oauth.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by jojoldu@gmail.com on 20/06/2018
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 2663423984697334074L;
    public static final String SESSION_KEY = "user";
    public static final SessionUser EMPTY = SessionUser.builder().build();

    private String accessToken;
    private String name;    // 본명
    private String email;

    @Builder
    public SessionUser(String accessToken, String name, String email) {
        this.accessToken = accessToken;
        this.name = name;
        this.email = email;
    }
}
