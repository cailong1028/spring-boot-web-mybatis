package com.modules.prime.log;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class LoggerFactory {

    private HashMap<String, Logger> loggerMap = new HashMap<>();
    private String writer;
    private String fileEncoding = "utf-8";
    private static LoggerFactory instance;

    private LoggerFactory(){
        InputStream is = LoggerFactory.class.getClassLoader().getResourceAsStream("application.properties");
        try{
            if(System.getProperty("file.encoding") != null){
                fileEncoding = System.getProperty("file.encoding");
            }
            if(is != null){
                Properties properties = new Properties();
                properties.load(is);
                fileEncoding = properties.getProperty("file.encode");
            }
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
            logger = new Logger(className, loggerFactory.writer, loggerFactory.fileEncoding);
            Thread logThread = new Thread(logger);
            logThread.setDaemon(true);
            logThread.start();
            //插入元素 与原有put方法不同的是，putIfAbsent方法中如果插入的key相同，则不替换原有的value值
            loggerFactory.loggerMap.putIfAbsent(clazz.getName(), logger);
        }
        return logger;
    }

    public static void setWriter(String filePath){
        setWriter(new File(filePath));
    }

    public static void setWriter(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getInstance().writer = file.getAbsolutePath();
    }

    public static void main(String[] args) throws IOException {

    }
}
