package com.project.config;

import com.project.config.thymeleaf.StringAndClassLoaderResourceResolver;
import javafx.application.Application;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(ThymeleafConfiguration.class);

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5 emails")
    public TemplateResolver emailTemplateResolver() {
      ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
       emailTemplateResolver.setPrefix("util/");
        emailTemplateResolver.setPrefix("service/util");
       emailTemplateResolver.setSuffix(".java");
        emailTemplateResolver.setTemplateMode("HTML5");
        emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        emailTemplateResolver.setOrder(1);
        return emailTemplateResolver;
    }

    @Bean
    @Description("Method to process String")
    public TemplateResolver stringResolver()
    {
       TemplateResolver resolver=new TemplateResolver();
       resolver.setResourceResolver(new StringAndClassLoaderResourceResolver());
        resolver.setPrefix("mail/"); // src/test/resources/mail
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding(CharEncoding.UTF_8);
        resolver.setOrder(1);
        return resolver;
    }
}
