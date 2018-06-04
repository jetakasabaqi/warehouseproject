package com.project.service.util;

import com.project.config.thymeleaf.StringAndClassLoaderResourceResolver;
import com.project.config.thymeleaf.StringContext;
import com.project.domain.User;
import com.sun.javaws.HtmlOptions;
import io.github.jhipster.config.JHipsterProperties;
import javafx.fxml.JavaFXBuilderFactory;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bouncycastle.asn1.cms.MetaData;
import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;
import org.thymeleaf.spring4.SpringTemplateEngine;


import java.io.IOException;
import java.util.Locale;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.w3c.dom.html.HTMLObjectElement;

import javax.swing.text.StringContent;
import javax.swing.text.html.HTML;

import static com.project.security.AuthoritiesConstants.USER;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Component
public class MailServiceTest {

    private final SpringTemplateEngine templateEngine;

    private final JHipsterProperties jHipsterProperties;

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private String template = "<!DOCTYPE html>\n" +
        "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
        "    <head>\n" +
        "        <title th:text=\"#{email.activation.title}\">JHipster activation</title>\n" +
        "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
        "        <link rel=\"shortcut icon\" th:href=\"@{|${baseUrl}/favicon.ico|}\" />\n" +
        "    </head>\n" +
        "    <body>\n" +
        "        <p th:text=\"#{email.activation.greeting(${user.login})}\">\n" +
        "            Dear\n" +
        "        </p>\n" +
        "        <p th:text=\"#{email.activation.text1}\">\n" +
        "            Your JHipster account has been created, please click on the URL below to activate it:\n" +
        "        </p>\n" +
        "        <p>\n" +
        "            <a th:href=\"@{|${baseUrl}/#/activate?key=${user.activationKey}|}\"\n" +
        "               th:text=\"@{|${baseUrl}/#/activate?key=${user.activationKey}|}\">Activation Link</a>\n" +
        "        </p>\n" +
        "        <p>\n" +
        "            <span th:text=\"#{email.activation.text2}\">Regards, </span>\n" +
        "            <br/>\n" +
        "            <em th:text=\"#{email.signature}\">JHipster.</em>\n" +
        "        </p>\n" +
        "    </body>\n" +
        "</html>\n";

    public MailServiceTest(SpringTemplateEngine templateEngine, JHipsterProperties jHipsterProperties) {
        this.templateEngine = templateEngine;
        this.jHipsterProperties = jHipsterProperties;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }


    public void sendTemplate(User user) throws IOException {
        Locale locale = Locale.forLanguageTag(user.getLangKey());

        StringContext context=new StringContext(template,locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(template,context);

        System.out.print(content);
    }
    @Override
    public String toString(){

        return new ToStringBuilder(this).append("test.html").append(template).toString();
    }

}







