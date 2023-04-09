package bankgui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;

/**
 * This class is the controller that handles inputs for the GUI and calls the methods required to run the functionality on the backend
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class Controller {
    //Open/close tab
    @FXML
    private DatePicker DOB;

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private ToggleGroup opening;

    @FXML
    private ToggleGroup accountType;

    @FXML
    private ToggleGroup campus;

    @FXML
    private RadioButton campusCamden;

    @FXML
    private RadioButton campusNB;

    @FXML
    private RadioButton campusNewark;

    @FXML
    private CheckBox loyalty;

    @FXML
    private RadioButton ccButton;

    @FXML
    private RadioButton checkingButton;

    @FXML
    private RadioButton mmButton;

    @FXML
    private RadioButton savingsButton;

    @FXML
    private TextArea output;

    @FXML
    private TextField initialDeposit;

    //deposit/withdraw tab
    @FXML
    private ToggleGroup depositAccountType;

    @FXML
    private DatePicker depositDOB;

    @FXML
    private TextField depositFirstName;

    @FXML
    private TextField depositLastName;

    @FXML
    private TextField depositAmount;

    private AccountDatabase database = new AccountDatabase();

    /**
     * This methods runs when the College Checking radio button is checked to ensure that the campus options are open
     * @param event
     */
    @FXML
    void clickCCButton(ActionEvent event) {
        campusNB.setDisable(false);
        campusCamden.setDisable(false);
        campusNewark.setDisable(false);
    }

    /**
     * This methods runs when the Checking radio button is checked to ensure that the campus options are not available and to disable the loyalty button
     * @param event
     */
    @FXML
    void clickCheckingButton(ActionEvent event) {
        campusNB.setDisable(true);
        campusCamden.setDisable(true);
        campusNewark.setDisable(true);
        loyalty.setDisable(true);
    }

    /**
     * This methods runs when the Money Market radio button is checked to ensure that the campus options and loyalty option is closed
     * @param event
     */
    @FXML
    void clickMMButton(ActionEvent event) {
        campusNB.setDisable(true);
        campusCamden.setDisable(true);
        campusNewark.setDisable(true);
        loyalty.setDisable(true);
    }

    /**
     * This methods runs when the Savings radio button is checked to ensure that the campus options are closed and the loyalty option is selectable
     * @param event
     */
    @FXML
    void clickSavingsButton(ActionEvent event) {
        campusNB.setDisable(true);
        campusCamden.setDisable(true);
        campusNewark.setDisable(true);
        loyalty.setDisable(false);
    }
    /**
     * This methods runs when the "Yes" option is checked to open the initial deposit text box
     * @param event
     */
    @FXML
    void enableInitialDeposit(ActionEvent event) {
        initialDeposit.setDisable(false);
    }
    /**
     * This methods runs when the "No" option is checked to close the initial deposit text box
     * @param event
     */
    @FXML
    void disableInitialDeposit(ActionEvent event) {
        initialDeposit.setDisable(true);
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
     * Runs when the open button is selected, taking in the data entered and creating a new account and checking if the correct data has been entered.
     * @param event
     */
    @FXML
    void open(ActionEvent event) {
        try{
            Date dob = new Date(DOB.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            if (!dob.isValid() || !isDOBValid(dob)) {
                output.setText("Invalid dob");
                return;
            }

            if(fName.getText().isBlank()||lName.getText().isBlank()){
                output.setText("Missing Profile information");
                return;
            }
            Profile newProfile = new Profile(fName.getText(), lName.getText(), dob);
            double balance = Double.parseDouble(initialDeposit.getText());
            if (balance <= 0) {
                output.setText("Balance cannot be less than or equal to 0");
                return;
            }
            Account newAcc = null;
            RadioButton button = (RadioButton) accountType.getSelectedToggle();
            switch(button.getText()){
                case "Money Market":
                    if(balance < 2500){
                        output.setText("Minimum of $2500 to open a MoneyMarket account.");
                        return;
                    }
                    newAcc = new MoneyMarket(newProfile, balance);
                    break;
                case "Savings":
                    newAcc = new Savings(newProfile, balance, loyalty.isSelected());
                    break;
                case "College Checking":
                    int campusCode;
                    RadioButton campusButton = (RadioButton) campus.getSelectedToggle();
                    switch(campusButton.getText()){
                        case "NB":
                            campusCode = 0;
                            newAcc = new CollegeChecking(newProfile, balance, campusCode);
                            break;
                        case "Newark":
                            campusCode = 1;
                            newAcc = new CollegeChecking(newProfile, balance, campusCode);
                            break;
                        case "Camden":
                            campusCode = 2;
                            newAcc = new CollegeChecking(newProfile, balance, campusCode);
                            break;
                    }
                    break;
                case "Checking":
                    newAcc = new Checking(newProfile, balance);
                    break;
            }
            if(database.open(newAcc)){
                output.setText("Account added");
            }
            else{
                output.setText("Account already exists");
            }
        }
        catch(NumberFormatException e){
            output.setText("Invalid data");
        }
        catch(NullPointerException e){
            output.setText("Missing data");
        }


    }

    /**
     * creates a temporary Account by putting together the parameters entered into the new Account values
     * @param dob
     * @param fname
     * @param lname
     * @param account
     * @return
     */
    private Account makeTempAcc(DatePicker dob, TextField fname, TextField lname, String account){
        Date birthDate = new Date(dob.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        if (!birthDate.isValid() || !isDOBValid(birthDate)) {
            output.setText("Invalid dob");
            return null;
        }
        Profile newProfile = new Profile(fname.getText(), lname.getText(), birthDate);
        double balance = 1.0;
        Account newAcc = null;
        switch(account){
            case "Money Market":
                newAcc = new MoneyMarket(newProfile, balance);
                break;
            case "Savings":
                newAcc = new Savings(newProfile, balance, true);
                break;
            case "College Checking":
                newAcc = new CollegeChecking(newProfile, balance, 0);
                break;
            case "Checking":
                newAcc = new Checking(newProfile, balance);
                break;
        }
        return newAcc;

    }

    /**
     * Called when the "Close" button is selected, causing the account whose information is entered to be closed. Also checks if it a valid account
     * @param event
     */
    @FXML
    void close(ActionEvent event) {
        try{
            if(fName.getText().isBlank()||lName.getText().isBlank()){
                output.setText("Missing Profile information");
                return;
            }
            RadioButton button = (RadioButton) accountType.getSelectedToggle();
            String account = button.getText();
            Account temp = makeTempAcc(DOB, fName, lName, account);
            if(temp == null){
                output.setText("Invalid details");
            }
            if(!database.contains(temp)){
                output.setText("Account does not exist");
            }
            else{
                database.close(temp);
                output.setText("Account closed");
            }
        }
        catch(NumberFormatException e){
            output.setText("Invalid data");
        }
        catch(NullPointerException e){
            output.setText("Missing data");
        }

    }

    /**
     * Called when the "Deposit" button is selected, checking if the Account details entered are valid, then if so depositing the money in
     * @param event
     */
    @FXML
    void deposit(ActionEvent event) {
        try{
            if(depositFirstName.getText().isBlank()||depositLastName.getText().isBlank()){
                output.setText("Missing Profile information");
                return;
            }
            RadioButton button = (RadioButton) depositAccountType.getSelectedToggle();
            String account = button.getText();
            Account temp =  makeTempAcc(depositDOB, depositFirstName, depositLastName, account);
            if (temp == null){
                output.setText("Invalid DOB");
                return;
            }
            double amount = Double.parseDouble(depositAmount.getText());
            if (amount <= 0) {
                output.setText("Cannot deposit less than 0");
                return;
            }
            amount -= 1.00;
            temp.deposit(amount);
            if (!database.contains(temp)) {
                output.setText("Account does not exist");
            } else {
                database.deposit(temp);
                output.setText("Deposit Successful");

            }
        }
        catch(NumberFormatException e){
            output.setText("Invalid data");
        }
        catch(NullPointerException e){
            output.setText("Missing data");
        }
    }

    /**
     * Called when the "Withdraw" button is selected, checking if the Account details entered are valid, then if so withdrawing the money
     * @param event
     */
    @FXML
    void withdraw(ActionEvent event) {
        try{
            if(depositFirstName.getText().isBlank()||depositLastName.getText().isBlank()){
                output.setText("Missing Profile information");
                return;
            }
            RadioButton button = (RadioButton) depositAccountType.getSelectedToggle();
            String account = button.getText();
            Account temp =  makeTempAcc(depositDOB, depositFirstName, depositLastName, account);
            if (temp == null){
                output.setText("Invalid DOB");
                return;
            }
            double amount = Double.parseDouble(depositAmount.getText());
            if (amount <= 0) {
                output.setText("Cannot withdraw less than 0");
                return;
            }
            amount -= 1.00;
            temp.deposit(amount);
            if (!database.contains(temp)) {
                output.setText("Account does not exist");
            } else {
                if (database.withdraw(temp)) {
                    output.setText("Withdrawal Successful");
                } else {
                    output.setText("Withdrawal amount greater than available");
                }
            }
        }
        catch(NumberFormatException e){
            output.setText("Invalid data");
        }
        catch(NullPointerException e){
            output.setText("Missing data");
        }

    }

    /**
     * Called when the print button is entered, printing all accounts
     * @param event
     */
    @FXML
    void printAll(ActionEvent event){
        output.setText(database.print());
    }

    /**
     * Sorts the accounts list and then prints them when the Prin by account type button is selected
     * @param event
     */
    @FXML
    void printSorted(ActionEvent event){
        output.setText(database.printByAccountType() );
    }

    /**
     * Calculates the fees and interest and prints them for each account when the Calculate fees and interest button is selected
     * @param event
     */
    @FXML
    void printInterest(ActionEvent event){
        output.setText(database.printFeeAndInterest());
    }

    /**
     * Updates the values of the account by applying the fees and interests when the Apply Fees and Interest button is selected
     * @param event
     */
    @FXML
    void printUpdated(ActionEvent event){
        database.updateDatabase();
        output.setText(database.print());
    }


}