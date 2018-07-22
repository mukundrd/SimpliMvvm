package com.trayis.simplikmvvmannotation

import java.io.File
import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.tools.Diagnostic

@SupportedAnnotationTypes("com.trayis.simplikmvvmannotation.SimpliViewComponent")
class SimpliAnnotationProcessor : AbstractProcessor() {

    private val packageName = "com.trayis.simplimvvmannotation.generated"
    private val kaptKotlinGeneratedOption = "kapt.kotlin.generated"
    private lateinit var kaptKotlinGenerated: File

    private var messager: Messager? = null

    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        messager = processingEnv.messager
        messager!!.printMessage(Diagnostic.Kind.NOTE, "Initializing KAPT Simpli Annotation Processor ...")
        kaptKotlinGenerated = File(processingEnv.options[kaptKotlinGeneratedOption])
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {

        val packageBuilder = StringBuilder()
        val classBuilder = StringBuilder()

        packageBuilder.append("package $packageName;")

        packageBuilder.append("\n\nimport android.arch.lifecycle.ViewModelProvider;")
        packageBuilder.append("\nimport android.arch.lifecycle.ViewModelProviders;")
        packageBuilder.append("\nimport android.support.v4.app.Fragment;")
        packageBuilder.append("\nimport android.support.v4.app.FragmentActivity;")

        packageBuilder.append("\n\nimport com.trayis.simplikmvvm.ui.Simpli;")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.utils.SimpliMvvmProvider;")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.viewmodel.SimpliViewModel;")
        packageBuilder.append("\n\nimport java.util.*;\n")

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on " + Date() + "\n * Do not modify.\n */")
        classBuilder.append("\nclass SimpliMvvmProviderImpl<V : SimpliViewModel> internal constructor() : SimpliMvvmProvider<V> {")

        classBuilder.append("\n\n\tcompanion object {")
        classBuilder.append("\n\t\tval instance: SimpliMvvmProvider<*> = SimpliMvvmProviderImpl<SimpliViewModel>()")
        classBuilder.append("\n\t}")

        classBuilder.append("\n\n\t@Throws(InvalidPropertiesFormatException::class)\n\toverride fun getViewModel(simpli: Simpli): V {")

        val annotation = annotations.firstOrNull { it.toString() == "com.trayis.simplikmvvmannotation.SimpliViewComponent" }
                ?: return false

        for (element in roundEnv.getElementsAnnotatedWith(annotation)) {

            if (element !is TypeElement) {
                messager?.printMessage(Diagnostic.Kind.ERROR, "Oops... This annotation is for Classes only")
                return true
            }

            val typeElement = element
            val declaredType = typeElement.superclass as DeclaredType
            val typeArguments = declaredType.typeArguments

            if (typeArguments.size < 2) {
                messager?.printMessage(Diagnostic.Kind.ERROR, "Oops... missing arguments for " + element.getSimpleName())
                return true
            }

            var viewModalName: String = typeArguments[1].toString()

            packageBuilder.append("\nimport $viewModalName;")

            classBuilder.append("\n\n\t\tval viewModelProvider: ViewModelProvider")
            classBuilder.append("\n\t\tif (simpli is Fragment) {")
            classBuilder.append("\n\t\t\tviewModelProvider = ViewModelProviders.of(simpli as Fragment)")
            classBuilder.append("\n\t\t} else {")
            classBuilder.append("\n\t\t\tviewModelProvider = ViewModelProviders.of(simpli as FragmentActivity)")
            classBuilder.append("\n\t\t}")

            classBuilder.append("\n\n\t\t@Suppress(\"UNCHECKED_CAST\")")
            classBuilder.append("\n\t\tif (simpli is " + element.qualifiedName + ")")
            classBuilder.append("\n\t\t\treturn viewModelProvider.get(" + viewModalName.substring(viewModalName.lastIndexOf(".") + 1) + "::class.java) as V")

        }

        classBuilder.append("\n\n\t\tthrow java.util.InvalidPropertiesFormatException(\"view of type\" + simpli.javaClass + \" is not supported.\")\n\n\t}\n\n}\n") // close class

        try {
            val source = packageBuilder.toString() + classBuilder.toString()
            val relativePath = packageName.replace('.', File.separatorChar)
            val folder = File(kaptKotlinGenerated, relativePath).apply { mkdirs() }
            val file = File(folder, "SimpliMvvmProviderImpl.kt")
            file.writeText(source)
        } catch (e: IOException) {
            messager?.printMessage(Diagnostic.Kind.ERROR, e.message)
        }

        return true

    }

}