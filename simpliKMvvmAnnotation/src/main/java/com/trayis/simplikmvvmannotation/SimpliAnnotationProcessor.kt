package com.trayis.simplikmvvmannotation

import java.io.File
import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.tools.Diagnostic

@SupportedAnnotationTypes("com.trayis.simplikmvvmannotation.SimpliViewComponent")
class SimpliAnnotationProcessor : AbstractProcessor() {

    private val packageName = "com.trayis.simplikmvvmannotation.generated"
    private val kaptKotlinGeneratedOption = "kapt.kotlin.generated"
    private lateinit var kaptKotlinGenerated: File
    private var initialized = false;

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

        if (initialized) return true

        val packageBuilder = StringBuilder()
        val classBuilder = StringBuilder()

        val packageName = "com.trayis.simplikmvvmannotation.generated"
        packageBuilder.append("package ").append(packageName).append(";")

        packageBuilder.append("\n\nimport com.trayis.simplikmvvm.utils.SimpliMvvmProvider;")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.viewmodel.SimpliViewModel;")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.ui.SimpliBase;")
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException;")

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(Date()).append("\n * Do not modify.\n */")
        classBuilder.append("\nclass SimpliMvvmProviderImpl internal constructor() : SimpliMvvmProvider {")

        classBuilder.append("\n\n\tprivate val mProviders: Array<SimpliMvvmProvider?>")

        classBuilder.append("\n\n\tcompanion object {")
        classBuilder.append("\n\t\tval instance = SimpliMvvmProviderImpl()")
        classBuilder.append("\n\t}")

        val providersMap = HashMap<String, MutableMap<String, List<VariableElement>>>()

        (roundEnv.getElementsAnnotatedWith(SimpliViewComponent::class.java)).forEach { classElement ->

            if (classElement !is TypeElement) {
                return true
            }

            val fcName = classElement.toString()
            val i = fcName.lastIndexOf(".")
            val list = classElement.enclosedElements
            val varElements = ArrayList<VariableElement>()
            list.forEach {
                if (it.kind == ElementKind.FIELD && it.getAnnotation<SimpliInject>(SimpliInject::class.java) != null) {
                    varElements.add(it as VariableElement)
                }
            }
            val sPackageName = fcName.substring(0, i)
            var listMap: MutableMap<String, List<VariableElement>>? = providersMap[sPackageName]
            if (listMap == null) {
                listMap = HashMap()
            }
            listMap[fcName] = varElements
            providersMap[sPackageName] = listMap
        }

        classBuilder.append("\n\n\tinit {")
        val entries = providersMap.entries
        classBuilder.append("\n\t\tmProviders = arrayOfNulls(${entries.size})")
        var i = 0
        for (entry in entries) {
            createSimpliMvvmProvider(entry)
            classBuilder.append("\n\t\tmProviders[").append(i++).append("] = ").append(entry.key).append(".PackageMvvmProvider.instance")
        }
        classBuilder.append("\n\t}")

        classBuilder.append("\n\n\toverride fun getViewModels(base: SimpliBase): Array<SimpliViewModel?>? {")

        classBuilder.append("\n\n\t\tvar viewModels: Array<SimpliViewModel?>? = null")
        classBuilder.append("\n\n\t\tfor (i in 0 until mProviders.size) {")
        classBuilder.append("\n\t\t\tvar provider = mProviders.get(i)")
        classBuilder.append("\n\t\t\tprovider?.let {")
        classBuilder.append("\n\t\t\t\tviewModels = it.getViewModels(base)")
        classBuilder.append("\n\t\t\t\tif (viewModels != null) return viewModels")
        classBuilder.append("\n\t\t\t}")
        classBuilder.append("\n\t\t}")

        classBuilder.append("\n\n\t\treturn viewModels;\n\n\t}\n\n}\n") // close class

        try {
            val source = packageBuilder.toString() + classBuilder.toString()
            val relativePath = packageName.replace('.', File.separatorChar)
            val folder = File(kaptKotlinGenerated, relativePath).apply { mkdirs() }
            val file = File(folder, "SimpliMvvmProviderImpl.kt")
            file.writeText(source)
            initialized = true
        } catch (e: IOException) {
            e.printStackTrace()
        }


        return true

    }

    private fun createSimpliMvvmProvider(entry: MutableMap.MutableEntry<String, MutableMap<String, List<VariableElement>>>) {
        val packageBuilder = StringBuilder()
        val classBuilder = StringBuilder()

        val packageName = entry.key
        packageBuilder.append("package ").append(packageName).append(";")

        packageBuilder.append("\n\nimport com.trayis.simplikmvvm.utils.SimpliMvvmProvider;")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.viewmodel.SimpliViewModel;")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.ui.SimpliBase;")
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException;")
        packageBuilder.append("\n\nimport java.util.ArrayList;")

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(Date()).append("\n * Do not modify.\n */")
        classBuilder.append("\nclass PackageMvvmProvider internal constructor() : SimpliMvvmProvider {")

        classBuilder.append("\n\n\tcompanion object {")
        classBuilder.append("\n\t\tval instance = PackageMvvmProvider()")
        classBuilder.append("\n\t}")

        classBuilder.append("\n\n\toverride fun getViewModels(base: SimpliBase): Array<SimpliViewModel?>? {")

        val value = entry.value

        classBuilder.append("\n\n\t\tvar viewModels: Array<SimpliViewModel?>? = null;")

        value.entries.forEach { listEntry ->

            val fcName = listEntry.key
            classBuilder.append("\n\n\t\tif (base is ").append(fcName).append(") {")

            val list = listEntry.value.filter { it.getKind() == ElementKind.FIELD && it.getAnnotation(SimpliInject::class.java) != null }
            classBuilder.append("\n\t\t\tviewModels = arrayOfNulls(${list.size})")
            var i = 0
            list.forEach { variable ->
                if (variable.getKind() == ElementKind.FIELD && variable.getAnnotation(SimpliInject::class.java) != null) {
                    classBuilder.append("\n\t\t\tbase.").append(variable.toString()).append(" = ").append(variable.asType()).append("();")
                    classBuilder.append("\n\t\t\tviewModels[").append(i++).append("] = base.").append(variable.toString()).append(";")
                }
            }
            classBuilder.append("\n\t\t}")
        }
        classBuilder.append("\n\n\t\treturn viewModels;\n\t}\n\n}\n")

        try {
            val source = packageBuilder.toString() + classBuilder.toString()
            val relativePath = packageName.replace('.', File.separatorChar)
            val folder = File(kaptKotlinGenerated, relativePath).apply { mkdirs() }
            val file = File(folder, "PackageMvvmProvider.kt")
            file.writeText(source)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}