package com.jojoldu.beginner.admin.controller;

import com.jojoldu.beginner.admin.oauth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 14.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RequiredArgsConstructor
@RestController
public class AdminBitlyRestController {

    @GetMapping("/admin/bitly/sample")
    public SessionUser sample(SessionUser sessionUser){
        return sessionUser;
    }
}
