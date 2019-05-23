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
 * ����ʵ������־�ļ�¼�ӿ�
 */
public class LogFile {
    // ��ǰ��־����
    public String date = null;
    // ��־�ļ�����
    public String logFile = "Log_";
    // �Ƿ��������д��־
    public boolean logOnDate = true;
    // �Ƿ��ӡ����Ļ
    public boolean logToConsole = true;

    // ��־��¼��
    public PrintWriter writer = null;

    /**
     * ʵ������־�ļ�
     */
    public LogFile(){
		init();
    }

	/**
     * ʵ������־�ļ�
	 * @param logFile ��־�ļ�����
	 * @param logOnDate �Ƿ����ڼ�¼��־
	 */
    public LogFile(String logFile, boolean logOnDate){
		this.logFile = logFile;
		this.logOnDate = logOnDate;
		init();
	}

    /**
     * ��¼ϵͳ���д�����־
     * @param msg ��־��Ϣ
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
     * ��ָ������Ϣ���쳣��Ϣд����־�ļ�
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
     * ��ʼ���ļ�Writer
     */
    private void init(){
        String ts = new java.sql.Timestamp(System.currentTimeMillis()).toString();
        initLogWriter(ts);
    }

    /**
     * ���³�ʼ���ļ�Writer
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
