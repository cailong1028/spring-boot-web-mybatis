package com.modules.demo2.test.nio.netty.primary.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sync {
    public static void main(String[] args){
        Sync sync = new Sync();
        Sync sync2 = new Sync();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            synchronized (sync){
                try {
                    System.out.println("1-->begin");
                    Thread.currentThread().sleep(100);
                    System.out.println("1-->end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.execute(() -> {
            synchronized (sync){
                try {
                    System.out.println("2-->begin");
                    Thread.currentThread().sleep(100);
                    System.out.println("2-->end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.execute(() -> {
            synchronized (sync2){
                try {
                    System.out.println("3-->begin");
                    sync2.wait();
                    System.out.println("3-->end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.execute(() -> {
            synchronized (sync2){
                try {
                    System.out.println("4-->begin");
                    Thread.currentThread().sleep(100);
                    sync2.notify();
                    System.out.println("4-->end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        while (threadGroup.getParent() != null){
            threadGroup = threadGroup.getParent();
        }
        System.out.println("active thread count --> " + threadGroup.activeCount());
    }
}
