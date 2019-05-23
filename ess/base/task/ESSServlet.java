/******************************************************************************
    File: ESSServlet.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.task;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import ess.base.util.Message;
import ess.base.bo.User;
import ess.base.db.*;
import ess.base.exp.*;

import ess.base.util.LogWriter;

/**
 * ����Servlet�࣬ʵ���˲�����ȡ�Ͳ������ݣ�session�����ȡ�ʹ洢��
 * ���ҳ����õȣ��Ļ������ܡ�ԭ���ϣ���ҵ�������Ӧ�Ӹ���̳С�
 */
public abstract class ESSServlet extends HttpServlet {
    /**
     * ƽ̨ȱʡ�Ĵ�����ʾҳ�档
     */
    protected String errorJsp = "/commonPage/error.jsp";
    protected String openErrorJsp = "/commonPage/openError.jsp";

    /**
     * ��ʾҳ�档
     */
    protected String alertJsp = "/commonPage/error.jsp";

    /**
     *��ƽ̨ȱʡ�ĳɹ���ʾҳ�档
     */
    protected String successJsp = "/commonPage/success.jsp";

    /**
     * Session����ʱ���ҳ�档
     */
    protected java.lang.String expireJsp = "/Login.jsp";

    /**
     * ��־�ࡣ
     */
    private static LogWriter logWriter = null;

    /**
     * ����Get����÷�������һ��perform()��
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
		response.setContentType( "text/html;charset=gb2312");
		//request.setCharacterEncoding("gb2312");	
        perform(request, response);
    }

    /**
     * ����Post����,�÷�������perform()��
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
		response.setContentType( "text/html;charset=gb2312");
        request.setCharacterEncoding("gb2312"); 
        perform(request, response);
    }

    /**
     * ��Task��δ������쳣���д���
     */
    public void perform(HttpServletRequest request, HttpServletResponse response) {
		try {
            execute(request, response);
        } catch (Throwable t) {
            log(t,"Not processed exception");
            forwardErrorPage(request, response, t.getMessage());
        }
    }

    /**
     * ������Ԥ���������Ա�������ָ�����߼������ұ����Աֻ���ڴ˴�����߼���
     * ���⣬�����һЩ���⴦���ˡ����ǿ�����ȱʡ�����������������������
     * ���ܡ�
     */
    public abstract void execute(
        HttpServletRequest request, 
        HttpServletResponse response)
        throws Throwable;

    /**
     *������ϵͳ����Ӧҳ�棬����Ӧҳ������Ǵ��󡢳ɹ������û�ָ����ҳ�档
     */
    protected void forward(
        HttpServletRequest request, 
        HttpServletResponse response, 
        String page) {
        try {
            RequestDispatcher requestdispatcher = 
                getServletContext().getRequestDispatcher(page); 
            requestdispatcher.forward(request, response);
        }
        /*
         * ��ʵ�ʱ���У�Ӧ�ø���ʵ�ʴ������ͷֱ�����Ϊȱ����Դ������
         * ������ʱͳһ����Ϊֱ�Ӵ�ӡ�����
         */
        catch (Exception ex) {
            log("Forward error:" + page, ex);
        }
    }

