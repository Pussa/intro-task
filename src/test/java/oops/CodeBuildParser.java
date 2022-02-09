package oops;

import java.util.*;
import java.util.function.Consumer;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.*;

public class CodeBuildParser implements Parser {

    private final String string;
    public CodeBuildParser(String string) {
        this.string = string;
    }

    @Override
    public Map<String, List<CodeBuildSteps>> parseForBuild() {
        String className = "Suite55";
        List<String> strings = new ArrayList<>(List.of(substringsBetween(string, "“", "”")));
        List<CodeBuildSteps> steps = new ArrayList<>();
        Consumer<String> consumer = s -> {
            CodeBuildSteps st = new CodeBuildSteps();
            if (s.startsWith("Reports.logCase")) {
                st.setAnnotation(getAnnotation(s));
                st.setInnerClassName(getInnerClassName(s));
            } else {
                st.setInnerClassName(getClassObject(s));
            }
            st.setInnerMethodName(getInnerMethodName(s));
            st.setParams(getParams(s));
            steps.add(st);
        };
        strings.forEach(consumer);
        Map<String, List<CodeBuildSteps>> result = new HashMap<>();
        result.put(className, steps);
        return result;
    }

    @Override
    public ArrayList<Steps> parse() {
        return null;
    }

    private String getAnnotation(String s) {
        return substringBefore(s, ":");
    }

    private String getInnerClassName(String s) {
        return substringBetween(s, ":", "|").trim();
    }

    private String getInnerMethodName(String s) {
        return countMatches(s, "|") == 2 ? substringBetween(s, "|") : substringAfter(s, "|");
    }

    private List<String> getParams(String s) {
        return countMatches(s, "|") == 2 ?
                Arrays.asList(substringAfterLast(s, "|").split(";", -1))
                : singletonList("");
    }
}
