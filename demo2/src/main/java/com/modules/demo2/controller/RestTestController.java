package com.modules.demo2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTestController {

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String test(){
        return "i am index path2";
    }

}
