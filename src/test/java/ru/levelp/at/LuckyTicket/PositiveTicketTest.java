package ru.levelp.at.LuckyTicket;


import org.assertj.core.api.Assertions;
//import org.testng.annotations.Test;
import ru.levelp.at.homework2.LuckyTicketCalculate;


public class PositiveTicketTest extends BaseLuckyTicketCalculateTest {

    private static final String  INPUT = "232322";
    private static final Boolean EXPECTED_OUTPUT = true;

//    @Test
    public void sumOfNumberIsEqualToEachOtherTest() {
        Boolean actualOutput = LuckyTicketCalculate.luckyTicketCalculate(INPUT);
        Assertions.assertThat(actualOutput).isEqualTo(EXPECTED_OUTPUT);
    }

}
