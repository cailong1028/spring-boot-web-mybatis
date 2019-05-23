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
 * 基础业务处理控制类，为业务处理的入口。原则上，处理业务处理
 * 的Servlet都应继承该类。此类封装了用户上下文过期和获取上下文
 * 信息的接口。对于编号形式的业务权限验证，需要在具体业务实现
 * 时完成验证。
 *
 * @see ESSServlet
 */
public abstract class ESSTask extends ESSServlet {
    /**
     * 完成执行命令时的初始化方法。
     */
    public void perform(HttpServletRequest request, HttpServletResponse response) {

        /*
         * 任何Task形式的任务，也就是servlet,也就是系统内部逻辑功能的入口，需要
         * 实现平台的第一次验证，那就是会员资格验证，而会员资格的验证是通过检查
         * 用户是否登陆来实现的，注意，因为这里只是检查用户的会员资格是否得到认
         * 证如果得到认证，那么在这里系统并不检查用户是否拥有执行指定逻辑的权限，
         * 关于逻辑权限的验证要在逻辑功能模块中完成。这里存在如下特殊情况：
         *　１、大部分访问并不是由Servlet发起，而是由html或是jsp,这里的建议是将
         *      所有的html都变成jsp,并且为jsp写一个父类，然后在这个父类中实现用
         *      户登陆验证。
         *　２、另外，系统的一些功能，为了实现广告性的效果，并不需要实现授权管理，
         *      这时建议的做法是，在页面中为用户提供一个guest用户，该用户无需登陆，
         *      直接部分浏览页面功能，这个guest用户实际上与普通用户的管理相同。
         *  3、系统的另外一个问题是，当用户登陆后，试图通过某个入口，包括Servlet、
         *     JSP、html访问逻辑功能时，系统应该验证当前的登陆用户是否拥有执行这
         *     个功能的权限。而平台逻辑的实现包括两种模式，一种是直接实现，另一种
         *     是接口实现，分别对应着两种应用场景。当平台逻辑为支撑逻辑时，包括基
         *     础框架、技术支撑和业务支撑对象，都采用直接实现，当平台逻辑为业务逻
         *     辑时，为了支撑用户的多样性， 采用接口实现，这样一个接口可以支撑多
         *     种实现方式。
         *　另外，平台实现用户权限验证需要具备三个要素：当前用户，也就是当前试图调用逻辑的用户是谁，也就是说平台
         *　必须纪录当前用户的身份，因为平台的多用户性，这种跟踪方法是由HTTP协议的cookie-session机制来完成的；
         *  用户权限配置，这种权限配置是存放在数据库中的，可以利用当前用户身份从数据库中获取，但是，最常用的方法还是
         *　将这种信息保存在session对象中；第三个要素是用户当前试图调用的功能，这里可以在调用时或是被调用时实现，
         *  为了统一的实现，我建议采用在被调用时完成，这时对于直接调用，需要每个方法在执行前必须加上权限检查语句，而
         *　对于接口调用，则可以由基础ＡＰＩ统一实现。
         *  另外，权限控制可以有两种模式：其一、是防患于未然，即让用户看不见其没有权限的接口；其二、是实时检查，即当用户在调用时
         *  检查是否有权执行当前功能。无论是采用哪一种形式，平台都必须能够识别当前的用户，这种当前用户识别是通过session完成，一旦获取
         *  当前用户的session后，用户可以将这个session对象设置到它自己创建的任何对象中去，这时session对象的生命周期实际上并不受
         *  web服务器对session的管理，所以说这时平台的权限保护必须严格控制在接口层上，即servlet、jsp和html中。但是，用户扩展的session对象
         * 如果并不存放在平台的全局变量环境中，那么这样的扩展实际上生命周期就是用户请求的线程的生命周期，除非程序有漏洞，不然不会存在
         * 安全隐患。
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
                        forwardAlertPage(request,response, "已经提交完成，请继续其它业务","/Welcome.jsp");
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
                request.setAttribute("message","您的登录已过期，请重新登录");
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
     * 当任务完成后记录日志信息。
     */
    protected void logTask(String userID, String authID, String msg){
        try{
            (new com.ess.log.LogBO()).addLog(userID, authID,msg);
        }catch(Throwable t){}
    }
    
    /**
     * 返回用户的上下文信息。当用户第一次登录系统时，将生成用户
     * 上下文信息并放入session中以标识用户，用于判别客户的访问权
     * 限及客户的基本信息。生成的客户基本信息存放在UserContext
     * 对象中。
     *
     * @see ess.base.bo.UserContext
     * @exception ESSContextNotExistedException 当session过期或
     *            用户上下文不存在时抛出此异常。
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
