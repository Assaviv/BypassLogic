package assaviv.bypass.logic.types

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Class which generates primitives returns (e.g., int, float...)
 */
class Primitives {
    /**
    * Writes default return, void, to the method visitor
    * @param mv method visitor
    */
    static void returnVoid(MethodVisitor mv) {
        mv.visitInsn(Opcodes.RETURN)
    }

    /**
    * Writes default return int, o, to the method visitor
    * @param mv method visitor
    */
    static void returnInt(MethodVisitor mv) {
        mv.visitInsn(Opcodes.ICONST_0) // Push 0 onto stack
        mv.visitInsn(Opcodes.IRETURN) // Return integer type
    }

    /**
    * Writes default return long, OL, to the method visitor
    * @param mv method visitor
    */
    static void returnLong(MethodVisitor mv) {
        mv.visitInsn(Opcodes.LCONST_0) // Push OL onto stack
        mv.visitInsn(Opcodes.LRETURN) // Return long
    }

    /**
    * Writes default return float, O.0F, to the method visitor
    * @param mv method visitor
    */
    static void returnFloat(MethodVisitor mv) {
        mv.visitInsn(Opcodes.FCONST_0) // Push 0.0f onto stack
        mv.visitInsn(Opcodes.FRETURN) // Return float
    }

    /**
     * Writes default return double, O.0, to the method visitor
     * @param mv method visitor
     */
    static void returnDouble(MethodVisitor mv) {
        mv.visitInsn(Opcodes.DCONST_0) //Push 0.0d onto stack
        mv.visitInsn(Opcodes.DRETURN) // Return double
    }

    /**
    * Writes default return boolean, false, to the method visitor
    * @param mv method visitor
    */
    static void returnBoolean(MethodVisitor mv) {
        mv.visitInsn(Opcodes.ICONST_0) // Push_false (0) onto stack
        mv.visitInsn(Opcodes.IRETURN) // Return boolean
    }
}
