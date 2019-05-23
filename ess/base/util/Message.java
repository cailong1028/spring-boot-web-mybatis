/******************************************************************************
    File: Message.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.util;

import java.io.Serializable;

/**
 * �����ڽ��ҳ����ʾ����Ϣ��������Ҫ������Servlet��
 * ���ҳ��JSP֮�䴫�ݳɹ�/������Ϣ������Ϣ�������²��֣�
 * title: �����ڽ������ʾ�ı�����Ϣ����Ҫ��ҳ�����ͽ����������
 * content: �����������Ϣ
 * okURL,backURL: ����/������Ŀ��URL,����ҳ������
 * �����Ӳ���Ϊnullʱ���������������Ӱ�졣���򣬻ṹ��Form��������
 * ��ΪForm��Action���ԡ�
 * 
 * @author Li Shengwang
 */
public class Message implements Serializable{
    /**
     * ���ҳ����Ϣ�ı�����Ϣ��
     */
    protected java.lang.String title = null;

    /**
     * ���ҳ����ִ�н��������
     */
    protected java.lang.String content = null;

    /**
     * ���ҳ���С�ȷ��������URL��
     */
    protected java.lang.String okURL = null;

    /**
     * ���ҳ���С����ء�����URL��
     */
    protected java.lang.String backURL = "javascript:history.back()";

    /**
     * ���ҳ�����ύ��ȷ��������URL����Ӧ�Ĳ������á���һάΪ
     * �������ƣ��ڶ�άΪ����ֵ��
     */
    protected java.lang.String[][] parameters = null;

    /**
     * Message �����ӡ�
     */
    public Message() {
        super();
    }

    /**
     * Message �����ӡ�
     */
    public Message(String content, String url) {
        super();

        this.content = content;
        this.backURL = url;
    }

    /**
     * Message �����ӡ�
     */
    public Message(String title,String content, String url) {
        super();

        this.title = title;
        this.content = content;
        this.backURL = url;
    }

    /**
     * ��ȡ���ҳ���С����ء�����URL��
     */
    public java.lang.String getBackURL() {
        return backURL;
    }

    /**
     * ��ȡ���ҳ����ִ�н��������
     */
    public java.lang.String getContent() {
        return content;
    }

    /**
     * ��ȡ���ҳ���С�ȷ��������URL��
     */
    public java.lang.String getOkURL() {
        return okURL;
    }

    /**
     * ��ȡ�ύURL����Ĳ���ֵ��
     *
     * @return java.lang.String[][]
     */
    public java.lang.String[][] getParameters() {
        return parameters;
    }

    /**
     * ��ȡ���ҳ����Ϣ�ı�����Ϣ��
     */
    public java.lang.String getTitle() {
        return title;
    }

    /**
     * ���ý��ҳ���С����ء�����URL��
     */
    public void setBackURL(java.lang.String newBackURL) {
        backURL = newBackURL;
    }

    /**
     * ���ý��ҳ����ִ�н��������
     */
    public void setContent(java.lang.String newContent) {
        content = newContent;
    }

    /**
     * ���ý��ҳ���С�ȷ��������URL��
     */
    public void setOkURL(java.lang.String newOkURL) {
        okURL = newOkURL;
    }

    /**
     * �����ύURL����Ĳ���ֵ��
     *
     * @param newParameters java.lang.String[][]
     */
    public void setParameters(java.lang.String[][] newParameters) {
        parameters = newParameters;
    }

    /**
     * ���ý��ҳ����Ϣ�ı�����Ϣ��
     */
    public void setTitle(java.lang.String newTitle) {
        title = newTitle;
    }
}
