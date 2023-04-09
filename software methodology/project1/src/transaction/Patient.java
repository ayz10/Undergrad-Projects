package src.transaction;

/**
 *This class represents a patient and methods to return patient information
 *@author Vineel Reddy
 *@author Alexander Zhao
 */
public class Patient implements Comparable<Patient> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     *Patient object constructor
     *@param fname string first name
     *@param lname string last name
     *@param dob date of birth
     */
    Patient(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     *convert patient object to string
     *@return string patient
     */
    @Override
    public String toString() {
        String patient = this.fname + " " + this.lname + ", DOB: " + this.dob.getMonth() + "/" + this.dob.getDay() + "/" + this.dob.getYear();
        return patient;
    }

    /**
     *compares two patients based on last name, first name, and date of birth in that order
     *@param patient
     *@return 1 if this patient is greater than parameter, -1 if less than, 0 if equal
     */
    @Override
    public int compareTo(Patient patient) {
        int greaterThan = 1;
        int lessThan = -1;
        int equal = 0;

        if(this.lname.compareTo(patient.lname) > equal){
            return greaterThan;
        }
        else if(this.lname.compareTo(patient.lname) < equal){
            return lessThan;
        }
        else{
            if(this.fname.compareTo(patient.fname) > equal){
                return greaterThan;
            }
            else if(this.fname.compareTo(patient.fname) < equal){
                return lessThan;
            }
            else{
                if(this.dob.compareTo(patient.dob) > equal){
                    return greaterThan;
                }
                else if(this.dob.compareTo(patient.dob) < equal){
                    return lessThan;
                }
                else{
                    return equal;
                }
            }
        }
    }

    /**
     *get patient first name
     *@return string first name
     */
    public String getFname(){
        return this.fname;
    }

    /**
     *get patient last name
     *@return string last name
     */
    public String getLname(){
        return this.lname;
    }

    /**
     *get patient date of birth
     *@return date of birth
     */
    public Date getDOB(){
        return this.dob;
    }

}

