package permission.kalu.processor;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import permission.kalu.annotation.PermissionAgain;
import permission.kalu.annotation.PermissionDenied;
import permission.kalu.annotation.PermissionFail;
import permission.kalu.annotation.PermissionSucc;

/**
 * description: 注解拦截处理, compile 'com.google.auto.service:auto-service:1.0-rc4'
 * created by kalu on 2017/12/16 15:41
 */
@AutoService(Processor.class)
public final class AnnotationProcessor extends AbstractProcessor {

    private final HashMap<String, AnnotationGenerate> map = new HashMap<>();
    private Elements mElements;
    private Filer mFiler;

    /**********************************************************************************************/

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.mElements = processingEnv.getElementUtils();
        this.mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        map.clear();

        if (!parseMethod(roundEnv, PermissionDenied.class)) return false;
        if (!parseMethod(roundEnv, PermissionSucc.class)) return false;
        if (!parseMethod(roundEnv, PermissionFail.class)) return false;
        if (!parseMethod(roundEnv, PermissionAgain.class)) return false;

        Writer writer = null;
        for (AnnotationGenerate info : map.values()) {
            try {
                JavaFileObject file = mFiler.createSourceFile(info.getProxyName(), info.getElement());
                writer = file.openWriter();
                writer.write(info.generateClassName());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>(16);
        set.add(PermissionSucc.class.getCanonicalName());
        set.add(PermissionFail.class.getCanonicalName());
        set.add(PermissionAgain.class.getCanonicalName());
        set.add(PermissionDenied.class.getName());

        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private boolean parseMethod(RoundEnvironment roundEnv, Class<? extends Annotation> clazz) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(clazz);
        for (Element element : elements) {
            if (isValid(element)) {
                return false;
            }
            ExecutableElement method = (ExecutableElement) element;
            TypeElement typeElement = (TypeElement) method.getEnclosingElement();
            String typeName = typeElement.getQualifiedName().toString();
            AnnotationGenerate info = map.get(typeName);
            if (info == null) {
                info = new AnnotationGenerate(mElements, typeElement);
                map.put(typeName, info);
            }

            final int params = method.getParameters().size();
            Annotation annotation = method.getAnnotation(clazz);
            String methodName = method.getSimpleName().toString();
            if (annotation instanceof PermissionSucc) {
                PermissionSucc succ = (PermissionSucc) annotation;
                final AnnotationModel model = new AnnotationModel();
                final int code = succ.value();
                model.setCode(code);
                model.setMethod(methodName);
                info.succ[0] = model;
            } else if (annotation instanceof PermissionFail) {
                PermissionFail fail = (PermissionFail) annotation;
                final AnnotationModel model = new AnnotationModel();
                final int code = fail.value();
                model.setCode(code);
                model.setMethod(methodName);
                info.fail[0] = model;
            } else if (annotation instanceof PermissionAgain) {
                PermissionAgain again = (PermissionAgain) annotation;
                final AnnotationModel model = new AnnotationModel();
                final int code = again.value();
                model.setCode(code);
                model.setMethod(methodName);
                info.again[0] = model;
            } else if (annotation instanceof PermissionDenied) {
                PermissionDenied denied = (PermissionDenied) annotation;
                final AnnotationModel model = new AnnotationModel();
                final int code = denied.value();
                model.setCode(code);
                model.setMethod(methodName);
                info.denied[0] = model;
            } else {
                error(method, "%s not support.", method);
                return false;
            }
        }

        return true;
    }

    private boolean isValid(Element element) {
        if (element.getModifiers().contains(Modifier.ABSTRACT) || element.getModifiers().contains
                (Modifier.PRIVATE)) {
            error(element, "%s must could not be abstract or private");
            return true;
        }
        return false;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
