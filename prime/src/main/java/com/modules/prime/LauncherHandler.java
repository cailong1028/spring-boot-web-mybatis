package com.modules.prime;

import com.modules.prime.annotation.Autowired;
import com.modules.prime.annotation.Component;
import com.modules.prime.annotation.Dao;
import com.modules.prime.annotation.Service;
import com.modules.prime.aop.asm.GetterSetterGenerator;
import com.modules.prime.biz.BizHandler;
import com.modules.prime.dao.DaoHandler;
import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import com.modules.prime.util.AppProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class LauncherHandler implements InvocationHandler {
    private static final transient Logger logger = LoggerFactory.getLogger(LauncherHandler.class);

    private Launcher app;

    private String rootPath;

    private String packageName;

    private LauncherHandler(Object o) {
        app = (Launcher)o;
    }

    private void beforLaunch() {
        String scanPackage = AppProperties.get("scan.package");
        if(scanPackage != null){
            packageName = scanPackage;
            doScan();
        }

    }

    private void doScan(){
        String resourcePath = packageName.replaceAll("\\.", File.separator);
        String _path = resourcePath.equals(File.separator) ? "" : resourcePath;
        URL packageUrl = ClassLoader.getSystemClassLoader().getResource(_path);
//        URL packageUrl = LauncherHandler.class.getClassLoader().getResource(File.separator);
        if(packageUrl == null){

            logger.warn("scan package [%s] is null", _path);
            return;
        }
        String packagePath = packageUrl.getFile();
        String realPath = getPath(packageUrl.getFile());
        rootPath = packagePath.substring(0, packagePath.lastIndexOf(resourcePath));

        List<String> classNames = new ArrayList<>();
        classNames = scan(realPath, classNames);

        List<Class<?>> daos = new ArrayList<>();
        List<Class<?>> services = new ArrayList<>();
        List<Class<?>> components = new ArrayList<>();
        for(String oneName:classNames){
            try {
                //Class<?> aClass = Class.forName(oneName);
                //logger.debug("begin load class [%s]", oneName);
                Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass(oneName);

                if(aClass.getAnnotation(Dao.class) != null){
                    daos.add(aClass);
                    continue;
                }
                if(aClass.getAnnotation(Service.class) != null){
                    services.add(aClass);
                    continue;
                }
                if(aClass.getAnnotation(Component.class) != null){
                    components.add(aClass);
                    continue;
                }
            } catch (ClassNotFoundException e) {
                logger.error(e);
                e.printStackTrace();
            }
        }

        for(Class<?> oneDao:daos){
            logger.debug("scan one dao: [%s]", oneDao.getName());
            Object daoObject = Proxy.newProxyInstance(Context.defaultGeneratorClassLoader, new Class[]{oneDao}, new DaoHandler(oneDao));
            Context.addDao(oneDao.getName(), daoObject);
        }

        for(Class<?> oneService:services){
            //TODO 暂时只允许service继承一个接口
            Class[] intfs = oneService.getInterfaces();
            if(intfs.length != 1){
                logger.error("service [%s] should only implements one interface, but not is [%d], init fail", oneService.getName(), intfs.length);
                continue;
            }
            logger.debug("scan one service: [%s]", oneService.getName());

            Object[] resolve = do1(oneService, "dao");
            Object serviceBean = resolve[0];
            Class<?> newClass = (Class<?>) resolve[1];
            Object serviceObject = Proxy.newProxyInstance(Context.defaultGeneratorClassLoader, oneService.getInterfaces(), new BizHandler(newClass, serviceBean));
            //Context.addService(oneService.getName(), serviceObject);
            //service bean name取接口
            Context.addService(intfs[0].getName(), serviceObject);
        }
        for(Class<?> oneComponent:components){
            logger.debug("scan one component: [%s]", oneComponent.getName());

            if(oneComponent == null){
                logger.error("fail to init component: [%s] after ChangeFieldGenerator, will continue", oneComponent.getName());
            }

            Object componentBean = do1(oneComponent, "service")[0];
            Context.addComponent(oneComponent.getName(), componentBean);
        }
    }

    private String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    private List<String> scan(String path, List<String> names){
        if(path.endsWith(".jar")){
            logger.info("jar file [%s] scan", path);
            try {
                names = readFromJarFile(path, packageName);
            } catch (IOException e) {
                logger.error(e);
            }
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

    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {
        logger.debug("从JAR包中读取类: %s", jarPath);
        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();
        List<String> nameList = new ArrayList<>();
        while (null != entry) {
            String name = entry.getName().replaceAll(File.separator, ".");
            //logger.debug("one jar entry file name is %s", name);
            if (name.startsWith(splashedPackageName) && name.endsWith(".class")) {
                // 6 ==> ".class".length
                nameList.add(name.substring(0, name.length() - 6));
            }

            entry = jarIn.getNextJarEntry();
        }
        logger.debug("jar file nameList size %d", nameList.size());
        return nameList;
    }

    //todo 方式一
    //使用asm方式生成带getter setter、修改了autowired field modifier的继承原类的新类的实例对象
    //并使用Context.defaultGeneratorClassLoader加载，但是经过了双层类创建，proxy class调用super.field的时候调用到的是asm创建的新类
    //需要在BizHandler中把带autowired的实例传递过去
    private Object[] do1(Class oneComponent, String type){
        Object[] ret = new Object[2];
        try {
            GetterSetterGenerator getterSetterGenerator = new GetterSetterGenerator();
            Class newOneComponent = getterSetterGenerator.generateGetterSetter(oneComponent);
            ret[1] = newOneComponent;
            //原类，不是newOneComponent
            //ChangeCurrentClazz changeFieldModifier = new ChangeCurrentClazz(oneComponent);

            Object componentObject = newOneComponent.newInstance();
            Field[] fields = oneComponent.getDeclaredFields();
            //初始化(setField) Autowired filed
            for(Field oneField:fields){
                if(oneField.getAnnotation(Autowired.class) != null){

                    Class fieldType = oneField.getType();
                    String fieldName = oneField.getName();

                    //修改原类field modifier
                    //changeFieldModifier.changeFieldModifier(fieldName, Opcodes.ACC_PROTECTED);
                    //TODO 重新加载oneComponent(使用热加载方式，但仅限实例方式，使用自定义ClassLoader生成oneComponent实例)
                    String beanClassName = fieldType.getName();
                    String setMethodName = "set"+GetterSetterGenerator.upperCaseFirstChar(fieldName);
                    Method setMethod = newOneComponent.getMethod(setMethodName, fieldType);
                    if(setMethod == null){
                        logger.error("no such set method [%s:%s] found", newOneComponent.getName(), setMethodName);
                        continue;
                    }
                    Object beanInitInstance = null;
                    if(type.equals("dao")){
                        beanInitInstance = Context.getDao(beanClassName);
                    }else if(type.equals("service")){
                        beanInitInstance = Context.getService(beanClassName);
                    }

                    if(beanInitInstance == null){
                        logger.error("no such init instance [%s] found ", newOneComponent.getName());
                        continue;
                    }
                    //beanInitInstance 是接口类型
                    setMethod.invoke(componentObject, beanInitInstance);
                }
            }
            ret[0] = componentObject;

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
        }
        return ret;
    }

    //todo 方式二
    //bean类默认自带getter setter，context bean中保存的是原类的instance

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
            this.app.beforeRun();
            this.app.run();
            this.app.afterRun();
        }

        return null;
    }

    public static void handle(Class<?> appClazz){

        if(!Launcher.class.isAssignableFrom(appClazz)){
            logger.error("%s is not a launcher", appClazz.getName());
            return;
        }

        try {
            Object app = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Launcher.class}, new LauncherHandler(appClazz.newInstance()));
            ((Launcher)app).run();
        } catch (InstantiationException e) {
            logger.error(e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    //TODO 自定义用于热加载的ClassLoader，但仅限实例
    private class BeanHotSwapClassLoader extends ClassLoader{
        private HashSet<Class<?>> dynaclazns; // 需要由该类加载器直接加载的类名
        public BeanHotSwapClassLoader(){
            super(null);//父类为BootstrapClassLoader
            dynaclazns = new HashSet<>();
        }
    }
}
