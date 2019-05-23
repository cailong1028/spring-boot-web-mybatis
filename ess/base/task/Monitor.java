package ess.base.task;

import javax.servlet.http.*;

/**
 * �������ݿ���������Ĺ�����
 */
public class Monitor extends ESSServlet {
    /**
     * ������Ԥ���������Ա�������ָ�����߼������ұ����Աֻ���ڴ˴�����߼���
     * ���⣬�����һЩ���⴦���ˡ����ǿ�����ȱʡ�����������������������
     * ���ܡ�
     */
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String dbName = getParameter(request,"dbname",false,"access",null);

        String message = getDataStore().monitorConnection(dbName);

        //System.out.println(message.current);
        
        request.setAttribute("message",message);
        forward(request,response,"/monitor.jsp");
    }
}
