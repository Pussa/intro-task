package oops;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.apache.commons.lang3.StringUtils.substringsBetween;

public class StringParser implements Parser {

    @Override
    public ArrayList<List<String>> parse() {
        List<String> strings = new ArrayList<>(List.of(substringsBetween(System.getProperty("string"), "“", "”")));
        ArrayList<List<String>> allSteps = new ArrayList<>();
        Consumer<String> cons = s -> allSteps.add(List.of(
                steps.getClassObject(s),
                steps.getMethod(s),
                steps.getFirstParam(steps.getParams(s)),
                steps.getSecondParam(steps.getParams(s))));
        strings
                .stream()
                .map(s -> s.replaceAll("\\s", ""))
                .forEach(cons);
        return allSteps;
    }
}
