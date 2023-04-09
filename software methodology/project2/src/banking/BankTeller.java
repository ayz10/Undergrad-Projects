package src.banking;
import java.util.Scanner;


/**
 * This class is the user interface to process banking transaction commands
 * entered through the console and outputs the results to the console
 * @author Vineel Reddy
 * @author Alexander Zhao
 */

public class BankTeller{
    private AccountDatabase database;
    /**
     * run method to interpret transaction codes
     */
    public void run(){
        int LISTS_INIT_SIZE = 4;
        Account[] accList = new Account[LISTS_INIT_SIZE];
        for(Account acc: accList){acc = null;}
        database = new AccountDatabase(accList, 0);
        System.out.println("Bank Teller is running.");
        boolean runner = true;
        Scanner scanner = new Scanner(System.in);
        while(runner){
            while(runner & scanner.hasNext()){
                String input = scanner.nextLine();
                String[] tokens = input.split("\\s+");
                switch (tokens[0]){
                    case "Q" : runner = false;
                        break;
                    case "O" : openAccount(tokens);
                        break;
                    case "C" : closeAcc(tokens);
                        break;
                    case "D" : deposit(tokens);
                        break;
                    case "W" : withdraw(tokens);
                        break;
                    case "P" : database.print();
                        break;
                    case "PT" : database.printByAccountType();
                        break;
                    case "PI" : database.printFeeAndInterest();
                        break;
                    case "UB" :
                        database.updateDatabase();
                        database.print();
                        break;
                    default : System.out.println("Invalid Command");
                        break;
                }
            }
        }
        scanner.close();
    }

    /**
     * private helper method to check if date of birth is valid
     * @param dob Date
     * @return boolean
     */
    private boolean isDOBValid(Date dob){
        Date today = new Date();
        return today.compareTo(dob) != 0 && today.compareTo(dob) >= 0;
    }

    /**
     * This method opens an account to the account database
     * @param input Profile and account information
     */
    private void openAccount(String[] input){
        try {
            Date dob = new Date(input[4]);
            if (!dob.isValid() || !isDOBValid(dob)) {
                System.out.println("Invalid dob");
                return;
            }
            Profile newProfile = new Profile(input[2], input[3], dob);
            double balance = Double.parseDouble(input[5]);
            if (balance <= 0) {
                System.out.println("Balance cannot be less than or equal to 0");
                return;
            }
            Account newAcc;
            switch (input[1]) {
                case "MM":
                    newAcc = new MoneyMarket(newProfile, balance);
                    break;
                case "S":
                    newAcc = new Savings(newProfile, balance, Integer.parseInt(input[6]) == 1);
                    break;
                case "CC":
                    if(Integer.parseInt(input[6]) >= 0 && Integer.parseInt(input[6]) <= 2){
                        newAcc = new CollegeChecking(newProfile, balance, Integer.parseInt(input[6]));
                        break;
                    }
                    else{
                        System.out.println("Invalid campus code");
                        return;
                    }
                case "C":
                    newAcc = new Checking(newProfile, balance);
                    break;
                default:
                    System.out.println("Invalid input for opening an account");
                    return;
            }
            if (database.open(newAcc)) {
                System.out.println("Account added");
            } else {
                System.out.println("Account already exists");
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Missing Data for operation");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid Data for operation");
        }
    }

    /**
     * private helper method that creates temporary account object to store account information
     * @param input Profile and account information
     * @return Account object
     */
    private Account makeTempAcc(String[] input){
        try {
            Date dob = new Date(input[4]);
            if (!dob.isValid() || !isDOBValid(dob)) {
                System.out.println("Invalid dob");
                return null;
            }
            Profile newProfile = new Profile(input[2], input[3], dob);
            double balance = 1.0;
            Account newAcc;
            switch (input[1]) {
                case "MM":
                    newAcc = new MoneyMarket(newProfile, balance);
                    break;
                case "S":
                    newAcc = new Savings(newProfile, balance, true);
                    break;
                case "CC":
                    newAcc = new CollegeChecking(newProfile, balance, 0);
                    break;
                case "C":
                    newAcc = new Checking(newProfile, balance);
                    break;
                default:
                    System.out.println("Invalid input for operation");
                    return null;
            }
            return newAcc;
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Missing Data for operation");
        }
        catch(NumberFormatException e){
            System.out.println("Invalid Data for operation");
        }
        return null;
    }

    /**
     * This method deposits an amount into an account balance
     * @param input Profile and account information
     */
    private void deposit(String[] input){
        try {
            Account temp = makeTempAcc(input);
            if (temp == null) {
                System.out.println("Invalid details");
                return;
            }
            double amount = Double.parseDouble(input[5]);
            if (amount < 0.00) {
                System.out.println("Deposit amount must be greater than 0");
                return;
            }
            amount -= 1.00;
            temp.deposit(amount);
            if (!database.contains(temp)) {
                System.out.println("Account does not exist");
            } else {
                database.deposit(temp);
                System.out.println("Deposit Successful");

            }
        }
        catch(NumberFormatException e){
            System.out.println("Invalid Data for operation");
        }
    }

    /**
     * This method withdraws an amount from an account balance
     * @param input Profile and account information
     */
    private void withdraw(String[] input){
        try {
            Account temp = makeTempAcc(input);
            if (temp == null) {
                System.out.println("Invalid details");
                return;
            }
            double amount = Double.parseDouble(input[5]);
            if (amount < 0.00) {
                System.out.println("Withdrawal amount must be greater than 0");
                return;
            }
            amount -= 1.00;
            temp.deposit(amount);
            if (!database.contains(temp)) {
                System.out.println("Account does not exist");
            } else {
                if (database.withdraw(temp)) {
                    System.out.println("Withdrawal Successful");
                } else {
                    System.out.println("Withdrawal amount greater than available");
                }
            }
        }
        catch(NumberFormatException e){
            System.out.println("Invalid Data for operation");
        }
    }

    /**
     * Set account to closed
     * @param input Profile and account information
     */
    private void closeAcc(String[] input){
        Account temp = makeTempAcc(input);
        if(temp == null){
            System.out.println("Invalid details");
            return;
        }
        if(!database.contains(temp)){
            System.out.println("Account does not exist");
        }
        else{
            database.close(temp);
            System.out.println("Account Close");
        }
    }

}