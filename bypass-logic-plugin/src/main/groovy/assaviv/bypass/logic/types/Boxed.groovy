package assaviv.bypass.logic.types

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

/**
 * Class which generates boxed primitives returns (e.g., Integer, Float...)
 */
class Boxed {
    /**
     * Writes default return string, "", to the method visitor
     * @param mv method visitor
     */
    static void returnString(MethodVisitor mv) {
        mv.visitLdcInsn("") // Push "" onto stack
        mv.visitInsn(Opcodes.ARETURN) // Return empty string
    }

    /**
     * Writes default return integer, 0, to the method visitor
     * @param mv method visitor
     */
    static void returnInteger(MethodVisitor mv) {
        invokeDefaultValueOf(mv, Opcodes.ICONST_0, Integer, int)
    }

    /**
     * Writes default return byte, 0, to the method visitor
     * @param mv method visitor
     */
    static void returnByte(MethodVisitor mv) {
        invokeDefaultValueOf(mv, Opcodes.ICONST_0, Byte, byte)
    }

    /**
     * Writes default return short, 0, to the method visitor
     * @param mv method visitor
     */
    static void returnShort(MethodVisitor mv) {
        invokeDefaultValueOf(mv, Opcodes.ICONST_0, Short, short)
    }

    /**
     * Writes default return character, \u0000, to the method visitor
     * @param mv method visitor
     */
    static void returnCharacter(MethodVisitor mv) {
        invokeDefaultValueOf(mv, Opcodes.ICONST_0, Character, char)
    }

    /**
     * Writes default return long, 0L, to the method visitor
     * @param mv method visitor
     */
    static void returnLong(MethodVisitor mv) {
        invokeDefaultValueOf(mv, Opcodes.LCONST_0, Long, long)
    }

    /**
     * Writes default return float, 0.0F, to the method visitor
     * @param mv method visitor
     */
    static void returnFloat(MethodVisitor mv) {
        invokeDefaultValueOf(mv, Opcodes.FCONST_0, Float, float)
    }

    /**
     * Writes default return double, 0.0, to the method visitor
     * @param mv method visitor
     */
    static void returnDouble(MethodVisitor mv) {
        invokeDefaultValueOf(mv, Opcodes.DCONST_0, Double, double)
    }

    /**
     * Writes default return boolean, false, to the method visitor
     * @param mv method visitor
     */
    static void returnBoolean(MethodVisitor mv) {
        invokeDefaultValueOf(mv, Opcodes.ICONST_0, Boolean, boolean)
    }

    private static void invokeDefaultValueOf(MethodVisitor mv, int opcode, Class<?> clazz, Class<?> returnClass) {
        Type classType = Type.getType(clazz)
        Type returnType = Type.getType(returnClass)

        mv.visitInsn(opcode)
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                classType.getInternalName(),
                "valueOf",
                Type.getMethodDescriptor(classType, returnType),
                false)
        mv.visitInsn(Opcodes.ARETURN)
    }
}
