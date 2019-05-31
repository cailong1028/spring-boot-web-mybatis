package com.modules.prime.test.aop.proxy;

public class HelloWorldImp implements HelloWorld {
    @Override
    public void say() {
        System.out.println("hello");
    }

    @Override
    public void work() {
        System.out.println("work");
    }
}
