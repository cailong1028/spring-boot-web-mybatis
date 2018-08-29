package com.cl.springbootwebmybatis.primary;

import org.springframework.stereotype.Component;

@Component
public class Component2 implements ComponentInterface {

    private String name = "component2";

    public void sayName(){
        System.out.println(this.name);
    }

}
