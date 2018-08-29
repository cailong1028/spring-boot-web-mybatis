package com.cl.springbootwebmybatis.config;

import com.cl.springbootwebmybatis.bean.UserBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TestConfiguration {

    public TestConfiguration(){
        System.out.println("test configuration init");
    }

    @Bean(name="userBean", initMethod = "start", destroyMethod = "destroy")
//    @Scope("prototype")
    public UserBean userBean(){
        return new UserBean("cl", "pwd");
    }

}
