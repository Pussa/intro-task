package oops;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.*;
import java.util.function.Consumer;

public class SuiteBuilder {

    public XmlSuite createSuite(int testNumber, String className, String methodName, List<String> params) {
        XmlSuite suite = new XmlSuite();
        suite.setName("intro");

        XmlTest test = new XmlTest(suite);
        test.setName("intro-test" + testNumber);

        List<XmlClass> classes = new ArrayList<>();
        classes.add(new XmlClass("tests." + className));

        XmlInclude include = new XmlInclude(methodName);

        include.setXmlClass(classes.get(0));
        Map<String, String> paramsMap = new IdentityHashMap<>();

        Consumer<String> cons = s ->
                paramsMap.put("param" + params.indexOf(s), s);

        params.forEach(cons);

        include.setParameters(paramsMap);
        classes.get(0).setIncludedMethods(Collections.singletonList(include));
        test.setXmlClasses(classes);
        return suite;
    }
}
