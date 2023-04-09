package src.banking;
import java.text.DecimalFormat;

/**
 * This class represents a database of Accounts and stores them in an array and
 * methods to deposit/withdraw/close/open accounts
 * @author Vineel Reddy
 * @author Alexander Zhao
 */

public class AccountDatabase {
    private Account [] accounts;
    private int numAcct;

    /**
     * AccountDatabase object constructor
     * @param list array of Accounts
     * @param numAcct number of Accounts in database
     */
    public AccountDatabase(Account[] list, int numAcct){
        this.accounts = list;
        this.numAcct = numAcct;
    }

    /**
     * AccountData base constructor initialized to null
     */
    public AccountDatabase(){
        this.accounts = new Account[4];
        this.numAcct = 0;
    }


    /**
     *Return index of account or -1
     * @param account find this account
     * @return int index
     */
    private int find(Account account) {
        for (int i = 0; i<numAcct; i++){
            if (accounts[i] != null && accounts[i].equals(account)){
                return i;
            }
        }
        return -1;
    }

    /**
     *increase capacity of accounts[] by 4
     */
    private void grow() {
        Account [] newArray = new Account[accounts.length+4];
        for(int i = 0; i < numAcct; i++){
            newArray[i] = accounts[i];
        }
        this.accounts = newArray;
    }

    /**
     *Open/reopen an account
     * @param account account to be added
     * @return Boolean if the account has been opened
     */
    public boolean open(Account account) {
        int index = find(account);
        if(index != -1){
            return false;
        }
        if(accounts.length == numAcct){
            grow();
        }
        accounts[numAcct] = account;
        numAcct++;
        return true;
    }

    /**
     *Mark existing account as closed
     * @param account account to be closed
     * @return boolean if account succesfully closed, false otherwise
     */
    public boolean close(Account account) {

        int index = find(account);
        if(index == -1){
            return false;
        }
        else{
            accounts[index].closed = true;
            return true;
        }
    }

    /**
     *Deposit into account
     * @param account deposit into this account
     */
    public void deposit(Account account) {
        int index = find(account);
        accounts[index].deposit(account.balance);
    }

    /**
     *Withdraw from account
     * @param account withdraw from this account
     * @return boolean true if withdrawn successfully, false otherwise
     */
    public boolean withdraw(Account account) {
        int index = find(account);
        if(accounts[index].balance < account.balance){
            return false;
        }
        else{
            accounts[index].withdraw(account.balance);
            return true;
        }
    } //return false if insufficient fund

    /**
     *print all accounts in current order
     */
    public void print() {
        System.out.println("*Start of List*");
        for(int i = 0; i < numAcct; i++){
            System.out.println(accounts[i].toString());
        }
        System.out.println("*End of List*");
    }

    /**
     *print all accounts by account type
     */
    public void printByAccountType() {
        if(numAcct == 0){
            System.out.println("Account Database is empty");
            return;
        }
        boolean sorted = false;
        while(!sorted){
            sorted = true;
            for(int i = 0; i <numAcct-1; i++){
                if(accounts[i] == null) {
                    System.out.println("Null");
                    sorted = true;
                    break;
                }
                String currAccount = accounts[i].getType();
                String nextAccount = accounts[i+1].getType();
                if(currAccount.compareTo(nextAccount)>0){
                    Account temp = accounts[i];
                    accounts[i] = accounts[i+1];
                    accounts[i+1] = temp;
                    sorted = false;
                }
            }
        }
        System.out.println("*list of accounts by account type.");
        for(Account a: accounts){
            if(a != null){
                System.out.println(a.toString());
            }
        }
        System.out.println("*End of List*");
    }

    /**
     *Print all accounts with monthly interest and fee
     */
    public void printFeeAndInterest() {
        if(numAcct == 0){
            System.out.println("Account Database is empty");
            return;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("*Start of List*");

        for(int i = 0; i < numAcct; i++){
            System.out.println(accounts[i].toString() + "::fee " + df.format(accounts[i].fee()) + "::monthly interest " + df.format(accounts[i].monthlyInterest()) + "");
        }
        System.out.println("*End of List*");
    }

    /**
     * Update balances after fees and interest
     */
    public void updateDatabase(){
        if(numAcct == 0){
            System.out.println("Account Database is empty");
            return;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        for (Account acc: accounts){
            if(acc != null){
                acc.balance += acc.monthlyInterest();
                acc.balance -= acc.fee();
                acc.balance = Double.parseDouble(df.format(acc.balance));
            }
        }
    }

    /**
     *Check if account exists in database
     * @param account check this account
     * @return true if account exists, false otherwise
     */
    public boolean contains(Account account){
        return find(account) != -1;
    }

    /**
     * Get accounts[] array
     * @return Account[] array of accounts
     */
    public Account[] getAccounts(){
        return this.accounts;
    }
}