package com.modules.prime.log;

import java.io.OutputStreamWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;

public class LoggerFactory {

    private ConcurrentMap<String, Logger> loggerMap = new ConcurrentHashMap<>();
    private OutputStreamWriter writer = new OutputStreamWriter(System.out);
    private static LoggerFactory instance;

    private LoggerFactory(){

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
            logger = loggerFactory.loggerMap.putIfAbsent(clazz.getName(), new Logger(className, loggerFactory.writer));
        }
        return logger;
    }

    public static void setWriter(OutputStreamWriter writer){
        LoggerFactory loggerFactory = getInstance();
        loggerFactory.writer = writer;
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
        logger.msg("aaa");
        Executors.newFixedThreadPool(1).execute(new Runnable() {
            @Override
            public void run() {
                logger.msg("ccc");
            }
        });
    }
}
