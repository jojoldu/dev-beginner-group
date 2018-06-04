package com.jojoldu.beginner.admin.service;

import com.jojoldu.beginner.admin.dto.mail.MailContentGroupDto;
import com.jojoldu.beginner.mail.template.TemplateComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by jojoldu@gmail.com on 2018. 2. 28.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Component
@RequiredArgsConstructor
public class NewsLetterFactory {

    private final TemplateComponent templateComponent;

    public String createContent(MailContentGroupDto contentGroupDto){
        return templateComponent.template("newsletter", contentGroupDto);
    }
}
