package com.modules.prime.test.aop.reflect;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {
    public String a(String a, String b){
        System.out.println("test"+a+b);
        return "gg";
    }
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException, InterruptedException {

        Class<?> clazz = ReflectTest.class.getClassLoader().loadClass("com.modules.prime.test.aop.reflect.ReflectTest");
        Object ins = clazz.newInstance();
        Method[] methods = ReflectTest.class.getMethods();
        for(Method method:methods){
            if(method.getName() != "a") continue;
            //method.getAnnotation();
            method.getDeclaredAnnotations();
            Class<?> retType = method.getReturnType();
            Object c = retType.newInstance();
            c = method.invoke(ins, "a", "b");
            System.out.println(c);
        }
    }

}
