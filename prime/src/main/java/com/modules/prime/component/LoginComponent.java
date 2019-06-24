package com.modules.prime.component;

import com.modules.prime.annotation.Autowired;
import com.modules.prime.annotation.Component;
import com.modules.prime.biz.LoginBiz;
import com.modules.prime.biz.imp.LoginBizImp;

@Component
public class LoginComponent {
    @Autowired
    private LoginBizImp loginBiz;

    public void login(){
        loginBiz.getSession();
    }

    public LoginBiz getLoginBiz() {
        return loginBiz;
    }

    public void setLoginBiz(LoginBiz loginBiz) {
        this.loginBiz = (LoginBizImp) loginBiz;
    }
}
