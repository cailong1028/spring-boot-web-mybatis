package com.modules.prime.test.aop.decorator;

public class DecoratorTest {

    public static void main(String[] args) {
        new B(new A()).say();
    }
}

interface Decorator{
    void say();
}

class A implements Decorator{
    public void say(){
        System.out.println("A");
    }
    public void say2(){
        System.out.println("A2");
    }
}
class AA implements Decorator{
    public void say(){
        System.out.println("AA");
    }
    public void say2(){
        System.out.println("AA2");
    }
}
class B implements Decorator{
    Decorator decorator;
    public B(Decorator decorator){
        this.decorator = decorator;
    }
    public void say(){
        beforeSay();
        decorator.say();
        afterSay();
    }
    public void beforeSay(){
        System.out.println("before say");
    }
    public void afterSay(){
        System.out.println("after say");
    }
}