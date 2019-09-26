package com.cl.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class RootController {

    @RequestMapping("")
    public String index(Model model){
        model.addAttribute("msg", "index page");
        return "index";
    }

}
