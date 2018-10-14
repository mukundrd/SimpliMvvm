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
import javax.tools.JavaFileObject;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.trayis.simplimvvmannotation.SimpliViewComponent")
public class SimpliAnnotationProcessor extends AbstractProcessor {

    // private Messager messager;
    private boolean initialized;

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

        String packageName = "com.trayis.simplimvvmannotation.generated";
        packageBuilder.append("package ").append(packageName).append(";");

        packageBuilder.append("\n\nimport com.trayis.simplimvvm.utils.SimpliMvvmProvider;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.viewmodel.SimpliViewModel;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.ui.SimpliBase;");
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException;");

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(new Date()).append("\n * Do not modify.\n */");
        classBuilder.append("\npublic class SimpliMvvmProviderImpl implements SimpliMvvmProvider {");
        classBuilder.append("\n\n\tprivate static final SimpliMvvmProviderImpl instance = new SimpliMvvmProviderImpl();");
        classBuilder.append("\n\n\tprivate SimpliMvvmProvider[] mProviders;");
        classBuilder.append("\n\n\tpublic static SimpliMvvmProviderImpl getInstance() {\n\t\treturn instance;\n\t}");

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
        Set<Map.Entry<String, Map<String, List<VariableElement>>>> entries = providersMap.entrySet();
        classBuilder.append("\n\t\tmProviders = new SimpliMvvmProvider[").append(entries.size()).append("];");
        int i = 0;
        for (Map.Entry<String, Map<String, List<VariableElement>>> entry : entries) {
            createSimpliMvvmProvider(entry);
            classBuilder.append("\n\t\tmProviders[").append(i++).append("] = ").append(entry.getKey()).append(".PackageMvvmProvider.getInstance();");
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

        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile(packageName + ".SimpliMvvmProviderImpl");
            Writer writer = source.openWriter();
            writer.write(packageBuilder.toString());
            writer.write(classBuilder.toString());
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
        packageBuilder.append("\nimport com.trayis.simplimvvm.viewmodel.SimpliViewModel;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.ui.SimpliBase;");
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException;");
        packageBuilder.append("\n\nimport java.util.ArrayList;");

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on ").append(new Date()).append("\n * Do not modify.\n */");
        classBuilder.append("\npublic class PackageMvvmProvider implements SimpliMvvmProvider {");
        classBuilder.append("\n\n\tprivate static final PackageMvvmProvider instance = new PackageMvvmProvider();");
        classBuilder.append("\n\n\tpublic static PackageMvvmProvider getInstance() {\n\t\treturn instance;\n\t}");
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
                    classBuilder.append("\n\t\t\t((").append(fcName).append(")base).").append(variable.toString()).append(" = new ").append(variable.asType()).append("();");
                    classBuilder.append("\n\t\t\tviewModels[").append(i++).append("] = ((").append(fcName).append(")base).").append(variable.toString()).append(";");
                }
            }
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
