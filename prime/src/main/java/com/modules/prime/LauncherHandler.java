package com.modules.prime;

import com.modules.prime.annotation.Autowired;
import com.modules.prime.annotation.Component;
import com.modules.prime.annotation.Service;
import com.modules.prime.biz.BizHandler;
import com.modules.prime.biz.imp.LoginBizImp;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import sun.jvm.hotspot.oops.FieldVisitor;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
            logger.warn("未发现扫描类");
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
            logger.debug("scan one service: [%s]", oneService.getName());
            Object serviceObject = Proxy.newProxyInstance(LauncherHandler.class.getClassLoader(), oneService.getInterfaces(), new BizHandler(oneService));
            Context.addService(oneService.getName(), serviceObject);
        }
        for(Class<?> oneComponent:components){
            logger.debug("scan one component: [%s]", oneComponent.getName());
            try {
                Object componentObject = oneComponent.newInstance();
                //Field[] fields = oneComponent.getFields();
                Field[] fields = oneComponent.getDeclaredFields();
                for(Field oneField: fields){
                    if(oneField.getAnnotation(Autowired.class) != null){
                        //Class<?> oneFieldClass = oneField.getClass();
                        Class<?> oneFieldType = oneField.getType();
                        String oneFieldTypeName = oneFieldType.getName();
                        String fieldName = oneField.getName();
                        Object service = Context.getService(oneFieldTypeName);
                        if(service == null){
                            logger.error("init Component [%s] FieldType [%s] FieldName [%s] failed", oneComponent.getName(), oneFieldTypeName, fieldName);
                        }

                        //oneFieldType.getDeclaredMethods()
                        //oneComponent.getMethod("set");
                    }
                }
                Context.addComponent(oneComponent.getName(), componentObject);
            } catch (InstantiationException e) {
                logger.error(e);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
    }

    private List<String> scan(String path, List<String> names){
        if(path.endsWith(".jar")){
            //TODO 加载jar文件待处理
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
