package oops;

import org.apache.poi.ss.usermodel.Cell;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class Method implements Steps {
    @Override
    public String getStep(Object s) {
        String newS;
        try {
            newS = ((String) s);
        } catch (Exception e) {
            return ((Cell) s).getStringCellValue();
    }
        return newS.length() > 15 ? substringBetween(newS, "|", "|") : substringAfter(newS, "|");
    }
}
