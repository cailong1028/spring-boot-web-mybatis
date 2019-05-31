package com.modules.prime.test.aop.loader;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.concurrent.ConcurrentHashMap;

public class InjectCodeClassLoader extends URLClassLoader {
    private static final String appClass = "App";
    private static final String oldClass = "A";
    private static final String newClass = "B";
    private final ConcurrentHashMap<String, Object> locksMap = new ConcurrentHashMap<String, Object>();

    public InjectCodeClassLoader(ClassLoader parent) {
        super(((URLClassLoader) parent).getURLs(), parent);
    }

    private static class InjectCodeClassWriter extends ClassWriter {
        InjectCodeClassWriter(int flags) {
            super(flags);
        }

        @Override
        public int newUTF8(final String value) {
            if (value.equals(oldClass)) {
                return super.newUTF8(newClass);
            }
            return super.newUTF8(value);
        }
    }

    private Class defineClassFromClassFile(String className, byte[] classFile)
            throws ClassFormatError {
        return defineClass(className, classFile, 0, classFile.length);
    }

    private Class<?> replaceClass(String name)
            throws ClassNotFoundException {

        InputStream is = getResourceAsStream(name.replace('.', '/') + ".class");
        if (is == null) {
            throw new ClassNotFoundException();
        }

        ClassWriter classWriter = new InjectCodeClassWriter(0);
        try {
            ClassReader classReader = new ClassReader(is);
            classReader.accept(classWriter, 0);
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }

        Class c = defineClassFromClassFile(name, classWriter.toByteArray());
        return c;
    }

    private Object getLock(String name) {
        Object lock = new Object();
        Object oldLock = locksMap.putIfAbsent(name, lock);
        if (oldLock == null) {
            oldLock = lock;
        }
        return oldLock;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        Object lock = getLock(name);
        synchronized (lock) {
            Class c = findLoadedClass(name);
            try {
                if (c == null) {
                    if (name.equals(appClass)) {
                        // 将App.class中常量池（constant pool）中类A的名字的字符串改为类B的名字
                        c = replaceClass(name);
                    } else {
                        c = findClass(name);
                    }
                }

                if (resolve) {
                    resolveClass(c);
                }
                return c;
            } catch (ClassNotFoundException e) {
            }
        }
        return super.loadClass(name, resolve);
    }
}
