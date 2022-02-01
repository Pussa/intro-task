package oops;

import org.apache.poi.ss.usermodel.Cell;

import static org.apache.commons.lang3.StringUtils.substringBefore;

public class ClassObject implements Steps {
    @Override
    public String getStep(Object s) {
        String newS;
        try {
            newS = ((String) s);
        } catch (Exception e) {
            return ((Cell) s).getStringCellValue();
        }
        return substringBefore(newS,"|");
    }
}
