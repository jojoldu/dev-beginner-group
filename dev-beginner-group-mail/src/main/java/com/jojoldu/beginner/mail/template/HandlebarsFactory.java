package com.jojoldu.beginner.mail.template;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dwlee on 2017. 6. 13..
 */

@Slf4j
public class HandlebarsFactory {

    private Handlebars handlebars;
    private Map<String, Template> factory = new LinkedHashMap<>();

    private static final String DEFAULT_PREFIX = "/templates/mail";
    private static final String DEFAULT_SUFFIX = ".hbs";

    public HandlebarsFactory() {
        init(DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    public HandlebarsFactory(String prefix, String suffix) {
        init(prefix, suffix);
    }

    private void init(String prefix, String suffix) {
        TemplateLoader templateLoader = new ClassPathTemplateLoader();
        templateLoader.setPrefix(prefix);
        templateLoader.setSuffix(suffix);
        handlebars = new Handlebars(templateLoader);
    }

    public void put(String fileName) throws IOException {
        try {
            Template template = handlebars.compile(fileName);
            factory.put(fileName, template);
        } catch (IOException e) {
            log.error("NOT Found File: {}", fileName);
            throw e;
        }
    }

    public Template get(String key){
        return factory.get(key);
    }
}
