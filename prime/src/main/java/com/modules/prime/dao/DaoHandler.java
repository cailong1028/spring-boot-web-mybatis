package com.modules.prime.dao;

import com.modules.prime.annotation.Sql;
import com.modules.prime.biz.BizHandler;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import com.modules.prime.sql.mysql.SBo;

import java.lang.reflect.*;
import java.util.List;

public class DaoHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(DaoHandler.class);
    public DaoHandler(Class<?> c){
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        SBo sbo = BizHandler.localSbo.get();
        Sql sqlAnno = method.getAnnotation(Sql.class);
        String sql = sqlAnno.value();
        beforeInvoke();
        Object ret = sbo.query(sql, args);
        //Object ret = method.invoke(this.dao, args);
        afterInvoke();
        return ret;
    }

    private void beforeInvoke(){
        //logger.info("before");
    }

    private void afterInvoke(){
        //判定方法调用最后的终结
        //logger.info("after");
    }

    public static void main(String[] args){
        LoginDao loginDao = (LoginDao) DaoWrapper.wrap(LoginDao.class);
        List list = loginDao.login("cl");

        logger.info("query list size %d", list.size());
    }
}

class DaoWrapper {
    public static Object wrap(Class<?> daoClazz){
        Object obj = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{daoClazz}, new DaoHandler(daoClazz));
        return obj;
    }
}

//参考
@Deprecated
class LoginDaoWrapper {
    public static <T> T getWrapper(Class<?> Handler, Class<?> BizImp) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = Handler.getConstructor(BizImp);
//        Object obj = java.lang.reflect.Proxy.newProxyInstance(
//                Biz.getClassLoader(),
//                BizImp.getInterfaces(),
//                (InvocationHandler) constructor.newInstance(BizImp.newInstance()));
        T proxy = (T) java.lang.reflect.Proxy.newProxyInstance(
                BizImp.getClassLoader(),
                BizImp.getInterfaces(),
                (InvocationHandler) constructor.newInstance(BizImp.newInstance()));
        return proxy;
    }

}
