package com.modules.prime.biz.imp;

import com.modules.prime.annotation.Autowired;
import com.modules.prime.annotation.Service;
import com.modules.prime.biz.LoginBiz;
import com.modules.prime.dao.LoginDao;
import com.modules.prime.dao.UserDao;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

import java.util.List;

@Service
public class LoginBizImp implements LoginBiz {
    public static transient Logger logger = LoggerFactory.getLogger(LoginBizImp.class);

    @Autowired
    public LoginDao loginDao;
    @Autowired
    public UserDao userDao;
//    public LoginDao loginDao = (LoginDao) Context.getDao(LoginDao.class);


    @Override
    public void login(){
        //LoginDao loginDao = (LoginDao) Proxy.newProxyInstance(LoginDao.class.getClassLoader(), new Class[]{LoginDao.class}, new DaoHandler(LoginDao.class));;
        List list = loginDao.login("cl");
//        logger.info("query list size %d", list.size());
    }

    @Override
    public void getInfo(){
        List list = loginDao.login("cl2");
    }

    @Override
    public void getSession() {
        login();
        getUser();
    }

    @Override
    public void getUser() {
        userDao.getUser("command cl1");
    }
}

