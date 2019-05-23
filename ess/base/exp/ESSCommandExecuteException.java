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
 * �û��ڵ��ù��ܽӿڶ�Ӧʵ��ʱ�������⣬���繦��ִ������û��׼���á�
 */
public class ESSCommandExecuteException extends ESSException {
    /**
     * ���캯��.
     */
    public ESSCommandExecuteException() {
        super("����ִ������");
    }

    /**
     * ���캯����
     * @param message ������ʾ��Ϣ��
     */
    public ESSCommandExecuteException(String message) {
        super(message);
    }

    /**
     * ���ܽӿ����ơ�
     */
    private String commandName = null;    

    /**
     * ���ܹ��ܽӿ����ơ�
     */
    public String getCommandName(){
        return commandName;
    }

    public String getMessage(){
        String msg = "����:"+getCommandName()+" ִ�д���:"+super.getMessage();
        return msg;
    }    

    /**
     * ���ù��ܽӿ����ơ�
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
}
