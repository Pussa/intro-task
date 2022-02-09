package oops;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.apache.commons.lang3.StringUtils.substringsBetween;

public class StringParser implements Parser {

    private final String string;
    public StringParser(String string) {
        this.string = string;
    }

    @Override
    public ArrayList<Steps> parse() {
        List<String> strings = new ArrayList<>(List.of(substringsBetween(string, "“", "”")));
        ArrayList<Steps> allSteps = new ArrayList<>();
        Consumer<String> cons = s -> {
            Steps steps = new Steps();
            steps.setClassName(getClassObject(s));
            steps.setMethodName(getMethod(s));
            String params = getParams(s);
            steps.setParams(List.of(getFirstParam(params), getSecondParam(params)));
            allSteps.add(steps);
        };
        strings
                .stream()
                .map(s -> s.replaceAll("\\s", ""))
                .forEach(cons);
        return allSteps;
    }

    @Override
    public Map<String, List<CodeBuildSteps>> parseForBuild() {
        return null;
    }
}
