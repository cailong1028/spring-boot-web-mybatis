package com.modules.prime.component;

import com.modules.prime.annotation.Autowired;
import com.modules.prime.annotation.Component;
import com.modules.prime.biz.LoginBiz;

@Component
public class LoginComponent {
    @Autowired
    private LoginBiz loginBiz;

    public void login(){
        loginBiz.getSession();
        System.out.println("111-====sdfs=df=sd=f=sdf=s=df=s=df=sd=f=");
    }

}
