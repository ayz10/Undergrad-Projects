package src.banking;

/**
 *This class extends the abstract Account class and represents a checking account
 * @author Vineel Reddy
 * @author Alexandr Zhao
 */

public class Checking extends Account{

    /**
     *Checking account constructor
     * @param holder Profile holder
     * @param balance balance amount
     */
    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     *Get monthly interest amount of checking account
     * @return double monthly interest
     */
    @Override
    public double monthlyInterest() //return the monthly interest
    {
        final double RATE = 0.1;
        final double CENT = 100;
        final double NUM_MONTHS = 12.0;
        return (super.balance * ((RATE/NUM_MONTHS)/CENT));

    }

    /**
     *Get monthly fee
     * @return double monthly fee
     */
    @Override
    public double fee() //return the monthly fee
    {
        final double FEE = 25;
        if(super.balance < 1000)
            return(FEE);
        return 0;
    }

    /**
     * @return account type as a String
     */
    @Override
    public String getType() //return the account type (class name)
    {
        return "Checking";
    }

}