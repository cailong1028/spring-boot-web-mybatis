package com.modules.demo2.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.Registration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class WebInitializer implements WebApplicationInitializer {

    private static final transient Logger log = LoggerFactory.getLogger(WebInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("utf-8");
//        characterEncodingFilter.setForceEncoding(true);
//
//        Registration.Dynamic registration = servletContext.addFilter("characterEncodingFilter", characterEncodingFilter);
//        ((FilterRegistration.Dynamic) registration).addMappingForUrlPatterns;

        log.info("WebInitializer on startup");

    }
}
