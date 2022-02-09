package oops;

import com.squareup.javapoet.*;
import org.testng.annotations.Test;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CodeBuilder {

    public void build(Map<String, List<CodeBuildSteps>> map) throws IOException {
        String className = String.valueOf(map.keySet().toArray()[0]);
        List<CodeBuildSteps> steps = map.get(className);
        List<CodeBlock> codeBlock = new ArrayList<>();
        final MethodSpec.Builder[] methodSpec = new MethodSpec.Builder[1];
        List<MethodSpec> methods = new ArrayList<>();

        Consumer<CodeBuildSteps> consumer = s -> {
            if (steps.indexOf(s) == 0 && s.getAnnotation() == null)
                methodSpec[0] = initializeMethod(className, methods);
            else if (steps.indexOf(s) == 0 && s.getAnnotation() != null)
                methodSpec[0] = initializeMethodWithAnnotation(className, methods);

            if (s.getAnnotation() != null && steps.indexOf(s) != 0) {
                methods.add(createMethod(methodSpec[0], codeBlock));
                codeBlock.clear();
                codeBlock.add(createCodeBlock(s));
                methodSpec[0] = initializeMethodWithAnnotation(className, methods);
            } else {
                codeBlock.add(createCodeBlock(s));
            }
            if (steps.indexOf(s) == steps.size() - 1) {
                methods.add(createMethod(methodSpec[0], codeBlock));
            }
        };
        steps.forEach(consumer);
        JavaFile javaFile = createFile(className, methods);
        File file = new File("src/test/java");
        javaFile.writeTo(file);
    }

    private CodeBlock createCodeBlock(CodeBuildSteps s) {
        return CodeBlock
                .builder()
                .addStatement(s.getInnerClassName() + "." + s.getInnerMethodName()
                        + "(" + s.getParams().toString().replace("[", "").replace("]", "") + ")")
                .build();
    }

    private MethodSpec.Builder initializeMethod(String className, List<MethodSpec> methods) {
        return MethodSpec
                .methodBuilder(className + "Test" + methods.size());
    }

    private MethodSpec.Builder initializeMethodWithAnnotation(String className, List<MethodSpec> methods) {
        return MethodSpec
                .methodBuilder(className + "Test" + methods.size())
                .addAnnotation(Override.class); //здесь вместо оверайда можно поставить нужную нам аннотацию
    }

    private MethodSpec createMethod(MethodSpec.Builder methodSpec, List<CodeBlock> codeBlock) {
        methodSpec
                .addAnnotation(Test.class)
                .addModifiers(Modifier.PUBLIC);
        for (CodeBlock block : codeBlock) {
            methodSpec.addCode(block);
        }
        return methodSpec.build();
    }

    private TypeSpec createClass(String className, List<MethodSpec> methods) {
        return TypeSpec
                .classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethods(methods)
                .build();
    }

    private JavaFile createFile(String className, List<MethodSpec> methods) {
        return JavaFile
                .builder("oops", createClass(className, methods))
                .indent("    ")
                .build();
    }
}
