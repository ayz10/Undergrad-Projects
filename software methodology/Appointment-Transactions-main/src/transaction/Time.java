package src.transaction;

import java.util.Calendar;

/**
 *This class builds a Time object
 *@author Vineel Reddy
 *@author Alexander Zhao
 */
public class Time implements Comparable<Time>{
    static final int MAX_MINUTE = 60;
    static final int MAX_HOUR = 24;
    private int hour;
    private int minute;

    /**
     *Time object constructor with current time using calendar class
     */
    public Time(){
        Calendar today = Calendar.getInstance();
        this.hour = today.get(Calendar.HOUR_OF_DAY);
        this.minute = today.get(Calendar.MINUTE);
    }

    /**
     *Time object constructor from string input
     *@param input string time
     */
    public Time(String input){
        String[] tokens = input.split(":");
        this.hour = Integer.parseInt(tokens[0]);
        this.minute = Integer.parseInt(tokens[1]);
    }

    /**
     *check if time is a valid
     *@return true if time is valid, false otherwise
     */
    public boolean isValid() {
        int min = 0;
        return this.minute < MAX_MINUTE && this.hour < MAX_HOUR && this.minute >= min && this.hour >= min;
    }

    /**
     *get hour from time
     *@return int hour
     */
    public int getHour(){
        return this.hour;
    }

    /**
     *get minute from time
     *@return int minute
     */
    public int getMinute(){
        return this.minute;
    }

    /**
     *convert time object to string
     *@return string time
     */
    @Override
    public String toString() {
        if(this.minute != 0){
            return this.hour + ":" + this.minute;

        }
        else{
            return this.hour + ":00";

        }
    }

    /**
     *compares two time objects
     *@param time
     *@return 1 if this time is later than parameter, -1 if before, 0 otherwise
     */
    @Override
    public int compareTo(Time time) {
      if (this.hour < time.getHour()){
          return -1;
      }
      else if (this.hour > time.getHour()){
          return 1;
      }
      else{
          if(this.minute < time.getMinute()){
              return -1;
          }
          else if(this.minute > time.getMinute()){
              return 1;
          }
          else{
              return 0;
          }
      }
    }
}
