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
 * 当指定用户没有指定功能接口权限时产生的例外类型。
 */
public final class ESSAccessDeniedException extends ESSException {
    
    /**
     * 功能接口名称。
     */
    private String commandName = null;

    /**
     * 用户名称。
     */
    private String userName = null;

    /**
     * 用户所属机构名称。
     */
    private String orgName = null;

    /**
     * ESSAccessDeniedException 构造子注解。
     */
    public ESSAccessDeniedException() {
        super("用户无权调用该项功能接口");
    }

    /**
     * ESSAccessDeniedException 构造子注解。
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
     * 返回例外信息。
     */
    public String getMessage(){
        String msg = "机构:"+getOrgName()+"的用户:"+getOrgName()+" 无权调用功能:"+getCommandName();
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
