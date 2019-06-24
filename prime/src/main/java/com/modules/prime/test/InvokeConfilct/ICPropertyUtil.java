package com.modules.prime.test.InvokeConfilct;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ICPropertyUtil {

    static ICLogFactory log = ICLogFactory.getInstance();

    public static Properties getProperties() {
        InputStream is = ICPropertyUtil.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            //log.logInfo("load properties done");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
