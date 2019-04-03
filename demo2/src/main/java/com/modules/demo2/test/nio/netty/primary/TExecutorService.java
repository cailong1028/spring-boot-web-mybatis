package com.modules.demo2.test.nio.netty.primary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TExecutorService {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Task().test();
        new MyJob("aaa").test();
    }

}

class Task implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        Thread.sleep(3000);
        int sum = 0;
        for(int i=0;i<100;i++)
            sum += i;
        return sum;
    }

    void test() throws ExecutionException, InterruptedException {
        //FutureTask<Double> futureTask = new FutureTask<>(new Task());
        ExecutorService threadPool = Executors.newFixedThreadPool(5);;
        Future<Integer> result = threadPool.submit(new Task());
        System.out.println("task运行结果"+result.get());
        threadPool.shutdown();
        System.out.println("main done");
    }
}

class MyJob implements Callable<Boolean> {
    private String t;
    public MyJob(String temp){
        this.t= temp;
    }

    public Boolean call() throws InterruptedException {
//        if (Thread.interrupted()){ //很重要
//            return false;
//        }
//        for(int i=0;i<999999999;i++){
//            if(i==999999997){
//                System.out.println(t);
//            }
//        }
        Thread.sleep(2000);
        System.out.println("继续执行..........");
        return true;
    }
    void test() throws InterruptedException {
        int timeout = 2; //秒.
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Boolean result = false;
        int cnt = 3;
        CountDownLatch latch = new CountDownLatch(cnt);
        executor.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                try {
                    System.out.println("开始等待：" + ((ThreadPoolExecutor)executor).getActiveCount());
                    latch.await();
                    System.out.println("结束等待：" + ((ThreadPoolExecutor)executor).getActiveCount());
                    executor.shutdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ((ThreadPoolExecutor)executor).getActiveCount();
            }
        });

        for(int i = 0; i< cnt; i++){
            Future<Boolean> future = executor.submit(new MyJob("请求参数"));// 将任务提交到线程池中
            System.out.println("活动线程数：" + ((ThreadPoolExecutor)executor).getActiveCount());
            try {
                result = future.get(timeout*100, TimeUnit.MILLISECONDS);// 设定在200毫秒的时间内完成
                System.out.println("result-->"+result);
                latch.countDown();
            } catch (InterruptedException e) {
                System.out.println("线程中断出错。");
                future.cancel(true);// 中断执行此任务的线程
            } catch (ExecutionException e) {
                System.out.println("线程服务出错。");
                future.cancel(true);// 中断执行此任务的线程
            } catch (TimeoutException e) {// 超时异常
                System.out.println("超时。");
                future.cancel(true);// 中断执行此任务的线程
                System.out.println("--活动线程数：" + ((ThreadPoolExecutor)executor).getActiveCount());
            }finally{
                System.out.println("线程服务关闭。");
                latch.countDown();
            }
        }

    }
}


