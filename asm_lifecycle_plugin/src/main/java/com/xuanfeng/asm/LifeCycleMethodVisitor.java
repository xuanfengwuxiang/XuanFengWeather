package com.xuanfeng.asm;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LifeCycleMethodVisitor extends MethodVisitor {

    private String mClassName;
    private String mMethodName;

    public LifeCycleMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM5, methodVisitor);
        mClassName = className;
        mMethodName = methodName;
    }

    @Override
    public void visitCode() {
        super.visitCode();

        System.out.println("字节码插入成功");
        mv.visitLdcInsn("BIG_TAG");
        mv.visitLdcInsn(mClassName + "---->" + mMethodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);



    }
}
