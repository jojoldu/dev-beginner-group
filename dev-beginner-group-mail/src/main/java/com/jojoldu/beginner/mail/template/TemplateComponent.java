package com.jojoldu.beginner.mail.template;

import com.github.jknack.handlebars.Template;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 27.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@AllArgsConstructor
public class TemplateComponent {

    private HandlebarsFactory handlebarsFactory;

    public String template(String templateName, Map<String, Object> model){
        final Template template = handlebarsFactory.get(templateName);
        try {
            return template.apply(model);
        } catch (IOException e) {
            log.error(String.format("Handlebars Template Exception: templateName: %s", templateName));
            return "";
        }
    }
}
