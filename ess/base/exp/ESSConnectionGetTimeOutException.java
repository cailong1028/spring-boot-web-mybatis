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
 * ��ȡ���ݿ����ӳ�ʱʱ�׳����쳣
 */
public class ESSConnectionGetTimeOutException extends java.sql.SQLException {
    public ESSConnectionGetTimeOutException(String message){
        super(message);
    }

}
