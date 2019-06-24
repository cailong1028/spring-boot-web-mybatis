package com.modules.prime.biz.imp;

import com.modules.prime.annotation.Service;
import com.modules.prime.biz.BizHandler;
import com.modules.prime.biz.LoginBiz;
import com.modules.prime.dao.DaoHandler;
import com.modules.prime.dao.LoginDao;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.List;

@Service
public class LoginBizImp implements LoginBiz {
    private static final transient Logger logger = LoggerFactory.getLogger(LoginBizImp.class);
    @Override
    public void login(){
//        logger.info("begin login");
        LoginDao loginDao = (LoginDao) Proxy.newProxyInstance(LoginDao.class.getClassLoader(), new Class[]{LoginDao.class}, new DaoHandler(LoginDao.class));;
        List list = loginDao.login("cl");
//        logger.info("query list size %d", list.size());
//        getInfo();
//        Object o = Proxy.newProxyInstance(LoginBizImp.class.getClassLoader(), LoginBizImp.class.getInterfaces(), new BizHandler(LoginBizImp.class));
//        ((LoginBiz)o).getInfo();
    }

    @Override
    public void getInfo(){
        logger.info("begin getInfo");
        LoginDao loginDao = (LoginDao) Proxy.newProxyInstance(LoginDao.class.getClassLoader(), new Class[]{LoginDao.class}, new DaoHandler(LoginDao.class));;
        List list = loginDao.login("cl2");
//        logger.info("query list size %d", list.size());
        //throw new Exception("a");
    }

    @Override
    public void getSession() {
//        Object o = Proxy.newProxyInstance(LoginBizImp.class.getClassLoader(), LoginBizImp.class.getInterfaces(), new BizHandler(LoginBizImp.class));
//        ((LoginBiz)o).login();
//        ((LoginBiz)o).getInfo();
        login();
        getInfo();
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Object o = Proxy.newProxyInstance(LoginBizImp.class.getClassLoader(), LoginBizImp.class.getInterfaces(), new BizHandler(LoginBizImp.class));
                    ((LoginBiz)o).getSession();
                }
            }).start();
        }
    }
}

