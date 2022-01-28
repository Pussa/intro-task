package tests;

import helpers.TestsBase;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Test1 extends TestsBase {

    @Parameters({"firstParam", "secondParam"})
    @Test
    public void action1_a(String data1a_1, String data1a_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[0].getName() );
        if (!data1a_1.equals("")) {
            System.out.print("->" + data1a_1 + ";" +
                    data1a_2);
        }
    }

    @Parameters({"firstParam", "secondParam"})
    @Test
    public void action1_b(String  data1b_1, String  data1b_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[1].getName() );
        if (!data1b_1.equals("")) {
            System.out.print("->" + data1b_1 + ";" +
                    data1b_2);
        }
    }
}
