package com.modules.demo2.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/producesConsumes")
public class ProducesConsumesController {

    @ResponseBody
    @RequestMapping(value = "/produces", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Object producesTest(@RequestParam("name") String name){

        User user = new User();
        user.setName(name);

        return user;
    }

}
