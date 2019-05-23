package com.modules.prime.util;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

import java.io.*;

public class IOUtil {

    private static transient final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static void writeFile(String content, String filePath){
        writeFile(content, filePath, false, System.getProperty("file.encoding"));
    }

    //writer字符串写入文件
    public static void writeFile(String content, String filePath, boolean append, String charset){
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(filePath, append);
            osw = new OutputStreamWriter(fos, charset);
            bw = new BufferedWriter(osw);
            bw.write(content);
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(osw!=null){
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //stream写字节数组到文件
    public static void writeFile(byte[] bytes, String filePath){
        try {
            FileOutputStream fos = new FileOutputStream(filePath, false);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //inputStream读取字节数组
    public static byte[] readByteArray(InputStream is){
        byte[] bytes = null;
        byte[] buff = new byte[4096];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int cursor;
        try {
            while ((cursor = is.read(buff, 0, buff.length)) != -1){
                baos.write(buff, 0, cursor);
            }
            baos.flush();
            bytes = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    //file读取字节数组
    public static byte[] readByteArray(String filePath){
        byte[] bytes = null;
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            bytes = new byte[is.available()];
            is.read(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        return bytes;
    }

}
