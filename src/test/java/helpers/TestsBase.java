package helpers;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;

public class TestsBase{

    @BeforeMethod
    private void setEnvironment(ITestResult iTestResult) {
        Object[] params = new Object[] {System.getProperty("firstParam"), System.getProperty("secondParam")};
        iTestResult.setParameters(params);
    }
}
