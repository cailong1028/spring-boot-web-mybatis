package com.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        System.getProperties().list(System.out);

        System.out.println("******************");

        final String encoding = System.getProperty("file.encoding");
        System.out.println("encoding:"+encoding);


        String path= "./哈haha哈AAA璎玥.txt";
        System.out.println(path);

        try {
            String newp = new String(path.getBytes("utf-8"),encoding);
            System.out.println(newp);
            File file = new File(newp);
            boolean b = file.createNewFile();
            System.out.println("file create:"+b);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
