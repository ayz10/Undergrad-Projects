package bankgui.gui;

import java.util.Calendar;

/**
 *This class builds a Date object with the year
 *month and day
 *@author Vineel Reddy
 *@author Alexander Zhao
 */

public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    /**
     *Date object constructor from a String input
     *formatted as mm/dd/yy
     *@param date as a String
     */
    public Date(String date) {
        String[] values = date.split("/");
        this.month  = Integer.parseInt(values[0]);
        this.day = Integer.parseInt(values[1]);
        this.year = Integer.parseInt(values[2]);
    }

    /**
     *Date object constructor with today's date using
     *the Calendar class
     */
    public Date() {
        Calendar today = Calendar.getInstance();
        this.year = today.get(Calendar.YEAR);
        this.month = today.get(Calendar.MONTH)+1;
        this.day = today.get(Calendar.DAY_OF_MONTH);
    }
    /**
     *Date object constructor with int input
     *@param year Year
     * @param month Month
     * @param day Day
     */
    public Date(int year, int month, int day){
        this.month  = month;
        this.day = day;
        this.year = year;
    }

    /**
     *Checks if a date is a valid calendar date
     *@return boolean true if date is valid, false otherwise
     */
    public boolean isValid() {
        Calendar today = Calendar.getInstance();
        Date date = new Date(today.get(Calendar.YEAR),today.get(Calendar.MONTH)+1,today.get(Calendar.DAY_OF_MONTH));
        int QUADRENNIAL = 4;
        int CENTENNIAL = 100;
        int QUARTERCENTENNIAL = 400;
        int longMonth = 31;
        int shortMonth = 30;
        int longFeb = 29;
        int shortFeb = 28;
        boolean leapYear;

        int MAX_MONTH = 12;

        if(this.month > MAX_MONTH ){
            return false;
        }

        if(this.year%QUADRENNIAL!=0){
            leapYear = false;
        }
        else if(this.year%CENTENNIAL!=0){
            leapYear = true;
        }
        else if(this.year%QUARTERCENTENNIAL!=0){
            leapYear = false;
        }
        else{
            leapYear = true;
        }

        switch(this.month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if(this.day<=longMonth) return true;
                break;
            case 2:
                if(this.day<=shortFeb) return true;
                if(this.day<=longFeb){
                    if(leapYear) return true;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if(this.day<=shortMonth) return true;
                break;
        }
        return false;
    }
    /**
     *Compares two dates and returns 1 or -1
     *@param date date object
     *@return int 1 if the date is later than the parameter, -1
     *if earlier, 0 if same date
     */
    @Override
    public int compareTo(Date date) {
        if(this.year> date.getYear()) return 1;
        else if(this.year< date.getYear()) return -1;
        else{
            if(this.month > date.getMonth()) return 1;
            else if (this.month < date.getMonth()) return -1;
            else{
                if(this.day > date.getDay()) return 1;
                else if(this.day < date.getDay()) return -1;
                else return 0;
            }
        }
    }

    /**
     *Get year from the date
     *@return int year
     */
    public int getYear(){
        return this.year;
    }

    /**
     *Get day from the date
     *@return int day
     */
    public int getDay(){
        return this.day;
    }

    /**
     *Get month from the date
     *@return int month
     */
    public int getMonth(){
        return this.month;
    }
}