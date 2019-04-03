package com.modules.demo2.service;

import com.modules.demo2.dao.TestDao;
import com.modules.demo2.entity.TestEntity;
import com.modules.demo2.mapper.TestMapper;
import com.modules.demo2.mapper.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class TestService {

    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TestDao testDao;

    public List<Test> getByName(String name){
        List<Test> testList = testMapper.getTestByName(name);
        return testList;
    }

    public TestEntity get(String id){
        TestEntity testEntity = new TestEntity();
        testEntity = testDao.get(id);
        return testEntity;
    }

    public void update(TestEntity test){
        testDao.update(test);
    }

    public List<TestEntity> findByName(String name){
        return testDao.findByName(name);
    }

    public void ThreadAndTrans(){
        ExecutorService executor = Executors.newCachedThreadPool();

        CountDownLatch latch = new CountDownLatch(3);

        Worker w1 = new Worker(latch,"张三");
        Worker w2 = new Worker(latch,"李四");
        Worker w3 = new Worker(latch,"王二");

        Boss boss = new Boss(latch);

        executor.execute(w3);
        executor.execute(w2);
        executor.execute(w1);
        executor.execute(boss);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(11000);
                    ThreadAndTrans2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        executor.shutdown();
    }

    //另外一个service方法和多线程穿插调用多个service方法测试事务
    private void ThreadAndTrans2(){
        TestEntity test = get("1");
        System.out.println("在ThreadAndTrans2中获取name: " + test.getName());
    }

    class Worker implements Runnable{

        private CountDownLatch downLatch;
        private String name;

        public Worker(CountDownLatch downLatch, String name){
            this.downLatch = downLatch;
            this.name = name;
        }

        public void run() {
            this.doWork();
            try{
                TimeUnit.SECONDS.sleep(new Random().nextInt(10));
            }catch(InterruptedException ie){
            }
            if(this.name == "张三") return;
            System.out.println(this.name + "活干完了！");
            TestEntity test = new TestEntity();
            test.setId(1);
            String newName = "zx"+(new Date().getTime());
            test.setName(newName);
            System.out.println("newName is -->"+newName);
            testDao.update(test);
            TestEntity test2 = testDao.get("1");
            System.out.println("刚设置完之后获取 name is -->"+test2.getName());
            this.downLatch.countDown();
        }

        private void doWork(){
            System.out.println(this.name + "正在干活!");
        }

    }

    class Boss implements Runnable {

        private CountDownLatch downLatch;

        public Boss(CountDownLatch downLatch){
            this.downLatch = downLatch;
        }

        public void run() {
            System.out.println("老板正在等所有的工人干完活......");
            try {
                this.downLatch.await(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
            }
            System.out.println("工人活都干完了，老板开始检查了！");
            TestEntity test = testDao.get("1");
            System.out.println("完成时的名字是 is -->"+test.getName());
        }

    }
}

