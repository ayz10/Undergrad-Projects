package src.banking;
import static org.junit.Assert.*;

public class AccountDatabaseTest {

    //test #1
    @org.junit.Test
    public void test_open() {
        Profile prof = new Profile("John", "Smith", new Date("1/1/1970"));
        Checking account = new Checking(prof, 1000);
        AccountDatabase database = new AccountDatabase();
        assertTrue(database.open(account));
    }
    //test #2
    @org.junit.Test
    public void test_open_alreadyExists() {
        Profile prof = new Profile("John", "Smith", new Date("1/1/1970"));
        Checking account = new Checking(prof, 1000);
        AccountDatabase database = new AccountDatabase();
        database.open(account);
        assertFalse(database.open(account));
    }

    //test #3
    @org.junit.Test
    public void test_close() {
        Profile prof = new Profile("John", "Smith", new Date("1/1/1970"));
        Checking account = new Checking(prof, 1000);
        AccountDatabase database = new AccountDatabase();
        database.open(account);
        assertTrue(database.close(account));
    }

    //test #4
    @org.junit.Test
    public void test_close_doesNotExist() {
        Profile prof = new Profile("John", "Smith", new Date("1/1/1970"));
        Checking account = new Checking(prof, 1000);
        AccountDatabase database = new AccountDatabase();
        assertFalse(database.close(account));
    }
}