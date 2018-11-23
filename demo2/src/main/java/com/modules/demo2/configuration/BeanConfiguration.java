package com.modules.demo2.configuration;

import com.modules.demo2.bean.UserBean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserBean1 userBean1(){
        return new UserBean1();
    }

}
