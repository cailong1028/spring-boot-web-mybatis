package com.modules.prime.log;

import com.modules.prime.util.DateUtil;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

/**
 * <ou>
 *     <li>volatile</li>
 *     <li>安全单例实现</li>
 *     <li>daemon线程</li>
 *     <li>event list > 0 notify </li>
 *     <li>wait while</li>
 *     <li>sync(who) 给谁加锁 list 还是当前this对象</li>
 *     <li>writer要即使新建销毁</li>
 *     <li>传入class</li>
 *     <li>显示调用线程</li>
 * </ou>
 *
 * */


public class Logger {
    private LinkedList<String> messageList = new LinkedList<>();
    private OutputStreamWriter writer;
    private String className;
    Logger(String className, OutputStreamWriter writer){
        this.writer = writer;
        this.className = className;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    synchronized (messageList) {
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

    public void msg(String info, Object... args){
        msg(0, info, args);
    }

    public void msg(int depth, String info, Object... args){
        StringBuffer sb = new StringBuffer(String.format("[%s] [%s] [%s] ", DateUtil.getFormatTime("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()), Thread.currentThread().getName(), className));
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
            writer.write("\n");
            writer.flush();
//            System.out.println(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
