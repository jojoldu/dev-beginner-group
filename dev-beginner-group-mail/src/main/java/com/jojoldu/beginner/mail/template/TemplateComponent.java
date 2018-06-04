package com.jojoldu.beginner.mail.template;

import com.github.jknack.handlebars.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 27.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
public class TemplateComponent {

    private final HandlebarsFactory handlebarsFactory;

    public String template(String templateName, Object model){
        Template template = handlebarsFactory.get(templateName);
        try {
            return template.apply(model);
        } catch (IOException e) {
            log.error(String.format("Handlebars Template Exception: templateName: %s", templateName), e);
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
