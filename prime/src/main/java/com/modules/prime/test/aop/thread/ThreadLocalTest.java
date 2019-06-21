package com.modules.prime.test.aop.thread;

public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal<Integer> local = new ThreadLocal<>();
        local.set(1);
        System.out.println(local.get());
        new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadLocal<String> local = new ThreadLocal<>();
                local.set("aa");
                System.out.println(local.get());
            }
        }).start();
        A a = new A();
        System.out.println(a.local.get());


    }
}
class A{
    public ThreadLocal<Integer> local = new ThreadLocal<>();
    A(){
        local.set(2);
    }
}