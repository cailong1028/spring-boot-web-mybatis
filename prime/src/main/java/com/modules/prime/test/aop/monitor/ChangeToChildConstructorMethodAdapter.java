package com.modules.prime.test.aop.monitor;

import com.sun.xml.internal.ws.org.objectweb.asm.MethodAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class ChangeToChildConstructorMethodAdapter extends MethodAdapter {

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