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
 * �������������ܹ��������Զ����������͵Ļ����ࡣ
 * @author Lishan
 * @version 1.0
 */
public class ESSException extends Exception {

    /**
     * �ñ������ڱ���ϵͳ��ԭʼ�������͡�
     */
    private Throwable innerException = null;

    /**
     * ȱʡ�Ĺ��캯����������ϢΪȱʡ��ʾ��Ϣ��
     */
    public ESSException() {
        this("����δ֪ԭ�����������ʱ���⣬���Ժ�����");
    }

    /**
     * ʹ���û�ָ����������Ϣ�����������͡�
     * @param message �û�ָ����������Ϣ��
     */
    public ESSException(String message) {
        this(message, null);
    }

    public ESSException(String msg, Throwable exp) {
        super(msg);
        this.setException(exp);
    }

    /**
     * ���ص��±������ϵͳ�����ʵ����
     */
    public Throwable getException() {
        return this.innerException;
    }

    /**
     * ����ϵͳ�����������͵�ʵ����
     */
    public void setException(Throwable exp) {
        this.innerException = exp;
    }
}
