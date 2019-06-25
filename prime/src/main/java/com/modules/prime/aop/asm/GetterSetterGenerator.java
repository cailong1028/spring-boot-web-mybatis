package com.modules.prime.aop.asm;

import com.modules.prime.annotation.Autowired;
import com.modules.prime.component.LoginComponent;
import com.sun.xml.internal.ws.org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.*;
import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.RETURN;

//asm 添加一个method示例
public class GetterSetterGenerator {
    String enhancedSuperName;
    String suffix = "$EnhancedByASM";
    public Class generateGetterSetter(Class clazz){
        try {
            ClassReader cr = new ClassReader(clazz.getName());
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassAdapter classAdapter = new GeneratorClassAdapter(cw);
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);

            Field[] fields = clazz.getDeclaredFields();
            for(Field oneField: fields){
                if(oneField.getAnnotation(Autowired.class) != null){
                    addGetSetMethod(cw, enhancedSuperName, oneField);
                }
            }

            byte[] data = cw.toByteArray();
            return new GeneratorClassLoader().defineClass(clazz.getName()+suffix, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addGetSetMethod(ClassWriter cw, String className, Field field){
        //一个无参方法示例
        /*MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "add", "()V", null, null);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("this is add method print!");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();*/
        String fieldName = field.getName();
        String getMethodName = "get"+upperCaseFirstChar(fieldName);
        String setMethodName = "set"+upperCaseFirstChar(fieldName);
        String typeof = Type.getType(field.getType()).getDescriptor();
        String getof = "()"+typeof;
        String setof = "("+typeof+")V";
        int[] loadAndReturnOf = loadAndReturnOf(typeof);

        //add field
        cw.visitField(ACC_PRIVATE, "__"+fieldName+"__", typeof, null, 0).visitEnd();

        //add getMethod
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, getMethodName, getof, null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, className, fieldName, typeof);
        mv.visitInsn(loadAndReturnOf[1]);
        mv.visitMaxs(2, 1);
        mv.visitEnd();

        //add setMethod
        mv = cw.visitMethod(ACC_PUBLIC, setMethodName, setof, null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(loadAndReturnOf[0], 1);
        mv.visitFieldInsn(PUTFIELD, className, fieldName, typeof);
        mv.visitInsn(RETURN);
        mv.visitMaxs(3, 3);
        mv.visitEnd();
    }

    public static String upperCaseFirstChar(String str){
        char firstChar = str.charAt(0);
        if(firstChar >= 'a' && firstChar <= 'z'){
            firstChar -= 32;
        }
        char[] charArr = str.toCharArray();
        charArr[0] = firstChar;
        return String.valueOf(charArr);
    }

    private int[] loadAndReturnOf(String typeof) {
        if (typeof.equals("I") || typeof.equals("Z")) {
            return new int[]{ILOAD,IRETURN};
        } else if (typeof.equals("J")) {
            return new int[]{LLOAD,LRETURN};
        } else if (typeof.equals("D")) {
            return new int[]{DLOAD,DRETURN};
        } else if (typeof.equals("F")) {
            return new int[]{FLOAD,FRETURN};
        } else {
            return new int[]{ALOAD,ARETURN};
        }
    }

    private class GeneratorClassLoader extends ClassLoader{
        private Class defineClass(String className, byte[] file){
            return defineClass(className, file, 0, file.length);
        }
    }

    //ClassAdapter时序 visit visitField visitMethod visitEnd
    private class GeneratorClassAdapter extends ClassAdapter{

        public GeneratorClassAdapter(ClassVisitor cv) {
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
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class clazz = new GetterSetterGenerator().generateGetterSetter(LoginComponent.class);
        Method[] methods = clazz.getDeclaredMethods();
        for(Method oneMethod:methods){
            System.out.println(oneMethod.getName());
        }
        Object obj = clazz.newInstance();

    }
}


