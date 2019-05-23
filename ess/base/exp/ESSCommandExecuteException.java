/******************************************************************************
    File: ESSCommandExecuteException.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.exp;

import ess.base.exp.ESSException;

/**
 * 用户在调用功能接口对应实现时产生例外，比如功能执行条件没有准备好。
 */
public class ESSCommandExecuteException extends ESSException {
    /**
     * 构造函数.
     */
    public ESSCommandExecuteException() {
        super("命令执行例外");
    }

    /**
     * 构造函数。
     * @param message 例外提示信息。
     */
    public ESSCommandExecuteException(String message) {
        super(message);
    }

    /**
     * 功能接口名称。
     */
    private String commandName = null;    

    /**
     * 功能功能接口名称。
     */
    public String getCommandName(){
        return commandName;
    }

    public String getMessage(){
        String msg = "功能:"+getCommandName()+" 执行错误:"+super.getMessage();
        return msg;
    }    

    /**
     * 设置功能接口名称。
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
}
