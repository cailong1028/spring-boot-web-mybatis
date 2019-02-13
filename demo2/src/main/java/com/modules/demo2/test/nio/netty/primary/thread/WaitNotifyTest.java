package com.modules.demo2.test.nio.netty.primary.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaitNotifyTest {
    public static void main(String[] args){
        Object obj = new Object();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() ->{
            synchronized(obj){
                try {
                    System.out.println("wait1...");
                    obj.wait();
                    System.out.println("wait1...done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.execute(() ->{
            synchronized(obj){
                try {
                    System.out.println("wait2...");
                    obj.wait();
                    System.out.println("wait2...done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.execute(() ->{
            synchronized(obj){
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("notify1...");
                obj.notifyAll();
                System.out.println("notify1...done");
            }
        });
    }
}
