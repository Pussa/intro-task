package oops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.*;

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
            if (s.startsWith("Reports.logCase")) {
                steps.setClassName(s);
            } else {
                steps.setClassName(getClassName(s));
                steps.setMethodName(getMethod(s));
                steps.setParams(getParams(s));
            }
            allSteps.add(steps);
        };
        strings
                .stream()
                .map(s -> s.replaceAll("\\s", ""))
                .forEach(cons);
        return allSteps;
    }

    public List<String> getParams(String s) {
        return countMatches(s, "|") == 2 ?
                Arrays.asList(substringAfterLast(s, "|").split(";", -1))
                : singletonList("");
    }
}
