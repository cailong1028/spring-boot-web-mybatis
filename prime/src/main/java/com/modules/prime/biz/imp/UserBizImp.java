package com.modules.prime.biz.imp;

import com.modules.prime.annotation.Autowired;
import com.modules.prime.annotation.Service;
import com.modules.prime.biz.UserBiz;
import com.modules.prime.dao.UserDao;

@Service
public class UserBizImp implements UserBiz {

    @Autowired
    protected UserDao userDao;

    @Override
    public void getUser() {
        userDao.getUser("command cl2");
    }
}
