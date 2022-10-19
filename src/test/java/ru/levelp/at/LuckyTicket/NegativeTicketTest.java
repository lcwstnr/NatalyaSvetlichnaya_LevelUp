package ru.levelp.at.LuckyTicket;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import ru.levelp.at.homework2.LuckyTicketCalculate;

public class NegativeTicketTest extends BaseLuckyTicketCalculateTest {

    private static final String  INPUT = "123456";
    private static final Boolean EXPECTED_OUTPUT = false;


    @Test
    public void sumOfNumberIsNotEqualToEachOtherTest() {
        Boolean actualOutput = LuckyTicketCalculate.luckyTicketCalculate(INPUT);
        Assertions.assertThat(actualOutput).isEqualTo(EXPECTED_OUTPUT);
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Количество символов не равно 6")
    public void listOfNumberMoreThanSixTest() {
       LuckyTicketCalculate.luckyTicketCalculate("1234567");
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Количество символов не равно 6")
    public void listOfNumberLessThanSixTest() {
        LuckyTicketCalculate.luckyTicketCalculate("12345");
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Номер должен состоять из цифр")
    public void listOfNumberContainsSymbolsTest() {
        LuckyTicketCalculate.luckyTicketCalculate("123y45");
    }

    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "Номер не может быть null")
    public void listOfNumberIsNullTest() {
        LuckyTicketCalculate.luckyTicketCalculate(null);
    }

}
