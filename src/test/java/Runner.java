import oops.ClassObject;
import oops.Method;
import oops.Params;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

import static org.apache.commons.lang3.StringUtils.substringsBetween;

public class Runner {

    static String className;
    static String methodName;
    static String firstParam;
    static String secondParam;
    static Params params = new Params();
    static ClassObject classObject = new ClassObject();
    static Method method = new Method();

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String string = bufferedReader.readLine();
        if (string.equals("file")) {
            runFromExcel();
        } else {
            runFromString(string);
        }
    }

    public static void runFromString(String string) {

        List<String> strings = new ArrayList<>(List.of(substringsBetween(string, "“", "”")));
        Consumer<String> cons = s -> {
            className = classObject.getStep(s);
            methodName = method.getStep(s);
            firstParam = params.getFirstParam(params.getStep(s));
            secondParam = params.getSecondParam(params.getStep(s));
            runTest(strings.indexOf(s));
        };
        strings
                .stream()
                .map(s -> s.replaceAll("\\s", ""))
                .forEach(cons);
        // обычный фор
//        for (String s : strings) {
//            String newS = s.replaceAll("\\s", "");
//
//            className = classObject.getStep(newS);
//            methodName = method.getStep(newS);
//            firstParam = params.getFirstParam(params.getStep(newS));
//            secondParam = params.getSecondParam(params.getStep(newS));
//
//            runTest(strings.indexOf(s));
//        }
    }

    public static void runFromExcel() throws IOException {
        FileInputStream inputStream = new FileInputStream("src/test/resources/intro-task.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Iterator<Cell> cellIterator = row.cellIterator();
            int rowNumber = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                rowNumber = cell.getRowIndex();
                switch (cell.getAddress().getColumn()) {
                    case 0:
                        className = classObject.getStep(cell);
                    case 1:
                        methodName = method.getStep(cell);
                    case 2:
                        firstParam = params.getStep(cell);
                    case 3:
                        secondParam = params.getStep(cell);
                }
            }
            if (className.equals(""))
                break;

            runTest(rowNumber);
        }
    }

    public static XmlSuite createSuite(int testNumber, String className, String methodName) {
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

    public static void runTest(int testNumber) {
        System.setProperty("firstParam", firstParam);
        System.setProperty("secondParam", secondParam);

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(createSuite(testNumber, className, methodName));

        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setXmlSuites(suites);
        testng.setTestNames(Collections.singletonList(methodName));
        testng.addListener(tla);
        testng.run();
    }
}