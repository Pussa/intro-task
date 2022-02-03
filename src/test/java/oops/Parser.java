package oops;

import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType._NONE;

public interface Parser {
    ArrayList<Steps> parse() throws IOException;

    default String getClassObject(Object s) {
        String newS;
        try {
            newS = ((String) s);
        } catch (Exception e) {
            return ((Cell) s).getStringCellValue();
        }
        return substringBefore(newS, "|");
    }

    default String getMethod(Object s) {
        String newS;
        try {
            newS = ((String) s);
        } catch (Exception e) {
            return ((Cell) s).getStringCellValue();
        }
        return newS.length() > 15 ? substringBetween(newS, "|", "|") : substringAfter(newS, "|");
    }

    default String getParams(Object s) {
        String newS;
        try {
            newS = ((String) s);
        } catch (Exception e) {
            Cell cell = (Cell) s;
            return (cell.getCellType() != _NONE || cell.getCellType() != BLANK)
                    ? cell.getStringCellValue() : "";
        }
        return newS.length() > 15 ? substringAfterLast(newS, "|") : "";
    }

    default String getFirstParam(String params) {
        return !params.equals("") ? substringBefore(params, ";") : "";
    }

    default String getSecondParam(String params) {
        return !params.equals("") ? substringAfter(params, ";") : "";
    }
}
