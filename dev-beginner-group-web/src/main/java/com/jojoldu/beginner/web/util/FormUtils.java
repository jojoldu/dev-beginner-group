package com.jojoldu.beginner.web.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 20.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public abstract class FormUtils {

    private static String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public static Boolean isEmail(String email){
        if(StringUtils.isEmpty(email)){
            return false;
        }

        return Pattern.matches(EMAIL_PATTERN, email);
    }
}
