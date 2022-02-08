package oops;

import com.squareup.javapoet.*;
import org.testng.annotations.Test;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CodeBuilder {

    public void build(Map<String, List<CodeBuildSteps>> map) throws IOException {

        String className = String.valueOf(map.keySet().toArray()[0]);
        List<CodeBuildSteps> steps = map.get(className);
        List<MethodSpec> methods = new ArrayList<>();
        List<CodeBlock> codeBlock = new ArrayList<>();
        final MethodSpec.Builder[] methodSpec = new MethodSpec.Builder[1];
        Consumer<CodeBuildSteps> consumer = s -> {

            if (steps.indexOf(s) == 0)
                methodSpec[0] = MethodSpec
                        .methodBuilder(className + "Test" + methods.size());

            if (s.getAnnotation() != null) {
                methodSpec[0]
                        .addAnnotation(Test.class)
                        .addModifiers(Modifier.PUBLIC);
                for (CodeBlock block : codeBlock) {
                    methodSpec[0].addCode(block);
                }

                methods.add(methodSpec[0].build());
                codeBlock.clear();

                methodSpec[0] = MethodSpec
                        .methodBuilder(className + "Test" + methods.size())
                        .addAnnotation(Override.class); //здесь вместо оверайда можно поставить нужную нам аннотацию

            }

            codeBlock.add(CodeBlock
                    .builder()
                    .addStatement(s.getInnerClassName() + "." + s.getInnerMethodName()
                            + "(" + s.getParams().toString().replace("[", "").replace("]", "") + ")")
                    .build());

            if (steps.indexOf(s) == steps.size() - 1 && s.getAnnotation() != null) {
                methodSpec[0]
                        .addAnnotation(Test.class)
                        .addModifiers(Modifier.PUBLIC);
                for (CodeBlock block : codeBlock) {
                    methodSpec[0].addCode(block);
                }
                methods.add(methodSpec[0].build());
            }
        };
        steps.forEach(consumer);

        TypeSpec person = TypeSpec
                .classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethods(methods)
                .build();

        JavaFile javaFile = JavaFile
                .builder("oops", person)
                .indent("    ")
                .build();
        javaFile.writeTo(System.out);

    }

}
