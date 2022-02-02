package oops;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Parser {
    Steps steps = new Steps();
    ArrayList<List<String>> parse() throws IOException;
}
