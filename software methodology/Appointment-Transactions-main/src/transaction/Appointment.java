package src.transaction;

/**
 *This class represents an Appointment that is used by the
 *Schedule class
 *@author Vineel Reddy
 *@author Alexander Zhao
 */
public class Appointment {
    private Patient patient;
    private Timeslot slot;
    private Location location;
    /**
     *Constructor creates Appointment object
     *@param patient is patient object
     *@param timeslot is timeslot object
     *@param location is location object
     */
    Appointment(Patient patient, Timeslot timeslot, Location location){
        this.patient = patient;
        this.slot = timeslot;
        this.location = location;
    }
    public Timeslot getSlot(){
        return this.slot;
    }
    public Location getLocation(){
        return this.location;
    }
    public Patient getPatient(){
        return this.patient;
    }
    /**
     *This method checks if two appointment objects are equal
     *@param obj Appointment object to compare to
     *@return boolean true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (obj instanceof Appointment){
            if(this.slot.compareTo(((Appointment) obj).getSlot()) == 0 &&
                this.location.equals(((Appointment) obj).getLocation()) &&
                this.patient.compareTo(((Appointment) obj).getPatient()) == 0) {
                    return true;
            }
        }
        return false;
    }
    /**
     * Convert Appointment object to String
     *@return String of appointment details
     */
    @Override
    public String toString(){
        return patient.toString() + ", Appointment Detail: " + slot.toString() + ", " + location.toString();
    }


}
