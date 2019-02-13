package com.modules.demo2.test.nio.netty.primary.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Copy {

    public static void copyFile(String origin, String dest) throws IOException {

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fic = null;
        FileChannel foc = null;

        fis = new FileInputStream(origin);
        fos = new FileOutputStream(dest);
        fic = fis.getChannel();
        foc = fos.getChannel();

        foc.transferFrom(fic, 0, fic.size());
        foc.close();
        fic.close();
        fos.close();
        fis.close();
    }


    public static void main(String[] args) throws IOException {
        for(int i = 0; i < 500; i++){
            Copy.copyFile("/Users/bqj/Desktop/a6.jpg", "/Users/bqj/Desktop/60_big_img2/" + i + "_a.jpg");
        }

    }
}
