package com.bluejean.modules.asm;

import org.objectweb.asm.*;

import java.io.IOException;

/**
 * ASM测试
 * @author: wangrl
 * @Date: 2016-03-25 16:37
 */
public class AsmTester {
    public final static float VERSION = 1.8f;

    public static void main(String[] args) throws IOException {
        testRead();
    }

    private static void testRead() throws IOException {
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5) {
            @Override
            public void visit(int version, int access, String name,
                              String signature, String superName, String[] interfaces) {
                System.out.println(name + " extends " + superName + " {");
            }
            @Override
            public void visitSource(String source, String debug) {
            }
            @Override
            public void visitOuterClass(String owner, String name, String desc) {
            }
            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visiable) {
                return null;
            }
            @Override
            public void visitAttribute(Attribute attr) {
            }
            @Override
            public void visitInnerClass(String name, String outerName,
                                        String innerName, int access) {
            }
            @Override
            public FieldVisitor visitField(int access, String name,
                                           String desc, String signature, Object value) {
                System.out.println(" " + desc + " " + name + "(" + value + ")");
                return null;
            }
            @Override
            public MethodVisitor visitMethod(int access, String name,
                                             String desc, String signature, String[] exceptions) {
                System.out.println(" " + name + desc);
                return null;
            }
            @Override
            public void visitEnd() {
                System.out.println("}");
            }

        };
        //可以将一个 .class 文件的字节流直接传递给 ClassReader 类，或者是通过 getResourceAsStream 来获取一个 .class 文件的流数据
        ClassReader cr = new ClassReader("com.bluejean.modules.asm.AsmTester");
        cr.accept(visitor, 0);
    }
}
