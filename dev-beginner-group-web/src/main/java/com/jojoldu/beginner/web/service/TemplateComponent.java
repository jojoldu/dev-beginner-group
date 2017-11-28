package com.jojoldu.beginner.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Locale;
import java.util.Map;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 27.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Component
public class TemplateComponent {

    @Autowired
    private SpringTemplateEngine templateEngine;

    public String template(String templateName, Map<String, Object> model){
        Context ctx = new Context(Locale.KOREA);

        model.forEach(ctx::setVariable);

        return this.templateEngine.process( templateName, ctx);
    }
}
