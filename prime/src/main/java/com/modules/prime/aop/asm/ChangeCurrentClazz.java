package com.modules.prime.aop.asm;

import com.modules.prime.App;
import com.sun.xml.internal.ws.org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ChangeCurrentClazz {
    private Class clazz;
    private ClassReader cr = null ;
    private ClassWriter cw = null ;
    private ClassAdapter ca = null ;
    private File classFile = null ;
    private final static String CLASS_FILE_SUFFIX = ".class" ;

    public ChangeCurrentClazz(Class clazz) {
        this.clazz = clazz;
        if (cr == null ) {
            try {
                cr = new ClassReader(clazz.getCanonicalName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        }
    }
    /**
     * 将字节码写入类的 .class 文件
     *
     */
    public void writeByteCode() {
        cr.accept(ca, ClassReader.SKIP_DEBUG);
        byte [] bys = cw.toByteArray();
        OutputStream os = null ;
        try {
            os = new FileOutputStream(getFile());
            os.write(bys);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 获得类文件的 File 对象
     * @return
     */
    private File getFile() {
        if (classFile == null ) {
            StringBuffer sb = new StringBuffer();
            sb.append(clazz.getResource( "/")).append(clazz.getCanonicalName().replace( "." , File.separator)).append(CLASS_FILE_SUFFIX);
            classFile = new File(sb.substring( 5 ));
        }
        return classFile;
    }

    /**
     * 添加一个 public 的类成员
     * @param fieldName 类成员名
     * @param fieldDesc 类成员类型描述
     */
    public void addPublicField(String fieldName, int modifier, String fieldDesc) {
        if (ca == null ) {
            ca = new AddFieldAdapter(cw, modifier, fieldName, fieldDesc);
        } else {
            ca = new AddFieldAdapter(ca, modifier, fieldName, fieldDesc);
        }
        writeByteCode();
    }

    public void changeFieldModifier(String fieldName, int modifier) {
        if (ca == null ) {
            ca = new ChangeFieldModifierAdapter(cw, modifier, fieldName);
        } else {
            ca = new ChangeFieldModifierAdapter(ca, modifier, fieldName);
        }
        writeByteCode();
    }

    private class AddFieldAdapter extends ClassAdapter{
        private int accessModifier;
        private String name;
        private String desc;
        private boolean isFieldPresent;
        public AddFieldAdapter(ClassVisitor cv, int accessModifier, String name, String desc) {
            super(cv);
            this.accessModifier = accessModifier;
            this.name = name;
            this.desc = desc;
        }

        public AddFieldAdapter(ClassVisitor cv) {
            super(cv);
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            if (name.equals(this.name)) {
                isFieldPresent = true ;
            }
            return cv.visitField(access, name, desc, signature, value);
        }

        @Override
        public void visitEnd() {
            if (!isFieldPresent) {
                FieldVisitor fv = cv.visitField(accessModifier, name, desc, null , null );
                if (fv != null ) {
                    fv.visitEnd();
                }
            }
            cv.visitEnd();
        }
    }

    private class ChangeFieldModifierAdapter extends ClassAdapter{
        private int accessModifier;
        private String name;
        public ChangeFieldModifierAdapter(ClassVisitor cv, int accessModifier, String name) {
            super(cv);
            this.accessModifier = accessModifier;
            this.name = name;
        }

        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            if (name.equals( this .name)) {
                return cv.visitField(accessModifier, name, desc, signature, value);
            }
            return cv.visitField(access, name, desc, signature, value);
        }

        @Override
        public void visitEnd() {
            cv.visitEnd();
        }
    }

    public static void main(String[] args) {

        ChangeCurrentClazz add = new ChangeCurrentClazz(App.class);
        add.addPublicField( "address" , Opcodes.ACC_PUBLIC, "Ljava/lang/String;" );
        //add.addPublicField( "test" , "Ljava/lang/String;" );
        add.changeFieldModifier("test", Opcodes.ACC_PUBLIC);
        //add.writeByteCode();
    }


}
