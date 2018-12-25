package com.trayis.simplimvvmannotation;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import static javax.lang.model.element.ElementKind.METHOD;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.trayis.simplimvvmannotation.SimpliViewComponent")
public class SimpliAnnotationProcessor extends AbstractProcessor {

    // private Messager messager;
    private boolean initialized;

    private static final String PACKAGE_NAME = "com.trayis.simplimvvmannotation.generated";

    private StringBuilder factoryPackageBuilder = new StringBuilder();
    private StringBuilder factoryClassBuilder = new StringBuilder();
    private StringBuilder factoryMethodsBuilder = new StringBuilder();

    private Set<String> methods = new HashSet<>();
    private Set<String> viewModels = new HashSet<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        // messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (initialized) return true;

        StringBuilder packageBuilder = new StringBuilder();
        StringBuilder classBuilder = new StringBuilder();
        packageBuilder.append("package ").append(PACKAGE_NAME).append(";");

        packageBuilder.append("\n\nimport android.content.Context;");
        packageBuilder.append("\n\nimport com.trayis.simplimvvm.utils.SimpliMvvmProvider;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.viewmodel.SimpliViewModel;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.ui.SimpliBase;");
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException;");

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(new Date()).append("\n * Do not modify.\n */");
        classBuilder.append("\npublic class SimpliMvvmProviderImpl extends SimpliMvvmProvider {");
        classBuilder.append("\n\n\tprivate static SimpliMvvmProvider instance;");
        classBuilder.append("\n\n\tprivate SimpliMvvmProvider[] mProviders;");
        classBuilder.append("\n\n\tpublic static synchronized SimpliMvvmProvider getInstance(Context context) {");
        classBuilder.append("\n\t\tif (instance == null) {");
        classBuilder.append("\n\t\t\tinstance = new SimpliMvvmProviderImpl(context.getApplicationContext());");
        classBuilder.append("\n\t\t}");
        classBuilder.append("\n\t\treturn instance;");
        classBuilder.append("\n\t}");

        factoryPackageBuilder.append("package ").append(PACKAGE_NAME).append(";");

        factoryPackageBuilder.append("\n\nimport android.content.Context;");
        factoryPackageBuilder.append("\n\nimport com.trayis.simplimvvm.utils.SimpliViewModelProvidersFactory;");
        factoryPackageBuilder.append("\n\nimport androidx.annotation.NonNull;");
        factoryPackageBuilder.append("\nimport androidx.lifecycle.ViewModel;");

        factoryClassBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(new Date()).append("\n * Do not modify.\n */");
        factoryClassBuilder.append("\npublic class SimpliViewModelProvidersFactoryImpl extends SimpliViewModelProvidersFactory {");
        factoryClassBuilder.append("\n\n\tprivate static SimpliViewModelProvidersFactory instance;");
        factoryClassBuilder.append("\n\n\tprivate SimpliViewModelProvidersFactoryImpl(Context context) {");
        factoryClassBuilder.append("\n\t\tthis.context = context;");
        factoryClassBuilder.append("\n\t}");
        factoryClassBuilder.append("\n\n\tpublic static synchronized SimpliViewModelProvidersFactory getInstance(Context context) {");
        factoryClassBuilder.append("\n\t\tif (instance == null) {");
        factoryClassBuilder.append("\n\t\t\tinstance = new SimpliViewModelProvidersFactoryImpl(context.getApplicationContext());");
        factoryClassBuilder.append("\n\t\t}");
        factoryClassBuilder.append("\n\t\treturn instance;");
        factoryClassBuilder.append("\n\t}");
        factoryClassBuilder.append("\n\n\t@NonNull\n\t@Override\n\t@SuppressWarnings(\"unchecked\")\n\tpublic <T extends ViewModel> T create(Class<T> modelClass) {");
        factoryClassBuilder.append("\n\t\tswitch (modelClass.getName()) {");

        String resourceProvider = null;
        for (Element classElement : roundEnv.getElementsAnnotatedWith(SimpliResourcesProvider.class)) {
            if (!(classElement instanceof TypeElement)) {
                return true;
            }
            resourceProvider = classElement.toString();
        }

