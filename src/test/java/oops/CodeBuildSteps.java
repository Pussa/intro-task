package oops;

import lombok.Data;

import java.util.List;

@Data
public class CodeBuildSteps {
    String className;
    String innerClassName;
    String innerMethodName;
    List<String> params;
    List<String> annotations;
}
