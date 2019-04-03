package com.modules.demo2.controller;

import com.modules.demo2.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/api/dbtrans")
public class DBTransController {

    @Autowired
    private TestService testService;

    @RequestMapping("/read")
    public String transRead(HttpServletRequest request){
        System.out.println("request.getRequestURI-->"+request.getRequestURI());
        testService.ThreadAndTrans();
        return null;
    }

}

