package com.modules.demo2.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "testFilter", urlPatterns = "/*")
public class TestFilter implements Filter {

    private static final transient Logger log = LoggerFactory.getLogger(TestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("testFilter init done!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        log.info("拦截地址 {}", httpServletRequest.getRequestURI());
        //验证shiro session
//        if(!requestURI.contains("info")){
//            servletRequest.getRequestDispatcher("/failed").forward(servletRequest, servletResponse);
//        }else{
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("testFilter destroy done!");
    }
}
