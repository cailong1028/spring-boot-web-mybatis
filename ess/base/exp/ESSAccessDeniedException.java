/******************************************************************************
    File: ESSAccessDeniedException.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.exp;

import ess.base.exp.ESSException;

/**
 * ��ָ���û�û��ָ�����ܽӿ�Ȩ��ʱ�������������͡�
 */
public final class ESSAccessDeniedException extends ESSException {
    
    /**
     * ���ܽӿ����ơ�
     */
    private String commandName = null;

    /**
     * �û����ơ�
     */
    private String userName = null;

    /**
     * �û������������ơ�
     */
    private String orgName = null;

    /**
     * ESSAccessDeniedException ������ע�⡣
     */
    public ESSAccessDeniedException() {
        super("�û���Ȩ���ø���ܽӿ�");
    }

    /**
     * ESSAccessDeniedException ������ע�⡣
     * @param message java.lang.String
     */
    public ESSAccessDeniedException(String message,String commandName,String userName,String orgName) {
        super(message);
        this.setCommandName(commandName);
        this.setUserName(userName);
        this.setOrgName(orgName);
    }

    public String getCommandName(){
        return commandName;
    }

    /**
     * ����������Ϣ��
     */
    public String getMessage(){
        String msg = "����:"+getOrgName()+"���û�:"+getOrgName()+" ��Ȩ���ù���:"+getCommandName();
        return msg;
    }

    public String getOrgName(){
        return orgName;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setCommandName(String commandName){
        this.commandName = commandName;
    }

    public void setOrgName(String orgName){
        this.orgName = orgName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
}
