package com.modules.prime.test.InvokeConfilct;

import java.util.Properties;

public class ICLogFactory {

    private static String fileEncode;
    static {
        Properties properties = ICPropertyUtil.getProperties();
        fileEncode = properties.getProperty("file.encode");
    }

    private static ICLogFactory instance;

    private ICLogFactory(){}

    public static ICLogFactory getInstance(){
        if(instance != null){
            return instance;
        }
        synchronized (ICLogFactory.class){
            if(instance != null){
                return instance;
            }
            return new ICLogFactory();
        }
    }

    public void logInfo(String msg){
        System.out.println("info is: " + msg);
    }
    public void logError(String error){
        System.out.println("error is: " + error);
    }

}
