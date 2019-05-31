package com.modules.prime.test.aop.monitor;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassReader;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;

public class asmAopGenerator {

    private AOPGeneratorClassLoader classLoader ;

    public asmAopGenerator(){
        classLoader = new AOPGeneratorClassLoader();
    }


    public Object proxy(Class c,String methodName,String startInfo,String endInfo) {
        try{
            if( c != null){
//                String classPach = c.toString().replace("/", ".");
//                ClassReader cr = new ClassReader(classPach.substring(6,classPach.length()));
                ClassReader cr = new ClassReader(c.getName());
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                asmAopClassAdapter classAdapter = new asmAopClassAdapter(cw,methodName,startInfo,endInfo);
                cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
                byte[] data = cw.toByteArray();
                Class obj = classLoader.defineClassFromClassFile(classAdapter.getEnhancedName(), data);
                //TODO:隐藏BUG
                return obj.newInstance();
            }}catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    class AOPGeneratorClassLoader extends ClassLoader {
        public Class defineClassFromClassFile(String className,
                                              byte[] classFile) throws ClassFormatError {
            return defineClass(className, classFile, 0,
                    classFile.length);
        }
    }
}