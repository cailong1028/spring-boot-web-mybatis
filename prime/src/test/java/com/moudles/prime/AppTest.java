package com.moudles.prime;


import com.modules.prime.log.LoggerFactory;
import com.modules.prime.util.DateUtil;
import com.modules.prime.log.Logger;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

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
        for(String one:list){
            System.out.println(one);
        }
        assertEquals(list.poll(), "c");

        logger.msg("aaa");
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
}