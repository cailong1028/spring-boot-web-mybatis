package com.modules.eurekaclient.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class HiController {

    @RequestMapping("/hi")
    public String sayHi(@RequestParam("name") String name, HttpServletRequest request){
        return "hi: " + name + ", from " + request.getRemoteAddr() + ":" + request.getServerPort();
    }

}
