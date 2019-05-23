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
 * 本类定义了系统操作的超时异常
 */
public class TimeoutException extends Exception {

    /**
     * 构造操作超时异常
     */
    public TimeoutException() {
        super();
    }

    /**
     * 构造操作超时异常
     */
    public TimeoutException(String message) {
        super(message);
    }
}
