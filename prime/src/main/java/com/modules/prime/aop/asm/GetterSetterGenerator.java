package com.modules.prime.aop.asm;

import com.modules.prime.Context;
import com.modules.prime.annotation.Autowired;
import com.modules.prime.component.LoginComponent;
import com.sun.xml.internal.ws.org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.*;
import static com.sun.xml.internal.ws.org.objectweb.asm.Opcodes.RETURN;

//asm 添加一个method示例
public class GetterSetterGenerator {
    public Class generateGetterSetter(Class clazz){
        try {
            ClassReader cr = new ClassReader(clazz.getName());
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

            Field[] fields = clazz.getDeclaredFields();
            List<Field> autowiredFields = new ArrayList<>();
            for(Field oneField: fields){
                if(oneField.getAnnotation(Autowired.class) != null){
                    autowiredFields.add(oneField);
                }
            }

            ClassAdapter classAdapter = new ChangeFieldModifierAdapter(cw, autowiredFields);
            cr.accept(classAdapter, ClassReader.SKIP_DEBUG);

            for(Field oneField: autowiredFields){
                addGetSetMethod(cw, ((DefaultGeneratorClassAdapter)classAdapter).getEnhancedSuperName(), oneField);
            }

            byte[] data = cw.toByteArray();
            Class retClass = Context.defaultGeneratorClassLoader.defineClass(clazz.getName()+DefaultGeneratorClassAdapter.suffix, data);
//            ClassFileGenerator.writeByteCode(retClass, data);
//            ClassLoader.getSystemClassLoader().loadClass(retClass.getName());
            return retClass;
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
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
        //cw.visitField(ACC_PRIVATE, "__"+fieldName+"__", typeof, null, 0).visitEnd();

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

    private void changeFieldModify(ClassWriter cw, String fieldName, int modifier){

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

    private class ChangeFieldModifierAdapter extends DefaultGeneratorClassAdapter{
        private List<Field> fields;
        public ChangeFieldModifierAdapter(ClassVisitor cv, List<Field> fields) {
            super(cv);
            this.fields = fields;
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            if (containsField(fields, name)) {
                return cv.visitField(Opcodes.ACC_PUBLIC, name, desc, signature, value);
            }
            return cv.visitField(access, name, desc, signature, value);
        }

//        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
//            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
//            MethodVisitor wrapperMv = mv;
//            if(mv != null){
//                if(name.equals("<init>")) {
//                    wrapperMv = new ChangeToChildConstructorMethodAdapter(mv, super.getEnhancedSuperName());
//                    return wrapperMv;
//                }
//                return null;
//            }
//            return null;
//        }
        private boolean containsField(List<Field> fields, String fieldName){
            for(Field oneField:fields){
                if(oneField.getName().equals(fieldName)){
                    return true;
                }
            }
            return false;
        }

        @Override
        public void visitEnd() {
            cv.visitEnd();
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


