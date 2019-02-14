package com.modules.demo2.controller;

import com.modules.demo2.dao.TestDao;
import com.modules.demo2.entity.TUserEntity;
import com.modules.demo2.entity.TestEntity;
import com.modules.demo2.mapper.TestMapper;
import com.modules.demo2.mapper.domain.Test;
import com.modules.demo2.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("test")
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

    @RequestMapping("/requestBodyTest")
    @ResponseBody
    User getUsers(@RequestParam(value = "name", required = false) String name,
                    @RequestParam(value = "time", required = false) Date time){
        //前端请求的格式
        // time: Thu Aug 13 2015 19:45:20 GMT+0800, name: "xxx"
        User user = new User();
        user.setName(name);
        user.setTime(time);
        return user;
    }

    @RequestMapping("/requestBodyTest2")
    @ResponseBody //返回个前端的是一个JSON对象
    List<User> getUsers(@RequestBody User user){
        //前端请求的格式是一个JSON对象的字符串，如下：'{"name":"zx", "time":"2019-02-14T02:53:00.867Z"}'
        List<User> users = new ArrayList<>();
        users.add(user);
        return users;
    }

    @RequestMapping("/requestBodyTest3")
    @ResponseBody
    User getUser(HttpServletRequest request){
        User user = new User();
        user.setName(request.getParameter("name"));
        return user;
    }

    @RequestMapping("/requestBodyTest4/{name}")
    @ResponseBody
    User getUser(@PathVariable("name") String name, HttpServletRequest request){
        User user = new User();
        user.setName(name);
        return user;
    }
}

class User {
    private String name;
    private Date time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}