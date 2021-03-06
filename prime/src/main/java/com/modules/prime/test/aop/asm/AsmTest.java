package com.modules.prime.test.aop.asm;

import com.sun.xml.internal.ws.org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.*;

public class AsmTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException, InterruptedException {
        //new Account().operation();
        System.out.println("--------------------");
        Account account = new SecureAccountGenerator().generateSecureAccount("com.modules.prime.test.aop.asm.Account");
        account.operation("");
        Method[] methods = account.getClass().getDeclaredMethods();
        for(Method method:methods){
            if(method.getName().equals("add1")){
                System.out.println(method.toString());
                method.invoke(Account.class);

            }
            //System.out.println(method.getName());
        }
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
        return null;
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
        visitMethodInsn(Opcodes.INVOKESTATIC, "com/modules/prime/test/aop/asm/SecurityChecker","checkSecurity", "()V");
    }
}

class SecureAccountGenerator {

    private static AccountGeneratorClassLoader classLoader =
            new AccountGeneratorClassLoader();

    private static Class secureAccountClass;

    public Account generateSecureAccount(String className) throws ClassFormatError,
            InstantiationException, IllegalAccessException, IOException {
        if (null == secureAccountClass) {

            ClassReader cr = new ClassReader(className);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);

            //新增一个方法
            //MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "add1", "([Ljava/lang/String;)V", null, null);
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "add1", "()V", null, null);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("this is add method print!");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();


            byte[] data = cw.toByteArray();
            secureAccountClass = classLoader.defineClassFromClassFile(
                    className+"$EnhancedByASM",data);
        }
        return (Account) secureAccountClass.newInstance();
    }

    private static class AccountGeneratorClassLoader extends ClassLoader {
        public Class defineClassFromClassFile(String className,
                                              byte[] classFile) throws ClassFormatError {
            return defineClass(className, classFile, 0,
                    classFile.length);
        }
    }
}





/*//删除类的字段、方法、指令
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
}*/


//https://www.ibm.com/developerworks/cn/java/j-lo-asm30/

/*ClassWriter  classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
ClassAdaptor delLoginClassAdaptor = new DelLoginClassAdapter(classWriter);
ClassAdaptor accessClassAdaptor = new AccessClassAdaptor(delLoginClassAdaptor);

ClassReader classReader = new ClassReader(strFileName);
classReader.accept(classAdapter, ClassReader.SKIP_DEBUG);*/


//https://blog.csdn.net/cn_yaojin/article/details/81033957