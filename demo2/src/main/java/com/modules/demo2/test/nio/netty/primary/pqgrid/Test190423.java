package com.modules.demo2.test.nio.netty.primary.pqgrid;

import java.io.File;
import java.io.IOException;

public class Test190423 {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/bqj/Desktop/mytest01.zip");
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
    }
}
