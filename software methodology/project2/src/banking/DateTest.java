package src.banking;

import static org.junit.Assert.*;

public class DateTest {

    //test case #1
    @org.junit.Test
    public void test_before_today() {
        Date date = new Date("1/1/2000");
        assertTrue(date.isValid());
    }
    //test case #2
    @org.junit.Test
    public void test_days_Feb_nonLeap(){
        Date date = new Date ("2/29/2025");
        assertFalse(date.isValid());
    }
    //test case #3
    @org.junit.Test
    public void test_validMonth(){
        Date date = new Date ("13/1/2022");
        assertFalse(date.isValid());
    }

    //test case #4
    @org.junit.Test
    public void test_days_April(){
        Date date = new Date ("4/31/2022");
        assertFalse(date.isValid());
    }

    //test case #5
    @org.junit.Test
    public void test_days_Jan(){
        Date date = new Date ("1/31/2023");
        assertTrue(date.isValid());
    }
}
