package com.modules.prime.biz;

import com.modules.prime.dao.DaoHandler;
import com.modules.prime.dao.LoginDao;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

public class LoginBizImp implements LoginBiz {
    private static final transient Logger logger = LoggerFactory.getLogger(LoginBizImp.class);
    @Override
    public void login() throws Exception{
        logger.info("begin login");
        LoginDao loginDao = (LoginDao) Proxy.newProxyInstance(LoginDao.class.getClassLoader(), new Class[]{LoginDao.class}, new DaoHandler(LoginDao.class));;
        List list = loginDao.login("cl");
        logger.info("query list size %d", list.size());
        getInfo();
        //throw new Exception("a");
    }

    @Override
    public void getInfo(){
        logger.info("begin getInfo");
        LoginDao loginDao = (LoginDao) Proxy.newProxyInstance(LoginDao.class.getClassLoader(), new Class[]{LoginDao.class}, new DaoHandler(LoginDao.class));;
        List list = loginDao.login("cl2");
        logger.info("query list size %d", list.size());
        //throw new Exception("a");
    }


    public static void main(String[] args) throws Exception {
        Object o = Proxy.newProxyInstance(LoginBizImp.class.getClassLoader(), LoginBizImp.class.getInterfaces(), new BizHandler(LoginBizImp.class));
        ((LoginBiz)o).login();
    }
}