        HashMap<String, Map<String, List<VariableElement>>> providersMap = new HashMap<>();

        for (Element classElement : roundEnv.getElementsAnnotatedWith(SimpliViewComponent.class)) {

            if (!(classElement instanceof TypeElement)) {
                return true;
            }

            TypeElement typeElement = (TypeElement) classElement;

            String fcName = typeElement.toString();
            int i = fcName.lastIndexOf(".");
            List<? extends Element> list = typeElement.getEnclosedElements();
            List<VariableElement> varElements = new ArrayList<>();
            for (Element enclosed : list) {
                if (enclosed.getKind() == ElementKind.FIELD && enclosed.getAnnotation(SimpliInject.class) != null) {
                    varElements.add((VariableElement) enclosed);
                }
            }
            String sPackageName = fcName.substring(0, i);
            Map<String, List<VariableElement>> listMap = providersMap.get(sPackageName);
            if (listMap == null) {
                listMap = new HashMap<>();
            }
            listMap.put(fcName, varElements);
            providersMap.put(sPackageName, listMap);
        }

        classBuilder.append("\n\n\tprivate SimpliMvvmProviderImpl(Context context) {");
        classBuilder.append("\n\t\tthis.context = context;");
        classBuilder.append("\n\t\tfactory = SimpliViewModelProvidersFactoryImpl.getInstance(context);\n");
        Set<Map.Entry<String, Map<String, List<VariableElement>>>> entries = providersMap.entrySet();
        classBuilder.append("\n\t\tmProviders = new SimpliMvvmProvider[").append(entries.size()).append("];");
        int i = 0;
        for (Map.Entry<String, Map<String, List<VariableElement>>> entry : entries) {
            createSimpliMvvmProvider(entry, resourceProvider);
            classBuilder.append("\n\t\tmProviders[").append(i).append("] = ").append(entry.getKey()).append(".PackageMvvmProvider.getInstance(factory);");
            classBuilder.append("\n\t\tmProviders[").append(i++).append("].setFactory(factory);");
        }
        classBuilder.append("\n\t}");

        classBuilder.append("\n\n\t@Override\n\t@SuppressWarnings(\"unchecked\")\n\tpublic SimpliViewModel[] getViewModels(SimpliBase base) throws InvalidPropertiesFormatException {");

        classBuilder.append("\n\n\t\tSimpliViewModel[] viewModels = new SimpliViewModel[mProviders.length];");
        classBuilder.append("\n\t\tfor (int i=0; i<mProviders.length; i++) {");
        classBuilder.append("\n\t\t\tSimpliMvvmProvider provider = mProviders[i];");
        classBuilder.append("\n\t\t\tviewModels = provider.getViewModels(base);");
        classBuilder.append("\n\t\t\tif (viewModels != null) break;");
        classBuilder.append("\n\t\t}");

        classBuilder.append("\n\n\t\treturn viewModels;\n\n\t}\n\n}\n"); // close class

        factoryClassBuilder.append("\n\t\t}");
        factoryClassBuilder.append("\n\t\tthrow new IllegalStateException(String.format(\"Cannot find mapping for %s class\", modelClass.getName()));");
        factoryClassBuilder.append("\n\t}"); // close class
        factoryMethodsBuilder.append("\n\n}\n");

        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile(PACKAGE_NAME + ".SimpliMvvmProviderImpl");
            Writer writer = source.openWriter();
            writer.write(packageBuilder.toString());
            writer.write(classBuilder.toString());
            writer.flush();
            writer.close();

            source = processingEnv.getFiler().createSourceFile(PACKAGE_NAME + ".SimpliViewModelProvidersFactoryImpl");
            writer = source.openWriter();
            writer.write(factoryPackageBuilder.toString());
            writer.write(factoryClassBuilder.toString());
            writer.write(factoryMethodsBuilder.toString());
            writer.flush();
            writer.close();

