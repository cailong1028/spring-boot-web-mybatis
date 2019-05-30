import com.sun.xml.internal.ws.org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AsmTest2 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException, InterruptedException {
        Account account = new SecureAccountGenerator().generateSecureAccount();
        account.operation();
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
        visitMethodInsn(Opcodes.INVOKESTATIC, "SecurityChecker","checkSecurity", "()V");
    }
}

class SecureAccountGenerator {

    private static SecureAccountGenerator.AccountGeneratorClassLoader classLoader =
            new SecureAccountGenerator.AccountGeneratorClassLoader();

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
            return defineClass(className, classFile, 0,
                    classFile.length);
        }
    }
}

