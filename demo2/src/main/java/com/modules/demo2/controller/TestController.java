package com.modules.demo2.controller;

import com.modules.demo2.dao.TestDao;
import com.modules.demo2.entity.TUserEntity;
import com.modules.demo2.entity.TestEntity;
import com.modules.demo2.mapper.TestMapper;
import com.modules.demo2.mapper.domain.Test;
import com.modules.demo2.service.TestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
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
        //BeanUtils.copyProperties测试
        MultiEntity2 multiEntity2 = new MultiEntity2();
        BeanUtils.copyProperties(user, multiEntity2);
        System.out.println(multiEntity2.time);
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

    @RequestMapping("/ajax/data/test")
    @ResponseBody
    MultiEntity ajaxData(@RequestBody UserFiles body, HttpServletRequest request){
        UserFiles userFiles = new UserFiles();
        userFiles.setName(body.getName());
        userFiles.setFiles(body.getFiles());
        MultiEntity multiEntity = new MultiEntity();
        BeanUtils.copyProperties(userFiles, multiEntity);
        //System.out.println(multiEntity.getFiles().get(0).getName());
        //MultiValueMap
        return multiEntity;
    }
}

class UserFiles{
    private String name;
    private List<File> files;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}

class File{
    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class User{
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

class Inner{
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class MultiEntity{

    String name;
    String age;
    List<Inner> files;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Inner> getFiles() {
        return files;
    }

    public void setFiles(List<Inner> files) {
        this.files = files;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

class MultiEntity2{
    String name;
    Date time;
    String my;

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

    public String getMy() {
        return my;
    }

    public void setMy(String my) {
        this.my = my;
    }
}