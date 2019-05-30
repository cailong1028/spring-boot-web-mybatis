package com.modules.prime.test.Asm.monitor;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class asmAopMethodAdapter extends MethodAdapter implements Opcodes {

    private final static int EXCEPTION_STACK = 2 + 1;//max_stack至少需要能够容纳2个常量地址(监控方法使用)和1个exception地址

    private Label try_catch_start,try_catch_end;

    private String startInfo,endInfo;

    public asmAopMethodAdapter(MethodVisitor mv, String start, String end) {
        super(mv);
        try_catch_start = new Label();
        try_catch_end = new Label();
        startInfo = start;
        endInfo = end;
    }

    public void visitCode() {
        mv.visitCode();
        mv.visitLabel(try_catch_start);

        mv.visitLdcInsn(startInfo);
//asmAopInvoker　这里写类的路径例如：com.asm.asmAopInvoker 应写成 com/asm/asmAopInvoker
        mv.visitMethodInsn(INVOKESTATIC, "asmAopInvoker",
                "methodStart", "(Ljava/lang/String;)V");
    }

    public void visitInsn(int opcode){
        if(opcode >= IRETURN && opcode <= RETURN){
            mv.visitLdcInsn(endInfo);
//asmAopInvoker　这里写类的路径例如：com.asm.asmAopInvoker 应写成 com/asm/asmAopInvoker
            mv.visitMethodInsn(INVOKESTATIC, "asmAopInvoker",
                    "methodEnd", "(Ljava/lang/String;)V");
        }
        mv.visitInsn(opcode);
    }
    public void visitEnd() {
        mv.visitLabel(try_catch_end);
        mv.visitTryCatchBlock(try_catch_start, try_catch_end, try_catch_end, null);
        mv.visitLdcInsn(endInfo);
//asmAopInvoker　这里写类的路径例如：com.asm.asmAopInvoker 应写成 com/asm/asmAopInvoker
        mv.visitMethodInsn(INVOKESTATIC, "asmAopInvoker",
                "methodEnd", "(Ljava/lang/String;)V");
        mv.visitInsn(Opcodes.ATHROW);
        mv.visitEnd();
    }

    public void visitMaxs(int maxStack,int maxLocals){
        //保证max stack足够大
        mv.visitMaxs(Math.max(EXCEPTION_STACK,maxStack), maxLocals);
    }
}