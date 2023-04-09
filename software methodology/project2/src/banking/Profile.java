package src.banking;

//import src.transaction.Date;

/**
 *This class represents a person who holds an account and methods to return profile information
 * @author Vineel Reddy
 * @author Alexander Zhao
 */

public class Profile{
    private String fname;
    private String lname;
    private Date dob;

    /**
     *Constructor of Profile object
     * @param fname first name
     * @param lname last name
     * @param dob date of birth
     */
    Profile(String fname, String lname, Date dob){
        this.fname =  fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     *Convert Profile object to string
     * @return String profile information
     */
    @Override
    public String toString(){
        String profile = this.fname + " " + this.lname + ", DOB: " + this.dob.getMonth() + "/" + this.dob.getDay() + "/" + this.dob.getYear();
        return profile;
    }

    /**
     *Check if two profiles are the same
     * @param profile profile to compare to
     * @return true if same profile, false otherwise
     */
    public boolean equals(Profile profile){
        if (this.fname.compareTo(profile.getFname())==0 &&
            this.lname.compareTo(profile.getLname())==0 &&
            this.dob.compareTo(profile.getDOB())==0){
            return true;
        }
        return false;
    }

    /**
     *get patient first name
     *@return String first name
     */
    public String getFname(){
        return this.fname;
    }

    /**
     *get patient last name
     *@return String last name
     */
    public String getLname(){
        return this.lname;
    }

    /**
     *get patient date of birth
     *@return Date date of birth
     */
    public Date getDOB(){
        return this.dob;
    }
}