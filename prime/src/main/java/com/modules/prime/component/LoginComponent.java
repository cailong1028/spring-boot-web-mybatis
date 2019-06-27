package com.modules.prime.component;

import com.modules.prime.annotation.Autowired;
import com.modules.prime.annotation.Component;
import com.modules.prime.biz.LoginBiz;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

@Component
public class LoginComponent {

    public static transient Logger logger = LoggerFactory.getLogger(LoginComponent.class);

    @Autowired
    public LoginBiz loginBiz;

    public void getSession(){
        loginBiz.getSession();
    }

}
