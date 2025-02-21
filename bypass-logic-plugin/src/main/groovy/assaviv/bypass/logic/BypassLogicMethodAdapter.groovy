package assaviv.bypass.logic

import assaviv.annotations.BypassLogic
import assaviv.bypass.logic.types.Boxed
import assaviv.bypass.logic.types.Collections
import assaviv.bypass.logic.types.DataType
import assaviv.bypass.logic.types.Primitives
//import assaviv.bypass.logic.types.TypesHandlersFactory
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

class BypassLogicMethodAdapter extends MethodVisitor {
    private final String name
    private final String descriptor
    private final int access
    private boolean shouldProcessAll = false
    private boolean hasBypassLogicAnnotation = false

    BypassLogicMethodAdapter(MethodVisitor mv, int access, String name, String descriptor, boolean shouldProcessAll) {
        super(Opcodes.ASM9, mv)
        this.name = name
        this.descriptor = descriptor
        this.access = access
        this.shouldProcessAll = shouldProcessAll
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor == Type.getDescriptor(BypassLogic)) {
            hasBypassLogicAnnotation = true
        }

        return super.visitAnnotation(descriptor, visible)
    }

    @Override
    void visitCode() {
        // Shouldn't process if it isn't annotated
        if (!hasBypassLogicAnnotation && !shouldProcessAll) {
            super.visitCode()
            return
        }
        // TODO: check if I can simple make it an enum
        final Map<Type, Closure> TYPE_HANDLERS = [
                (Type.VOID_TYPE)         : { MethodVisitor mv -> Primitives.returnVoid(mv) },
                (Type.INT_TYPE)          : { MethodVisitor mv -> Primitives.returnInt(mv) },
                (Type.BYTE_TYPE)         : { MethodVisitor mv -> Primitives.returnInt(mv) },
                (Type.SHORT_TYPE)        : { MethodVisitor mv -> Primitives.returnInt(mv) },
                (Type.CHAR_TYPE)         : { MethodVisitor mv -> Primitives.returnInt(mv) },
                (Type.LONG_TYPE)         : { MethodVisitor mv -> Primitives.returnLong(mv) },
                (Type.FLOAT_TYPE)        : { MethodVisitor mv -> Primitives.returnFloat(mv) },
                (Type.DOUBLE_TYPE)       : { MethodVisitor mv -> Primitives.returnDouble(mv) },
                (Type.BOOLEAN_TYPE)      : { MethodVisitor mv -> Primitives.returnBoolean(mv) },
                (Type.getType(String))   : { MethodVisitor mv -> Boxed.returnString(mv) },
                (Type.getType(Integer))  : { MethodVisitor mv -> Boxed.returnInteger(mv) },
                (Type.getType(Byte))     : { MethodVisitor mv -> Boxed.returnByte(mv) },
                (Type.getType(Short))    : { MethodVisitor mv -> Boxed.returnShort(mv) },
                (Type.getType(Character)): { MethodVisitor mv -> Boxed.returnCharacter(mv) },
                (Type.getType(Long))     : { MethodVisitor mv -> Boxed.returnLong(mv) },
                (Type.getType(Float))    : { MethodVisitor mv -> Boxed.returnFloat(mv) },
                (Type.getType(Double))   : { MethodVisitor mv -> Boxed.returnDouble(mv) },
                (Type.getType(Boolean))  : { MethodVisitor mv -> Boxed.returnBoolean(mv) },
                (Type.getType(List))     : { MethodVisitor mv -> Collections.returnEmpty("List", mv) },
                (Type.getType(Set))      : { MethodVisitor mv -> Collections.returnEmpty("Set", mv) },
                (Type.getType(Map))      : { MethodVisitor mv -> Collections.returnEmpty("Map", mv) },
                (Type.getType(Optional)) : { MethodVisitor mv -> returnEmptyOptional(mv) }
        ]

//        switch (Type.getReturnType(descriptor)) {
//            case Type.INT_TYPE -> Primitives.returnInt(mv)
//        }

        String returnType = Type.getReturnType(descriptor).toString()

        // Void methods ⁃ end with V
        if (returnType == "V") {
            Primitives.returnVoid(mv)
        }
        // Check 1f return type is array
        else if (returnType.startsWith("[")) {
            Collections.returnEmptyArray(mv, returnType)
        }
        // Integer types (int, byte, short, char) ⁃ end with I, B, S, or C
        else if (returnType in ["I", "B", "S", "C"]) {
            Primitives.returnInt(mv)
        }
        // Long - ends with J
        else if (returnType == "J") {
            Primitives.returnLong(mv)
        }
        // Float - ends with F
        else if (returnType == "F") {
            Primitives.returnFloat(mv)
        }
        // Double - ends with D
        else if (returnType == "D") {
            Primitives.returnDouble(mv)
        }
        // Boolean - ends with Z
        else if (returnType == "Z") {
            Primitives.returnBoolean(mv)
        }
        // Check if return type is string
        else if (returnType == "Ljava/lang/String;") {
            Boxed.returnString(mv)
        }
        // Check if return type is Integer
        else if (returnType == "Ljava/lang/Integer;") {
            Boxed.returnInteger(mv)
        }
        // Check if return type is Byte
        else if (returnType == "Ljava/lang/Byte;") {
            Boxed.returnByte(mv)
        }
        // Check if return type is Short
        else if (returnType == "Ljava/lang/Short;") {
            Boxed.returnShort(mv)
        }
        // Check if return type is Character
        else if (returnType == "Ljava/lang/Character;") {
            Boxed.returnCharacter(mv)
        }
        // Check if return type is Long
        else if (returnType == "Ljava/lang/Long;") {
            Boxed.returnLong(mv)
        }
        // Check if return type is Float
        else if (returnType == "Ljava/lang/Float;") {
            Boxed.returnFloat(mv)
        }
        // Check if return type is Double
        else if (returnType == "Ljava/lang/Double;") {
            Boxed.returnDouble(mv)
        }
        // Check if return type is Boolean
        else if (returnType == "Ljava/lang/Boolean;") {
            Boxed.returnBoolean(mv)
        }
        // Check if return type is empty List
        else if (returnType == "Ljava/util/List;") {
            Collections.returnEmpty("List", mv)
        }
        // Check if return type is empty Set
        else if (returnType == "Ljava/util/Set;") {
            Collections.returnEmpty("Set", mv)
        }
        // Check if return type is empty Map
        else if (returnType == "Ljava/util/Map;") {
            Collections.returnEmpty("Map", mv)
        }
        // Check if return type is Optional
        else if (returnType == "Ljava/util/Optional;") {
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    "java/util/Optional",
                    "empty",
                    "()Ljava/util/Optional;",
                    false) // Push Optional.empty() onto stack
            mv.visitInsn(Opcodes.ARETURN) // Return empty Optional
        }
        // Object types - start with L and end with ;
        else {
            mv.visitInsn(Opcodes.ACONST_NULL)
            mv.visitInsn(Opcodes.ARETURN)
        }

        mv.visitEnd()
    }
}
