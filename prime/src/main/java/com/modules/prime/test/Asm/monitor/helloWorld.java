package com.modules.prime.test.Asm.monitor;

public class helloWorld {

    public void sayHello(){
        System.out.println("helloWorld....");
    }

    public static void main(String[]args){
        asmAopGenerator aag = new asmAopGenerator();
        helloWorld hw = (helloWorld) aag.proxy(helloWorld.class, "sayHello", "it's begin", "it's end");
        hw.sayHello();
    }
}