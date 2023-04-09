package src.transaction;

/**
 *This class represents a schedule of appointments and keeps them as an array.
 *Includes methods to add/remove/sort appointments
 *@author Vineel Reddy
 *@author Alexander Zhao
 */

public class Schedule {
    private Appointment [] appointments;
    private int numAppts;

    /**
     *Schedule object constructor
     *@param appointments array of appointments
     *@param numAppts number of appointments in array
     */
    Schedule(Appointment[] appointments, int numAppts){
        this.appointments = appointments;
        for(int i = 0; i < appointments.length; i++){
            appointments[i] = null;
        }
        this.numAppts = numAppts;
    }

    /**
     *method return index of appointment or NOT_FOUND
     *@param appointment finds this Appointment
     *@return int index
     */
    private int find(Appointment appointment) {
        int i = 0;
        for(Appointment p: appointments){

            if (p != null && p.equals(appointment)){
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     *increase the capacity of the container by 4
     */
    private void grow() {
        Appointment [] newArray = new Appointment[appointments.length+4];
        for(int i = 0; i < appointments.length; i++){
            newArray[i] = appointments[i];
        }
        this.appointments = newArray;
    }

    /**
     *Add appointment to schedule
     *@param appointment Adds an appointment
     *@return boolean true if appointment added, false otherwise
     */
    public boolean add(Appointment appointment) {
        if (find(appointment) == -1 ){
            if(numAppts == appointments.length){
                grow();
            }
            appointments[numAppts] = appointment;
            numAppts++;
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *Remove appointment from schedule
     *@param appointment removes this appoinment
     *@return boolean true if appointment removed, false otherwise
     */
    public boolean remove(Appointment appointment) {
        int bool = find(appointment);
        if(bool == -1){
            return false;
        }
        appointments[bool] = null;
        for(int i = bool; i < appointments.length - 1; i++){
            appointments[i] = appointments[i+1];
        }
        numAppts--;
        return true;
    }

    /**
     *print all the appointments in current order
     */
    public void print() {//print all the appointments in current order
        System.out.println("*list of appointments in schedule*");
        for(Appointment a: appointments){
            if(a != null){
                System.out.println(a.toString());

            }
        }
    }

    /**
     *sort appointments by zip codes and print
     */
    public void printByZip() {
        for(int i = 0; i < numAppts - 1; i++){
            for(int x = 0; x < numAppts - i - 1; x++){
                int s = appointments[x].getLocation().getZip().compareTo(appointments[x + 1].getLocation().getZip());
                int t = appointments[x].getSlot().compareTo(appointments[x + 1].getSlot());
                if(s > 0){
                    Appointment temp = appointments[x];
                    appointments[x] = appointments [x + 1];
                    appointments[x + 1] = temp;
                }
                if(s == 0 && t > 0){
                    Appointment temp = appointments[x];
                    appointments[x] = appointments [x + 1];
                    appointments[x + 1] = temp;
                }
            }
        }

        System.out.println("*list of appointments in schedule by zip and time slot.");
        for(Appointment a: appointments){
            if(a != null){
                System.out.println(a.toString());

            }
        }
    }

    /**
     *sort appointments by patient and print
     */
    public void printByPatient() {
        boolean sorted = false;
        while(!sorted){

            sorted = true;
            for(int i  = 0; i < numAppts - 1; i++){
                if(appointments[i] == null){
                    System.out.println("Null");
                    sorted = true;
                    break;
                }
                else{
                    System.out.println(appointments[i].toString());
                }
                int l = appointments[i].getPatient().getLname().compareTo(appointments[i+1].getPatient().getLname());
                int f = appointments[i].getPatient().getFname().compareTo(appointments[i+1].getPatient().getFname());
                int d = appointments[i].getPatient().getDOB().compareTo(appointments[i+1].getPatient().getDOB());
                if (l > 0){
                    Appointment temp= appointments[i];
                    appointments[i] = appointments[i+1];
                    appointments[i+1] = temp;
                    sorted = false;
                }
                else if(l == 0){
                    if(f > 0){
                        Appointment temp = appointments[i];
                        appointments[i] = appointments[i+1];
                        appointments[i+1] = temp;
                        sorted = false;
                    }
                    else if(f ==0){
                        if(d > 0) {
                            Appointment temp = appointments[i];
                            appointments[i] = appointments[i + 1];
                            appointments[i + 1] = temp;
                            sorted = false;
                        }
                    }
                }
            }
        }

        System.out.println("List of appointments by patient.");
        for(Appointment a: appointments){
            if(a != null){
                System.out.println(a.toString());

            }
        }
    }

    /**
     *get array of appointments
     *@return array of appointments
     */
    public Appointment [] getAppointments(){
        return this.appointments;
    }
}
