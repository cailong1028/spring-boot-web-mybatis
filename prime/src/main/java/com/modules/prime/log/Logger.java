package com.modules.prime.log;

import com.modules.prime.util.DateUtil;

import java.io.*;
import java.lang.management.ManagementFactory;
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
 *     //支持FileWriter append
 *         //获取进程号
 *         //获取pom编码 pom properties profiles
 *         //运行销毁模式，不再是使用同一个writer
 * </ou>
 *
 * */

public class Logger implements Runnable{
    private LinkedList<String> messageList = new LinkedList<>();
    private String className;
    private OutputStream defaultOutputStream = System.out;
    private String writer = null;
    private String fileEncoding = null;
    //RuntimeMXBean Java虚拟机的运行时系统的管理接口
    // MemoryMXBean Java虚拟机内存系统的管理接口
    String name = ManagementFactory.getRuntimeMXBean().getName();
    String pid = name.split("@")[0];
    enum MessageType {
        INFO(1, "INFO"), WARN(2, "WARN"), ERROR(3, "ERROR"), DEBUG(4, "DEBUG");
        private int code;
        private String value;
        private MessageType(int code, String value){
            this.code = code;
            this.value = value;
        }
    }

    protected Logger(String className, final String writer, final String fileEncoding){
        this.className = className;
        this.writer = writer;
        this.fileEncoding = fileEncoding;
//        thread.setDaemon(true);
//        thread.start();
    }

    public void run() {
        while (true) {
            synchronized (this) {
                try {
                    //每次IO流程一次stream完整操作，不采用一个writer永不释放的方式
                    FileOutputStream fileOutputStream = null;
                    OutputStreamWriter outputStreamWriter = null;
                    BufferedWriter bufferedWriter = null;
                    if(writer != null && fileEncoding != null){
                        File file = new File(writer);
                        if(!file.exists()){
                            file.createNewFile();
                        }
                        fileOutputStream = new FileOutputStream(file, true);
                        outputStreamWriter = new OutputStreamWriter(fileOutputStream, fileEncoding);
                        bufferedWriter = new BufferedWriter(outputStreamWriter);
                    }else{
                        outputStreamWriter = new OutputStreamWriter(defaultOutputStream, fileEncoding);
                        bufferedWriter = new BufferedWriter(outputStreamWriter);
                    }
                    while (messageList.size() > 0) {
                        bufferedWriter.write(messageList.poll()+"\n");
                    }
                    /**
                     * 把当前messageList的数据写完之后再flush(一次flush多个message信息)
                     * 而不是每次write之后就flush
                     * */
                    if(writer != null && fileEncoding != null){
                        if(bufferedWriter != null){
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        }
                        if(fileOutputStream != null){
                            fileOutputStream.close();
                        }
                        if(outputStreamWriter != null){
                            outputStreamWriter.close();
                        }
                    }else{
                        bufferedWriter.flush();
                    }
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //加入messageList头
                    messageList.addFirst(combineExceptionInfo(e.getMessage()));
                } catch (IOException e) {
                    e.printStackTrace();
                    //加入messageList头
                    messageList.addFirst(combineExceptionInfo(e.getMessage()));
                }
            }
        }
    }

    public void info(String info, Object... args){
        print(MessageType.INFO.value, 0, info, args);
    }
    public void info(int depth, String info, Object... args){
        print(MessageType.INFO.value, depth, info, args);
    }
    public void warn(String info, Object... args){
        print(MessageType.WARN.value, 0, info, args);
    }
    public void warn(int depth, String info, Object... args){
        print(MessageType.WARN.value, depth, info, args);
    }
    public void error(String info, Object... args){
        print(MessageType.ERROR.value, 0, info, args);
    }
    public void error(Exception exp, Object... args){
        print(MessageType.ERROR.value, 0, exp.getClass().getName()+": "+exp.getMessage(), args);
        StackTraceElement[] stes = exp.getStackTrace();
        for(StackTraceElement oneSte:stes){
            print(MessageType.ERROR.value, 1, ">> "+oneSte.toString(), args);
        }
    }
    public void error(int depth, String info, Object... args){
        print(MessageType.ERROR.value, depth, info, args);
    }
    public void debug(String info, Object... args){
        print(MessageType.DEBUG.value, 0, info, args);
    }
    public void debug(int depth, String info, Object... args){
        print(MessageType.DEBUG.value, depth, info, args);
    }

    public synchronized void print(String type, int depth, String info, Object... args){
        //将now放在sync之外测试
            /**
             * StringBuffer是非原子操作，不能放在锁(synchronized块)外部，否则输出顺序会出乱（看下面示例时间）
             * <pre>
             *      [2019-05-17 10:15:29.811] [INFO] [Thread-114] [com.modules.prime.log.LoggerFactory] : Thread-114 112
             *      [2019-05-17 10:15:30.471] [INFO] [Thread-114] [com.modules.prime.log.Logger] : Thread-114 lalala
             *      [2019-05-17 10:15:29.810] [INFO] [Thread-113] [com.modules.prime.log.LoggerFactory] : Thread-113 111
             *      [2019-05-17 10:15:30.472] [INFO] [Thread-113] [com.modules.prime.log.Logger] : Thread-113 lalala
             * </pre>
             * */
            StringBuffer sb = new StringBuffer(String.format("[%s] [%s] [%s - %s] [%s] : ",
                    DateUtil.getFormatTime("yyyy-MM-dd HH:mm:ss.S", System.currentTimeMillis()),
                    type,
                    pid,
                    Thread.currentThread().getName(),
                    className));
//            StringBuffer sb = new StringBuffer();
//            sb.append("["+DateUtil.getFormatTime("yyyy-MM-dd HH:mm:ss.S", System.currentTimeMillis())+"]");
//            sb.append("["+type+"]");
//            sb.append("["+pid+"]");
//            sb.append("["+Thread.currentThread().getName()+"]");
//            sb.append("["+className+"] : ");
            while(depth-- > 0){
                sb.append("\t");
            }
            sb.append(String.format(info, args));
            messageList.addLast(sb.toString());
            this.notify();
    }

    private String combineExceptionInfo(String message){
        StringBuffer sb = new StringBuffer(String.format("[%s] [%s] [%s] [%s] : %s",
                DateUtil.getFormatTime("yyyy-MM-dd HH:mm:ss.S", System.currentTimeMillis()),
                "ERROR",
                Thread.currentThread().getName(),
                className, message));
        return sb.toString();
    }
}
