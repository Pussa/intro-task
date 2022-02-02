package oops;

import org.apache.poi.ss.usermodel.Cell;

import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType._NONE;

public class Steps {

    public String getClassObject(Object s) {
        String newS;
        try {
            newS = ((String) s);
        } catch (Exception e) {
            return ((Cell) s).getStringCellValue();
        }
        return substringBefore(newS,"|");
    }

    public String getMethod(Object s) {
        String newS;
        try {
            newS = ((String) s);
        } catch (Exception e) {
            return ((Cell) s).getStringCellValue();
        }
        return newS.length() > 15 ? substringBetween(newS, "|", "|") : substringAfter(newS, "|");
    }

    public String getParams(Object s) {
        String newS;
        try {
            newS = ((String) s);
        } catch (Exception e) {
            Cell cell = (Cell) s;
            return (cell.getCellType() != _NONE || cell.getCellType() != BLANK)
                    ? cell.getStringCellValue() : "";
        }
        return newS.length() > 15 ? substringAfterLast(newS,"|") : "";
    }

    public String getFirstParam(String params) {
        return !params.equals("") ? substringBefore(params,";") : "";
    }

    public String getSecondParam(String params) {
        return !params.equals("") ? substringAfter(params, ";") : "";
    }
}
