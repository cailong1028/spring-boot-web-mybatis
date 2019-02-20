package com.modules.demo2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/form")
public class FormController {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Object formTest(@RequestBody MultiValueMap<String, String> body){

        User user = new User();
        user.setName(body.toSingleValueMap().get("name"));

        return user;
    }

}

