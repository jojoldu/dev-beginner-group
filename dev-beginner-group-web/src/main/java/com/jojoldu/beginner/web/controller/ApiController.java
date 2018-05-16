package com.jojoldu.beginner.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@AllArgsConstructor
@RestController
public class ApiController {
    private Environment env;

    @GetMapping("/profile")
    public String getProfile () {
        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if(activeProfiles.contains("set1")){
            return "set1";
        } else if(activeProfiles.contains("set2")) {
            return "set2";
        } else {
            return "";
        }
    }
}
