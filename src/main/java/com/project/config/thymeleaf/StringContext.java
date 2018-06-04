package com.project.config.thymeleaf;

import org.springframework.context.annotation.Configuration;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

public class StringContext extends Context{

    private final String content;

    public StringContext(String content) {


        this.content = content;
    }

    public StringContext(String content, Locale locale) {
        super(locale);
        this.content = content;
    }

    public StringContext(String content, Locale locale, Map<String, ?> variables) {
        super(locale, variables);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
