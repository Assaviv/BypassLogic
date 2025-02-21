package assaviv.bypass.logic.types

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

class ReturnTypeHandler {
    private static final primitiveHandlers = [
            (Type.VOID_TYPE):    { MethodVisitor mv -> Primitives.returnVoid(mv) },
            (Type.INT_TYPE):     { MethodVisitor mv -> Primitives.returnInt(mv) },
            (Type.BYTE_TYPE):    { MethodVisitor mv -> Primitives.returnInt(mv) },
            (Type.SHORT_TYPE):   { MethodVisitor mv -> Primitives.returnInt(mv) },
            (Type.CHAR_TYPE):    { MethodVisitor mv -> Primitives.returnInt(mv) },
            (Type.LONG_TYPE):    { MethodVisitor mv -> Primitives.returnLong(mv) },
            (Type.FLOAT_TYPE):   { MethodVisitor mv -> Primitives.returnFloat(mv) },
            (Type.DOUBLE_TYPE):  { MethodVisitor mv -> Primitives.returnDouble(mv) },
            (Type.BOOLEAN_TYPE): { MethodVisitor mv -> Primitives.returnBoolean(mv) }
    ]

    private static final boxedHandlers = [
            (Type.getType(String)):     { MethodVisitor mv -> Boxed.returnString(mv) },
            (Type.getType(Integer)):    { MethodVisitor mv -> Boxed.returnInteger(mv) },
            (Type.getType(Byte)):       { MethodVisitor mv -> Boxed.returnByte(mv) },
            (Type.getType(Short)):      { MethodVisitor mv -> Boxed.returnShort(mv) },
            (Type.getType(Character)):  { MethodVisitor mv -> Boxed.returnCharacter(mv) },
            (Type.getType(Long)):       { MethodVisitor mv -> Boxed.returnLong(mv) },
            (Type.getType(Float)):      { MethodVisitor mv -> Boxed.returnFloat(mv) },
            (Type.getType(Double)):     { MethodVisitor mv -> Boxed.returnDouble(mv) },
            (Type.getType(Boolean)):    { MethodVisitor mv -> Boxed.returnBoolean(mv) }
    ]

    private static final collectionHandlers = [
            'java.util.List': { mv -> Collections.returnEmpty('List', mv) },
            'java.util.Set':  { mv -> Collections.returnEmpty('Set', mv) },
            'java.util.Map':  { mv -> Collections.returnEmpty('Map', mv) }
    ].collectEntries { className ->
        [(toDescriptor(className.key)): className.value]
    }

    // Convert class name to JVM descriptor format
    private static String toDescriptor(String className) {
        "L${className.replace('.', '/')};"
    }

    static void handleReturn(String descriptor, MethodVisitor mv) {
        Type returnType = Type.getReturnType(descriptor)

//        switch (returnType) {
//            case Type.INT_TYPE -> 0
//        }

        // Use Groovy's switch expression for cleaner matching
        switch(returnType) {
            case { Type it -> it.toString().startsWith('[') }:
                Collections.returnEmptyArray(mv, returnType.toString())
                break
            case primitiveHandlers.keySet():
                primitiveHandlers[returnType](mv)
                break

            case boxedHandlers.keySet():
                boxedHandlers[returnType](mv)
                break

            case collectionHandlers.keySet():
                collectionHandlers[returnType](mv)
                break

            case toDescriptor('java.util.Optional'):
                mv.with {
                    visitMethodInsn(
                            Opcodes.INVOKESTATIC,
                            'java/util/Optional',
                            'empty',
                            '()Ljava/util/Optional;',
                            false
                    )
                    visitInsn(Opcodes.ARETURN)
                }
                break

            default:
                mv.with {
                    visitInsn(Opcodes.ACONST_NULL)
                    visitInsn(Opcodes.ARETURN)
                }
        }
    }
}
