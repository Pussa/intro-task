package oops;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuiteBuilder {

    public XmlSuite createSuite(int testNumber, String className, String methodName) {
        XmlSuite suite = new XmlSuite();
        suite.setName("intro");

        XmlTest test = new XmlTest(suite);
        test.setName("intro-test" + testNumber);

        List<XmlClass> classes = new ArrayList<>();
        classes.add(new XmlClass("tests." + className));

        XmlInclude include = new XmlInclude(methodName);

        include.setXmlClass(classes.get(0));
        classes.get(0).setIncludedMethods(Collections.singletonList(include));
        test.setXmlClasses(classes);
        return suite;
    }
}
