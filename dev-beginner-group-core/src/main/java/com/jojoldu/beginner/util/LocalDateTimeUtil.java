package com.jojoldu.beginner.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class LocalDateTimeUtil {

    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String toStringDate(LocalDateTime localDateTime){
        if(localDateTime == null){
            return "";
        }

        return localDateTime.format(DATE_TIME_FORMATTER);
    }
}
