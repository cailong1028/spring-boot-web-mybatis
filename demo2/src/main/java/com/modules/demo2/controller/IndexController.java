package com.modules.demo2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request, HttpServletResponse response){
        return "template";
    }

    @RequestMapping(value = "/temp2")
    public String index2(HttpServletRequest request, HttpServletResponse response){
        return "template2";
    }
}
