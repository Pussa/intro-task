package tests;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Test2 {

    @Parameters({"param0", "param1"})
    @Test
    public void action2_a(@Optional("") String data2a_1, @Optional("") String data2a_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[0].getName());
        if (!data2a_1.equals("")) {
            System.out.print("->" + data2a_1 + ";" +
                    data2a_2);
        }
    }

    @Parameters({"param0", "param1"})
    @Test
    public void action2_b(@Optional("") String data2b_1, @Optional("") String data2b_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[1].getName());
        if (!data2b_1.equals("")) {
            System.out.print("->" + data2b_1 + ";" + data2b_2);
        }
    }
}
