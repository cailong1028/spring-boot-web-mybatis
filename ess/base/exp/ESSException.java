/******************************************************************************
    File: ESSException.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.exp;

import java.lang.Exception;

/**
 * 本类用作基础架构中所有自定义例外类型的基础类。
 * @author Lishan
 * @version 1.0
 */
public class ESSException extends Exception {

    /**
     * 该变量用于保存系统的原始例外类型。
     */
    private Throwable innerException = null;

    /**
     * 缺省的构造函数，例外信息为缺省提示信息。
     */
    public ESSException() {
        this("由于未知原因引起的运行时例外，请稍后再试");
    }

    /**
     * 使用用户指定的例外信息构造例外类型。
     * @param message 用户指定的例外信息。
     */
    public ESSException(String message) {
        this(message, null);
    }

    public ESSException(String msg, Throwable exp) {
        super(msg);
        this.setException(exp);
    }

    /**
     * 返回导致本例外的系统例外的实例。
     */
    public Throwable getException() {
        return this.innerException;
    }

    /**
     * 设置系统基础例外类型的实例。
     */
    public void setException(Throwable exp) {
        this.innerException = exp;
    }
}
