package ru.levelp.at.LuckyTicket;

//import org.testng.annotations.BeforeMethod;
import ru.levelp.at.homework2.LuckyTicketCalculate;

public abstract class BaseLuckyTicketCalculateTest {

    protected LuckyTicketCalculate ticketCalculate;

//    @BeforeMethod
    public void BeforeMethod() {
        LuckyTicketCalculate ticketCalculate = new LuckyTicketCalculate();
    }

}
