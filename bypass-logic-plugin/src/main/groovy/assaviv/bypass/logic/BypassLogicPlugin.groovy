package assaviv.bypass.logic

import assaviv.annotations.BypassLogic
import assaviv.bypass.logic.visitors.AnnotationsCheckerVisitor
import assaviv.bypass.logic.visitors.BypassLogicClassAdapter
import assaviv.bypass.logic.visitors.SuperClassResolvingWriter
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

class BypassLogicPlugin implements Plugin<Project> {
    BypassLogicExtension config

    @Override
    void apply(Project project) {
        config = project.extensions.create('bypassLogicConfig', BypassLogicExtension)

        project.afterEvaluate {
            if (!config.enabled) {
                return
            }
            println(GroovySystem.version)
            project.tasks.withType(JavaCompile).tap {
                URLClassLoader projectClassLoader = new URLClassLoader(
                        project.configurations.compileClasspath.files.collect { File file ->
                            file.toURI().toURL() } as URL[],
                        this.class.classLoader
                )

                configureEach { JavaCompile compileJava ->
                    compileJava.doLast {
                        compileJava.destinationDirectory.asFileTree.each { File file ->
                            if (file.name.endsWith('.class')) {
                                processClass(file, projectClassLoader)
                            }
                        }
                    }
                }
            }
        }
    }

    private void processClass(File classFile, ClassLoader classLoader) {
        byte[] originalBytes = classFile.bytes

        if (!containsBypassAnnotation(originalBytes)) {
            return
        }

        ClassReader reader = new ClassReader(originalBytes)
        SuperClassResolvingWriter writer = new SuperClassResolvingWriter(ClassWriter.COMPUTE_FRAMES, classLoader)
        BypassLogicClassAdapter visitor = new BypassLogicClassAdapter(Opcodes.ASM9, writer)

        reader.accept(visitor, ClassReader.EXPAND_FRAMES)
        classFile.bytes = writer.toByteArray()
    }

    private static boolean containsBypassAnnotation(byte[] classFile) {
        ClassReader reader = new ClassReader(classFile)
        AnnotationsCheckerVisitor annotationsChecker = new AnnotationsCheckerVisitor(Opcodes.ASM9)
        reader.accept(annotationsChecker, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG | ClassReader.SKIP_CODE)

        return annotationsChecker.hasAnnotations()
    }
}
