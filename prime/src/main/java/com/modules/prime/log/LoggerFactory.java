package com.modules.prime.log;

import com.modules.prime.util.DateUtil;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;

public class LoggerFactory {

    private HashMap<String, Logger> loggerMap = new HashMap<>();
    private OutputStream defaultOutputStream = System.out;
    private String writer;
    private String fileEncoding = "utf-8";
    private static LoggerFactory instance;
    private LinkedList<String> messageList = new LinkedList<>();
    private int logLevel = MessageType.valueOf("INFO").getCode();

    private LoggerFactory(){

        InputStream is = LoggerFactory.class.getClassLoader().getResourceAsStream("application.properties");
        if(System.getProperty("file.encoding") != null){
            fileEncoding = System.getProperty("file.encoding");
        }
        if(is != null){
            Properties properties = new Properties();
            try{
                properties.load(is);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(properties.getProperty("file.encode") != null){
                fileEncoding = properties.getProperty("file.encode");
            }
            if(properties.getProperty("log.level") != null){
                String _logLevel = properties.getProperty("log.level");
                try{
                    logLevel = MessageType.valueOf(_logLevel.toUpperCase()).getCode();
                }catch (IllegalArgumentException e){
                    messageList.addLast(e.getMessage());
                }
            }
            if(properties.getProperty("log.path") != null){
                String _logPath = properties.getProperty("log.path");
                setWriter(_logPath);
            }
        }

        String defaultClassName = LoggerFactory.class.getName();
        final Logger defaultLogger = new Logger(defaultClassName, logLevel);
        //插入元素 与原有put方法不同的是，putIfAbsent方法中如果插入的key相同，则不替换原有的value值
        loggerMap.putIfAbsent(defaultClassName, defaultLogger);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                defaultLogger.debug("log thread running");
                Thread logHooker = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(LoggerFactory.getInstance() != null){
                            defaultLogger.debug("when Runtime shutdown, [%d] messages will tobe log", messageList.size());
                            if(messageList.size() > 0){
                                write(defaultLogger, false);
                            }
                        }
                    }
                });
                logHooker.setName("log-hooker");
                Runtime.getRuntime().addShutdownHook(logHooker);
                while (true) {
                    write(defaultLogger, true);
                }
            }

            private void write(Logger defaultLogger, boolean willWaitWhenNoMessage){
                synchronized (messageList) {
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
                        if(willWaitWhenNoMessage){
                            messageList.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        defaultLogger.error(e);
                    } catch (IOException e) {
                        e.printStackTrace();
                        defaultLogger.error(e);
                    }
                }
            }
        });
        thread.setName("log");
        thread.setDaemon(true);
        thread.start();
    }

    public static LoggerFactory getInstance(){
        if(instance != null){
            return instance;
        }else{
            synchronized (LoggerFactory.class){
                if(instance != null){
                    return instance;
                }else{
                    instance = new LoggerFactory();
                    return instance;
                }
            }
        }
    }

    public static Logger getLogger(Class clazz) {
        LoggerFactory loggerFactory = getInstance();
        String className = clazz.getName();
        Logger logger = loggerFactory.loggerMap.get(className);
        if(logger != null){
            return logger;
        }else{
            logger = new Logger(className, loggerFactory.logLevel);
//            Thread logThread = new Thread(logger);
//            logThread.setDaemon(true);
//            logThread.start();
            //插入元素 与原有put方法不同的是，putIfAbsent方法中如果插入的key相同，则不替换原有的value值
            loggerFactory.loggerMap.putIfAbsent(clazz.getName(), logger);
        }
        return logger;
    }

    void notifyMsg(String msg){
        synchronized (messageList) {
            //时间在同步块内部，不然线程执行频繁的情况下，时间顺序会乱
            messageList.addLast(String.format("[%s] "+msg, DateUtil.getFormatTime("yyyy-MM-dd HH:mm:ss.SSS", System.currentTimeMillis())));
            messageList.notify();
        }
    }

    private void setWriter(String filePath){
        setWriter(new File(filePath));
    }

    private void setWriter(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                messageList.addLast(e.getMessage());
                System.exit(1);
            }
        }
        writer = file.getAbsolutePath();
    }

    public int getLogLevel() {
        return logLevel;
    }

}
