package com.moudles.prime;


import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import com.modules.prime.sql.mysql.BO;
import com.modules.prime.util.DateUtil;
import com.modules.prime.log.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import sun.rmi.runtime.Log;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.net.URL;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {
    Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Before
    public void init(){
        //Logger logger = LoggerFactory.getLogger(AppTest.class);
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
        //        LoggerFactory.setWriter("/Users/bqj/Desktop/d.txt");
        long begin = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
        Logger logger2 = LoggerFactory.getLogger(Logger.class);
        for(int i = 0; i < 500; i++){
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

        logger.info("%s", "A");
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

    //TODO 多线程log测试

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
        BO bo = new BO();
        for(int i = 0; i < 1; i++){
            final int b = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONArray ja = bo.query("select * from test where name = ?", "cl");
                    logger.info(((JSONObject)ja.get(0)).getString("name"));
                    assertNotEquals(ja.length(), 1);
                }
            }).start();
        }

    }
}