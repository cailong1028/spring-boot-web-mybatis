/******************************************************************************
    File: TimeoutException.java

    Version 1.0
    Date            Author                Changes
    Jul.04,2003     Li Shengwang          Created

    Copyright (c) 2003, Eagle Soar
    all rights reserved.
******************************************************************************/
package ess.base.util;

/**
 * ���ඨ����ϵͳ�����ĳ�ʱ�쳣
 */
public class TimeoutException extends Exception {

    /**
     * ���������ʱ�쳣
     */
    public TimeoutException() {
        super();
    }

    /**
     * ���������ʱ�쳣
     */
    public TimeoutException(String message) {
        super(message);
    }
}
