package com.trayis.simplimvvmannotation;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.trayis.simplimvvmannotation.SimpliViewComponent")
public class SimpliAnnotationProcessor extends AbstractProcessor {

    private String packageName = "com.trayis.simplimvvmannotation.generated";

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "Initializing SimpliAnnotationProcessor ...");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        StringBuilder packageBuilder = new StringBuilder();
        StringBuilder classBuilder = new StringBuilder();

        packageBuilder.append("package " + packageName + ";");

        packageBuilder.append("\n\nimport android.arch.lifecycle.ViewModelProvider;");
        packageBuilder.append("\nimport android.arch.lifecycle.ViewModelProviders;");
        packageBuilder.append("\nimport android.support.v4.app.Fragment;");
        packageBuilder.append("\nimport android.support.v4.app.FragmentActivity;");

        packageBuilder.append("\n\nimport com.trayis.simplimvvm.ui.Simpli;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.utils.SimpliMvvmProvider;");
        packageBuilder.append("\nimport com.trayis.simplimvvm.viewmodel.SimpliViewModel;");
        packageBuilder.append("\n\nimport java.util.InvalidPropertiesFormatException;\n");

        classBuilder.append("\n\n/**\n * This is a generated class\n * Generated on " + new Date() + "\n * Do not modify.\n */");
        classBuilder.append("\npublic class SimpliMvvmProviderImpl<V extends SimpliViewModel> implements SimpliMvvmProvider<V> {");

        classBuilder.append("\n\n\tprivate static final SimpliMvvmProvider instance = new SimpliMvvmProviderImpl();");

        classBuilder.append("\n\n\tpublic static SimpliMvvmProvider getInstance() {\n\t\treturn instance;\n\t}");

        classBuilder.append("\n\n\tprivate SimpliMvvmProviderImpl() {}");

        classBuilder.append("\n\n\t@Override\n\t@SuppressWarnings(\"unchecked\")\n\tpublic V getViewModel(Simpli simpli) throws InvalidPropertiesFormatException {");

        for (Element element : roundEnv.getElementsAnnotatedWith(SimpliViewComponent.class)) {

            if (!(element instanceof TypeElement)) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Oops... This annotation is for Classes only");
                return true;
            }

            TypeElement typeElement = (TypeElement) element;
            DeclaredType declaredType = (DeclaredType) typeElement.getSuperclass();
            List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();

            if (typeArguments.size() < 2) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Oops... missing arguments for " + element.getSimpleName());
                return true;
            }

            String viewModalName = String.valueOf(typeArguments.get(1));

            packageBuilder.append("\nimport " + viewModalName + ";");

            classBuilder.append("\n\n\t\tViewModelProvider viewModelProvider;");
            classBuilder.append("\n\t\tif (simpli instanceof Fragment) {");
            classBuilder.append("\n\t\t\tviewModelProvider = ViewModelProviders.of((Fragment) simpli);");
            classBuilder.append("\n\t\t} else {");
            classBuilder.append("\n\t\t\tviewModelProvider = ViewModelProviders.of((FragmentActivity) simpli);");
            classBuilder.append("\n\t\t}");

            classBuilder.append("\n\n\t\tif (simpli instanceof " + ((TypeElement) element).getQualifiedName() + ")");
            classBuilder.append("\n\t\t\treturn (V)viewModelProvider.get(" + viewModalName.substring(viewModalName.lastIndexOf(".") + 1) + ".class);");

        }

        classBuilder.append("\n\n\t\tthrow new java.util.InvalidPropertiesFormatException(\"view of type\" + simpli.getClass() + \" is not supported.\");\n\n\t}\n\n}\n"); // close class

        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile(packageName + ".SimpliMvvmProviderImpl");
            Writer writer = source.openWriter();
            writer.write(packageBuilder.toString());
            writer.write(classBuilder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }

        return true;
    }

}
