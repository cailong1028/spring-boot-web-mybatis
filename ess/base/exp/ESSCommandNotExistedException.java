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
 * 当用户请求的接口名称在COMMANDS找不到对应的实现时抛出的例外类型。
 */
public class ESSCommandNotExistedException extends ESSException {
    /**
     * 构造函数。
     */
    public ESSCommandNotExistedException() {
        super("用户请求的接口没有对应的实现！");
    }

    /**
     * 接口名称。
     */
    private String commandName = null;

    /**
     * 接口名称。
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
     * 返回接口名称。
     */
    public String getCommandName() {
        return this.commandName;
    }

    public String getMessage() {
        String msg = 
            "用户:" + getOrgName() + " 请求的功能接口:" + getCommandName() + " 没有对应的实现！"; 
        return msg;
    } 

    /**
     * 返回机构名称。
     */
    public String getOrgName() {
        return this.orgName;
    } 

    /**
     * 设置接口名称。
     * @param commandName 被设置的接口名称。
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    } 

    /**
     * 设置机构名称。
     * @param orgName 被设置的机构名称。
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
