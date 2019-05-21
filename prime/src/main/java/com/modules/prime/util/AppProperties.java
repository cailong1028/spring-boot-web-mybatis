package com.modules.prime.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

    private static Properties properties;

    static {
        InputStream is = AppProperties.class.getClassLoader().getResourceAsStream("application.properties");
        try{
            if(is != null){
                properties = new Properties();
                properties.load(is);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String get(String keyName){
        if(properties.getProperty(keyName) != null){
            return properties.getProperty(keyName);
        }
        return null;
    }
}
