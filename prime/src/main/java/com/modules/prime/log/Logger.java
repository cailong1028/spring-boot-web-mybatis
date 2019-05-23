package com.modules.prime.log;

import com.modules.prime.util.DateUtil;

import java.lang.management.ManagementFactory;

public class Logger {

    private String className;
    //RuntimeMXBean Java虚拟机的运行时系统的管理接口
    // MemoryMXBean Java虚拟机内存系统的管理接口
    private String name = ManagementFactory.getRuntimeMXBean().getName();
    private String pid = name.split("@")[0];

    protected Logger(String className, int logLevel){
        this.className = className;
    }

    public void info(String info, Object... args){
        print(MessageType.INFO.getValue(), 0, info, args);
    }
    public void info(int depth, String info, Object... args){
        print(MessageType.INFO.getValue(), depth, info, args);
    }
    public void warn(String info, Object... args){
        print(MessageType.WARN.getValue(), 0, info, args);
    }
    public void warn(int depth, String info, Object... args){
        print(MessageType.WARN.getValue(), depth, info, args);
    }
    public void error(String info, Object... args){
        print(MessageType.ERROR.getValue(), 0, info, args);
    }
    public void error(Exception exp, Object... args){
        print(MessageType.ERROR.getValue(), 0, exp.getClass().getName()+": "+exp.getMessage(), args);
        StackTraceElement[] stes = exp.getStackTrace();
        for(StackTraceElement oneSte:stes){
            print(MessageType.ERROR.getValue(), 1, ">> "+oneSte.toString(), args);
        }
    }
    public void error(int depth, String info, Object... args){
        print(MessageType.ERROR.getValue(), depth, info, args);
    }
    public void debug(String info, Object... args){
        print(MessageType.DEBUG.getValue(), 0, info, args);
    }
    public void debug(int depth, String info, Object... args){
        print(MessageType.DEBUG.getValue(), depth, info, args);
    }

    public void print(String type, int depth, String info, Object... args){
        if(checkLevel(type)){
            return;
        }
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
        LoggerFactory.getInstance().notifyMsg(sb.append(String.format(info, args)).toString());
    }

    String combineExceptionInfo(String message){
        StringBuffer sb = new StringBuffer(String.format("[%s] [%s] [%s] [%s] : %s",
                DateUtil.getFormatTime("yyyy-MM-dd HH:mm:ss.S", System.currentTimeMillis()),
                "ERROR",
                Thread.currentThread().getName(),
                className, message));
        return sb.toString();
    }

    private boolean checkLevel(String level){
        return MessageType.valueOf(level).getCode() > LoggerFactory.getInstance().getLogLevel();
    }

}

enum MessageType {
    ERROR(1, "ERROR"), WARN(2, "WARN"), INFO(3, "INFO"), DEBUG(4, "DEBUG");
    private int code;
    private String value;
    private MessageType(int code, String value){
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}