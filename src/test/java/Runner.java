import org.apache.commons.lang3.StringUtils;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Runner {

    public static void main(String[] args) throws IOException {
        int shortStringLength = 15;
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String string = bufferedReader.readLine();
        List<String> strings = new ArrayList<>(List.of(StringUtils.substringsBetween(string, "“", "”")));

        for (String s : strings) {
            String newS = s.replaceAll("\\s", "");

            String methodName = newS.length() <= shortStringLength ? StringUtils.substringAfter(newS, "|")
                    : StringUtils.substringBetween(newS, "|", "|");
            String firstParam = newS.length() <= shortStringLength ? ""
                    : StringUtils.substringAfterLast(newS, "|");
            firstParam = newS.length() <= shortStringLength ? ""
                    : StringUtils.substringBefore(firstParam, ";");
            String secondParam = newS.length() <= shortStringLength ? ""
                    : StringUtils.substringAfter(newS, ";");

            System.setProperty("firstParam", firstParam);
            System.setProperty("secondParam", secondParam);

            TestListenerAdapter tla = new TestListenerAdapter();
            TestNG testng = new TestNG();
            testng.setTestSuites(Collections.singletonList("src/test/java/configurations/testng.xml"));
            testng.setTestNames(Collections.singletonList(methodName));
            testng.addListener(tla);
            testng.run();
        }
    }
}
