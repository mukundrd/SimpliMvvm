package com.trayis.simplimvvmannotation;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.trayis.simplimvvmannotation.SimpliViewComponent")
public class SimpliAnnotationProcessor extends AbstractProcessor {

    // private Messager messager;
    private boolean initialized;

    private static final String PACKAGE_NAME = "com.trayis.simplimvvmannotation.generated";

    private StringBuilder factoryPackageBuilder = new StringBuilder();
    private StringBuilder factoryClassBuilder = new StringBuilder();

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

        packageBuilder.append("\n\nimport com.trayis.simplimvvm.utils.SimpliMvvmProvider;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.viewmodel.SimpliViewModel;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.ui.SimpliBase;");
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException;");

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(new Date()).append("\n * Do not modify.\n */");
        classBuilder.append("\npublic class SimpliMvvmProviderImpl extends SimpliMvvmProvider {");
        classBuilder.append("\n\n\tprivate static final SimpliMvvmProvider instance = new SimpliMvvmProviderImpl();");
        classBuilder.append("\n\n\tprivate SimpliMvvmProvider[] mProviders;");
        classBuilder.append("\n\n\tpublic static SimpliMvvmProvider getInstance() {\n\t\treturn instance;\n\t}");

        factoryPackageBuilder.append("package ").append(PACKAGE_NAME).append(";");

        factoryPackageBuilder.append("\n\nimport com.trayis.simplimvvm.utils.SimpliViewModelProvidersFactory;");
        factoryPackageBuilder.append("\nimport com.trayis.simplimvvm.viewmodel.SimpliViewModel;");
        factoryPackageBuilder.append("\n\nimport androidx.annotation.NonNull;");
        factoryPackageBuilder.append("\nimport androidx.lifecycle.ViewModel;");


        factoryClassBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(new Date()).append("\n * Do not modify.\n */");
        factoryClassBuilder.append("\npublic class SimpliViewModelProvidersFactoryImpl extends SimpliViewModelProvidersFactory {");
        factoryClassBuilder.append("\n\n\tprivate static final SimpliViewModelProvidersFactory instance = new SimpliViewModelProvidersFactoryImpl();");
        factoryClassBuilder.append("\n\n\tpublic static SimpliViewModelProvidersFactory getInstance() {\n\t\treturn instance;\n\t}");
        factoryClassBuilder.append("\n\n\t@NonNull\n\t@Override\n\t@SuppressWarnings(\"unchecked\")\n\tpublic <T extends ViewModel> T create(Class<T> modelClass) {");
        factoryClassBuilder.append("\n\n\t\tswitch (modelClass.getName()) {");

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

        classBuilder.append("\n\n\tprivate SimpliMvvmProviderImpl() {");
        classBuilder.append("\n\t\tfactory = SimpliViewModelProvidersFactoryImpl.getInstance();\n");
        Set<Map.Entry<String, Map<String, List<VariableElement>>>> entries = providersMap.entrySet();
        classBuilder.append("\n\t\tmProviders = new SimpliMvvmProvider[").append(entries.size()).append("];");
        int i = 0;
        for (Map.Entry<String, Map<String, List<VariableElement>>> entry : entries) {
            createSimpliMvvmProvider(entry);
            classBuilder.append("\n\t\tmProviders[").append(i).append("] = ").append(entry.getKey()).append(".PackageMvvmProvider.getInstance(factory);");
            classBuilder.append("\n\t\tmProviders[").append(i++).append("].setFactory(factory);");
        }
        classBuilder.append("\n\t}");

        classBuilder.append("\n\n\t@Override\n\t@SuppressWarnings(\"unchecked\")\n\tpublic SimpliViewModel[] getViewModels(SimpliBase base) throws InvalidPropertiesFormatException {");

        classBuilder.append("\n\n\t\tSimpliViewModel[] viewModels = new SimpliViewModel[mProviders.length];");
        classBuilder.append("\n\n\t\tfor (int i=0; i<mProviders.length; i++) {");
        classBuilder.append("\n\t\t\tSimpliMvvmProvider provider = mProviders[i];");
        classBuilder.append("\n\t\t\tviewModels = provider.getViewModels(base);");
        classBuilder.append("\n\t\t\tif (viewModels != null) break;");
        classBuilder.append("\n\t\t}");

        classBuilder.append("\n\n\t\treturn viewModels;\n\n\t}\n\n}\n"); // close class

        factoryClassBuilder.append("\n\n\t\t}");
        factoryClassBuilder.append("\n\n\t\tthrow new IllegalStateException(String.format(\"Cannot find mapping for %s class\", modelClass.getName()));");
        factoryClassBuilder.append("\n\t}\n\n}\n"); // close class

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
            writer.flush();
            writer.close();

