/******************************************************************************
    File: Logger.java

    Version 1.0
    Date            Author                Changes
    Jun.11,2003     Li Shengwang          Created

    Copyright (c) 2003, Eagle Soar
    all rights reserved.
******************************************************************************/
package ess.base.util;

/**
 * ����ʵ������־�ļ�¼�ӿ�
 */
public class Logger {
    public String date = null;
    public String TRANS_LOG_FILE_NAME="TransLog_";
    public String SERVER_FILE_NAME = "Server_";
    public String TRACE_FILE_NAME = "Trace_";
    public String BIZ_FILE_NAME = "Biz_";

    public boolean transOnDate = true;
    public boolean logOnDate = false;
    boolean LOG_TO_CONSOL = true;

    /**
     * The file name for save the trans message.
     */
    private LogFile transLog = null;

    /**
     * The file name for save the running message.
     */
    private LogFile serverLog = null;

    /**
     * The file name for save the trace message.
     */
    private LogFile traceLog = null;

    /**
     * The file name for save the biz process message.
     */
    private LogFile bizLog = null;

    private static Logger instance = null;

    protected Logger(){
        init();
    }

    /**
     * ��ȡ��־ʵ��
     * @return Logger
     */
    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

    /**
     * ��ȡ��־��¼����ʵ��
     * @param transLogFile ʹ�õĽ�����־ǰ׺��
     * @param logFile ʹ�õķ�����������־�ļ���
     * @param transOnDate �Ƿ����ڽ�����־
     * @param logOnDate �Ƿ����ڽ�����־
     * @return Logger ��־��¼����ʵ��
     */
    public static Logger getInstance(String transLogFile, String logFile,
    boolean transOnDate, boolean logOnDate){
        if(instance == null){
            instance = new Logger();
        }
        instance.TRANS_LOG_FILE_NAME = transLogFile;
        instance.SERVER_FILE_NAME = logFile;
        instance.transOnDate = transOnDate;
        instance.logOnDate = logOnDate;

        instance.init();

        return instance;
    }

    /**
     * ��¼ϵͳ���д�����־
     * @param msg
     */
    public void log(String msg){
        if(serverLog == null){
            serverLog = new LogFile(SERVER_FILE_NAME, true);
        }
        if(serverLog != null){
            serverLog.log(msg);
        }
    }

    public void log(String msg, Throwable exp){
        if(serverLog == null){
            serverLog = new LogFile(SERVER_FILE_NAME, true);
        }
        if(serverLog != null){
            serverLog.log(msg, exp);
        }
    }

    /**
     * ��¼ϵͳ����ִ�д�����־
     * @param msg
     */
    public void logTrans(String msg){
        if(transLog == null){
            transLog = new LogFile(TRANS_LOG_FILE_NAME, true);
        }
        if(transLog != null){
            transLog.log(msg);
        }
    }

    public void logTrans(String msg, Throwable exp){
        if(transLog == null){
            transLog = new LogFile(TRANS_LOG_FILE_NAME, true);
        }
        if(transLog != null){
            transLog.log(msg, exp);
        }
    }

    /**
     * ��¼ϵͳҵ������־
     * @param msg
     */
    public void logBiz(String msg){
        if(bizLog == null){
            bizLog = new LogFile(BIZ_FILE_NAME, true);
        }
        if(bizLog != null){
            bizLog.log(msg);
        }
    }

    public void logBiz(String msg, Throwable exp){
        if(bizLog == null){
            bizLog = new LogFile(BIZ_FILE_NAME, true);
        }
        if(bizLog != null){
            bizLog.log(msg, exp);
        }
    }

    /**
     * ��¼ϵͳ����ִ�д�����־
     * @param msg
     */
    public void trace(String msg){
        if(traceLog == null){
            traceLog = new LogFile(TRACE_FILE_NAME, true);
        }
        if(traceLog != null){
            traceLog.log(msg);
        }
    }

    public void trace(String msg, Throwable exp){
        if(traceLog == null){
            traceLog = new LogFile(TRACE_FILE_NAME, true);
        }
        if(traceLog != null){
            traceLog.log(msg, exp);
        }
    }

    private void init(){
//        transLog = new LogFile(TRANS_LOG_FILE_NAME, true);
//        serverLog = new LogFile(SERVER_FILE_NAME, true);
//        traceLog = new LogFile(TRACE_FILE_NAME, true);
//        bizLog = new LogFile(BIZ_FILE_NAME, true);
    }
}
