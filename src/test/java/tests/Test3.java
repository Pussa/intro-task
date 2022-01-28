package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Test3 {

    @Parameters({"firstParam", "secondParam"})
    @Test
    public void action3_a(String  data3a_1, String  data3a_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[0].getName() );
        if (!data3a_1.equals("")) {
            System.out.print("->" + data3a_1 + ";" +
                    data3a_2);
        }
    }

    @Parameters({"firstParam", "secondParam"})
    @Test
    public void action3_b(String  data3b_1, String  data3b_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[1].getName()
        );
        if (!data3b_1.equals("")) {
            System.out.print("->" + data3b_1 + ";" +
                    data3b_2);
        }
    }
}
