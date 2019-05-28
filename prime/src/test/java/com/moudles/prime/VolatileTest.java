package com.moudles.prime;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class VolatileTest {

    Logger logger = LoggerFactory.getLogger(VolatileTest.class);

    int nubmer = 0;
    volatile Integer volatile_number = 0;
    Integer not_volatile_number = 0;
    AtomicInteger atom_number = new AtomicInteger(0);
    private final int loop = 100;
    private final int times = 10000;

    @Before
    public void before(){
        //LoggerFactory.setWriter("/Users/bqj/Desktop/f.txt");
    }

    @Test
    public void numberTest(){
        CountDownLatch latch = new CountDownLatch(loop);
        for(int i = 0; i < loop; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info(Thread.currentThread().getName()+": when in: "+nubmer);
                    for(int j = 0; j < times; j++){
                        nubmer++;
                        logger.info(Thread.currentThread().getName()+": when running: "+nubmer);
                    }
                    logger.info(Thread.currentThread().getName()+": when done: "+nubmer);
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(loop * times, nubmer);
    }

    @Test
    public void VolatileTest(){
        CountDownLatch latch = new CountDownLatch(loop);
        for(int i = 0; i < loop; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+": when in: "+volatile_number);
                    for(int j = 0; j < times; j++){
                        volatile_number++;
                    }
                    System.out.println(Thread.currentThread().getName()+": when done: "+volatile_number);
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(loop * times, volatile_number.intValue());
    }

    @Test
    public void VolatileTest2(){
        CountDownLatch latch = new CountDownLatch(loop);
        for(int i = 0; i < loop; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (volatile_number){
                        System.out.println(Thread.currentThread().getName()+": when in volatile_number hashcode is: "+volatile_number.hashCode());
                        for(int j = 0; j < times; j++){
                            volatile_number++;
                        }
                        System.out.println(Thread.currentThread().getName()+": when done volatile_number hashcode is: "+volatile_number.hashCode());
                        latch.countDown();
                    }
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(loop * times, volatile_number.intValue());
    }

    @Test
    public void VolatileTest3(){
        CountDownLatch latch = new CountDownLatch(loop);
        byte[] bytes = new byte[4];
        for(int i = 0; i < loop; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (bytes){
                        System.out.println(Thread.currentThread().getName()+": when in: "+not_volatile_number);
                        for(int j = 0; j < times; j++){
                            not_volatile_number++;
                        }
                        System.out.println(Thread.currentThread().getName()+": when done: "+not_volatile_number);
                        latch.countDown();
                    }
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(loop * times, not_volatile_number.intValue());
    }

    @Test
    public void VolatileAtomicTest(){
        CountDownLatch latch = new CountDownLatch(loop);
        for(int i = 0; i < loop; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+": when in: "+atom_number.intValue());
                    for(int j = 0; j < times; j++){
                        atom_number.incrementAndGet();
                        //atom_number.incrementAndGet();
                    }
                    System.out.println(Thread.currentThread().getName()+": when done: "+atom_number.intValue());
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(loop * times, atom_number.get());
    }

    @Test
    public void SyncTest(){
        CountDownLatch latch = new CountDownLatch(loop);
        byte[] bytes = new byte[4];
        for(int i = 0; i < loop; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info("when i run: %d", not_volatile_number.intValue());
                    synchronized (not_volatile_number){
                        logger.info("when i sync: %d", not_volatile_number.intValue());
                        for(int j = 0; j < times; j++){
                            not_volatile_number++;
                            logger.info(j+": running"+not_volatile_number);
                        }
                        latch.countDown();
                    }
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(loop * times, not_volatile_number.intValue());
    }

    @Test
    public void SyncTest3(){
        LinkedList<Integer> list = new LinkedList<>();

        CountDownLatch latch = new CountDownLatch(loop);
        byte[] bytes = new byte[4];
        for(int i = 0; i < loop; i++){
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info("when i run: %d", list.size());
//                    System.out.println(String.format("when i run: %d", list.size()));
                    synchronized (list){
                        logger.info("when i sync: %d", list.size());
//                        System.out.println(String.format("when i sync: %d", list.size()));
                        for(int j = 0; j < times; j++){
                            list.push(finalI *times + times);
                            logger.info(j+": running"+list.size());
//                            System.out.println(String.format("when i running: %d", list.size()));
                            list.notify();
                        }
                        latch.countDown();
                    }
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(loop * times, list.size());
    }

   /* @Test
    public void SyncTest2(){
        CountDownLatch latch = new CountDownLatch(loop);
        for(int i = 0; i < loop; i++){
            new Thread(new A(latch)).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(loop * times, not_volatile_number);
    }*/

    /*class  A implements Runnable{

        CountDownLatch latch;
        public A(CountDownLatch latch){
            this.latch = latch;
        }
        @Override
        public void run() {
            logger.info("when i run: %d", not_volatile_number);
            synchronized (this){
                logger.info("when i sync: %d", not_volatile_number);
                for(int j = 0; j < times; j++){
                    not_volatile_number++;
                    //logger.info(j+": running"+not_volatile_number);
                }
                this.latch.countDown();
            }
        }
    }*/
}

