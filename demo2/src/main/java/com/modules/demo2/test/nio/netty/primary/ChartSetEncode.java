package com.modules.demo2.test.nio.netty.primary;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;

public class ChartSetEncode {
    public static void main(String[] args) throws IOException {
        String a = "汉语";
        byte[] bytes = a.getBytes("gbk");
        System.out.println("bytes:"+DatatypeConverter.printHexBinary(bytes));
        byte[] byte2 = DatatypeConverter.parseHexBinary("E6B189E8AFAD");
        byte[] byte3 = DatatypeConverter.parseHexBinary("BABAD3EF");
        String aa = new String(bytes, "gbk");
        System.out.println("a:"+a);
        System.out.println("aa:"+aa);

        System.out.println("bytes to string encoding by utf-8:"+new String(byte2, "utf-8"));
        System.out.println("bytes to string encoding by gbk:"+new String(byte3, "gbk"));
    }
}
