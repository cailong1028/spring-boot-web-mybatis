package com.modules.demo2.config;

import com.modules.demo2.bean.Cat;
import com.modules.demo2.bean.Dog;
import org.springframework.context.annotation.Bean;

public class MyConfig {

    @Bean
    public Cat catBean(){
        return new Cat();
    }

    @Bean
    public Dog dogBean(){
        return new Dog();
    }
}
