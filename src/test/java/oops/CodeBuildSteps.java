package oops;

import lombok.Data;

import java.util.List;

@Data
public class CodeBuildSteps {
    String innerClassName;
    String innerMethodName;
    List<String> params;
    String annotation;
}
