package com.modules.demo2.test.nio.netty.primary.annotation_interface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnoImp {

    @AnnotationTest
    public void doSomething(String str){
        System.out.println(str);
    }

    public void doSomething(){
        System.out.println("fixed !!!");
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<?>[] methodArgsTypes = {};
        Class<?>[] methodArgsTypes_2 = {};
//        List<Class<?>> list = Arrays.asList(methodArgsTypes);

        List<Class<?>> list = new ArrayList<>();
        Arrays.stream(methodArgsTypes).forEach(one -> {
            list.add(one);
        });
        list.add(String.class);
        Class<?>[] clzs = new Class[list.size()];
        Method method = AnnoImp.class.getMethod("doSomething", list.toArray(clzs));
        Method method2 = AnnoImp.class.getMethod("doSomething", methodArgsTypes);
        method.invoke(new AnnoImp(), new String("aaa"));
        method2.invoke(new AnnoImp());
        if(method.isAnnotationPresent(AnnotationTest.class)){
            System.out.println(method.getAnnotation(AnnotationTest.class));
        }


        list.stream().forEach(one -> {
            System.out.println("aa-->"+one);
        });

        //list array互转问题
        String[] strs = {"1", "2"};
        Arrays.stream(strs).forEach(one -> { System.out.println(one);});

        List list_2 = Arrays.asList(strs);
        list_2.stream().forEach(one -> {System.out.println("bbb-->"+one);});

        list_2.toArray(new String[list_2.size()]);

    }
}
