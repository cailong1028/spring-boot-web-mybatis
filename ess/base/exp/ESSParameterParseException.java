/******************************************************************************
    File: ESSParameterParseException.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.exp;

import ess.base.exp.ESSException;

/**
 * 当Parse某个参数发生错误时，抛出此异常。
 */
public class ESSParameterParseException extends ESSException {
    /**
     * 参数名称。
     */
    private java.lang.String paraName = null;

    /**
     * ParameterParseException constructor comment.
     */
    public ESSParameterParseException() {
        super();
    }

    /**
     * ParameterParseException constructor comment.
     * @param message java.lang.String
     */
    public ESSParameterParseException(String message) {
        super(message);
    }

    /**
     * ParameterParseException constructor comment.
     * @param message java.lang.String
     */
    public ESSParameterParseException(String message, String paraName) {
        super(message+":"+paraName);

        this.paraName = paraName;
    }

    /**
     * 获取参数名称
     */
    public java.lang.String getParaName() {
        return paraName;
    }

    /**
     * 设置参数名称
     */
    public void setParaName(java.lang.String newParaName) {
        paraName = newParaName;
    }
}
