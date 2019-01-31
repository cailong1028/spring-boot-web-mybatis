package com.modules.demo2.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "characterEncodingUTF8Filter", urlPatterns = "/*")
public class CharacterEncodingUTF8Filter implements Filter {

    private static final transient Logger log = LoggerFactory.getLogger(CharacterEncodingUTF8Filter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("characterEncodingUTF8Filter init done!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        log.info("characterEncodingUTF8Filter 拦截地址 {}", httpServletRequest.getRequestURI());
        //验证shiro session
//        if(!requestURI.contains("info")){
//            servletRequest.getRequestDispatcher("/failed").forward(servletRequest, servletResponse);
//        }else{
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("characterEncodingUTF8Filter destroy done!");
    }
}
