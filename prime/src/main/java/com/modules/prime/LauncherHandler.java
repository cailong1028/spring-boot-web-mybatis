package com.modules.prime;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

import java.io.File;
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

        for(String oneName:classNames){
            logger.info(oneName);
        }
        logger.info(String.valueOf(classNames.size()));
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
                logger.info("非class文件: [%s]", oneName);
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
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

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
