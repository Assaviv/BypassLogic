package assaviv.bypass.logic.visitors

import assaviv.annotations.BypassLogic
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

class AnnotationsCheckerVisitor extends ClassVisitor {
    private List<Class<?>> annotations
    private boolean hasAnnotations
    
    AnnotationsCheckerVisitor(int api, Class<?>... annotations) {
        super(api)
        this.annotations = annotations
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor == Type.getDescriptor(BypassLogic)) {
            hasAnnotations = true
        }

        return super.visitAnnotation(descriptor, visible)
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions)

        return new MethodVisitor(Opcodes.ASM9, mv) {
            @Override
            AnnotationVisitor visitAnnotation(String annotationDescriptor, boolean visible) {
                if (annotationDescriptor == Type.getDescriptor(BypassLogic)) {
                    hasAnnotations = true
                }

                return super.visitAnnotation(descriptor, visible)
            }
        }
    }
    
    boolean hasAnnotations() {
        return hasAnnotations
    }
    
    boolean containsAnnotations(Class<?>... annotations) {
        return false
    }
}
