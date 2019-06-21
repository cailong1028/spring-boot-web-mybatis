package com.modules.prime.biz;

import com.modules.prime.annotation.BoSession;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import com.modules.prime.sql.mysql.SBo;

import java.lang.reflect.*;

public class BizHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(BizHandler.class);

    public static ThreadLocal<SBo> localSbo = new ThreadLocal<>();

    LoginBiz biz;
    private Class<?> bizType;

    public BizHandler(Class<?> c){
        Class<?> bizInft = null;
        //Type[] intfs = c.getInterfaces();
        Class<?>[] intfs = c.getInterfaces();
        for(Class<?> type:intfs){
            if(Biz.class.isAssignableFrom(type)){
                bizInft = type;
                break;
            }
        }
        if(bizInft == null){
            logger.error("Class %s is not a BoBiz", c.getName());
            return;
        }
        try {
            this.biz = (LoginBiz) c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //BoSession boSession = c.getAnnotation(BoSession.class);

    }

    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     *
     * @param proxy  the proxy instance that the method was invoked on
     * @param method the {@code Method} instance corresponding to
     *               the interface method invoked on the proxy instance.  The declaring
     *               class of the {@code Method} object will be the interface that
     *               the method was declared in, which may be a superinterface of the
     *               proxy interface that the proxy class inherits the method through.
     * @param args   an array of objects containing the values of the
     *               arguments passed in the method invocation on the proxy instance,
     *               or {@code null} if interface method takes no arguments.
     *               Arguments of primitive types are wrapped in instances of the
     *               appropriate primitive wrapper class, such as
     *               {@code java.lang.Integer} or {@code java.lang.Boolean}.
     * @return the value to return from the method invocation on the
     * proxy instance.  If the declared return type of the interface
     * method is a primitive type, then the value returned by
     * this method must be an instance of the corresponding primitive
     * wrapper class; otherwise, it must be a type assignable to the
     * declared return type.  If the value returned by this method is
     * {@code null} and the interface method's return type is
     * primitive, then a {@code NullPointerException} will be
     * thrown by the method invocation on the proxy instance.  If the
     * value returned by this method is otherwise not compatible with
     * the interface method's declared return type as described above,
     * a {@code ClassCastException} will be thrown by the method
     * invocation on the proxy instance.
     * @throws Throwable the exception to throw from the method
     *                   invocation on the proxy instance.  The exception's type must be
     *                   assignable either to any of the exception types declared in the
     *                   {@code throws} clause of the interface method or to the
     *                   unchecked exception types {@code java.lang.RuntimeException}
     *                   or {@code java.lang.Error}.  If a checked exception is
     *                   thrown by this method that is not assignable to any of the
     *                   exception types declared in the {@code throws} clause of
     *                   the interface method, then an
     *                   {@link UndeclaredThrowableException} containing the
     *                   exception that was thrown by this method will be thrown by the
     *                   method invocation on the proxy instance.
     * @see UndeclaredThrowableException
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        //BoSession boSession = method.getAnnotation(BoSession.class);

        beforeInvoke();
        Object ret = null;
        try {
            ret = method.invoke(this.biz, args);
        } catch (IllegalAccessException e) {
            logger.error(e);
        } catch (InvocationTargetException e) {
            logger.error(e);
        }finally {
            afterInvoke();
        }
        return ret;
    }

    private void beforeInvoke(/*BoSession boSession*/){
        //判定初次进入bo方法还是多次进入
//        if(null == boSession){
//            sbo = new SBo();
//        }else{
//            sbo = new SBo(boSession.value());
//        }
        if(null == localSbo.get()){
            localSbo.set(new SBo());
        }
        localSbo.get().deepAdd();
        logger.info("before1");
    }

    private void afterInvoke(){
        //判定方法调用最后的终结
        localSbo.get().deepReduce();
        if(localSbo.get().getDeep() == 0){
            localSbo.get().commit();
        }
        logger.info("after1");
    }
}
