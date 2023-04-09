package src.banking;

/**
 *This class extends the Savings class and represents a money market account
 * @author Vineel Reddy
 * @author Alexandr Zhao
 */

public class MoneyMarket extends Savings{
    private int withdrawalCount = 0;

    /**
     * Money Market account constructor
     * @param holder Profile holder
     * @param balance balance amount
     */
    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance,true);
    }

    /**
     *Update loyalty if balance drops below 2500
     */
    public void updateLoyalty(){
        if(super.balance < 2500.00){
            super.isLoyal = false;
        }
        else{
            super.isLoyal = true;
        }
    }

    /**
     *Get monthly interest amount of money market account
     * @return double monthly interest
     */
    @Override
    public double monthlyInterest() {
        final double MONTHLY_INTEREST_RATE;
        final double CENT = 100;
        final double NUM_MONTHS = 12.0;
        final double LOYAL_RATE = 0.95;
        final double RATE = 0.8;
        if(super.isLoyal){
            MONTHLY_INTEREST_RATE = (LOYAL_RATE / NUM_MONTHS)/CENT;
        }
        else{
            MONTHLY_INTEREST_RATE = (RATE / NUM_MONTHS)/CENT;
        }

        return super.balance * MONTHLY_INTEREST_RATE;
    }

    /**
     *Get monthly fee
     * @return double monthly fee
     */
    @Override
    public double fee() {
        final double MAX_FREE_WITHDRAWS = 3;
        final double FEE = 10.00;
        if(super.isLoyal && this.withdrawalCount < MAX_FREE_WITHDRAWS){
            return 0;
        }
        else{
            return FEE;
        }
    }

    /**
     *Withdraw amount from account and update withdrawal count
     * @param amount amount to be withdrawn
     */
    @Override
    public void withdraw(double amount) {
        updateLoyalty();
        withdrawalCount++;
        super.withdraw(amount);
    }

    /**
     * @return String account type and withdrawl count
     */
    @Override
    public String toString(){
        updateLoyalty();
        return super.toString() + "::withdrawal: " + withdrawalCount + "";
    }

    /**
     *Set withdrawal count of account
     * @param count int count
     */
    public void setWithdrawalCount(int count){
        this.withdrawalCount = count;
    }

    /**
     * @return account type as a String
     */
    @Override
    public String getType() {
        return "Money Market";
    }
}