package tests;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Test3 {

    @Parameters({"param0", "param1"})
    @Test
    public void action3_a(@Optional("") String data3a_1, @Optional("") String data3a_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[0].getName());
        if (!data3a_1.equals("")) {
            System.out.print("->" + data3a_1 + ";" +
                    data3a_2);
        }
    }

    @Parameters({"param0", "param1"})
    @Test
    public void action3_b(@Optional("") String data3b_1, @Optional("") String data3b_2) {
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
