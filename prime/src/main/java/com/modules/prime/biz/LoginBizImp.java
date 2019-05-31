package com.modules.prime.biz;


import com.modules.prime.annotation.BoSession;
import com.modules.prime.annotation.BoSessionIsolation;

@BoSession(BoSessionIsolation.TRANSACTION_REPEATABLE_READ)
public class LoginBizImp implements LoginBiz{
    @Override
    public void login(String name, String pwd) {
        //
    }
}
