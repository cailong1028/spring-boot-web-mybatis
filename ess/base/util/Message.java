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
 * 用于在结果页面显示的信息。该类主要用于在Servlet与
 * 结果页面JSP之间传递成功/错误信息。该信息包括以下部分：
 * title: 用于在结果中显示的标题信息，主要是页面标题和结果描述标题
 * content: 结果的描述信息
 * okURL,backURL: 返回/继续的目标URL,用于页面连接
 * 当连接参数为null时，将不对连接造成影响。否则，会构造Form对象，连接
 * 设为Form的Action属性。
 * 
 * @author Li Shengwang
 */
public class Message implements Serializable{
    /**
     * 结果页面信息的标题信息。
     */
    protected java.lang.String title = null;

    /**
     * 结果页面中执行结果描述。
     */
    protected java.lang.String content = null;

    /**
     * 结果页面中“确定”连接URL。
     */
    protected java.lang.String okURL = null;

    /**
     * 结果页面中“返回”连接URL。
     */
    protected java.lang.String backURL = "javascript:history.back()";

    /**
     * 结果页面中提交“确定”连接URL所对应的参数设置。第一维为
     * 参数名称，第二维为参数值。
     */
    protected java.lang.String[][] parameters = null;

    /**
     * Message 构造子。
     */
    public Message() {
        super();
    }

    /**
     * Message 构造子。
     */
    public Message(String content, String url) {
        super();

        this.content = content;
        this.backURL = url;
    }

    /**
     * Message 构造子。
     */
    public Message(String title,String content, String url) {
        super();

        this.title = title;
        this.content = content;
        this.backURL = url;
    }

    /**
     * 获取结果页面中“返回”连接URL。
     */
    public java.lang.String getBackURL() {
        return backURL;
    }

    /**
     * 获取结果页面中执行结果描述。
     */
    public java.lang.String getContent() {
        return content;
    }

    /**
     * 获取结果页面中“确定”连接URL。
     */
    public java.lang.String getOkURL() {
        return okURL;
    }

    /**
     * 获取提交URL所需的参数值。
     *
     * @return java.lang.String[][]
     */
    public java.lang.String[][] getParameters() {
        return parameters;
    }

    /**
     * 获取结果页面信息的标题信息。
     */
    public java.lang.String getTitle() {
        return title;
    }

    /**
     * 设置结果页面中“返回”连接URL。
     */
    public void setBackURL(java.lang.String newBackURL) {
        backURL = newBackURL;
    }

    /**
     * 设置结果页面中执行结果描述。
     */
    public void setContent(java.lang.String newContent) {
        content = newContent;
    }

    /**
     * 设置结果页面中“确定”连接URL。
     */
    public void setOkURL(java.lang.String newOkURL) {
        okURL = newOkURL;
    }

    /**
     * 设置提交URL所需的参数值。
     *
     * @param newParameters java.lang.String[][]
     */
    public void setParameters(java.lang.String[][] newParameters) {
        parameters = newParameters;
    }

    /**
     * 设置结果页面信息的标题信息。
     */
    public void setTitle(java.lang.String newTitle) {
        title = newTitle;
    }
}
