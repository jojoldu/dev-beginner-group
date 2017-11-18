package com.jojoldu.beginner.util;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 18.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Component
public class CryptoComponent {

    public String sha256(String origin){
        return Hashing.sha256()
                .hashString(origin, StandardCharsets.UTF_8)
                .toString();
    }
}
