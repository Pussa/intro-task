package oops;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XLSXParser implements Parser {
    @Override
    public ArrayList<Steps> parse() throws IOException {
        FileInputStream inputStream = new FileInputStream("src/test/resources/intro-task.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        ArrayList<Steps> allSteps = new ArrayList<>();
        for (Row row : sheet) {
            Steps step = new Steps();
            List<String> params = new ArrayList<>();
            for (Cell cell : row) {
                int index = cell.getColumnIndex();
                if (index == 0) {
                    step.setClassName(getClassObject(cell));
                } else if (index == 1) {
                    step.setMethodName(getMethod(cell));
                } else if (index == 2) {
                    params.add(getParams(cell));
                } else if (index == 3) {
                    params.add(getParams(cell));
                }
            }
            step.setParams(params);
            if (step.getClassName().equals(""))
                break;
            allSteps.add(step);
        }
        return allSteps;
    }

    @Override
    public Map<String, List<CodeBuildSteps>> parseForBuild() {
        return null;
    }
}
