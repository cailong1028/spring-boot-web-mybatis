/******************************************************************************
    File: ESSNotDefineException.java

    Version 1.0
    Date            Author                Changes
    2003-3-27     Li Shengwang          Created

    Copyright (c) 2003, Eagle Soar
    all rights reserved.
******************************************************************************/
package ess.base.exp;

/**
 * 本类实现了数据处理过程中处理对象不存在时的异常。
 */
public class ESSNotDefineException extends ESSException {
    public ESSNotDefineException(String message){
        super(message);
    }
}
