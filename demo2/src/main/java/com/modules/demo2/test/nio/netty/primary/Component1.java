package com.modules.demo2.test.nio.netty.primary;

import org.springframework.stereotype.Component;

@Component
public class Component1 implements ComponentInterface {

    private String name = "component1";

    public Component1(){

    }

    public void sayName(){
        System.out.println(this.name);
    }

}
