/******************************************************************************
    File: ESSCommandNotExistedException.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.exp;

import ess.base.exp.ESSException;

/**
 * ���û�����Ľӿ�������COMMANDS�Ҳ�����Ӧ��ʵ��ʱ�׳����������͡�
 */
public class ESSCommandNotExistedException extends ESSException {
    /**
     * ���캯����
     */
    public ESSCommandNotExistedException() {
        super("�û�����Ľӿ�û�ж�Ӧ��ʵ�֣�");
    }

    /**
     * �ӿ����ơ�
     */
    private String commandName = null;

    /**
     * �ӿ����ơ�
     */
    private String orgName = null;

    /**
     * ESSCommandNotExistedException constructor comment.
     * @param message java.lang.String
     */
    public ESSCommandNotExistedException(
        String message, 
        String orgName, 
        String commandName) {
        super(message);
        this.setOrgName(orgName);
        this.setCommandName(commandName);
    } 

    /**
     * ���ؽӿ����ơ�
     */
    public String getCommandName() {
        return this.commandName;
    }

    public String getMessage() {
        String msg = 
            "�û�:" + getOrgName() + " ����Ĺ��ܽӿ�:" + getCommandName() + " û�ж�Ӧ��ʵ�֣�"; 
        return msg;
    } 

    /**
     * ���ػ������ơ�
     */
    public String getOrgName() {
        return this.orgName;
    } 

    /**
     * ���ýӿ����ơ�
     * @param commandName �����õĽӿ����ơ�
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    } 

    /**
     * ���û������ơ�
     * @param orgName �����õĻ������ơ�
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
