package oops;

import java.util.*;
import java.util.function.Consumer;

import static org.apache.commons.lang3.StringUtils.*;

public class CodeBuildParser implements Parser  {

    @Override
    public Map<String ,List<CodeBuildSteps>> parseForBuild() {
        String className = "Suite55";
        List<String> strings = new ArrayList<>(List.of(substringsBetween(System.getProperty("string"), "“", "”")));
        List<CodeBuildSteps> steps = new ArrayList<>();
        Consumer<String> consumer = s -> {
            CodeBuildSteps st = new CodeBuildSteps();
            st.setInnerClassName(getClassObject(s));
            st.setInnerMethodName(getMethod(s));
            st.setParams(List.of(getFirstParam(getParams(s)), getSecondParam(getParams(s))));
            steps.add(st);
        };
        strings.forEach(consumer);
        Map<String , List<CodeBuildSteps>> result = new HashMap<>();
        result.put(className,steps);
        return result;
    }

    @Override
    public ArrayList<Steps> parse() {
        return null;
    }


    // пока закоменчено, если вдруг понадобятся отдельные геттеры
//    private String getInnerClassName(String s) {
//        return substringBefore(s, "-").trim();
//    }
//
//    private String getInnerMethodName(String s) {
//        return substringBetween(s, "-","-");
//    }
//
//    private List<String> getParams(String s) {
//        return Arrays.asList(substringAfterLast(s, "-").split(",", -1));
//    }
}
