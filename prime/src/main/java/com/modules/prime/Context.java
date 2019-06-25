package com.modules.prime;

import com.modules.prime.aop.asm.reflect.BeanClassLoader;

import java.util.HashMap;
import java.util.Map;

public class Context {
    public static Map<String, Object> services = new HashMap<>();
    public static Map<String, Object> components = new HashMap<>();

    public static BeanClassLoader beanClassLoader = new BeanClassLoader();

    public static void addService(String serviceName, Object service){
        services.put(serviceName, service);
    }

    public static Object getService(String serviceName){
        return services.get(serviceName);
    }

    public static void addComponent(String componentName, Object component){
        components.put(componentName, component);
    }

    public static Object getComponent(String componentName){
        return components.get(componentName);
    }

    public static Object getComponent(Class clazz){
        return components.get(clazz.getName());
    }
}
