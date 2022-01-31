import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType._NONE;

public class Runner {

    static String className;
    static String methodName;
    static String firstParam;
    static String secondParam;

    public static void main(String[] args) throws IOException {

        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String string = bufferedReader.readLine();
        if (string.equals("file")) {
            setParamsFromExcel();
        } else {
            setParamsFromString(string);
        }
    }

    public static void setParamsFromString(String string) {
        int shortStringLength = 15;
        List<String> strings = new ArrayList<>(List.of(substringsBetween(string, "“", "”")));

        for (String s : strings) {
            String newS = s.replaceAll("\\s", "");

            className = substringBefore(newS, "|");
            methodName = newS.length() <= shortStringLength ? substringAfter(newS, "|")
                    : substringBetween(newS, "|", "|");
            firstParam = newS.length() <= shortStringLength ? ""
                    : substringAfterLast(newS, "|");
            firstParam = newS.length() <= shortStringLength ? ""
                    : substringBefore(firstParam, ";");
            secondParam = newS.length() <= shortStringLength ? ""
                    : substringAfter(newS, ";");

            runTest(strings.indexOf(s));
        }
    }

    public static void setParamsFromExcel() throws IOException {
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
                        className = cell.getStringCellValue();
                    case 1:
                        methodName = cell.getStringCellValue();
                    case 2:
                        firstParam = (cell.getCellType() != _NONE || cell.getCellType() != BLANK)
                                ? cell.getStringCellValue() : "";
                    case 3:
                        secondParam = (cell.getCellType() != _NONE || cell.getCellType() != BLANK)
                                ? cell.getStringCellValue() : "";
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