package com.modules.prime.util;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

import java.io.*;

public class IOUtil {

    private static transient final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static byte[] readByteArray(File file){

        if(null != file && file.exists()){
            try{

            }catch (Exception e){

            }
            //FileInputStream fis = new FileInputStream(file);

        }
        return null;
    }

    public static void stringToFile(String content, String filePath){
        stringToFile(content, filePath, false, System.getProperty("file.encoding"));
    }

    //writer字符串写入文件
    public static void stringToFile(String content, String filePath, boolean append, String charset){
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
    public static void byteArrayToFile(byte[] bytes, String filePath){
        try {
            FileOutputStream fos = new FileOutputStream(filePath, false);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //文件读取字节数组


    //文件读取流

    //文件读取字符串

}
