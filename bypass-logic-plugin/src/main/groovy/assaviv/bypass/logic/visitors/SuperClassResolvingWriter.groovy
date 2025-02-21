package assaviv.bypass.logic.visitors

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class SuperClassResolvingWriter extends ClassWriter {
    private final ClassLoader loader

    SuperClassResolvingWriter(int flags, ClassLoader loader) {
        super(flags)
        this.loader = loader
    }

    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        Class<?> class1, class2

        try {
            class1 = loadClass(type1)
            class2 = loadClass(type2)
        } catch (Exception exception) {
            println("Tried to load class: $type1, $type2, but caught an exception $exception")
            return "java/lang/Object"
        }

        if (class1.isAssignableFrom(class2)) {
            return type1
        }

        if (class2.isAssignableFrom(class1)) {
            return type2
        }

        if (class1.isInterface() || class2.isInterface()) {
            return "java/lang/Object"
        }

        do {
            class1 = class1.getSuperclass()
        } while (!class1.isAssignableFrom(class2))

        return class1.getName().replace('.', '/')
    }

    Class<?> loadClass(String type) {
        try {
            return Class.forName(type.replace('/', '.'), false, loader)
        } catch (ClassNotFoundException exception) {
            println("Class $type not found within the project classpath trying with system classpath\n$exception")
            return Class.forName(type.replace('/', '.'), false, ClassLoader.systemClassLoader)
        }
    }
}
