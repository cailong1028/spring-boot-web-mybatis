/******************************************************************************
    File LogWriter.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar
    all rights reserved
******************************************************************************/
package ess.base.util;

/**
 * The LogWriter simplifies printing log messages to a PrintWriter.
 * It adds a timestamp and the owner's name to each message to make it easier to 
 * interpre a large log file with messages from many different parts of an 
 * application.
 */
public class LogWriter extends ess.base.bo.ESSObject{

    /**
     * Use as log level if no messages should be logged.
     */
    public static final int NONE=0;

    /**
     * Use as log level and severity level for error messages.
     */
    public static final int ERROR=1;
    
    /**
     * Use as log level and severity level for information messages.
     */
    public static final int INFO=2;
    
    /**
     * Use as log level and severity level for debug messages.
     */
    public static final int DEBUG=3;
    
    /**
     * The log message should be output to log file.
     */
    public boolean toFile = true;
    
    /**
     * The log message should be output to database.
     */
    public boolean toDb = true;

    /**
     * The default log level.
     */
    private int logLevel = -1;

    /**
     * The file writer for output the log message.
     */
    private LogFile logWriter = null;

    /**
     * The FILE name for output the log message.
     */
    private String logFileName = "Error";

    /**
     * 日志类。
     */
    private static LogWriter instance = null;

    /**
     * Creates new LogWriter.
     * @param owner A string to use for all log messages
     * @param logLevel The highest severity level to log
     * @param pw The PrintWriter to write log message to
     */
    protected LogWriter(){
        super();
        logWriter = new LogFile(logFileName, true);
    }

    /**
     * 获取日志类记录接口实例
     * @return ESSLogWriter
     */
    public static LogWriter getInstance(){
        if(instance == null){
            instance = new LogWriter();
        }
        return instance;
    }

    /**
     * Sets the output way of the log message.
     * @param outTo The log message should be output to
     */
    public void setOutTo(boolean toFile, boolean toDb){
        this.toFile = toFile;
        this.toDb = toDb;
    }

    /**
     * Sets the current log level.
     * @param logLevel The severity level for the message
     */
    public void setLogLevel(int logLevel){
        this.logLevel = logLevel;
    }
    
    /**
     * Writes the message to the current PrintWriter if the severity level is 
     * lower than or equal to the current log level.
     * @param msg The message
     * @param severityLevel The severity level for the message
     */
    public void log(Object owner,String msg){
        log(owner, null, msg, LogWriter.INFO);
    }
    
    /**
     * Writes the message to the current PrintWriter if the severity level is 
     * lower than or equal to the current log level.
     * @param msg The message
     * @param severityLevel The severity level for the message
     */
    public void log(Object owner,String msg,int severityLevel){
        log(owner, null, msg, severityLevel);
    }
    
    /**
     * Writes the message to the current PrintWriter if the severity level is 
     * lower than or equal to the current log level. If so, the stack
     * trace for the Throwable is also logged.
     * @param t the Throwable to include in the message
     * @param msg The message
     * @param severityLevel The severity level for the message
     */
    public void log(Object owner,Throwable exp,String msg){
        log(owner, exp, msg, LogWriter.ERROR);
    }
    
    /**
     * Writes the message to the current PrintWriter if the severity level is 
     * lower than or equal to the current log level. If so, the stack
     * trace for the Throwable is also logged.
     * @param t the Throwable to include in the message
     * @param msg The message
     * @param severityLevel The severity level for the message
     */
    public void log(Object owner,Throwable exp,String msg,int severityLevel){
        if(toFile){
            logWriter.log("{@"+owner.getClass().getName()+" #"
                + getSeverityLevelString(severityLevel)+" $"+msg+"}", exp);
        }
        if(toDb){
            writeToDB(owner.getClass().getName(), getSeverityLevelString(severityLevel),
                msg, exp);
        }
    }

    /**
     * Writes the log message to the specifile place.
     *
     * @param log The log message
     */
    private void writeToDB(String owner, String level, String msg, Throwable exp){
        java.sql.Connection conn = null;
        java.sql.PreparedStatement ps = null;
        try{
            conn = getDataStore().getConnection();
            ps = conn.prepareStatement("insert into errlog(owner,level,message,exp) values(?,?,?,?)");
            ps.setString(1, owner);
            ps.setString(2, level);
            ps.setString(3, msg);
            ps.setString(4, exp.toString());
            ps.executeUpdate();
        }catch(Throwable ex){
        }finally{
            try{
                getDataStore().freeConnection(conn);
            }catch(Throwable t){
            }
        }
    }
    
    /**
     * Returns the current log level.
     */
    public int getLogLevel(){
        return logLevel;
    }

    /**
     * Converts the severity level to a string.
     */
    private String getSeverityLevelString(int severityLevel){
        if(severityLevel==LogWriter.ERROR)
            return "ERROR";
        else if(severityLevel==LogWriter.INFO)
            return "INFO";
        else if(severityLevel==LogWriter.DEBUG)
            return "ERROR";
        else
            return "INFO";
        
    }
}