            initialized = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void createSimpliMvvmProvider(Map.Entry<String, Map<String, List<VariableElement>>> entry, String resourceProvider) {
        StringBuilder packageBuilder = new StringBuilder();
        StringBuilder classBuilder = new StringBuilder();
        StringBuilder methodsBuilder = new StringBuilder();

        String packageName = entry.getKey();
        packageBuilder.append("package ").append(packageName).append(";");

        packageBuilder.append("\n\nimport android.content.Context;");
        packageBuilder.append("\n\nimport com.trayis.simplimvvm.utils.SimpliMvvmProvider;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.utils.SimpliViewModelProvidersFactory;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.viewmodel.SimpliViewModel;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.ui.SimpliBase;");
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException;");

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(new Date()).append("\n * Do not modify.\n */");
        classBuilder.append("\npublic class PackageMvvmProvider extends SimpliMvvmProvider {");
        classBuilder.append("\n\n\tprivate static final PackageMvvmProvider instance = new PackageMvvmProvider();");
        classBuilder.append("\n\n\tpublic static PackageMvvmProvider getInstance(SimpliViewModelProvidersFactory factory) {\n\t\tinstance.factory = factory;\n\t\treturn instance;\n\t}");
        classBuilder.append("\n\n\tprivate PackageMvvmProvider() {}");
        classBuilder.append("\n\n\t@Override\n\tpublic SimpliViewModel[] getViewModels(SimpliBase base) throws InvalidPropertiesFormatException {");

        Map<String, List<VariableElement>> value = entry.getValue();

        for (Map.Entry<String, List<VariableElement>> listEntry : value.entrySet()) {

            String fcName = listEntry.getKey();
            classBuilder.append("\n\n\t\tif (base instanceof ").append(fcName).append(") {");
            String viewModelMethodName = "prepareViewModelsFor" + fcName.substring(fcName.lastIndexOf(".") + 1);
            classBuilder.append("\n\t\t\treturn ").append(viewModelMethodName).append("(context, base);");

            List<VariableElement> list = listEntry.getValue();
            Iterator<VariableElement> iterator = list.iterator();
            while (iterator.hasNext()) {
                Element enclosed = iterator.next();
                if (!(enclosed.getKind() == ElementKind.FIELD && enclosed.getAnnotation(SimpliInject.class) != null))
                    iterator.remove();
            }

            methodsBuilder.append("\n\n\tprivate SimpliViewModel[] ").append(viewModelMethodName).append("(Context context, SimpliBase base) {");
            methodsBuilder.append("\n\t\tSimpliViewModel[] viewModels = new SimpliViewModel[").append(list.size()).append("];");
            int i = 0;
            for (Element enclosed : list) {
                if (enclosed.getKind() == ElementKind.FIELD && enclosed.getAnnotation(SimpliInject.class) != null) {
                    VariableElement variable = (VariableElement) enclosed;
                    TypeMirror typeMirror = variable.asType();
                    String viewModelName = typeMirror.toString();
                    boolean includedInFactoryClassBuilder = viewModels.contains(viewModelName);
                    if (!includedInFactoryClassBuilder) {
                        viewModels.add(viewModelName);
                    }
                    TypeElement element = (TypeElement) processingEnv.getTypeUtils().asElement(typeMirror);
                    for (Element cons : element.getEnclosedElements()) {
                        if (cons.getKind() == ElementKind.CONSTRUCTOR) {
                            List<? extends TypeMirror> constructorParamTypeMirrors = ((ExecutableType) cons.asType()).getParameterTypes();
                            if (!includedInFactoryClassBuilder) {
                                factoryClassBuilder.append("\n\t\t\tcase \"").append(viewModelName).append("\":");
                            }
                            if (constructorParamTypeMirrors == null || constructorParamTypeMirrors.isEmpty()) {
                                if (!includedInFactoryClassBuilder) {
                                    factoryClassBuilder.append("\n\t\t\t\t\treturn (T)new ").append(viewModelName).append("();");
                                }
                                methodsBuilder.append("\n\t\t((").append(fcName).append(")base).").append(variable.toString()).append(" = getViewModelProviderFor(base).get(").append(viewModelName).append(".class);");
                            } else {
                                if (!includedInFactoryClassBuilder) {
                                    factoryClassBuilder.append("\n\t\t\t\treturn (T)new ").append(viewModelName).append("(\n\t\t\t\t\t");
                                }
                                boolean comma = false;
                                for (TypeMirror constructorParam : constructorParamTypeMirrors) {
                                    TypeElement resourceElement = (TypeElement) processingEnv.getTypeUtils().asElement(constructorParam);
                                    TypeMirror resourceTypeMirror = resourceElement.asType();
                                    String resourceName = resourceTypeMirror.toString();
                                    resourceName = resourceName.substring(resourceName.lastIndexOf(".") + 1);

                                    if (!hasGotGetInstancemethod(resourceElement)) {
                                        throw new IllegalStateException(String.format("The resource %1$s has not got method 'public static %1$s getInstance(Context)' implemented.", resourceName));
                                    }

                                    if (!includedInFactoryClassBuilder) {
                                        if (comma) {
                                            factoryClassBuilder.append(", ");
                                        }
                                        comma = true;
                                        factoryClassBuilder.append("prepare").append(resourceName).append("(context)");
                                    }

                                    if (!methods.contains(resourceName)) {
                                        methods.add(resourceName);
                                        factoryPackageBuilder.append("\nimport ").append(resourceTypeMirror).append(";");
                                        factoryMethodsBuilder.append("\n\n\tprivate ").append(resourceName).append(" prepare").append(resourceName).append("(Context context) {");
                                        if (resourceProvider != null) {
                                            factoryMethodsBuilder.append("\n\t\t return ").append(resourceProvider).append(".getInstance().prepare").append(resourceName).append("(context);");
                                        } else {
                                            factoryMethodsBuilder.append("\n\t\t").append(resourceName).append(" resource = getResource(").append(resourceName).append(".class);");
                                            factoryMethodsBuilder.append("\n\t\tif (resource == null) {");
                                            factoryMethodsBuilder.append("\n\t\t\tresource = ").append(resourceName).append(".getInstance(context);");
                                            factoryMethodsBuilder.append("\n\t\t\tputResource(resource);");
                                            factoryMethodsBuilder.append("\n\t\t}");
                                            factoryMethodsBuilder.append("\n\t\treturn resource;");
                                        }
                                        factoryMethodsBuilder.append("\n\t}");
                                    }
                                }
                                if (!includedInFactoryClassBuilder) {
                                    factoryClassBuilder.append(");");
                                }
                                methodsBuilder.append("\n\t\t((").append(fcName).append(")base).").append(variable.toString()).append(" = getViewModelProviderFor(base).get(").append(viewModelName).append(".class);");
                            }
                        }
                    }
                    methodsBuilder.append("\n\t\tviewModels[").append(i++).append("] = ((").append(fcName).append(")base).").append(variable.toString()).append(";");
                }
            }
            methodsBuilder.append("\n\t\treturn viewModels;\n\t}");
            classBuilder.append("\n\t\t}");
        }
        classBuilder.append("\n\n\t\treturn null;\n\t}");
        methodsBuilder.append("\n\n}\n");

        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile(packageName + ".PackageMvvmProvider");
            Writer writer = source.openWriter();
            writer.write(packageBuilder.toString());
            writer.write(classBuilder.toString());
            writer.write(methodsBuilder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasGotGetInstancemethod(TypeElement resourceElement) {
        for (Element resourceProperty : resourceElement.getEnclosedElements()) {
            if (METHOD == resourceProperty.getKind() && "getInstance".equals(resourceProperty.getSimpleName().toString())) {
                Set<Modifier> modifiers = resourceProperty.getModifiers();
                if (modifiers.contains(Modifier.PUBLIC) && modifiers.contains(Modifier.STATIC)) {
                    for (Element param : resourceProperty.getEnclosedElements()) {
                        System.out.println(param.getSimpleName() + " : " + param.getKind());
                    }
                    return true;
                }
            }
        }
        return false;
    }


}
