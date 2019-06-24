package com.modules.prime.test.aop.thread;

//值传递测试，结论是：没有应用传递
public class Propagation {

    AB ab = null;
    C c = new C();
    Propagation(){
        ab = c.a;
    }

    public static void main(String[] args) {
        Propagation p = new Propagation();
        p.ab = null;
        System.out.println(p.c.a == null);
        System.out.println(p.ab == null);
        System.out.println(String.format("%b", true));
    }
}

class AB{
    AB(){

    }
}

class C{
    AB a = new AB();
    C(){

    }
}


