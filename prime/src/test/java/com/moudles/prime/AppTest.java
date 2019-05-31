package com.moudles.prime;


import com.modules.prime.biz.LoginBiz;
import com.modules.prime.biz.LoginBizHandler;
import com.modules.prime.biz.LoginBizImp;
import com.modules.prime.biz.LoginBizWrapper;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import com.modules.prime.sql.mysql.Bo;
import com.modules.prime.test.aop.proxy.HelloWorld;
import com.modules.prime.util.DateUtil;
import com.modules.prime.util.IOUtil;
import org.junit.Before;
import org.junit.Test;
import sun.rmi.runtime.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class AppTest {
    Logger logger = LoggerFactory.getLogger(AppTest.class);
    volatile int volatile_number = 0;
    volatile AtomicInteger volatile_atom_number = new AtomicInteger(0);

    @Before
    public void init(){
    }

    @Test
    public void dateUtil(){
        assertEquals(1, 1);
        println("print \\t test");
    }

    @Test
    public void readResource(){
        URL url = AppTest.class.getResource("/");
        println(1, "url path is %s", url.getPath());

    }

    @Test
    public void linkedList(){
        LinkedList<String> list = new LinkedList<>();
        list.push("a");
        list.push("b");
        list.push("c");
        assertEquals(list.poll(), "c");
        logger.info("aaa");
    }

    @Test
    public void loggerFactoryTest(){
                LoggerFactory.setWriter("/Users/bqj/Desktop/d.txt");
        long begin = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
        Logger logger2 = LoggerFactory.getLogger(Logger.class);
        for(int i = 0; i < 2; i++){
            final int b = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info("%s %d", Thread.currentThread().getName(), b);
                    logger2.info("%s %s", Thread.currentThread().getName(), "lalala");
                }
            }).start();
        }
        logger.info("%d", System.currentTimeMillis() - begin);

        logger.info("%s", "中文");
        logger.info(1, "%s", "A");
        logger.warn("%s", "A");
        logger.warn(1, "%s", "A");
        logger.error("%s", "A");
        logger.error(1, "%s", "A");
        logger.debug("%s", "A");
        logger.debug(1, "%s", "A");


        Logger logger3 = LoggerFactory.getLogger(Log.class);
        logger3.info("im coming");
        String name = ManagementFactory.getRuntimeMXBean().getName();
        long i = ManagementFactory.getClassLoadingMXBean().getTotalLoadedClassCount();
        long[] threads = ManagementFactory.getThreadMXBean().findDeadlockedThreads();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        logger3.info(memoryMXBean.getHeapMemoryUsage().toString());
        logger3.info(memoryMXBean.getNonHeapMemoryUsage().toString());
        logger3.info(threads+"");
        logger3.info("%s %d", "-->", System.currentTimeMillis()-begin);
        assertEquals(threads, null);
    }

    private void println(String info, Object... args){
        this.println(0, info, args);
    }

    private void println(int depth, String info, Object... args){
        StringBuffer sb = new StringBuffer();
        while(depth-- > 0){
            sb.append("\t");
        }

        System.out.print(String.format("[%s] ", DateUtil.getFormatTime("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis())));
        System.out.print(sb);
        System.out.println(String.format(info, args));
    }

    @Test
    public void BOTest(){
        Bo bo = new Bo();
        int loop = 10;
        CountDownLatch latch = new CountDownLatch(loop);
        for(int i = 0; i < loop; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Map<String, Object>> result = bo.query("select * from test where name = ?", "cl");
                    List<Map<String, Object>> result2 = bo.query("select * from test where name = ? and email = ?", "cl", "cl@126.com");
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            assertFalse(false);
        }
        assertTrue(true);
    }

    @Test
    public void IOUtilTest() throws FileNotFoundException {
        String filePath = "c:/Users/cl/Desktop/_号.txt";
        byte[] bytes = IOUtil.readByteArray(filePath);
        assertNotEquals(1, bytes.length);
        InputStream is = new FileInputStream(filePath);
        byte[] bytes1 = IOUtil.readByteArray(is);
        assertNotEquals(1, bytes1.length);

        IOUtil.writeFile(bytes, "c:/Users/cl/Desktop/a.txt");
        assertTrue(true);
        IOUtil.writeFile(bytes1, "c:/Users/cl/Desktop/b.txt");
        assertTrue(true);
        IOUtil.writeFile("这个一个系统简介__123abc谢谢", "c:/Users/cl/Desktop/c.txt");
        assertTrue(true);
    }

    @Test
    public void VolatileTest(){
        int loop = 100;
        int times = 10000;
        CountDownLatch latch = new CountDownLatch(loop);
        for(int i = 0; i < loop; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j = 0; j < times; j++){
                        volatile_number++;
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
        assertEquals(loop * times, volatile_number);
    }

    @Test
    public void BizTest(){
        LoginBiz loginBiz = LoginBizWrapper.getWrapper(new LoginBizImp());
        loginBiz.login("a", "b");
    }
}