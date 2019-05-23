/******************************************************************************
    File: ESSTask.java

    Version 1.0
    Date            Author          Changes
    Feb.10,2003     Lishengwang     Created

    Copyright (c), 2003, Eagle Soar 
    all rights reserved
******************************************************************************/
package ess.base.task;

import javax.servlet.http.*;

//import ess.base.bo.*;
import ess.base.exp.*;

import ess.base.bo.User;
import ess.base.bo.UserContext;

/**
 * ����ҵ��������࣬Ϊҵ�������ڡ�ԭ���ϣ�����ҵ����
 * ��Servlet��Ӧ�̳и��ࡣ�����װ���û������Ĺ��ںͻ�ȡ������
 * ��Ϣ�Ľӿڡ����ڱ����ʽ��ҵ��Ȩ����֤����Ҫ�ھ���ҵ��ʵ��
 * ʱ�����֤��
 *
 * @see ESSServlet
 */
public abstract class ESSTask extends ESSServlet {
    /**
     * ���ִ������ʱ�ĳ�ʼ��������
     */
    public void perform(HttpServletRequest request, HttpServletResponse response) {

        /*
         * �κ�Task��ʽ������Ҳ����servlet,Ҳ����ϵͳ�ڲ��߼����ܵ���ڣ���Ҫ
         * ʵ��ƽ̨�ĵ�һ����֤���Ǿ��ǻ�Ա�ʸ���֤������Ա�ʸ����֤��ͨ�����
         * �û��Ƿ��½��ʵ�ֵģ�ע�⣬��Ϊ����ֻ�Ǽ���û��Ļ�Ա�ʸ��Ƿ�õ���
         * ֤����õ���֤����ô������ϵͳ��������û��Ƿ�ӵ��ִ��ָ���߼���Ȩ�ޣ�
         * �����߼�Ȩ�޵���֤Ҫ���߼�����ģ������ɡ���������������������
         *�������󲿷ַ��ʲ�������Servlet���𣬶�����html����jsp,����Ľ����ǽ�
         *      ���е�html�����jsp,����Ϊjspдһ�����࣬Ȼ�������������ʵ����
         *      ����½��֤��
         *���������⣬ϵͳ��һЩ���ܣ�Ϊ��ʵ�ֹ���Ե�Ч����������Ҫʵ����Ȩ����
         *      ��ʱ����������ǣ���ҳ����Ϊ�û��ṩһ��guest�û������û������½��
         *      ֱ�Ӳ������ҳ�湦�ܣ����guest�û�ʵ��������ͨ�û��Ĺ�����ͬ��
         *  3��ϵͳ������һ�������ǣ����û���½����ͼͨ��ĳ����ڣ�����Servlet��
         *     JSP��html�����߼�����ʱ��ϵͳӦ����֤��ǰ�ĵ�½�û��Ƿ�ӵ��ִ����
         *     �����ܵ�Ȩ�ޡ���ƽ̨�߼���ʵ�ְ�������ģʽ��һ����ֱ��ʵ�֣���һ��
         *     �ǽӿ�ʵ�֣��ֱ��Ӧ������Ӧ�ó�������ƽ̨�߼�Ϊ֧���߼�ʱ��������
         *     ����ܡ�����֧�ź�ҵ��֧�Ŷ��󣬶�����ֱ��ʵ�֣���ƽ̨�߼�Ϊҵ����
         *     ��ʱ��Ϊ��֧���û��Ķ����ԣ� ���ýӿ�ʵ�֣�����һ���ӿڿ���֧�Ŷ�
         *     ��ʵ�ַ�ʽ��
         *�����⣬ƽ̨ʵ���û�Ȩ����֤��Ҫ�߱�����Ҫ�أ���ǰ�û���Ҳ���ǵ�ǰ��ͼ�����߼����û���˭��Ҳ����˵ƽ̨
         *�������¼��ǰ�û�����ݣ���Ϊƽ̨�Ķ��û��ԣ����ָ��ٷ�������HTTPЭ���cookie-session��������ɵģ�
         *  �û�Ȩ�����ã�����Ȩ�������Ǵ�������ݿ��еģ��������õ�ǰ�û���ݴ����ݿ��л�ȡ�����ǣ���õķ�������
         *����������Ϣ������session�����У�������Ҫ�����û���ǰ��ͼ���õĹ��ܣ���������ڵ���ʱ���Ǳ�����ʱʵ�֣�
         *  Ϊ��ͳһ��ʵ�֣��ҽ�������ڱ�����ʱ��ɣ���ʱ����ֱ�ӵ��ã���Ҫÿ��������ִ��ǰ�������Ȩ�޼����䣬��
         *�����ڽӿڵ��ã�������ɻ������У�ͳһʵ�֡�
         *  ���⣬Ȩ�޿��ƿ���������ģʽ����һ���Ƿ�����δȻ�������û���������û��Ȩ�޵Ľӿڣ��������ʵʱ��飬�����û��ڵ���ʱ
         *  ����Ƿ���Ȩִ�е�ǰ���ܡ������ǲ�����һ����ʽ��ƽ̨�������ܹ�ʶ��ǰ���û������ֵ�ǰ�û�ʶ����ͨ��session��ɣ�һ����ȡ
         *  ��ǰ�û���session���û����Խ����session�������õ����Լ��������κζ�����ȥ����ʱsession�������������ʵ���ϲ�����
         *  web��������session�Ĺ�������˵��ʱƽ̨��Ȩ�ޱ��������ϸ�����ڽӿڲ��ϣ���servlet��jsp��html�С����ǣ��û���չ��session����
         * ������������ƽ̨��ȫ�ֱ��������У���ô��������չʵ�����������ھ����û�������̵߳��������ڣ����ǳ�����©������Ȼ�������
         * ��ȫ������
         */
        String subPage = null;
        String context = null;
        try{
            subPage = getParameter(request,"subpage",false,null,null);
            context = getParameter(request,"context",false,null,null);
        }catch(ESSParameterParseException pex){
        }

        try {
            UserContext user = (UserContext)getUserContext(request);
            try {
                if(context != null){
                    if(!context.equals(user.getContext())){
                        forwardAlertPage(request,response, "�Ѿ��ύ��ɣ����������ҵ��","/Welcome.jsp");
                        return;
                    }
                    user.setContext(null);
                }

                execute(request, response);
            } catch (Throwable t) {
                log("Not processed exception", t);
                forwardErrorPage(request, response, t.getMessage());
            }
        } catch (ESSContextNotExistedException ex) {
            if(subPage == null){
                request.setAttribute("message","���ĵ�¼�ѹ��ڣ������µ�¼");
                forward(request, response, expireJsp);
            }else{
                try{
                    execute(request,response);
                } catch (Throwable t) {
                    log("Not processed exception", t);
                    forwardErrorPage(request, response, t.getMessage());
                }
            }
        }
    }

    /**
     * ��������ɺ��¼��־��Ϣ��
     */
    protected void logTask(String userID, String authID, String msg){
        try{
            (new com.ess.log.LogBO()).addLog(userID, authID,msg);
        }catch(Throwable t){}
    }
    
    /**
     * �����û�����������Ϣ�����û���һ�ε�¼ϵͳʱ���������û�
     * ��������Ϣ������session���Ա�ʶ�û��������б�ͻ��ķ���Ȩ
     * �޼��ͻ��Ļ�����Ϣ�����ɵĿͻ�������Ϣ�����UserContext
     * �����С�
     *
     * @see ess.base.bo.UserContext
     * @exception ESSContextNotExistedException ��session���ڻ�
     *            �û������Ĳ�����ʱ�׳����쳣��
     */
    protected User getUserContext(HttpServletRequest request)
    throws ESSContextNotExistedException {
        User user = (User)getObjectFromSession(request, "userContext");

        if(user == null){
            throw new ESSContextNotExistedException();
        }

        return user;
    }
}
