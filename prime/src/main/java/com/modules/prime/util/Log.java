package com.modules.prime.util;

import oracle.jvm.hotspot.jfr.StreamWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <ou>
 *     <li>volatile</li>
 *     <li>安全单例实现</li>
 *     <li>daemon线程</li>
 *     <li>event list > 0 notify </li>
 *     <li>wait while</li>
 *     <li>sync(who) 给谁加锁 list 还是当前this对象</li>
 * </ou>
 *
 * */


public class Log{

    private static Log INSTANCE = null;

    private static volatile LinkedList<String> messageList = new LinkedList<>();

    private static OutputStreamWriter writer = new OutputStreamWriter(System.out);

    private Log(){

        Thread thread = new Thread(new Runnable() {
            public void run() {

                while (true) {
                    synchronized (messageList) {
                        System.out.println("log running");
                        while (messageList.size() > 0) {
                            write(messageList.poll());
                        }
                        try {
                            messageList.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            messageList.addLast(e.getMessage());
                        }
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            public void run() {
//                synchronized (messageList){
//                    while (true){
//                        while (messageList.size() > 0){
//                            write(messageList.poll());
//                        }
//                        try {
//                            wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            messageList.addLast(e.getMessage());
//                        }
//                    }
//                }
//            }
//        });
    }

    //强安全单例
    public static Log getInstance(){
        if(null != INSTANCE){
            return INSTANCE;
        }else{
            synchronized(Log.class){
                if(null != INSTANCE){
                    return INSTANCE;
                }else{
                    return new Log();
                }
            }
        }
    }

    public static void msg(String info, Object... args){
        msg(0, info, args);
    }

    public static void msg(int depth, String info, Object... args){
        StringBuffer sb = new StringBuffer(String.format("[%s] [Thread-%s] ", DateUtil.getFormatTime("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()), Thread.currentThread().getId()));
        while(depth-- > 0){
            sb.append("\t");
        }
        sb.append(String.format(info, args));
        synchronized (messageList){
            messageList.addLast(sb.toString());
            messageList.notifyAll();
        }
    }

    private void write(String info){
        try {
            writer.write(info);
            System.out.println(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Log.getInstance();
        Log.msg("bbb");
        Executors.newFixedThreadPool(1).execute(new Runnable() {
            @Override
            public void run() {
                Log.msg("ccc");
            }
        });
    }
}
