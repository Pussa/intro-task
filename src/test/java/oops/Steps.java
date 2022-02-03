package oops;

import lombok.Data;

import java.util.List;

@Data
public class Steps {
    String className;
    String methodName;
    List<String> params;
}
