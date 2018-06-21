package com.cl.springbootwebmybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @RequestMapping(value="/")
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res){
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "index view here");
        mv.setViewName("index model view");
        return mv;
    }
}
