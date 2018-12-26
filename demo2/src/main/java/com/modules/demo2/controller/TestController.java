package com.modules.demo2.controller;

import com.modules.demo2.dao.TestDao;
import com.modules.demo2.entity.TestEntity;
import com.modules.demo2.mapper.TestMapper;
import com.modules.demo2.mapper.domain.Test;
import com.modules.demo2.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    List<Test> getUserByName(HttpServletRequest req, HttpServletResponse res){
        List<Test> testList = testService.getByName(req.getParameter("name"));
        return testList;
    }

    @RequestMapping("/test2")
    TestEntity getUserByName2(HttpServletRequest req, HttpServletResponse res){
        TestEntity testEntity = testService.get(req.getParameter("id"));
        return testEntity;
    }

    @RequestMapping("/test3")
    List<TestEntity> findByName(String name){
        List<TestEntity> testList = testService.findByName(name);
        return testList;
    }
}
