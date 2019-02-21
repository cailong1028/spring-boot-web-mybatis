package com.modules.demo2.controller;

import com.modules.demo2.entity.TUserEntity;
import com.modules.demo2.service.TUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class TUserController {

    private static final transient Logger log = LoggerFactory.getLogger(TUserController.class);

    @Autowired
    private TUserService tUserService;

    @RequestMapping(value = "/getByUsername")
    public TUserEntity getByUsername(HttpServletRequest request, HttpServletResponse response){
        return tUserService.getByUsername(request.getParameter("username"));
    }

    @RequestMapping(value = "/login")
    public TUserEntity login(HttpServletRequest request, HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken upToken = new UsernamePasswordToken(request.getParameter("username"), request.getParameter("password"));
        try{
            subject.login(upToken);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
        return tUserService.getByUsername(request.getParameter("username"));
    }

    @RequestMapping(value = "/batchInsert", method = RequestMethod.POST)
    public int batchInsert(@RequestParam("names") String names, @RequestParam("passwds") String passwds){

        String[] nameArr = names.split(",");
        String[] passwdArr = passwds.split(",");
        List<TUserEntity> tUserList = new ArrayList<>();
        for(int i = 0; i < nameArr.length; i++){
            TUserEntity tUserEntity = new TUserEntity();
            tUserEntity.setUserName(nameArr[i]);
            tUserEntity.setPassword(passwdArr[i]);
            tUserList.add(tUserEntity);
        }
        int insertCount = tUserService.batchInsert(tUserList);
        return insertCount;
    }
}
