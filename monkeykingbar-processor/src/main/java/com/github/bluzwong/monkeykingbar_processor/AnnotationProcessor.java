package com.github.bluzwong.monkeykingbar_processor;



import com.github.bluzwong.monkeykingbar_lib.InjectExtra;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/24.
 */
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor{


    private Filer filer;
    private Elements elementUtils;
    private Types typeUtils;

    private boolean needLog = false;
    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);

        filer = env.getFiler();
        elementUtils = env.getElementUtils();
        typeUtils = env.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add("com.github.bluzwong.monkeykingbar_lib.InjectExtra");
        types.add("com.github.bluzwong.monkeykingbar_lib.KeepState");
        return types;
    }
    @Override public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    /**
     * {@inheritDoc}
     *
     * @param annotations
     * @param roundEnv
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log("start process ");
        Map<TypeElement, ClassInjector> targetClassMap = findAndParseTargets(annotations, roundEnv);
        for (Map.Entry<TypeElement, ClassInjector> entry : targetClassMap.entrySet()) {
            TypeElement typeElement = entry.getKey();
            ClassInjector injector = entry.getValue();
            try {
                String value = injector.brewJava();
                log(value);
                JavaFileObject jfo = filer.createSourceFile(injector.getFqcn(), typeElement);
                Writer writer = jfo.openWriter();
                writer.write(value);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), typeElement);
            }
        }

        return true;
    }

    private Map<TypeElement, ClassInjector> findAndParseTargets(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv){
        Map<TypeElement, ClassInjector> targetClassMap = new LinkedHashMap<TypeElement, ClassInjector>();
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(InjectExtra.class);
        /*log("InjectExtra.class@@@@@@@@@@@@@@@@");
        for (Element element : elements) {
            log("element => " + element);
        }*/
        for (TypeElement te : annotations) {
            // te = zhujie
            String annoName = te.getQualifiedName().toString();
            log(" anno name => " + annoName); //  anno name => InjectExtra
            if (!getSupportedAnnotationTypes().contains(annoName)) {
                log("not supported annotation => " + annoName);
                continue;
            }
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {
                //log("work on -> " + e.toString());
                Name fieldName = e.getSimpleName();
                TypeElement className = (TypeElement) e.getEnclosingElement();
                String fieldType = e.asType().toString();
                log("fieldName -> " + fieldName); //   fieldName -> ccf
                log("fieldInClass -> " + className); //   fieldInClass -> com.github.bluzwong.mycache.MainActivity
                log("fieldType -> " + fieldType); //   fieldType -> int
                final boolean[] isKeepStateAsProperty = {false};
                final boolean[] isInjectfieldAsProperty = {false};

                for (AnnotationMirror mirror : e.getAnnotationMirrors()) {
                    DeclaredType annotationType = mirror.getAnnotationType();
                    if (annotationType.toString().equals("com.github.bluzwong.monkeykingbar_lib.KeepState")) {
                        Map<? extends ExecutableElement, ? extends AnnotationValue> values = mirror.getElementValues();
                        values.forEach(new BiConsumer<ExecutableElement, AnnotationValue>() {
                            @Override
                            public void accept(ExecutableElement executableElement, AnnotationValue annotationValue) {
                                if (executableElement.toString().equals("asProperty()")) {
                                    String valueString = annotationValue.getValue().toString();
                                    log("asProperty() => " + valueString);
                                    isKeepStateAsProperty[0] = "true".equals(valueString);
                                }
                            }
                        });
                    }
                    if (annotationType.toString().equals("com.github.bluzwong.monkeykingbar_lib.InjectExtra")) {
                        Map<? extends ExecutableElement, ? extends AnnotationValue> values = mirror.getElementValues();
                        values.forEach(new BiConsumer<ExecutableElement, AnnotationValue>() {
                            @Override
                            public void accept(ExecutableElement executableElement, AnnotationValue annotationValue) {
                                if (executableElement.toString().equals("asProperty()")) {
                                    String valueString = annotationValue.getValue().toString();
                                    log("asProperty() => " + valueString);
                                    isInjectfieldAsProperty[0] = "true".equals(valueString);
                                }
                            }
                        });
                    }
                }




                ClassInjector injector = getOrCreateTargetClass(targetClassMap, className);
                List<String> fields = new ArrayList<String>();
                for (Element element : className.getEnclosedElements()) {
                    if (element instanceof VariableElement) {
                        fields.add(element.getSimpleName().toString());
                    }
                }
                // todo remove it
                for (String field : fields) {
                    log(className + " -@-@-> " + field);
                }
                injector.setFields(fields);
                List<? extends AnnotationMirror> annotationMirrors = e.getAnnotationMirrors();
                boolean isUnserializable = false;
                for (AnnotationMirror annotationMirror : annotationMirrors) {
                    String annotationType = annotationMirror.getAnnotationType().toString();
                    log("annotationType => " + annotationType); // annotationType => com.github.bluzwong.monkeykingbar_lib.KeepState
                    if (annotationType.equals("com.github.bluzwong.monkeykingbar_lib.UnSerializable")) {
                        isUnserializable = true;
                        log("isUnserializable => true");
                        break;
                    }
                }

                if (annoName.contains("InjectExtra")) {
                    log("InjectExtra => " + isUnserializable);
                    InjectFieldInjector injectFieldInjector = new InjectFieldInjector(fieldName.toString(), fieldType, isInjectfieldAsProperty[0]);
                    injector.addInjectField(injectFieldInjector);
                }
                if (annoName.contains("KeepState")) {
                    KeepFieldInjector keepFieldInjector = new KeepFieldInjector(fieldName.toString(), fieldType, isKeepStateAsProperty[0]);
                    injector.addKeepField(keepFieldInjector);
                }

            }

        }
        return targetClassMap;
    }
    /**
     *
     *
     * @param targetClassMap
     * @param enclosingElement
     * @return
     */

    private ClassInjector getOrCreateTargetClass(Map<TypeElement, ClassInjector> targetClassMap, TypeElement enclosingElement) {
        ClassInjector injector = targetClassMap.get(enclosingElement);
        if (injector == null) {
            String classPackage = getPackageName(enclosingElement);
            String className = getClassName(enclosingElement, classPackage);

            injector = new ClassInjector(classPackage, className);
            targetClassMap.put(enclosingElement, injector);
        }
        return injector;
    }

    /**
     *
     * @param type
     * @param packageName
     * @return
     */
    private String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    /**

     *
     * @param type
     * @return
     */
    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }
    private void log(String msg) {
        if (!needLog) {
            return;
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }
}
