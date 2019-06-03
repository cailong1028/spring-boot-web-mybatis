package com.modules.prime;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class Launch {

    public static void main(String[] args) throws IOException {

        Enumeration<URL> urls = Launch.class.getClassLoader().getResources("/");
        URL root = Launch.class.getClassLoader().getResource("com/modules/prime/App");
        System.out.println("-->"+root.getPath());
//        if(urls.hasMoreElements()){
//            URL url = urls.nextElement();
//            System.out.println(url.getPath());
//        }
    }
}
