package oops;

import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.*;

public interface Parser {
    ArrayList<Steps> parse() throws IOException;

    default String getClassName(Object s) {
        String newS;
        try {
            newS = ((String) s);
        } catch (Exception e) {
            return ((Cell) s).getStringCellValue();
        }
        return contains(newS, ":") ? substringBetween(newS, ":", "|").trim() : substringBefore(newS, "|");
    }

    default String getMethod(Object s) {
        String newS;
        try {
            newS = ((String) s);
        } catch (Exception e) {
            return ((Cell) s).getStringCellValue();
        }
        return countMatches(newS, "|") == 2 ? substringBetween(newS, "|") : substringAfter(newS, "|");
    }

    default String getAnnotation(String s) {
        return substringBefore(s, ":");
    }
}
