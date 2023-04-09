package src.transaction;

import java.util.Scanner;

/**
 *This class is the user interface to process transactions entered through the console
 *and outputs the results to the console
 *@author Vineel Reddy
 *@author Alexander Zhao
 */
public class Kiosk {
    private Schedule schedule;

    /**
     *run method to interpret transaction codes
     */
    public void run(){
        Appointment[] appList = new Appointment[2];
        for(Appointment app: appList) {app = null;}
        schedule = new Schedule(appList, 0);
        System.out.println("src.Kiosk Running. Ready to process transactions.");
        boolean runner = true;
        while(runner){
            Scanner scanner = new Scanner(System.in);
            while(runner & scanner.hasNext()){
                String input = scanner.nextLine();
                String[] tokens = input.split(" ");
                switch (tokens[0]){
                    case "Q" : runner = false;
                        break;
                    case "B" : bookAppointment(tokens);
                        break;
                    case "C" : cancelAppointment(tokens);
                        break;
                    case "CP" : cancelPatient(tokens);
                        break;
                    case "P" : printAppointments();
                        break;
                    case "PZ" : printZipAppointments();
                        break;
                    case "PP" : printPatientCommand();
                        break;
                    default : System.out.println("Invalid Command");
                        break;
                }
            }
            scanner.close();
        }
    }

    /**
     *private helper method to check if the timeslot and the location are open
     *@param slot Timeslot
     *@param loc Location
     *@return boolean
     */
    private boolean isSlotValid(Timeslot slot, Location loc){
        for(Appointment appt: this.schedule.getAppointments()){
            if(appt != null
            && (slot.compareTo(appt.getSlot()) == 0 && loc.toString().equals(appt.getLocation().toString()))){
                return false;
            }
        }
        return true;
    }

    /**
     *private helper method to check if the date of birth is a valid one
     *@param dob Date
     *@return boolean
     */
    private boolean isDOBValid(Date dob){
        Date today = new Date();
        if (today.compareTo(dob) == 0 || today.compareTo(dob)< 0 ){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     *private helper method to check if the date for the appointment is valid given the context
     *@param date Date
     *@return boolean
     */
    private boolean isApptDateValid(Date date){
        Date today = new Date();
        if (today.compareTo(date) == 0 || today.compareTo(date) > 0 || date.getYear() != today.getYear()){
            return false;
        }
        else{
            return true;
        }
    }


    /**
     *This method adds an appointment to the schedule
     *@param input String of patient and appointment information
     */
    public void bookAppointment(String[] input){
        Appointment app = makeAppointment(input);
        if(app != null){
            for(Appointment appt: this.schedule.getAppointments()){
                if(appt != null
                && app.getLocation().toString().equals(appt.getLocation().toString())
                && app.getSlot().compareTo(appt.getSlot()) == 0) {
                    System.out.println("Timeslot Already Booked");
                    return;
                }
                if(appt != null
                && app.getPatient().compareTo(appt.getPatient()) == 0
                && app.getSlot().getDate().compareTo(appt.getSlot().getDate()) == 0
                && !app.getLocation().toString().equals(appt.getLocation().toString())){
                    System.out.println("Patient has already booked an appointment at a different location for the same day.");
                    return;
                }
            }
            this.schedule.add(app);
            System.out.println();
            System.out.println("Appointment added");
        }
        else{
            System.out.println("Invalid Appointment Details");
        }
    }

    /**
     *method removes an appointment from the schedule
     *@param input String of patient and appointment information
     */
    public void cancelAppointment(String[] input){
        Appointment appointment = makeAppointment(input);
        if(appointment!=null){
            this.schedule.remove(appointment);
            System.out.println("Appointment Cancelled");
        }
        else{
            System.out.println(appointment.toString());

            System.out.println("Not Cancelled, appointment does not exist");
        }
    }

    /**
     *This method cancels all appointments of a given patient
     *@param input String of patient information
     */
    public void cancelPatient(String[] input){
        String[] tokens = input;

        if(tokens.length != 4){
            System.out.println("Invalid Patient Details");
            return;
        }
        Date dob = new Date(tokens[1]);
        if(!dob.isValid() && !isDOBValid(dob)){
            System.out.println("Invalid Patient DOB");
            return;
        }
        String fname = tokens[2];
        String lname = tokens[3];
        Patient p = new Patient(fname, lname, dob);
        Appointment[] apps = this.schedule.getAppointments();
        for(int i = apps.length - 1; i >= 0; i-- ){
            if( apps[i] != null && apps[i].getPatient().compareTo(p) == 0){
                this.schedule.remove(apps[i]);
            }
        }
        System.out.println("All Patient Appointments Deleted");
    }

    /**
     *method prints all appointments to console as they exist in the array
     */
    public void printAppointments(){
        schedule.print();
    }

    /**
     *method prints all appointments to console ordered by zip code
     */
    public void printZipAppointments(){
        schedule.printByZip();
    }

    /**
     *method prints all appointments to console ordered by patient information
     */
    public void printPatientCommand(){
        schedule.printByPatient();
    }

    /**
     *private helper method to create appointment object from a string input
     *@param input appointment information
     *@return Appointment
     */
    private Appointment makeAppointment(String[] input){
        String[] tokens = input;
        if(tokens.length == 7){
            Date dob = new Date(tokens[1]);
            if(!dob.isValid() || !isDOBValid(dob)){
                System.out.println("DOB invalid");
                return null;
            }
            String fname = tokens[2];
            String lname = tokens[3];
            Patient p = new Patient(fname, lname, dob);
            Date date = new Date(tokens[4]);
            if(!isApptDateValid(date)){
                System.out.println("Invalid Date");
                return null;
            }
            Time time =  new Time(tokens[5]);
            Timeslot slot = new Timeslot(time, date);
            Location location = loc(tokens[6]);
            if (slot.isValid() && location != null){
                return new Appointment(p, slot, location);
            }
            else{
                return null;
            }
        }
        else{
            System.out.println("Appoint details invalid: Incorrect number of Arguments");
            return null;
        }
    }

    /**
     *private helper method to create location object
     *@param county String of county name
     *@return src.Location
     */
    private Location loc(String county){
        String c = county.toUpperCase();
        switch (c){
            case "MERCER": return Location.valueOf("MERCER");
            case "SOMERSET": return Location.valueOf("SOMERSET");
            case "MIDDLESEX": return Location.valueOf("MIDDLESEX");
            case "UNION": return Location.valueOf("UNION");
            case "MORRIS": return Location.valueOf("MORRIS");
            default: return null;
        }

    }




}
