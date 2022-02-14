package oops;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.testng.annotations.Test;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

//[“Reports.logCase("asd)”, “Test3|action3_b|data3b_1; data3b_2” , “Reports.logCase("12")”, “Test1|action1_a”, ….]
public class CodeBuilder {

    public void build(List<Steps> steps) throws IOException {
        String className = "Suite55";
        Map<String, List<Steps>> allPairs = separateSteps(steps);
        List<MethodSpec> methods = buildMethods(allPairs, className);
        JavaFile javaFile = createFile(className, methods);
        File file = new File("src/test/java");
        javaFile.writeTo(file);
    }

    private Map<String, List<Steps>> separateSteps(List<Steps> steps) {
        Map<String, List<Steps>> allPairs = new HashMap<>();
        final List<Steps>[] ss = new List[]{new ArrayList<>()};
        final String[] logCase = new String[1];
        logCase[0] = null;
        Consumer<Steps> cons = s -> {

            if (s.getClassName().contains("Reports.logCase") && steps.indexOf(s) == 0) {
                logCase[0] = s.getClassName();
            } else if (s.getClassName().contains("Reports.logCase") && steps.indexOf(s) != 0) {
                allPairs.put(logCase[0], ss[0]);
                logCase[0] = s.getClassName();
                ss[0] = new ArrayList<>();
            } else {
                ss[0].add(s);
            }

            if (steps.indexOf(s) == steps.size() - 1) {
                allPairs.put(logCase[0], ss[0]);
            }
        };

        steps.forEach(cons);
        return allPairs;
    }

    private List<MethodSpec> buildMethods(Map<String, List<Steps>> allPairs, String className) {
        List<MethodSpec> methods = new ArrayList<>();
        allPairs.forEach((annotation, steps) -> {

            int index = new ArrayList<>(allPairs.keySet()).indexOf(annotation);
            MethodSpec.Builder methodSpec;
            List<CodeBlock> codeBlocks = new ArrayList<>();

            if (annotation == null) {
                methodSpec = initializeMethod(className, index);
            } else {
                methodSpec = initializeMethodWithAnnotation(className, index);
            }

            steps.forEach(c -> codeBlocks.add(createCodeBlock(c)));
            methods.add(createMethod(methodSpec, codeBlocks));
        });
        return methods;
    }

    private CodeBlock createCodeBlock(Steps s) {
        return CodeBlock
                .builder()
                .addStatement(s.getClassName() + "." + s.getMethodName()
                        + "(" + s.getParams().toString().replace("[", "").replace("]", "") + ")")
                .build();
    }

    private MethodSpec.Builder initializeMethod(String className, int number) {
        return MethodSpec
                .methodBuilder(className + "Test" + number);
    }

    private MethodSpec.Builder initializeMethodWithAnnotation(String className, int number) {
        return MethodSpec
                .methodBuilder(className + "Test" + number)
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
