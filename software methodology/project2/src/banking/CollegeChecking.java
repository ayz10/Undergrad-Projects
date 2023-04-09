package src.banking;

/**
 *This class extends the Checking class and represents a college checking account
 * @author Vineel Reddy
 * @author Alexandr Zhao
 */

public class CollegeChecking extends Checking{
    final int campus;

    /**
     *College Checking account constructor
     * @param holder Profile holder
     * @param balance balance amount
     * @param campus campus location
     */
    public CollegeChecking(Profile holder, double balance, int campus) {
        super(holder, balance);
        this.campus = campus;

    }

    /**
     *Get monthly interest amount of college checking account
     * @return double monthly interest
     */
    @Override
    public double monthlyInterest() //return the monthly interest
    {
        final double RATE = 0.25;
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
        return 0;
    }

    /**
     * @return String account type
     */
    @Override
    public String getType() //return the account type (class name)
    {
        return "College Checking";
    }

    /**
     * @return String account type and campus location
     */
    @Override
    public String toString(){
        String result = "";
        switch(this.campus){
            case 0: result = super.toString() + ":: New Brunswick";
                break;
            case 1: result = super.toString() + ":: Newark";
                break;
            case 2: result = super.toString() + ":: Camden";
                break;
        }
        return result;
    }
}