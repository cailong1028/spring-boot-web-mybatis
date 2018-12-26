package com.modules.demo2.service;

import com.modules.demo2.dao.TestDao;
import com.modules.demo2.entity.TestEntity;
import com.modules.demo2.mapper.TestMapper;
import com.modules.demo2.mapper.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TestDao testDao;

    public List<Test> getByName(String name){
        List<Test> testList = testMapper.getTestByName(name);
        return testList;
    }

    public TestEntity get(String id){
        TestEntity testEntity = new TestEntity();
        testEntity = testDao.get(id);
        return testEntity;
    }

    public List<TestEntity> findByName(String name){
        return testDao.findByName(name);
    }
}
