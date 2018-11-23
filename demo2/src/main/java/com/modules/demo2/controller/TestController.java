package com.modules.demo2.controller;

import com.modules.demo2.mapper.TestMapper;
import com.modules.demo2.mapper.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @RequestMapping("/test")
    Test getUserByName(HttpServletRequest req, HttpServletResponse res){
        Test test = testMapper.getTestByName(req.getParameter("name"));
        return test;
    }
}
