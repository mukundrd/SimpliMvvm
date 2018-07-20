package com.trayis.simplikmvvmannotation

import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.tools.Diagnostic

@SupportedAnnotationTypes("com.trayis.simplikmvvmannotation.SimpliViewComponent")
class SimpliAnnotationProcessor : AbstractProcessor() {

    private val packageName = "com.trayis.simplimvvmannotation.generated"

    private var messager: Messager? = null

    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        messager = processingEnv.messager
        messager!!.printMessage(Diagnostic.Kind.NOTE, "Initializing SimpliAnnotationProcessor ...")
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val packageBuilder = StringBuilder()
        val classBuilder = StringBuilder()

        packageBuilder.append("package $packageName;")

        packageBuilder.append("\n\nimport android.arch.lifecycle.ViewModelProvider;")
        packageBuilder.append("\nimport android.arch.lifecycle.ViewModelProviders;")
        packageBuilder.append("\nimport android.support.v4.app.Fragment;")
        packageBuilder.append("\nimport android.support.v4.app.FragmentActivity;")

        packageBuilder.append("\n\nimport com.trayis.simplimvvm.ui.Simpli;")
        packageBuilder.append("\nimport com.trayis.simplimvvm.utils.SimpliMvvmProvider;")
        packageBuilder.append("\nimport com.trayis.simplimvvm.viewmodel.SimpliViewModel;")
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException;\n")

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on " + Date() + "\n * Do not modify.\n */")
        classBuilder.append("\npublic class SimpliMvvmProviderImpl<V extends SimpliViewModel> implements SimpliMvvmProvider<V> {")

        classBuilder.append("\n\n\tprivate static final SimpliMvvmProvider instance = new SimpliMvvmProviderImpl();")

        classBuilder.append("\n\n\tpublic static SimpliMvvmProvider getInstance() {\n\t\treturn instance;\n\t}")

        classBuilder.append("\n\n\tprivate SimpliMvvmProviderImpl() {}")

        classBuilder.append("\n\n\t@Override\n\t@SuppressWarnings(\"unchecked\")\n\tpublic V getViewModel(Simpli simpli) throws InvalidPropertiesFormatException {")

        val annotation = annotations.firstOrNull { it.toString() == "com.trayis.simplimvvmannotation.generated" }
                ?: return false

        for (element in roundEnv.getElementsAnnotatedWith(annotation)) {

            if (element !is TypeElement) {
                messager?.printMessage(Diagnostic.Kind.ERROR, "Oops... This annotation is for Classes only")
                return true
            }

            val typeElement = element as TypeElement
            val declaredType = typeElement.superclass as DeclaredType
            val typeArguments = declaredType.typeArguments

            if (typeArguments.size < 2) {
                messager?.printMessage(Diagnostic.Kind.ERROR, "Oops... missing arguments for " + element.getSimpleName())
                return true
            }

            var viewModalName: String = typeArguments[1].toString()

            packageBuilder.append("\nimport $viewModalName;")

            classBuilder.append("\n\n\t\tViewModelProvider viewModelProvider;")
            classBuilder.append("\n\t\tif (simpli instanceof Fragment) {")
            classBuilder.append("\n\t\t\tviewModelProvider = ViewModelProviders.of((Fragment) simpli);")
            classBuilder.append("\n\t\t} else {")
            classBuilder.append("\n\t\t\tviewModelProvider = ViewModelProviders.of((FragmentActivity) simpli);")
            classBuilder.append("\n\t\t}")

            classBuilder.append("\n\n\t\tif (simpli instanceof " + (element as TypeElement).qualifiedName + ")")
            classBuilder.append("\n\t\t\treturn (V)viewModelProvider.get(" + viewModalName.substring(viewModalName.lastIndexOf(".") + 1) + ".class);")

        }

        classBuilder.append("\n\n\t\tthrow new java.util.InvalidPropertiesFormatException(\"view of type\" + simpli.getClass() + \" is not supported.\");\n\n\t}\n\n}\n") // close class

        try {
            val source = processingEnv.filer.createSourceFile(packageName + ".SimpliMvvmProviderImpl")
            val writer = source.openWriter()
            writer.write(packageBuilder.toString())
            writer.write(classBuilder.toString())
            writer.flush()
            writer.close()
        } catch (e: IOException) {
        }

        return true

    }

}