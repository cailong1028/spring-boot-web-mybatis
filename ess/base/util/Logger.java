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
 * 本类实现了日志的记录接口
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
     * 获取日志实例
     * @return Logger
     */
    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

    /**
     * 获取日志记录对象实例
     * @param transLogFile 使用的交易日志前缀名
     * @param logFile 使用的服务器运行日志文件名
     * @param transOnDate 是否按日期建立日志
     * @param logOnDate 是否按日期建立日志
     * @return Logger 日志记录对象实例
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
     * 记录系统运行错误日志
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
     * 记录系统交易执行错误日志
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
     * 记录系统业务处理日志
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
     * 记录系统交易执行错误日志
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
