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
 * ��Parseĳ��������������ʱ���׳����쳣��
 */
public class ESSParameterParseException extends ESSException {
    /**
     * �������ơ�
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
     * ��ȡ��������
     */
    public java.lang.String getParaName() {
        return paraName;
    }

    /**
     * ���ò�������
     */
    public void setParaName(java.lang.String newParaName) {
        paraName = newParaName;
    }
}
