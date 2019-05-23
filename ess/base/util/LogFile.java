/******************************************************************************
    File: Logger.java

    Version 1.0
    Date            Author                Changes
    Jun.11,2003     Li Shengwang          Created

    Copyright (c) 2003, Eagle Soar
    all rights reserved.
******************************************************************************/
package ess.base.util;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * 本类实现了日志的记录接口
 */
public class LogFile {
    // 当前日志日期
    public String date = null;
    // 日志文件名称
    public String logFile = "Log_";
    // 是否基于日期写日志
    public boolean logOnDate = true;
    // 是否打印到屏幕
    public boolean logToConsole = true;

    // 日志记录器
    public PrintWriter writer = null;

    /**
     * 实例化日志文件
     */
    public LogFile(){
		init();
    }

	/**
     * 实例化日志文件
	 * @param logFile 日志文件名称
	 * @param logOnDate 是否按日期记录日志
	 */
    public LogFile(String logFile, boolean logOnDate){
		this.logFile = logFile;
		this.logOnDate = logOnDate;
		init();
	}

    /**
     * 记录系统运行错误日志
     * @param msg 日志信息
     */
    public void log(String msg){
        String ts = new java.sql.Timestamp(System.currentTimeMillis()).toString();
        initLogWriter(ts);
        if(writer != null){
            writer.println("["+ts+"]" + msg);
            writer.flush();
        }
        if(logToConsole){
            System.out.println(msg);
        }
    }

    /**
     * 将指定的信息和异常信息写入日志文件
     */
    public void log(String msg, Throwable exp){
        log(msg);
        if(writer != null && exp != null){
            exp.printStackTrace(writer);
        }
        if( logToConsole && exp != null){
            exp.printStackTrace(System.out);
        }
    }

    /**
     * 初始化文件Writer
     */
    private void init(){
        String ts = new java.sql.Timestamp(System.currentTimeMillis()).toString();
        initLogWriter(ts);
    }

    /**
     * 重新初始化文件Writer
     */
    private void initLogWriter(String ts){
        if(writer == null || (logOnDate && !ts.substring(0,10).equals(date))){
            String fileName = null;
            if(logOnDate){
                date = ts.substring(0,10);
                fileName = logFile + date.replace('-','_')+".log";
            }else{
                fileName = logFile+".log";
            }
            try{
                if(writer != null){
                    writer.close();
                }
                writer = new PrintWriter(new FileWriter(fileName, true));
            }catch(java.io.IOException ex){
                writer = null;
            }
        }
    }
}
