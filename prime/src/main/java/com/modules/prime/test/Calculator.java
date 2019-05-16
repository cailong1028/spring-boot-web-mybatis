package com.modules.prime.test;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Calculator{

    //共享变量
    private boolean b1 = false;

    private int add(int a, int b){
        return a + b;
    }

    //volatile测试
    private void t1(){
        if(b1){
            System.out.println("running!");
        }else{
            System.out.println("waiting!");
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Calculator calculator = new Calculator();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    calculator.b1 = !calculator.b1;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }

            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    calculator.t1();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        executorService.shutdown();
    }

    private void t2(){
        HashMap<String, String> m1 = new HashMap<>();
        m1.put("a", "A");
        m1.put("b", "B");

    }

}
