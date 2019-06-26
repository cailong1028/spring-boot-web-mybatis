package com.modules.prime.aop.asm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ClassFileGenerator {

    //写字节码文件(.class)
    public static void writeByteCode(Class clazz, byte[] classByteData) {
        OutputStream os = null ;
        try {
            os = new FileOutputStream(getFile(clazz));
            os.write(classByteData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //获得文件名称
    private static File getFile(Class clazz) {
        File classFile = null;
        if (classFile == null ) {
            StringBuffer sb = new StringBuffer();
            sb.append(clazz.getResource( "/")).append(clazz.getCanonicalName().replace( "." , File.separator)).append(".class");
            classFile = new File(sb.substring( 5 ));
        }
        return classFile;
    }
}
