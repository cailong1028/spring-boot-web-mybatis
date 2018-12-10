package com.modules.demo2.service;

import com.modules.demo2.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public void addTest(){
        //testMapper
    }

}
