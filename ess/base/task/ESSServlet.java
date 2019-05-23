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
 * 基础Servlet类，实现了参数获取和参数传递（session对象获取和存储，
 * 结果页面调用等）的基本功能。原则上，非业务控制类应从该类继承。
 */
public abstract class ESSServlet extends HttpServlet {
    /**
     * 平台缺省的错误提示页面。
     */
    protected String errorJsp = "/commonPage/error.jsp";
    protected String openErrorJsp = "/commonPage/openError.jsp";

    /**
     * 提示页面。
     */
    protected String alertJsp = "/commonPage/error.jsp";

    /**
     *　平台缺省的成功提示页面。
     */
    protected String successJsp = "/commonPage/success.jsp";

    /**
     * Session过期时结果页面。
     */
    protected java.lang.String expireJsp = "/Login.jsp";

    /**
     * 日志类。
     */
    private static LogWriter logWriter = null;

    /**
     * 处理Get命令，该方法调用一个perform()。
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
		response.setContentType( "text/html;charset=gb2312");
		//request.setCharacterEncoding("gb2312");	
        perform(request, response);
    }

    /**
     * 处理Post命令,该方法调用perform()。
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
		response.setContentType( "text/html;charset=gb2312");
        request.setCharacterEncoding("gb2312"); 
        perform(request, response);
    }

    /**
     * 对Task中未处理的异常进行处理。
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
     * 本方法预留给编程人员用于完成指定的逻辑，并且编程人员只需在此处添加逻辑。
     * 另外，如果有一些例外处理不了、或是可以由缺省方法处理，则这样的例外可以
     * 不管。
     */
    public abstract void execute(
        HttpServletRequest request, 
        HttpServletResponse response)
        throws Throwable;

    /**
     *　返回系统的响应页面，该响应页面可以是错误、成功或是用户指定的页面。
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
         * 在实际编程中，应该根据实际错误类型分别处理，因为缺少资源，所以
         * 本处暂时统一处理为直接打印输出。
         */
        catch (Exception ex) {
            log("Forward error:" + page, ex);
        }
    }

    /**
     * 返回指定的页面，转换当前的请求处理。这里是为了避免
     * 数据重复提交的情况。
     */
    protected void forwardDirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        }
        /*
         * 在实际编程中，应该根据实际错误类型分别处理，因为缺少资源，所以
         * 本处暂时统一处理为直接打印输出。
         */
        catch (Exception ex) {
            log("Forward error:", ex);
        }
    }

    /**
     * 如果请求没有特殊的错误提示，返回到缺省的操作错误的提示页面。
     * 调用此方法必须预先生成ResultMessage对象，用于描述错误结果。该对象
     * 必须设置以下属性：
     *        content,backURL
     * 该对象必须以以下形式置于request对象中：
     *        request.setAttribute("message",resultMessage);
     * for example:
     *         ResultMessage msg = new ResultMessage();
     *        msg.setContent("执行某操作时发生错误，请检查您的输入数据是否正确！");
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
         * 在实际编程中，应该根据实际错误类型分别处理，因为缺少资源，所以
         * 本处暂时统一处理为直接打印输出。
         */
        catch (Exception ex) {
            log("Forward page error:" + errorJsp, ex);
        }
    }

    /**
     * 如果请求没有特殊的错误提示，返回到缺省的操作错误的提示页面。
     * 
     * @param expMsg 发生错误时的错误消息
     */
    public void forwardErrorPage(HttpServletRequest request, 
    HttpServletResponse response, String expMsg) {
        forwardErrorPage(request,response,expMsg,null);
    }

    /**
     * 如果请求没有特殊的错误提示，返回到缺省的操作错误的提示页面。
     * 
     * @param expMsg 发生错误时的错误消息
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
         * 在实际编程中，应该根据实际错误类型分别处理，因为缺少资源，所以
         * 本处暂时统一处理为直接打印输出。
         */
        catch (Exception ex) {
            log("Forward page error:" + errorJsp, ex);
        }
    }

    /**
     * 如果请求没有特别指定的返回页面，返回到缺省的操作完成并成功的提示页面。
     * 调用此方法必须预先生成ResultMessage对象，用于描述成功结果。该对象
     * 必须设置以下属性：
     *        content,backURL
     * 该对象必须以以下形式置于request对象中：
     *        request.setAttribute("message",resultMessage);
     * for example:
     *         ResultMessage msg = new ResultMessage();
     *        msg.setContent("执行某操作时发生错误，请检查您的输入数据是否正确！");
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
         * 在实际编程中，应该根据实际错误类型分别处理，因为缺少资源，所以
         * 本处暂时统一处理为直接打印输出。
         */
        catch (Throwable ex) {
            log("Forward page error:" + successJsp, ex);
        }
    }

    /**
     * 返回提示页面，
     */
    public void forwardAlertPage(HttpServletRequest request, 
    HttpServletResponse response, String message, String backURL) {
        try {
            Message msg = new Message("提示", message, backURL);
            request.setAttribute("message", msg);
            RequestDispatcher requestdispatcher = 
                getServletContext().getRequestDispatcher(alertJsp); 
            requestdispatcher.forward(request, response);
        }catch (Throwable ex) {
            log("Forward page error:" + successJsp, ex);
        }
    }

    /**
     * 系统返回唯一一个数据源管理器实例，平台可以从任何地方通过该管理器获取数据源。
     */
    public static ESSDataStoreManager getDataStore() {
        return ESSDataStoreManager.getInstance();
    }

    /**
     * 将对象存储在session中。
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
     * 从session中获取对象。
     * @exception ECSContextNotExistedException 当指定的对象不存在时抛出此异常。
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
     * 从request中获取指定的名称的对象。
     * @exception ECSParameterParseException 当指定的参数对象不存在且该参数必需时抛出此异常。
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
     * 从request中获取指定的名称的对象值。
     * @exception ECSParameterParseException 当指定的参数对象不存在且该参数必需时抛出此异常。
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
     * 从session中除去对象。
     *
     * @param index 对象索引
     */
    protected void removeObjectFromSession(HttpServletRequest request, String index){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(index);
        }
    }

    /**
     * 返回当前的日志对象。
     */
    public LogWriter getLogWriter() {
        return LogWriter.getInstance();
    }

    /**
     * 添加消息到平台缺省的日志文件。
     */
    public void log(String msg) {
        //super.log(msg);
        getLogWriter().log(this, msg, LogWriter.INFO);
    }

    /**
     * 添加消息到平台缺省的日志文件。
     */
    public void log(Throwable t, String msg) {
        super.log(msg,t);
        getLogWriter().log(this, t, msg, LogWriter.ERROR);
    }

    /**
     * 将指定的ResultMessage对象放入request对象中。在调用
     * forwardErrorPage(request,response) 或
     * forwardSuccessPage(request,response)前应直接或间接调用此方法
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
        //检查是否为子窗口
        if(subPage != null){
            page = openErrorJsp;
        }

        return page;
    }
    
    public String getApplicationTempDirectory(){
        return getServletContext().getRealPath("/")+ java.io.File.separatorChar+"app_temp";
    }
    
}
