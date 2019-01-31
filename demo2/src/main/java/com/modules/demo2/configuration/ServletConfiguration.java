package com.modules.demo2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

@Configuration
public class ServletConfiguration {

    @Bean
    public HttpRequestHandler defaultServlet(){
        DefaultServletHttpRequestHandler defaultServletHttpRequestHandler = new DefaultServletHttpRequestHandler();
        return defaultServletHttpRequestHandler;
    }


}
