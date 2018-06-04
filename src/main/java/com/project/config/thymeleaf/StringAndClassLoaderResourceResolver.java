package com.project.config.thymeleaf;

import liquibase.util.Validate;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.ClassLoaderResourceResolver;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.util.ClassLoaderUtils;

import java.io.InputStream;
@Configuration
public class StringAndClassLoaderResourceResolver implements IResourceResolver {

    public StringAndClassLoaderResourceResolver() {
        super();
    }


    public String getName() {
        return getClass().getName().toUpperCase();
    }


    public InputStream getResourceAsStream(final TemplateProcessingParameters params, final String resourceName) {
        Validate.notNull(resourceName, "Resource name cannot be null");
        if( StringContext.class.isAssignableFrom( params.getContext().getClass() ) ){
            String content = ((StringContext)params.getContext()).getContent();
            return IOUtils.toInputStream(content);
        }
        return ClassLoaderUtils.getClassLoader(ClassLoaderResourceResolver.class).getResourceAsStream(resourceName);
    }


}
