package com.modules.prime.aop.asm;

public class DefaultGeneratorClassLoader extends ClassLoader{

    public DefaultGeneratorClassLoader(){
        //super(Launcher.class.getClassLoader());
        super(ClassLoader.getSystemClassLoader());
    }

    public Class defineClass(String className, byte[] file){
        return defineClass(className, file, 0, file.length);
    }
}
