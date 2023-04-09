package src.banking;

/**
 *This class represent an Account that is used by the AccountDatabase class
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public abstract class Account {
    protected Profile holder;
    protected boolean closed = false;
    protected double balance;

    /**
     *Constructor for Account object
     * @param holder Profile object holder
     * @param balance balance of account
     */
    public Account(Profile holder, double balance){
        this.holder = holder;

        this.balance = balance;
    }

    /**
     *Returns true if accounts are the same
     * @param obj Account
     * @return boolean trie of equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        else if (obj instanceof Account){
            return this.holder.equals( ((Account) obj).holder) &&
            getType().compareTo(((Account) obj).getType()) == 0;
        }
        else{
            return false;
        }
    }

    /**
     *Convert Appointment object to String
     * @return String of Account details
     */
    @Override
    public String toString() {
        if(this.closed){
            return getType() + "::" + holder.toString() + "::Balance " + this.balance + "::CLOSED";
        }
        else{
            return getType() + "::" + holder.toString() + "::Balance " + this.balance;
        }
    }

    /**
     *Withdraw certain amount from balance
     * @param amount amount to be withdrawn
     */
    public void withdraw(double amount) {
        this.balance -= amount;
    }

    /**
     *Deposit certain amount to balance
     * @param amount amount to be deposited
     */
    public void deposit(double amount) {
        this.balance += amount;
    }

    /**
     *Get monthly interest amount
     * @return double monthly interest
     */
    public abstract double monthlyInterest(); //return the monthly interest

    /**
     *Get monthly fee
     * @return double monthly fee
     */
    public abstract double fee(); //return the monthly fee

    /**
     *Get type of account
     * @return String account type
     */
    public abstract String getType(); //return the account type (class name)
}