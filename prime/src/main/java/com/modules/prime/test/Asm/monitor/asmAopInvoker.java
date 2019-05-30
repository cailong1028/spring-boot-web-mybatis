package com.modules.prime.test.Asm.monitor;

public class asmAopInvoker {

    public static void methodEnd(String evtID){
        System.out.println(evtID);
    }

    public static void methodStart(String evtID){
        System.out.println(evtID);
    }

    public void sayHello(){
        System.out.println("doing");
    }
}