package ess.base.task;

import javax.servlet.http.*;

/**
 * 监视数据库连接情况的工具类
 */
public class Monitor extends ESSServlet {
    /**
     * 本方法预留给编程人员用于完成指定的逻辑，并且编程人员只需在此处添加逻辑。
     * 另外，如果有一些例外处理不了、或是可以由缺省方法处理，则这样的例外可以
     * 不管。
     */
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String dbName = getParameter(request,"dbname",false,"access",null);

        String message = getDataStore().monitorConnection(dbName);

        //System.out.println(message.current);
        
        request.setAttribute("message",message);
        forward(request,response,"/monitor.jsp");
    }
}
