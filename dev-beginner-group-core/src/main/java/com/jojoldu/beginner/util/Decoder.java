package com.jojoldu.beginner.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by jojoldu@gmail.com on 2017. 12. 22.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
public abstract class Decoder {
   public static String decode(String origin){
        try {
            return URLDecoder.decode(origin, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            final String message = "Decode Exception, origin: " + origin;
            log.error(message, e);
            throw new IllegalArgumentException(message);
        }
    }
}
