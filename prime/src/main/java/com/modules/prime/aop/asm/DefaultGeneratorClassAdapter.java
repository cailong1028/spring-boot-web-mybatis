package com.modules.prime.aop.asm;

import com.sun.xml.internal.ws.org.objectweb.asm.*;

//ClassAdapter时序 visit visitField visitMethod visitEnd
public class DefaultGeneratorClassAdapter extends ClassAdapter {
    private String enhancedSuperName;
    public static String suffix = "$EnhancedByASM";
    public DefaultGeneratorClassAdapter(ClassVisitor cv) {
        super(cv);
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
        String enhancedName = name + suffix;  // 改变类命名
        enhancedSuperName = name; // 改变父类，这里是”Account”
        super.visit(version, access, enhancedName, signature, enhancedSuperName, interfaces);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor wrapperMv = mv;
        if(mv != null){
            if(name.equals("<init>")) {
                wrapperMv = new ChangeToChildConstructorMethodAdapter(mv, enhancedSuperName);
            }
            return wrapperMv;
        }
        return null;
    }

    public String getEnhancedSuperName() {
        return enhancedSuperName;
    }

    public void setEnhancedSuperName(String enhancedSuperName) {
        this.enhancedSuperName = enhancedSuperName;
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