            initialized = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void createSimpliMvvmProvider(Map.Entry<String, Map<String, List<VariableElement>>> entry) {
        StringBuilder packageBuilder = new StringBuilder();
        StringBuilder classBuilder = new StringBuilder();

        String packageName = entry.getKey();
        packageBuilder.append("package ").append(packageName).append(";");

        packageBuilder.append("\n\nimport com.trayis.simplimvvm.utils.SimpliMvvmProvider;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.utils.SimpliViewModelProvidersFactory;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.viewmodel.SimpliViewModel;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.ui.SimpliBase;");
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException;");
        packageBuilder.append("\n\nimport java.util.ArrayList;");
        packageBuilder.append("\n\nimport androidx.fragment.app.FragmentActivity;");
        packageBuilder.append("\n\nimport androidx.lifecycle.ViewModelProviders;");

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(new Date()).append("\n * Do not modify.\n */");
        classBuilder.append("\npublic class PackageMvvmProvider extends SimpliMvvmProvider {");
        classBuilder.append("\n\n\tprivate static final PackageMvvmProvider instance = new PackageMvvmProvider();");
        classBuilder.append("\n\n\tpublic static PackageMvvmProvider getInstance(SimpliViewModelProvidersFactory factory) {\n\t\tinstance.factory = factory;\n\t\treturn instance;\n\t}");
        classBuilder.append("\n\n\tprivate PackageMvvmProvider() {}");
        classBuilder.append("\n\n\t@Override\n\t@SuppressWarnings(\"unchecked\")\n\tpublic SimpliViewModel[] getViewModels(SimpliBase base) throws InvalidPropertiesFormatException {");

        Map<String, List<VariableElement>> value = entry.getValue();

        classBuilder.append("\n\n\t\tSimpliViewModel[] viewModels = null;");

        for (Map.Entry<String, List<VariableElement>> listEntry : value.entrySet()) {

            String fcName = listEntry.getKey();
            classBuilder.append("\n\n\t\tif (base instanceof ").append(fcName).append(") {");

            List<VariableElement> list = listEntry.getValue();
            Iterator<VariableElement> iterator = list.iterator();
            while (iterator.hasNext()) {
                Element enclosed = iterator.next();
                if (!(enclosed.getKind() == ElementKind.FIELD && enclosed.getAnnotation(SimpliInject.class) != null))
                    iterator.remove();
            }

            classBuilder.append("\n\t\t\tviewModels = new SimpliViewModel[").append(list.size()).append("];");
            int i = 0;
            for (Element enclosed : list) {
                if (enclosed.getKind() == ElementKind.FIELD && enclosed.getAnnotation(SimpliInject.class) != null) {
                    VariableElement variable = (VariableElement) enclosed;
                    TypeMirror typeMirror = variable.asType();
                    TypeElement element = (TypeElement) processingEnv.getTypeUtils().asElement(typeMirror);
                    for (Element cons : element.getEnclosedElements()) {
                        if (cons.getKind() == ElementKind.CONSTRUCTOR) {
                            List<? extends TypeMirror> memberParamTypes = ((ExecutableType) cons.asType()).getParameterTypes();
                            factoryClassBuilder.append("\n\t\t\tcase \"").append(typeMirror).append("\":");
                            if (memberParamTypes == null || memberParamTypes.isEmpty()) {
                                factoryClassBuilder.append("\n\t\t\t\treturn (T)new ").append(typeMirror).append("();");
                                classBuilder.append("\n\t\t\t((").append(fcName).append(")base).").append(variable.toString()).append(" = getViewModelProviderFor(base).get(").append(typeMirror).append(".class);");
                            } else {
                                factoryClassBuilder.append("\n\t\t\t\treturn (T)new ").append(typeMirror).append("(");
                                for (TypeMirror e : memberParamTypes) {
                                    TypeElement paramElement = (TypeElement) processingEnv.getTypeUtils().asElement(e);
                                    factoryClassBuilder.append("\n\t\t\t\t\tgetResource(").append(paramElement.asType()).append(".class)");
                                }
                                factoryClassBuilder.append(");");
                            }
                        }
                    }
                    classBuilder.append("\n\t\t\tviewModels[").append(i++).append("] = ((").append(fcName).append(")base).").append(variable.toString()).append(";");
                }
            }
            classBuilder.append("\n\t\t\treturn viewModels;");
            classBuilder.append("\n\t\t}");
        }
        classBuilder.append("\n\n\t\treturn viewModels;\n\t}\n\n}\n");

        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile(packageName + ".PackageMvvmProvider");
            Writer writer = source.openWriter();
            writer.write(packageBuilder.toString());
            writer.write(classBuilder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
