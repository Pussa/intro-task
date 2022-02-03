package tests;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Test4 {

    @Parameters({"param0", "param1"})
    @Test
    public void action4_a(@Optional("") String data4a_1, @Optional("") String data4a_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[0].getName());
        if (!data4a_1.equals("")) {
            System.out.print("->" + data4a_1 + ";" +
                    data4a_2);
        }
    }

    @Parameters({"param0", "param1"})
    @Test
    public void action4_b(@Optional("") String data4b_1, @Optional("") String data4b_2) {
        System.out.print(
                this.getClass().getSimpleName() + "->" +
                        this.getClass().getMethods()[1].getName());
        if (!data4b_1.equals("")) {
            System.out.print("->" + data4b_1 + ";" +
                    data4b_2);
        }
    }
}
