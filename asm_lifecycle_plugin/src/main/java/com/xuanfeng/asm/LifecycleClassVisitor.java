package com.xuanfeng.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LifecycleClassVisitor extends ClassVisitor {

    private String mClassName;
    private String mSuperName;

    public LifecycleClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mClassName = name;
        mSuperName = superName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("mSuperName================"+mSuperName);

        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        if ("com/xuanfeng/mylibrary/mvp/BaseActivity".equals(mSuperName)) {
            if (name.startsWith("onResume")) {
                return new LifeCycleMethodVisitor(mv, mClassName, name);
            }
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
