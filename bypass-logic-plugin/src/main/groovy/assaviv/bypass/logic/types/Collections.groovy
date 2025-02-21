package assaviv.bypass.logic.types

import org.gradle.internal.impldep.org.apache.commons.lang3.NotImplementedException
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Class which generates collections returns (e.g., int[], map...)
 */
class Collections {
    /**
        * Writes default return, empty collection, to the method
        * param collectionType type of collection List/set/Map,
        * param mv method visitor
     */
    static void returnEmpty(String collectionType, MethodVisitor mv) {
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                "java/util/Collections",
                "empty" + collectionType,
                getCollectionDescriptor(collectionType),
                false)
        mv.visitInsn(Opcodes.ARETURN)
    }

    private static String getCollectionDescriptor(String collectionType) {
        switch (collectionType) {
            case "List": return "()Ljava/util/List;"
            case "Set": return "()Ljava/util/Set;"
            case "Map": return "()Ljava/util/Map;"
            default: return "()Ljava/util/Collection;"
        }
    }

    /**
     * Writes default return, type[], to the method visitor
     * @param mv method visitor
     * @param returnType the type of the array
     */
    static void returnEmptyArray(MethodVisitor mv, String returnType) {
        String arrayType = returnType.substring(1)

        switch (arrayType) {
            case "I":
                returnArray(mv, Opcodes.T_INT)
                break
            case "J":
                returnArray(mv, Opcodes.T_LONG)
                break
            case "F":
                returnArray(mv, Opcodes.T_FLOAT)
                break
            case "D":
                returnArray(mv, Opcodes.T_DOUBLE)
                break
            case "Z":
                returnArray(mv, Opcodes.T_BOOLEAN)
                break
            case "B":
                returnArray(mv, Opcodes.T_BYTE)
                break
            case "C":
                returnArray(mv, Opcodes.T_CHAR)
                break
            case "S":
                returnArray(mv, Opcodes.T_SHORT)
                break
            default:
                // Handles array of objects.
                if (arrayType.startsWith("L")) {
                    String className = arrayType.substring(1, arrayType.length() - 1)
                    mv.visitInsn(Opcodes.ICONST_0)
                    mv.visitTypeInsn(Opcodes.ANEWARRAY, className)
                } else {
                    throw new NotImplementedException("Array of arrays is currently not supported")
                }
        }

        mv.visitInsn(Opcodes.ARETURN)
    }

    private static void returnArray(MethodVisitor mv, int opcode) {
        mv.visitInsn(Opcodes.ICONST_0)
        mv.visitIntInsn(Opcodes.NEWARRAY, opcode)
    }
}