    /**
     * ����ָ����ҳ�棬ת����ǰ��������������Ϊ�˱���
     * �����ظ��ύ�������
     */
    protected void forwardDirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        }
        /*
         * ��ʵ�ʱ���У�Ӧ�ø���ʵ�ʴ������ͷֱ�����Ϊȱ����Դ������
         * ������ʱͳһ����Ϊֱ�Ӵ�ӡ�����
         */
        catch (Exception ex) {
            log("Forward error:", ex);
        }
    }

    /**
     * �������û������Ĵ�����ʾ�����ص�ȱʡ�Ĳ����������ʾҳ�档
     * ���ô˷�������Ԥ������ResultMessage���������������������ö���
     * ���������������ԣ�
     *        content,backURL
     * �ö��������������ʽ����request�����У�
     *        request.setAttribute("message",resultMessage);
     * for example:
     *         ResultMessage msg = new ResultMessage();
     *        msg.setContent("ִ��ĳ����ʱ�������������������������Ƿ���ȷ��");
     *        msg.setBackURL("javascript:history.back()");
     *         request.setAttribute("message",msg);
     */
    public void forwardErrorPage(
        HttpServletRequest request, 
        HttpServletResponse response) {
        User user = (User)getObjectFromSession(request, "userContext");
        try {
            if(user != null){
                user.restoreContext();
            }

            RequestDispatcher requestdispatcher = 
                getServletContext().getRequestDispatcher(getErrorPage(request)); 
            requestdispatcher.forward(request, response);
        }
        /*
         * ��ʵ�ʱ���У�Ӧ�ø���ʵ�ʴ������ͷֱ�����Ϊȱ����Դ������
         * ������ʱͳһ����Ϊֱ�Ӵ�ӡ�����
         */
        catch (Exception ex) {
            log("Forward page error:" + errorJsp, ex);
        }
    }

    /**
     * �������û������Ĵ�����ʾ�����ص�ȱʡ�Ĳ����������ʾҳ�档
     * 
     * @param expMsg ��������ʱ�Ĵ�����Ϣ
     */
    public void forwardErrorPage(HttpServletRequest request, 
    HttpServletResponse response, String expMsg) {
        forwardErrorPage(request,response,expMsg,null);
    }

    /**
     * �������û������Ĵ�����ʾ�����ص�ȱʡ�Ĳ����������ʾҳ�档
     * 
     * @param expMsg ��������ʱ�Ĵ�����Ϣ
     */
    public void forwardErrorPage(HttpServletRequest request, 
    HttpServletResponse response, 
    String expMsg, String backURL) {
        User user = (User)getObjectFromSession(request, "context");
        try {
            Message msg = new Message();
            msg.setContent(expMsg);
            if(backURL == null){
                msg.setBackURL("javascript:history.back()");
            }else{
                msg.setBackURL(backURL);
            }
            if(user != null){
                user.restoreContext();
            }

            request.setAttribute("message", msg);
            RequestDispatcher requestdispatcher = 
                getServletContext().getRequestDispatcher(getErrorPage(request)); 
            requestdispatcher.forward(request, response);
        }
        /*
         * ��ʵ�ʱ���У�Ӧ�ø���ʵ�ʴ������ͷֱ�����Ϊȱ����Դ������
         * ������ʱͳһ����Ϊֱ�Ӵ�ӡ�����
         */
        catch (Exception ex) {
            log("Forward page error:" + errorJsp, ex);
        }
    }

    /**
     * �������û���ر�ָ���ķ���ҳ�棬���ص�ȱʡ�Ĳ�����ɲ��ɹ�����ʾҳ�档
     * ���ô˷�������Ԥ������ResultMessage�������������ɹ�������ö���
     * ���������������ԣ�
     *        content,backURL
     * �ö��������������ʽ����request�����У�
     *        request.setAttribute("message",resultMessage);
     * for example:
     *         ResultMessage msg = new ResultMessage();
     *        msg.setContent("ִ��ĳ����ʱ�������������������������Ƿ���ȷ��");
     *        msg.setBackURL("javascript:history.back()");
     *         request.setAttribute("message",msg);
     */
    public void forwardSuccessPage(
        HttpServletRequest request, 
        HttpServletResponse response) {
        try {
            RequestDispatcher requestdispatcher = 
                getServletContext().getRequestDispatcher(successJsp); 
            requestdispatcher.forward(request, response);
        }
        /*
         * ��ʵ�ʱ���У�Ӧ�ø���ʵ�ʴ������ͷֱ�����Ϊȱ����Դ������
         * ������ʱͳһ����Ϊֱ�Ӵ�ӡ�����
         */
        catch (Throwable ex) {
            log("Forward page error:" + successJsp, ex);
        }
    }

    /**
     * ������ʾҳ�棬
     */
    public void forwardAlertPage(HttpServletRequest request, 
    HttpServletResponse response, String message, String backURL) {
        try {
            Message msg = new Message("��ʾ", message, backURL);
            request.setAttribute("message", msg);
            RequestDispatcher requestdispatcher = 
                getServletContext().getRequestDispatcher(alertJsp); 
            requestdispatcher.forward(request, response);
        }catch (Throwable ex) {
            log("Forward page error:" + successJsp, ex);
        }
    }

    /**
     * ϵͳ����Ψһһ������Դ������ʵ����ƽ̨���Դ��κεط�ͨ���ù�������ȡ����Դ��
     */
    public static ESSDataStoreManager getDataStore() {
        return ESSDataStoreManager.getInstance();
    }

    /**
     * ������洢��session�С�
     */
    protected void addObjectToSession(
        HttpServletRequest request, 
        String index, 
        Object obj) {
        HttpSession session = request.getSession(true);
        if (obj == null) {
            session.removeAttribute(index);
        } else {
            session.setAttribute(index, obj);
        }
    }

    /**
     * ��session�л�ȡ����
     * @exception ECSContextNotExistedException ��ָ���Ķ��󲻴���ʱ�׳����쳣��
     */
    protected Object getObjectFromSession(HttpServletRequest request, String index){

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object obj = session.getAttribute(index);

            return obj;
        } else {
            return null;
        }
    }

    /**
     * ��request�л�ȡָ�������ƵĶ���
     * @exception ECSParameterParseException ��ָ���Ĳ������󲻴����Ҹò�������ʱ�׳����쳣��
     */
    protected String getParameter(
        HttpServletRequest request, 
        String name, 
        boolean isRequired, 
        String defaultValue, 
        String message)
        throws ESSParameterParseException {

        String objValue = null;

        // Get the parameter values with specified parameter name
        String[] values = request.getParameterValues(name);
        if (values != null) {
            objValue = values[0];
        }

        // If it is required but the value is null, throw the exception
        if (isRequired && (objValue == null || objValue.equals(""))) {
            throw new ESSParameterParseException(message,name);
        }

        // If it is not specified and is not required, use the default value
        if (objValue == null || objValue.equals("")) {
            objValue = defaultValue;
        }

        return objValue;
    }

    /**
     * ��request�л�ȡָ�������ƵĶ���ֵ��
     * @exception ECSParameterParseException ��ָ���Ĳ������󲻴����Ҹò�������ʱ�׳����쳣��
     */
    protected String[] getParameter(HttpServletRequest request, 
        String name, boolean isRequired, String message)
        throws ESSParameterParseException {

        // Get the parameter values with specified parameter name
        String[] values = request.getParameterValues(name);

        // If it is required but the value is null, throw the exception
        if (isRequired && (values == null || values.length==0)) {
            throw new ESSParameterParseException(message,name);
        }

        return values;
    }

    /**
     * ��session�г�ȥ����
     *
     * @param index ��������
     */
    protected void removeObjectFromSession(HttpServletRequest request, String index){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(index);
        }
    }

    /**
     * ���ص�ǰ����־����
     */
    public LogWriter getLogWriter() {
        return LogWriter.getInstance();
    }

    /**
     * �����Ϣ��ƽ̨ȱʡ����־�ļ���
     */
    public void log(String msg) {
        //super.log(msg);
        getLogWriter().log(this, msg, LogWriter.INFO);
    }

    /**
     * �����Ϣ��ƽ̨ȱʡ����־�ļ���
     */
    public void log(Throwable t, String msg) {
        super.log(msg,t);
        getLogWriter().log(this, t, msg, LogWriter.ERROR);
    }

    /**
     * ��ָ����ResultMessage�������request�����С��ڵ���
     * forwardErrorPage(request,response) ��
     * forwardSuccessPage(request,response)ǰӦֱ�ӻ��ӵ��ô˷���
     *
     * @param request javax.servlet.http.HttpServletRequest
     */
    public void putMessage(HttpServletRequest request, Message msg) {
        request.setAttribute("message",msg);
    }
    
    private String getErrorPage(HttpServletRequest request){
        String page = errorJsp;
        String subPage = null;
        try{
            subPage = getParameter(request,"subpage",false,null,null);
        }catch(ESSParameterParseException pex){
        }
        //����Ƿ�Ϊ�Ӵ���
        if(subPage != null){
            page = openErrorJsp;
        }

        return page;
    }
    
    public String getApplicationTempDirectory(){
        return getServletContext().getRealPath("/")+ java.io.File.separatorChar+"app_temp";
    }
    
}
