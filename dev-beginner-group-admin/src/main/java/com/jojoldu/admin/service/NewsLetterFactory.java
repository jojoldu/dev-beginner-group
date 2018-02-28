package com.jojoldu.admin.service;

import com.jojoldu.admin.dto.mail.MailContentGroupDto;
import com.jojoldu.beginner.mail.template.TemplateComponent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by jojoldu@gmail.com on 2018. 2. 28.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Component
@AllArgsConstructor
public class NewsLetterFactory {

    private TemplateComponent templateComponent;

    public String createContent(MailContentGroupDto contentGroupDto){
        return templateComponent.template("newsletter", contentGroupDto);
    }
}
