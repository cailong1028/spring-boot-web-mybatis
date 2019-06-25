package com.modules.prime;

import com.modules.prime.biz.LoginBiz;
import com.modules.prime.component.LoginComponent;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

public class App implements Launcher {

    private String test;

    private static final transient Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        LauncherHandler.handle(App.class);
    }

    @Override
    public void run() {
        for(Object obj: Context.services.values()){
            logger.info("context one service: %s", obj instanceof LoginBiz);
        }
        for(Object obj: Context.components.values()){
            logger.info("context one component: %s", obj instanceof LoginComponent);
        }
//        LoginComponent loginComponent = new LoginComponent();
//        loginComponent.login();
        ((LoginComponent)Context.getComponent(LoginComponent.class)).login();
    }
}
