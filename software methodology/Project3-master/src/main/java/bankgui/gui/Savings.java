package bankgui.gui;


/**
 *This class extends the abstract Account class and represent a savings account
 * @author Vineel Reddy
 * @author Alexander Zhao
 */

public class Savings extends Account{
    protected boolean isLoyal;

    /**
     * Constructor of savings account
     * @param holder Profile holder
     * @param balance balance amount
     * @param isLoyal loyalty status
     */
    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     *Get monthly interest amount of savings account
     * @return double monthly interest
     */
    @Override
    public double monthlyInterest() {
        final double MONTHLY_INTEREST_RATE;
        final double NUM_MONTHS = 12.0;
        final double LOYAL_RATE = 0.45;
        final double RATE = 0.3;
        final double CENT = 100;

        if(isLoyal){
            MONTHLY_INTEREST_RATE = (LOYAL_RATE / NUM_MONTHS) / CENT;
        }
        else{
            MONTHLY_INTEREST_RATE = (RATE / NUM_MONTHS) / CENT;
        }
        return super.balance * MONTHLY_INTEREST_RATE;
    }

    /**
     *Get monthly fee
     * @return double monthly fee
     */
    @Override
    public double fee() {
        final double FEE = 6.00;
        final double BALANCE_CEILING = 300.00;
        if(super.balance >= BALANCE_CEILING){
            return 0;
        }
        else{
            return FEE;
        }
    }

    /**
     * @return account type as a String
     */
    @Override
    public String getType() {
        return "Savings";
    }

    /**
     * @return String account type and loyalty status
     */
    @Override
    public String toString(){
        if(isLoyal){
            return super.toString() + "::LOYAL";
        }
        else{
            return super.toString();
        }
    }
}