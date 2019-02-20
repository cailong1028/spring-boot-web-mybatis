package com.modules.demo2.service;

import com.modules.demo2.dao.TUserDao;
import com.modules.demo2.entity.TUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TUserService {

    @Autowired
    private TUserDao tUserDao;

    public TUserEntity getByUsername(String username){
        return tUserDao.getByUsername(username);
    }

    public void batchInsert(List<TUserEntity> tUserList){
        tUserDao.batchInsert(tUserList);
    }
}
