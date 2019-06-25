package com.modules.prime;

import com.modules.prime.annotation.Autowired;
import com.modules.prime.annotation.Component;
import com.modules.prime.annotation.Service;
import com.modules.prime.aop.asm.ChangeCurrentClazz;
import com.modules.prime.aop.asm.GetterSetterGenerator;
import com.modules.prime.biz.BizHandler;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LauncherHandler implements InvocationHandler {
    private static final transient Logger logger = LoggerFactory.getLogger(LauncherHandler.class);

    private Launcher app;

    private String rootPath;

    private LauncherHandler(Object o) {
        app = (Launcher)o;
    }

    private void beforLaunch() {

        doScan(".");

    }

    private void doScan(String packageName){
        String resourcePath = packageName.replaceAll("\\.", File.separator);
        URL packageUrl = LauncherHandler.class.getClassLoader().getResource(resourcePath.equals(File.separator) ? "" : resourcePath);
        if(packageUrl == null){
            logger.warn("scan package [%s] is null", packageUrl.getFile());
            return;
        }
        String packagePath = packageUrl.getFile();
        rootPath = packagePath.substring(0, packagePath.lastIndexOf(resourcePath));
        String realPath = getPath(packageUrl.getFile());
        List<String> classNames = new ArrayList<>();
        classNames = scan(realPath, classNames);

        List<Class<?>> daos = new ArrayList<>();
        List<Class<?>> services = new ArrayList<>();
        List<Class<?>> components = new ArrayList<>();
        for(String oneName:classNames){
            try {
                Class<?> aClass = Class.forName(oneName);
                if(aClass.getAnnotation(Service.class) != null){
                    services.add(aClass);
                }
                if(aClass.getAnnotation(Component.class) != null){
                    components.add(aClass);
                }
            } catch (ClassNotFoundException e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
        for(Class<?> oneService:services){
            //TODO 暂时只允许service继承一个接口
            Class[] intfs = oneService.getInterfaces();
            if(intfs.length != 1){
                logger.error("service [%s] should only implements one interface, but not is [%d], init fail", oneService.getName(), intfs.length);
                continue;
            }
            logger.debug("scan one service: [%s]", oneService.getName());
            Object serviceObject = Proxy.newProxyInstance(LauncherHandler.class.getClassLoader(), oneService.getInterfaces(), new BizHandler(oneService));
            //Context.addService(oneService.getName(), serviceObject);
            Context.addService(intfs[0].getName(), serviceObject);
        }
        for(Class<?> oneComponent:components){
            logger.debug("scan one component: [%s]", oneComponent.getName());
            try {
                GetterSetterGenerator getterSetterGenerator = new GetterSetterGenerator();
                Class newOneComponent = getterSetterGenerator.generateGetterSetter(oneComponent);
                //原类，不是newOneComponent
                ChangeCurrentClazz changeFieldModifier = new ChangeCurrentClazz(oneComponent);

                Object componentObject = newOneComponent.newInstance();
                //初始化(setField) Autowired filed
                Field[] fields = newOneComponent.getDeclaredFields();
                for(Field oneField:fields){
                    if(oneField.getAnnotation(Autowired.class) != null){

                        Class fieldType = oneField.getType();
                        String fieldName = oneField.getName();

                        //修改原类field modifier
                        changeFieldModifier.changeFieldModifier(fieldName, Opcodes.ACC_PROTECTED);
                        //重新加载修改的class
                        //oneComponent = LauncherHandler.class.getClassLoader().loadClass(oneComponent.getName());
                        //LauncherHandler.class.getClassLoader().cl
                        oneComponent = Class.forName(oneComponent.getName());
                        String beanClassName = fieldType.getName();
                        String setMethodName = "set"+GetterSetterGenerator.upperCaseFirstChar(fieldName);
                        Method setMethod = newOneComponent.getMethod(setMethodName, fieldType);
                        if(setMethod == null){
                            logger.error("no such set method [%s:%s] found", newOneComponent.getName(), setMethodName);
                            continue;
                        }
                        Object beanInitInstance = Context.getService(beanClassName);
                        if(beanInitInstance == null){
                            logger.error("no such init instance [%s] found ", newOneComponent.getName());
                            continue;
                        }
                        //beanInitInstance 是接口类型
                        setMethod.invoke(componentObject, beanInitInstance);
                    }
                }

                Context.addComponent(oneComponent.getName(), componentObject);
            } catch (InstantiationException e) {
                logger.error(e);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                logger.error(e);
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                logger.error(e);
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> scan(String path, List<String> names){
        if(path.endsWith(".jar")){
            //TODO 加载jar文件待处理
            logger.warn("jar file scan escape!");
            return names;
        }
        String prefix = path.substring(rootPath.length()).replaceAll(File.separator, "\\.");
        prefix = prefix.startsWith(".") ? prefix.substring(1) : prefix;
        File file = new File(path);
        File[] subFiles = file.listFiles();
        for(File oneFile:subFiles){
            String oneName = oneFile.getName();
            if(oneFile.isFile() && oneFile.getName().endsWith(".class")){

                StringBuffer sb = new StringBuffer(prefix);
                sb.append(".");
                int index = oneName.indexOf(".");
                if(index != -1){
                    oneName = oneName.substring(0, index);
                }
                sb.append(oneName);
                names.add(sb.toString());
            }else if(oneFile.isDirectory()){
                String dir = "";
                if(path.endsWith(File.separator)){
                    dir = path + oneName;
                }else{
                    dir = path+File.separator+oneName;
                }
                scan(dir, names);
            }else{
                //logger.debug("not a class: [%s]", oneName);
            }
        }
        return names;
    }

    private String getPath(String url){
        int index = url.indexOf("!");
        if(index == -1){
            return url;
        }
        return url.substring(5, index);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        if(method.getName().equals("run")){
            beforLaunch();
            this.app.run();
        }

        return null;
    }

    public static void handle(Class<?> appClazz){

        if(!Launcher.class.isAssignableFrom(appClazz)){
            logger.error("%s is not a launcher", appClazz.getName());
            return;
        }

        try {
            Object app = Proxy.newProxyInstance(appClazz.getClassLoader(), new Class[]{Launcher.class}, new LauncherHandler(appClazz.newInstance()));
            ((Launcher)app).run();
        } catch (InstantiationException e) {
            logger.error(e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}
