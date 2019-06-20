package com.modules.prime;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class Launch {

    public static void main(String[] args) throws IOException {

        App app = new App();
        Enumeration<URL> urls = Launch.class.getClassLoader().getResources("com/modules/prime/dao");
        URL root = Launch.class.getResource("/");
//        URL root = Launch.class.getClassLoader().getResource("com/modules/prime/App");
//        URL root = Launch.class.getClassLoader().getResource("com.modules.prime.App");
//        System.out.println("-->"+root.getPath());
        if(urls.hasMoreElements()){
            URL url = urls.nextElement();
            System.out.println(url.getPath());
        }
    }
}
