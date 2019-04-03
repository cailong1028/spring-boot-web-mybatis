package com.modules.demo2.test.nio.netty.primary;

import com.modules.demo2.test.nio.netty.primary.rabbitmq.Run;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class Test190403 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(1 & 2);
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        System.out.println(linkedList.poll());
        Test test = new Test();
        new Thread(new Runnable(){
            @Override
            public void run() {
                test.wit();
            }
        }).start();
        new Thread(new Runnable(){
            @Override
            public void run() {
                test.not();
            }
        }).start();

        System.out.println(String.format("%s", "http:"));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 1);

        System.out.println(cal.getTime());
    }
}

class Test{
    boolean b;
    Test() {
        this.b = true;
    }

    void wit(){
        while(b){
            synchronized(this){
                System.out.println("before notify");
                this.notify();
                System.out.println("after notify");
                try {
                    this.wait(3000);
                    System.out.println("wait done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void not(){
        synchronized(this){
            this.b = false;
            this.notify();
        }
    }
}
