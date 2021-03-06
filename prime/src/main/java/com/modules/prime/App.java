package com.modules.prime;

import com.modules.prime.biz.LoginBiz;
import com.modules.prime.component.LoginComponent;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

public class App implements Launcher {

    private static final transient Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        LauncherHandler.handle(App.class);
    }

    @Override
    public void run() {
        for(Object obj: Context.services.values()){
            logger.info("context one service 服务: %s", obj instanceof LoginBiz);
        }
        for(Object obj: Context.components.values()){
            logger.info("context one component 组件: %s", obj instanceof LoginComponent);
        }
//        LoginComponent loginComponent = new LoginComponent();
//        loginComponent.login();
        for(int i = 0; i < 2; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ((LoginComponent)Context.getComponent(LoginComponent.class)).getSession();
                }
            }).start();

        }

    }

    @Override
    public void beforeRun() {

    }

    @Override
    public void afterRun() {

    }
}
