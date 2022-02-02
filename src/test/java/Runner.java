import oops.StringParser;
import oops.SuiteBuilder;
import oops.XLSXParser;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class Runner {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String string = bufferedReader.readLine();

        ArrayList<List<String>> allSteps;
        if (string.equals("file")) {
            allSteps = new XLSXParser().parse();
        } else {
            System.setProperty("string", string);
            allSteps = new StringParser().parse();
        }
        runTest(allSteps);
    }

    private static void runTest(ArrayList<List<String>> allSteps) {
        Consumer<List<String>> consumer = s -> {
            System.setProperty("firstParam", s.get(2));
            System.setProperty("secondParam", s.get(3));
            List<XmlSuite> suites = new ArrayList<>();
            suites.add(new SuiteBuilder().createSuite(allSteps.indexOf(s), s.get(0), s.get(1)));

            TestListenerAdapter tla = new TestListenerAdapter();
            TestNG testng = new TestNG();
            testng.setXmlSuites(suites);
            testng.setTestNames(Collections.singletonList(s.get(1)));
            testng.addListener(tla);
            testng.run();
        };
        allSteps.forEach(consumer);
    }
}