/******************************************************************************
    File: PropFile.java

    Version 1.0
    Date            Author                Changes
    Sep.28,2003     Li Shengwang          Created

    Copyright (c) 2003, Eagle Soar
    all rights reserved.
******************************************************************************/
package ess.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 本类实现了属性文件的读取和修改功能
 */
public class PropFile {
    public static final Properties parsePropFile(String fileName){ 
        if(fileName == null){
            return null;
        }

        File file = new File(fileName.trim());
        if(!file.isAbsolute()){
            file = new File(file.getAbsolutePath());
        }
        if(!file.exists()){
            return null;
        }
        Properties properties = new Properties();
        FileInputStream fileinputstream = null;
        try{
            fileinputstream = new FileInputStream(file);
            properties.load(fileinputstream);
            fileinputstream.close();
        }catch(IOException ioexception){
            if(fileinputstream != null){
                try{
                    fileinputstream.close();
                }catch(IOException ioexception1) { }
            }
            return null;
        }

        return properties;
    }

    public static final void savePropFile(Properties props, String fileName)
    throws IOException{
        FileOutputStream fileoutputstream = null;
        if(fileName == null){
            throw new IOException("No specified file name");
        }

        File file = new File(fileName.trim());
        if(!file.isAbsolute()){
            file = new File(file.getAbsolutePath());
        }
        if(!file.exists()){
            throw new IOException("No file exist for \""+fileName+"\"");
        }
        try{
            fileoutputstream = new FileOutputStream(file);
            props.store(fileoutputstream, null);
            fileoutputstream.close();
        }catch(IOException ioexception2){
            if(fileoutputstream != null)
                try{
                    fileoutputstream.close();
                }catch(IOException ioexception3) { }
        }
    }
}
