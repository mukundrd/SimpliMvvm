package com.trayis.simplikmvvmannotation

import java.io.File
import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.ExecutableType
import javax.tools.Diagnostic

@SupportedAnnotationTypes("com.trayis.simplikmvvmannotation.SimpliViewComponent")
class SimpliAnnotationProcessor : AbstractProcessor() {

    private val kaptKotlinGeneratedOption = "kapt.kotlin.generated"
    private lateinit var kaptKotlinGenerated: File
    private var initialized = false;

    private var messager: Messager? = null

    private val PACKAGE_NAME = "com.trayis.simplikmvvmannotation.generated"

    private val factoryPackageBuilder = StringBuilder()
    private val factoryClassBuilder = StringBuilder()
    private val factoryMethodsBuilder = StringBuilder()

    private val methods = HashSet<String>()
    private val viewModels = HashSet<String>()

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

        packageBuilder.append("package ").append(PACKAGE_NAME)

        packageBuilder.append("\n\nimport android.content.Context");
        packageBuilder.append("\n\nimport com.trayis.simplikmvvm.utils.SimpliMvvmProvider")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.viewmodel.SimpliViewModel")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.ui.SimpliBase")
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException")

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(Date()).append("\n * Do not modify.\n */")
        classBuilder.append("\nclass SimpliMvvmProviderImpl internal constructor(context : Context) : SimpliMvvmProvider() {")
        classBuilder.append("\n\n\tinit {")
        classBuilder.append("\n\t\tthis.context = context")
        classBuilder.append("\n\t\tfactory = SimpliViewModelProvidersFactoryImpl.getInstance(context)")
        classBuilder.append("\n\t}")

        classBuilder.append("\n\n\tprivate val mProviders: Array<SimpliMvvmProvider?>")

        classBuilder.append("\n\n\tcompanion object {")
        classBuilder.append("\n\n\t\tvar INSTANCE: SimpliMvvmProvider? = null")
        classBuilder.append("\n\n\t\t@Synchronized")
        classBuilder.append("\n\t\tfun getInstance(context: Context) : SimpliMvvmProvider {")
        classBuilder.append("\n\t\t\tif (INSTANCE == null) INSTANCE = SimpliMvvmProviderImpl(context)")
        classBuilder.append("\n\t\t\treturn INSTANCE!!")
        classBuilder.append("\n\t\t}")
        classBuilder.append("\n\t}")

        factoryPackageBuilder.append("package ").append(PACKAGE_NAME)

        factoryPackageBuilder.append("\n\nimport android.content.Context")
        factoryPackageBuilder.append("\n\nimport com.trayis.simplikmvvm.utils.SimpliViewModelProvidersFactory")
        factoryPackageBuilder.append("\n\nimport androidx.annotation.NonNull")
        factoryPackageBuilder.append("\nimport androidx.lifecycle.ViewModel")

        factoryClassBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(Date()).append("\n * Do not modify.\n */")
        factoryClassBuilder.append("\nclass SimpliViewModelProvidersFactoryImpl(context: Context) : SimpliViewModelProvidersFactory() {")
        factoryClassBuilder.append("\n\n\tinit {")
        factoryClassBuilder.append("\n\t\tthis.context = context")
        factoryClassBuilder.append("\n\t}")
        factoryClassBuilder.append("\n\n\tcompanion object {")
        factoryClassBuilder.append("\n\n\t\tvar INSTANCE: SimpliViewModelProvidersFactory? = null")
        factoryClassBuilder.append("\n\n\t\t@Synchronized")
        factoryClassBuilder.append("\n\t\tfun getInstance(context: Context) : SimpliViewModelProvidersFactory {")
        factoryClassBuilder.append("\n\t\t\tif (INSTANCE == null) INSTANCE = SimpliViewModelProvidersFactoryImpl(context)")
        factoryClassBuilder.append("\n\t\t\treturn INSTANCE!!")
        factoryClassBuilder.append("\n\t\t}")
        factoryClassBuilder.append("\n\t}")
        factoryClassBuilder.append("\n\n\toverride fun <T : ViewModel?> create(modelClass: Class<T>): T {")
        factoryClassBuilder.append("\n\t\t@Suppress(\"UNCHECKED_CAST\")")
        factoryClassBuilder.append("\n\t\twhen (modelClass.getName()) {")

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
            classBuilder.append("\n\t\tmProviders[").append(i).append("] = ").append(entry.key).append(".PackageMvvmProvider.getInstance(factory!!)")
        }
        classBuilder.append("\n\t}")

        classBuilder.append("\n\n\toverride fun getViewModels(simpli: SimpliBase): Array<SimpliViewModel?>? {")

        classBuilder.append("\n\n\t\tvar viewModels: Array<SimpliViewModel?>? = null")
        classBuilder.append("\n\n\t\tfor (i in 0 until mProviders.size) {")
        classBuilder.append("\n\t\t\tvar provider = mProviders.get(i)")
        classBuilder.append("\n\t\t\tprovider?.let {")
        classBuilder.append("\n\t\t\t\tviewModels = it.getViewModels(simpli)")
        classBuilder.append("\n\t\t\t\tif (viewModels != null) return viewModels")
        classBuilder.append("\n\t\t\t}")
        classBuilder.append("\n\t\t}")

        classBuilder.append("\n\n\t\treturn viewModels;\n\n\t}\n\n}\n") // close class

        factoryClassBuilder.append("\n\t\t}");
        factoryClassBuilder.append("\n\t\tthrow IllegalStateException(String.format(\"Cannot find mapping for %s class\", modelClass.getName()));");
        factoryClassBuilder.append("\n\t}"); // close class
        factoryMethodsBuilder.append("\n\n}\n");

        try {
            var source = packageBuilder.toString() + classBuilder.toString()
            var relativePath = PACKAGE_NAME.replace('.', File.separatorChar)
            var folder = File(kaptKotlinGenerated, relativePath).apply { mkdirs() }
            var file = File(folder, "SimpliMvvmProviderImpl.kt")
            file.writeText(source)

            source = factoryPackageBuilder.toString() + factoryClassBuilder.toString() + factoryMethodsBuilder.toString()
            relativePath = PACKAGE_NAME.replace('.', File.separatorChar)
            folder = File(kaptKotlinGenerated, relativePath).apply { mkdirs() }
            file = File(folder, "SimpliViewModelProvidersFactoryImpl.kt")
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
        val methodsBuilder = StringBuilder()

        val packageName = entry.key
        packageBuilder.append("package ").append(packageName)

        packageBuilder.append("\n\nimport android.content.Context");
        packageBuilder.append("\n\nimport com.trayis.simplikmvvm.utils.SimpliMvvmProvider")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.utils.SimpliViewModelProvidersFactory;")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.viewmodel.SimpliViewModel")
        packageBuilder.append("\nimport com.trayis.simplikmvvm.ui.SimpliBase")
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException")
        packageBuilder.append("\n\nimport java.util.ArrayList;")

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(Date()).append("\n * Do not modify.\n */")
        classBuilder.append("\nclass PackageMvvmProvider internal constructor() : SimpliMvvmProvider() {")

        classBuilder.append("\n\n\tcompanion object {")
        classBuilder.append("\n\t\tvar INSTANCE: PackageMvvmProvider = PackageMvvmProvider()")
        classBuilder.append("\n\n\t\t@Synchronized")
        classBuilder.append("\n\t\tfun getInstance(factory: SimpliViewModelProvidersFactory) : PackageMvvmProvider {")
        classBuilder.append("\n\t\t\tINSTANCE.factory = factory")
        classBuilder.append("\n\t\t\tINSTANCE.context = factory.context")
        classBuilder.append("\n\t\t\treturn INSTANCE")
        classBuilder.append("\n\t\t}")
        classBuilder.append("\n\t}")

        classBuilder.append("\n\n\toverride fun getViewModels(simpli: SimpliBase): Array<SimpliViewModel?>? {")

        val value = entry.value

        value.entries.forEach { listEntry ->

            val fcName = listEntry.key
            classBuilder.append("\n\n\t\tif (simpli is ").append(fcName).append(") {")
            val viewModelMethodName = "prepareViewModelsFor" + fcName.substring(fcName.lastIndexOf(".") + 1)
            classBuilder.append("\n\t\t\treturn ").append(viewModelMethodName).append("(context, simpli);")

            val list = listEntry.value.filter { it.getKind() == ElementKind.FIELD && it.getAnnotation(SimpliInject::class.java) != null }
            // classBuilder.append("\n\t\t\tviewModels = arrayOfNulls(${list.size})")

            methodsBuilder.append("\n\n\tprivate fun ").append(viewModelMethodName).append("(context: Context?, simpli: SimpliBase): Array<SimpliViewModel?>? {");
            methodsBuilder.append("\n\t\tval viewModels = arrayOfNulls<SimpliViewModel?>(").append(list.size).append(")")

            var i = 0
            list.forEach { variable ->
                if (variable.getKind() == ElementKind.FIELD && variable.getAnnotation(SimpliInject::class.java) != null) {
                    val typeMirror = variable.asType()
                    val viewModelName = typeMirror.toString()
                    val includedInFactoryClassBuilder = viewModels.contains(viewModelName)
                    if (!includedInFactoryClassBuilder) {
                        viewModels.add(viewModelName)
                    }
                    val element = processingEnv.typeUtils.asElement(typeMirror) as TypeElement
                    for (cons in element.enclosedElements) {
                        if (cons.kind === ElementKind.CONSTRUCTOR) {
                            val constructorParamTypeMirrors = (cons.asType() as ExecutableType).parameterTypes
                            if (!includedInFactoryClassBuilder) {
                                factoryClassBuilder.append("\n\t\t\t\"").append(viewModelName).append("\"->")
                            }
                            if (constructorParamTypeMirrors == null || constructorParamTypeMirrors.isEmpty()) {
                                if (!includedInFactoryClassBuilder) {
                                    factoryClassBuilder.append("\n\t\t\t\t\treturn ").append(viewModelName).append("() as T")
                                }
                                methodsBuilder.append("\n\t\t(").append("simpli as ${fcName}).").append(variable.toString()).append(" = getViewModelProviderFor(simpli)?.get(").append(viewModelName).append("::class.java)")
                            } else {
                                if (!includedInFactoryClassBuilder) {
                                    factoryClassBuilder.append("\n\t\t\t\treturn ").append(viewModelName).append("(\n\t\t\t\t\t")
                                }
                                var comma = false
                                for (constructorParam in constructorParamTypeMirrors) {
                                    val resourceElement = processingEnv.typeUtils.asElement(constructorParam) as TypeElement
                                    val resourceTypeMirror = resourceElement.asType()
                                    var resourceName = resourceTypeMirror.toString()
                                    resourceName = resourceName.substring(resourceName.lastIndexOf(".") + 1)

                                    /*
                                    if (!hasGotGetInstancemethod(resourceElement)) {
                                        throw IllegalStateException(String.format("The resource %1\$s has not got method 'public static %1\$s getInstance(Context)' implemented.", resourceName))
                                    }
                                    */

                                    if (!includedInFactoryClassBuilder) {
                                        if (comma) {
                                            factoryClassBuilder.append(", ")
                                        }
                                        comma = true
                                        factoryClassBuilder.append("prepare").append(resourceName).append("(context!!)")
                                    }

                                    if (!methods.contains(resourceName)) {
                                        methods.add(resourceName)
                                        factoryPackageBuilder.append("\nimport ").append(resourceTypeMirror)
                                        factoryMethodsBuilder.append("\n\n\tprivate fun prepare").append(resourceName).append("(context: Context): ${resourceName} {")
                                        factoryMethodsBuilder.append("\n\t\treturn getResource(${resourceName}::class.java)?.let { it as ${resourceName} }?: run {")
                                        factoryMethodsBuilder.append("\n\t\t\tval resource = ").append(resourceName).append(".getInstance(context)")
                                        factoryMethodsBuilder.append("\n\t\t\tputResource(resource)")
                                        factoryMethodsBuilder.append("\n\t\t\treturn resource")
                                        factoryMethodsBuilder.append("\n\t\t}")
                                        factoryMethodsBuilder.append("\n\t}")
                                    }
                                }
                                if (!includedInFactoryClassBuilder) {
                                    factoryClassBuilder.append(") as T")
                                }
                                methodsBuilder.append("\n\t\t(").append("simpli as ${fcName}).").append(variable.toString()).append(" = getViewModelProviderFor(simpli)?.get(").append(viewModelName).append("::class.java)")
                            }
                        }
                    }
                    methodsBuilder.append("\n\t\tviewModels[").append(i++).append("] = ").append("simpli.").append(variable.toString())
                }
            }
            methodsBuilder.append("\n\t\treturn viewModels\n\t}");
            classBuilder.append("\n\t\t}")
        }
        classBuilder.append("\n\n\t\treturn null\n\t}");
        methodsBuilder.append("\n\n}\n");

        try {
            val source = packageBuilder.toString() + classBuilder.toString() + methodsBuilder.toString()
            val relativePath = packageName.replace('.', File.separatorChar)
            val folder = File(kaptKotlinGenerated, relativePath).apply { mkdirs() }
            val file = File(folder, "PackageMvvmProvider.kt")
            file.writeText(source)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }

}