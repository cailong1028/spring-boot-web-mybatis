package com.modules.demo2.test.nio.netty.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Bean1 implements BeanInterface {

    private String beanName;

    public Bean1(){

    }

    @Override
    public void sayBean() {
        System.out.println(this.beanName);
    }

    @Override
    @Bean
    public BeanInterface getBean() {
        return new Bean1();
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
