package tests;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Test1 {

    @Parameters({"param0", "param1"})
    @Test
    public void action1_a(@Optional("") String data1a_1, @Optional("") String data1a_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[0].getName());
        if (!data1a_1.equals("")) {
            System.out.print("->" + data1a_1 + ";" +
                    data1a_2);
        }
    }

    @Parameters({"param0", "param1"})
    @Test
    public void action1_b(@Optional("") String data1b_1, @Optional("") String data1b_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[1].getName());
        if (!data1b_1.equals("")) {
            System.out.print("->" + data1b_1 + ";" +
                    data1b_2);
        }
    }
}
