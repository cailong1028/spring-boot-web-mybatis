/******************************************************************************
    File: ConnectionGetTimeOutException.java

    Version 1.0
    Date            Author                Changes
    Jun.08,2003     Li Shengwang          Created

    Copyright (c) 2003, Eagle Soar
    all rights reserved.
******************************************************************************/
package ess.base.exp;

/**
 * 获取数据库连接超时时抛出此异常
 */
public class ESSConnectionGetTimeOutException extends java.sql.SQLException {
    public ESSConnectionGetTimeOutException(String message){
        super(message);
    }

}
