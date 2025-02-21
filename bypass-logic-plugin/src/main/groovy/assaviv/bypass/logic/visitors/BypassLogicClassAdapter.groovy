package assaviv.bypass.logic.visitors

import assaviv.annotations.BypassLogic
import assaviv.bypass.logic.BypassLogicMethodAdapter
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

class BypassLogicClassAdapter extends ClassVisitor {
    private static final String CONSTRUCTOR_NAME = "<init>"
    private static final String STATIC_INIT_NAME = "<clinit>"
    private boolean isClassAnnotated = false

    BypassLogicClassAdapter(int api, ClassVisitor classVisitor) {
        super(api, classVisitor)
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (Type.getDescriptor(BypassLogic) == descriptor) {
            isClassAnnotated = true
            return null
        }

        return super.visitAnnotation(descriptor, visible)
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (canOptimizedMethod(access)) {
            return null
        }

        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions)

        if (isConstructorOrStaticInitializer(name)) {
            return mv
        }

        if (isBridgeOrSyntheticMethod(access)) {
            return mv
        }

        return new BypassLogicMethodAdapter(mv, access, name, descriptor, isClassAnnotated)
    }

    private canOptimizedMethod(int access) {
        isClassAnnotated && (access & Opcodes.ACC_PRIVATE) != 0
    }

    private static boolean isConstructorOrStaticInitializer(String name) {
        return name == CONSTRUCTOR_NAME || name == STATIC_INIT_NAME
    }

    private static boolean isBridgeOrSyntheticMethod(int access) {
        return (access & Opcodes.ACC_BRIDGE) != 0 || (access & Opcodes.ACC_SYNTHETIC) != 0
    }
}
