package com.jojoldu.beginner.admin.sns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by jojoldu@gmail.com on 17/06/2018
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@AllArgsConstructor
public enum Sns {

    TELEGRAM("Telegram", "telegram"),
    FACEBOOK("Facebook", "facebook"),
    BLOG("Blog", "blog");

    private String prefix;
    private String tag;

    public String createTitle(String title){
        return "["+prefix+"] "+title;
    }

    public String createUrl(String url){
        return url+"/#ref="+tag;
    }

}
