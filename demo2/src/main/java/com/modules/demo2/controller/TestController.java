package com.modules.demo2.controller;

import com.alibaba.fastjson.JSON;
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
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
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
    User getUsers(@RequestParam(value = "name") String name,
                    @RequestParam(value = "time", required = false) Date time){
        User user = new User();
        user.setName(name);
        user.setTime(time);
        return user;
    }

    @RequestMapping("/requestBodyTest2")
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
    User getUser(HttpServletRequest request){
        User user = new User();
        user.setName(request.getParameter("name"));
        return user;
    }

    @RequestMapping("/requestBodyTest4/{name}")
    User getUser(@PathVariable("name") String name, HttpServletRequest request){
        User user = new User();
        user.setName(name);
        return user;
    }

    @RequestMapping("/ajax/data/test")
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

    @RequestMapping("/fastJsonTest")
    List<UserFiles> fastJsonTest(@RequestParam("userListString") String userListString){

        List<UserFiles> userFilesList1 = JSON.parseArray(userListString, UserFiles.class);

        List<UserFiles> userFilesList2 = new ArrayList<>();
        UserFiles userFiles2 = new UserFiles();
        List<File> files2 = new ArrayList<>();
        File file = new File();
        file.setName("fileFromServer");
        files2.add(file);
        userFiles2.setFiles(files2);
        userFilesList2.add(userFiles2);

        userFilesList1.addAll(userFilesList2);

        return userFilesList1;
    }

    @RequestMapping("/session")
    MySession session1(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("name", "cl");
        MySession mySession = new MySession();
        mySession.setName("cl");
        mySession.setId(session.getId());
        return mySession;
    }

    @RequestMapping("/session2")
    MySession session2(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        MySession mySession = new MySession();
        mySession.setId(session.getId());
        mySession.setName((String)session.getAttribute("name"));
        return mySession;
    }
}

class MySession{
    String name;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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