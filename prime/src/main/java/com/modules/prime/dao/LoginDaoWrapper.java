package com.modules.prime.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;

public class LoginDaoWrapper {

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
