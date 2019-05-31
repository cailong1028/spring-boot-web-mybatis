package com.modules.prime.biz;

public class LoginBizWrapper {

    public static LoginBiz getWrapper(LoginBiz loginBiz){
        LoginBizHandler loginBizHandler = new LoginBizHandler(loginBiz);
        LoginBiz proxy = (LoginBiz) java.lang.reflect.Proxy.newProxyInstance(
                loginBiz.getClass().getClassLoader(),
                loginBiz.getClass().getInterfaces(),
                loginBizHandler);
        return proxy;
    }

}
