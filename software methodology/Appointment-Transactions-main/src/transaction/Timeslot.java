package src.transaction;

/**
 *This class builds a Timeslot object
 *@author Vineel Reddy
 *@author Alexander Zhao
 */
public class Timeslot implements Comparable<Timeslot>{
    private static final int INTERVAL = 15;
    private static final int START_HOUR = 9;
    private static final int START_MIN = 0;
    private static final int END_HOUR = 16;
    private static final int END_MIN = 45;

    private Date date;
    private Time time;

    /**
     *Timeslot object constructor
     */
    Timeslot(){
        this.date = new Date();
        this.time = new Time();
    }

    /**
     *Timeslot object constructor with given time and date arguments
     * @param time time object
     * @param date date object
     */
    Timeslot(Time time, Date date){
        this.time = time;
        this.date = date;
    }

    /**
     *Check if timeslot is valid
     *@return true if valid, false if not
     */
    public boolean isValid(){
        if(!this.time.isValid() || !this.date.isValid()){
            return false;
        }
        else if(this.time.getMinute() % INTERVAL == 0){
            if(this.time.getHour() == 16
            && this.time.getMinute() <= END_MIN){
                return true;
            }
            else if(this.time.getHour() >= START_HOUR
            && this.time.getHour() <= END_HOUR){
                return true;
            }
            else{
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     *get date
     *@return date
     */
    public Date getDate(){
        return this.date;
    }

    /**
     *get time
     *@return time
     */
    public Time getTime(){
        return this.time;

    }

    /**
     *return timeslot as a string
     *@return string
     */
    @Override
    public String toString() {
       return this.date.getMonth() + "/" + this.date.getDay() + "/" + this.date.getYear() + ", " + this.time.toString();
    }

    /**
     *compares two timeslots and returns an int 1, -1, or 0
     *@param slot timeslot
     *@return 1 if this timeslot is after slot, -1 if before than, 0 if equal
     */
    @Override
    public int compareTo(Timeslot slot) {
        int dateCompareResult = this.date.compareTo(slot.getDate());
        int timeCompareResult = this.time.compareTo(slot.getTime());
        if(dateCompareResult == 0 && timeCompareResult == 0){
            return 0;
        }
        else if(dateCompareResult != 0){
            return dateCompareResult;
        }
        else{
            return timeCompareResult;
        }
    }

    /**
     *This is the Testbed main method to test specific cases of compareTo()
     */
    public static void main(String[]args){
        //Test case 1
        Time testTime1 = new Time("9:00");
        Date testDate1 = new Date("2/2/2023");
        Time testTime2 = new Time("9:00");
        Date testDate2 = new Date("2/3/2023");
        Timeslot timeslot1 = new Timeslot(testTime1, testDate1);
        Timeslot timeslot2 = new Timeslot(testTime2, testDate2);
        int test1 = timeslot1.compareTo(timeslot2);
        System.out.print(test1);
        if(test1 == -1){
            System.out.println(" - Test Passed");
        }
        else{
            System.out.println(" - Test Failed");
        }

        //Test Case 2
        Time testTime3 = new Time("10:00");
        Date testDate3 = new Date("2/2/2023");
        Time testTime4 = new Time("9:00");
        Date testDate4 = new Date("2/2/2023");
        Timeslot timeslot3 = new Timeslot(testTime3, testDate3);
        Timeslot timeslot4 = new Timeslot(testTime4, testDate4);
        int test2 = timeslot3.compareTo(timeslot4);
        System.out.print(test2);
        if(test2 == 1){
            System.out.println(" - Test Passed");
        }
        else{
            System.out.println(" - Test Failed");
        }

        //Test Case 3
        Time testTime5 = new Time("9:00");
        Date testDate5 = new Date("1/2/2023");
        Time testTime6 = new Time("9:00");
        Date testDate6 = new Date("2/2/2023");
        Timeslot timeslot5 = new Timeslot(testTime5, testDate5);
        Timeslot timeslot6 = new Timeslot(testTime6, testDate6);
        int test3 = timeslot5.compareTo(timeslot6);
        System.out.print(test3);
        if(test3 == -1){
            System.out.println(" - Test Passed");
        }
        else{
            System.out.println(" - Test Failed");
        }

        //Test Case 4
        Time testTime7 = new Time("9:00");
        Date testDate7 = new Date("2/2/2024");
        Time testTime8 = new Time("9:00");
        Date testDate8 = new Date("2/2/2023");
        Timeslot timeslot7 = new Timeslot(testTime7, testDate7);
        Timeslot timeslot8 = new Timeslot(testTime8, testDate8);
        int test4 = timeslot7.compareTo(timeslot8);
        System.out.print(test4);
        if(test4 == 1){
            System.out.println(" - Test Passed");
        }
        else{
            System.out.println(" - Test Failed");
        }

        //Test Case 5
        Time testTime9 = new Time("9:30");
        Date testDate9 = new Date("2/2/2023");
        Time testTime10 = new Time("9:00");
        Date testDate10 = new Date("2/2/2023");
        Timeslot timeslot9 = new Timeslot(testTime9, testDate9);
        Timeslot timeslot10 = new Timeslot(testTime10, testDate10);
        int test5 = timeslot9.compareTo(timeslot10);
        System.out.print(test5);
        if(test5 == 1){
            System.out.println(" - Test Passed");
        }
        else{
            System.out.println(" - Test Failed");
        }

        //Test Case 6
        Time testTime11 = new Time("9:00");
        Date testDate11 = new Date("2/2/2023");
        Time testTime12 = new Time("9:00");
        Date testDate12 = new Date("2/2/2023");
        Timeslot timeslot11 = new Timeslot(testTime11, testDate11);
        Timeslot timeslot12 = new Timeslot(testTime12, testDate12);
        int test6 = timeslot11.compareTo(timeslot12);
        System.out.print(test6);
        if(test6 == 0){
            System.out.println(" - Test Passed");
        }
        else{
            System.out.println(" - Test Failed");
        }
    }
}
