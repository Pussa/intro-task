package oops;

import com.squareup.javapoet.*;
import org.apache.commons.collections4.IterableUtils;
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
        Consumer<CodeBuildSteps> consumer = s -> {
            CodeBlock codeBlock = CodeBlock
                    .builder()
                    .addStatement(s.getInnerClassName() + "." + s.getInnerMethodName()
                            + "(" + s.getParams().toString().replace("[", "").replace("]", "") + ")")
                    .build();
            MethodSpec methodSpec = MethodSpec
                    .methodBuilder(className + "Test" + steps.indexOf(s))
                    .addAnnotation(Test.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addCode(codeBlock)
                    .build();
            methods.add(methodSpec);
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
