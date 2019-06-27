package com.modules.prime.biz;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import com.modules.prime.sql.mysql.SBo;

import java.lang.reflect.*;

public class BizHandler implements InvocationHandler {

    public static Logger logger = LoggerFactory.getLogger(BizHandler.class);

    public static ThreadLocal<SBo> localSbo = new ThreadLocal<>();

    Biz biz;

    public BizHandler(Class<?> c, Object initBean){
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
            if(initBean != null){
                this.biz = (Biz) initBean;
            }else{
                this.biz = (Biz) c.newInstance();
            }


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
        Class<?> invokerType = this.biz.getClass();
        beforeInvoke(invokerType, method);
        Object ret = null;
        try {
            ret = method.invoke(this.biz, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            logger.error(e);
        }finally {
            afterInvoke(invokerType, method);
        }
        return ret;
    }

    private void beforeInvoke(Class<?> invokerType, Method method){
        SBo _sbo = localSbo.get();
        if(null == _sbo || !_sbo.isValid()){
            localSbo.set(new SBo());
            _sbo = localSbo.get();
        }
        //todo 判断是否使用新的sbo (默认同一个方法内部使用同一个sbo)
        _sbo.deepAdd(invokerType, method);
    }

    private void afterInvoke(Class<?> invokerType, Method method){
        localSbo.get().deepReduce(invokerType, method);
        if(localSbo.get().getDeep() == 0){
            localSbo.get().commit();
        }
    }
}
