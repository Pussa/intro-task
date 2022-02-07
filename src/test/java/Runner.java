import oops.*;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Runner {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String string = bufferedReader.readLine();

        ArrayList<Steps> allSteps;
        Map<String ,List<CodeBuildSteps>> codeBuild = null;
        if (string.equals("file")) {
            allSteps = new XLSXParser().parse();
        } else {
            System.setProperty("string", string);
            codeBuild = new CodeBuildParser().parseForBuild();
            allSteps = new StringParser().parse();
        }
        new CodeBuilder().build(codeBuild);
        runTest(allSteps);
    }

    private static void runTest(ArrayList<Steps> allSteps) {
        Consumer<Steps> consumer = s -> {

            List<XmlSuite> suites = new ArrayList<>();
            suites.add(new SuiteBuilder().createSuite(allSteps.indexOf(s), s.getClassName(), s.getMethodName(), s.getParams()));

            TestListenerAdapter tla = new TestListenerAdapter();
            TestNG testng = new TestNG();
            testng.setXmlSuites(suites);
            testng.setTestNames(Collections.singletonList(s.getMethodName()));
            testng.addListener(tla);
            testng.run();
        };
        allSteps.forEach(consumer);
    }
}