package src.banking;
import static org.junit.Assert.*;

public class MoneyMarketTest {

    //test #1
    @org.junit.Test
    public void test_lowBalance() {
        Profile prof = new Profile("John", "Smith", new Date("1/1/1970"));
        MoneyMarket acc = new MoneyMarket(prof, 2400);
        double expectedResult = ((.8/12.0)/100)*2400;
        acc.updateLoyalty();
        assertEquals(expectedResult, acc.monthlyInterest(),0.1);
    }

    //test #2
    @org.junit.Test
    public void test_highBalance() {
        Profile prof = new Profile("John", "Smith", new Date("1/1/1970"));
        MoneyMarket acc = new MoneyMarket(prof, 2600);
        double expectedResult = (((.95/12)/100)*2600);
        assertEquals(expectedResult, acc.monthlyInterest(),0.1);
    }

    //test #3
    @org.junit.Test
    public void test_highWithdrawals() {
        Profile prof = new Profile("John", "Smith", new Date("1/1/1970"));
        MoneyMarket acc = new MoneyMarket(prof, 2600);
        acc.setWithdrawalCount(4);
        double expectedResult = (((.95/12)/100)*2600);
        assertEquals(expectedResult, acc.monthlyInterest(),0.1);
    }
}
