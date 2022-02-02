package oops;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XLSXParser implements Parser {
    @Override
    public ArrayList<List<String>> parse() throws IOException {
        FileInputStream inputStream = new FileInputStream("src/test/resources/intro-task.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        ArrayList<List<String>> allSteps = new ArrayList<>();
        for (Row row : sheet) {
            Iterator<Cell> cellIterator = row.cellIterator();

            String classObject = "";
            String method = "";
            String firstParam = "";
            String secondParam = "";
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getAddress().getColumn()) {
                    case 0:
                        classObject = steps.getClassObject(cell);
                    case 1:
                        method = steps.getMethod(cell);
                    case 2:
                        firstParam = steps.getParams(cell);
                    case 3:
                        secondParam = steps.getParams(cell);
                }
            }
            if (classObject.equals(""))
                break;
            allSteps.add(List.of(classObject, method, firstParam, secondParam));
        }
        return allSteps;
    }
}
