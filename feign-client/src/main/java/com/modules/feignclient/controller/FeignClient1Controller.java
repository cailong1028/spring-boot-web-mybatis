package com.modules.feignclient.controller;

import com.modules.feignclient.biz.FeignClient1Biz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FeignClient1Controller {

    @Autowired
    private FeignClient1Biz feignClient1Biz;

    @RequestMapping("/hi")
    public String getFeign1(@RequestParam("name") String name){
        return feignClient1Biz.getHi(name);
    }

}
