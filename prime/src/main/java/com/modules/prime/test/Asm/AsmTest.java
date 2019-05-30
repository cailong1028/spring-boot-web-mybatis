package com.modules.prime.test.Asm;

import com.sun.xml.internal.ws.org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AsmTest {
    //reflect public
    public String a(String a, String b){
        System.out.println("test"+a+b);
        return "gg";
    }
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException, InterruptedException {
        new B(new A()).say();
        //ClassLoader.getSystemClassLoader().
        Class<?> clazz = AsmTest.class.getClassLoader().loadClass("com.modules.prime.test.Asm.AsmTest");
        Object ins = clazz.newInstance();
        Method[] methods = AsmTest.class.getMethods();
        for(Method method:methods){
            if(method.getName() != "a") continue;
            //method.getAnnotation();
            method.getDeclaredAnnotations();
            Class<?> retType = method.getReturnType();
            Object c=retType.newInstance();
            c = method.invoke(ins, "a", "b");
            //method.
            System.out.println(c);
            new B(new A()).say();

            //after load done
            //scan class 修改

            new Account();

            String className = "com.modules.prime.test.Asm.Account";
            String enhancedName = "com.modules.prime.test.Asm.SecurityChecker";
            ClassReader cr = new ClassReader(className);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
            byte[] data = cw.toByteArray();
//            File file = new File("/Users/bqj/workspace/spring-boot-web-mybatis/prime/target/classes/com/modules/prime/test/Asm/Account.class");
//            System.out.println(file.getAbsolutePath());
//            FileOutputStream fout = new FileOutputStream(file);
//            fout.write(data);
//            fout.close();
            enhancedName = className.replace(".", "$")+"$EnhancedASM";
            Class obj = new AOPGeneratorClassLoader().defineClassFromClassFile(enhancedName, data);
            obj.newInstance();
            //new Account().operation();
        }
//        ClassWriter
//        A a = new A();
//        a.say();
    }

    static class AOPGeneratorClassLoader extends ClassLoader {
        public Class defineClassFromClassFile(String className,
                                             byte[] classFile) throws ClassFormatError {
            return defineClass(className, classFile, 0,
                    classFile.length);
        }
    }

    public void asm(){
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassAdapter delLoginClassAdaptor = new DelLoginClassAdapter(classWriter);
        ClassAdapter accessClassAdaptor = new AccessClassAdapter(delLoginClassAdaptor);

//        ClassReader classReader = new ClassReader(strFileName);
//        classReader.accept(classAdapter, ClassReader.SKIP_DEBUG);
    }
}



//删除类的字段、方法、指令
class DelLoginClassAdapter extends ClassAdapter {
    public DelLoginClassAdapter(ClassVisitor cv) {
        super(cv);
    }

    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        if (name.equals("login")) {
            return null;
        }
        return cv.visitMethod(access, name, desc, signature, exceptions);
    }
}

//修改类、字段、方法的名字或修饰符
class AccessClassAdapter extends ClassAdapter{
    public AccessClassAdapter(ClassVisitor cv){
        super(cv);
    }

    public FieldVisitor visitField(final int access, final String name,
                                   final String desc, final String signature, final Object value){
        int privateAccess = Opcodes.ACC_PRIVATE;
        return cv.visitField(privateAccess, name, desc, signature, value);
    }
}


class  AddSecurityCheckClassAdapter extends ClassAdapter{

    String enhancedSuperName;
    public  AddSecurityCheckClassAdapter(ClassVisitor cv){
        super(cv);
    }

    public MethodVisitor visitMethod(final int access, final String name, final String desc,
                                     final String signature, final String[] exceptions) {

        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor wrapperMv = mv;
        if (mv != null) {
            if (name.equals("operation")) {
                wrapperMv = new AddScrtMethodAdapter(mv);
            } else if (name.equals("<init>")) {
                wrapperMv = new ChangeToChildConstructorMethodAdapter(mv, enhancedSuperName);
            }
            return wrapperMv;
        }
    }
    public void visit(final int version, final int access, final String name,
                      final String signature, final String superName,
                      final String[] interfaces) {
        String enhancedName = name + "$EnhancedByASM";  // 改变类命名
        enhancedSuperName = name; // 改变父类，这里是”Account”
        super.visit(version, access, enhancedName, signature,
                enhancedSuperName, interfaces);
    }
}
class ChangeToChildConstructorMethodAdapter extends MethodAdapter {
    private String superClassName;

    public ChangeToChildConstructorMethodAdapter(MethodVisitor mv,
                                                 String superClassName) {
        super(mv);
        this.superClassName = superClassName;
    }

    public void visitMethodInsn(int opcode, String owner, String name,
                                String desc) {
        // 调用父类的构造函数时
        if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
            owner = superClassName;
        }
        super.visitMethodInsn(opcode, owner, name, desc);// 改写父类为 superClassName
    }
}
class AddScrtMethodAdapter extends MethodAdapter{
    public AddScrtMethodAdapter(MethodVisitor mv){
        super(mv);
    }
    public void visitCode(){
        visitMethodInsn(Opcodes.INVOKESTATIC, "com.modules.prime.test.Asm.SecurityChecker","checkSecurity", "()V");
    }
}

class SecureAccountGenerator {

    private static AccountGeneratorClassLoader classLoader =
            new AccountGeneratorClassLoader();

    private static Class secureAccountClass;

    public Account generateSecureAccount() throws ClassFormatError,
            InstantiationException, IllegalAccessException, IOException {
        if (null == secureAccountClass) {
            ClassReader cr = new ClassReader("Account");
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
            byte[] data = cw.toByteArray();
            secureAccountClass = classLoader.defineClassFromClassFile(
                    "Account$EnhancedByASM",data);
        }
        return (Account) secureAccountClass.newInstance();
    }

    private static class AccountGeneratorClassLoader extends ClassLoader {
        public Class defineClassFromClassFile(String className,
                                              byte[] classFile) throws ClassFormatError {
            return defineClass("Account$EnhancedByASM", classFile, 0,
                    classFile.length());
        }
    }
}



interface Decorator{
    void say();
}

class A implements Decorator{
    public void say(){
        System.out.println("A");
    }
    public void say2(){
        System.out.println("A2");
    }
}
class AA implements Decorator{
    public void say(){
        System.out.println("AA");
    }
    public void say2(){
        System.out.println("AA2");
    }
}
class B implements Decorator{
    Decorator decorator;
    public B(Decorator decorator){
        this.decorator = decorator;
    }
    public void say(){
        beforeSay();
        decorator.say();
        afterSay();
    }
    public void beforeSay(){
        System.out.println("before say");
    }
    public void afterSay(){
        System.out.println("after say");
    }
}