package com.moudles.prime;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class VolatileTest {

    Logger logger = LoggerFactory.getLogger(VolatileTest.class);

    volatile Integer volatile_number = 0;
    Integer volatile_number2 = 0;
    volatile AtomicInteger volatile_atom_number = new AtomicInteger(0);
    private final int loop = 10;
    private final int times = 10;

    @Before
    public void before(){
        LoggerFactory.setWriter("/Users/bqj/Desktop/f.txt");
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
    public void VolatileAtomicTest(){
        CountDownLatch latch = new CountDownLatch(loop);
        for(int i = 0; i < loop; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j = 0; j < times; j++){
                        volatile_atom_number.incrementAndGet();
                    }
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(loop * times, volatile_atom_number.get());
    }

    @Test
    public void SyncTest(){
        CountDownLatch latch = new CountDownLatch(loop);
        for(int i = 0; i < loop; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info("when i run: %d", volatile_number2);
                    synchronized (volatile_number2){
                        logger.info("when i sync: %d", volatile_number2);
                        for(int j = 0; j < times; j++){
                            volatile_number2++;
                            //logger.info(j+": running"+volatile_number2);
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
        assertEquals(loop * times, volatile_number2.intValue());
    }

    @Test
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
        assertEquals(loop * times, volatile_number2.intValue());
    }

    class  A implements Runnable{

        CountDownLatch latch;
        public A(CountDownLatch latch){
            this.latch = latch;
        }
        @Override
        public void run() {
            logger.info("when i run: %d", volatile_number2);
            synchronized (volatile_number2){
                logger.info("when i sync: %d", volatile_number2);
                for(int j = 0; j < times; j++){
                    volatile_number2++;
                    //logger.info(j+": running"+volatile_number2);
                }
                this.latch.countDown();
            }
        }
    }
}

