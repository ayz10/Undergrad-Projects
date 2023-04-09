package com.example.project4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * This class is the controller for the orderCoffee view and contains all the code to respond to user events
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class OrderCoffeeController implements Initializable {



    @FXML
    private CheckBox caramel;

    @FXML
    private CheckBox cream;

    @FXML
    private CheckBox milk;

    @FXML
    private Button submit;

    @FXML
    private TextField subtotal;

    @FXML
    private CheckBox syrup;

    @FXML
    private CheckBox whipped;

    @FXML
    private ComboBox<String> amount;

    @FXML
    private ComboBox<String> size;

    private double price = 0;
    ObservableList<String> sizes = FXCollections.observableArrayList("Short", "Tall", "Grande", "Venti");
    ObservableList<String> amounts = FXCollections.observableArrayList("1", "2", "3", "4");

    /**
     * initalizes the GUI on startup
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        size.setItems(sizes);
        amount.setItems(amounts);
        size.getSelectionModel().select(0);
        amount.getSelectionModel().select(0);

    }

    /**
     * returns the size of the addins
     * @return
     */
    private int getAddins(){
        int addins = 0;
        if (cream.isSelected()) addins += 1;
        if (milk.isSelected()) addins += 1;
        if (syrup.isSelected()) addins += 1;
        if (whipped.isSelected()) addins += 1;
        if (caramel.isSelected()) addins += 1;
        return addins;
    }


    /**
     * calculates the price of the coffee depending on the information entered
     * @param event
     */
    public void calculatePrice(ActionEvent event){
        this.price = 0;
        double addins = 0;
        final double ADDIN_PRICE = 0.30;
        final double SHORT_PRICE = 1.69;
        final double SIZE_INCREASE = 0.40;
        addins = getAddins();
        this.price = addins * ADDIN_PRICE;
        String selected = size.getSelectionModel().getSelectedItem();
        switch(selected){
            case "Short":
                this.price += SHORT_PRICE;
                break;
            case "Tall":
                this.price += SHORT_PRICE + SIZE_INCREASE;
                break;
            case "Grande":
                this.price += SHORT_PRICE + (SIZE_INCREASE * 2);
                break;
            case "Venti":
                this.price += SHORT_PRICE + (SIZE_INCREASE * 3);
                break;
            default:
                break;
        }
        String quant = amount.getSelectionModel().getSelectedItem();
        switch(quant){
            case "1":
                this.price *= 1;
                break;
            case "2":
                this.price *= 2;
                break;
            case "3":
                this.price *= 3;
                break;
            case "4":
                this.price *= 4;
                break;
            default:
                break;
        }
        NumberFormat format = new DecimalFormat("#0.00");
        subtotal.setText("" + format.format(this.price));
    }

    /**
     * adds the coffee to the current order
     * @param event
     */
    public void submit(ActionEvent event){
        String selected = size.getSelectionModel().getSelectedItem();
        int cupSize = -1;
        switch(selected){
            case "Short":
                cupSize = 0;
                break;
            case "Tall":
                cupSize = 1;
                break;
            case "Grande":
                cupSize = 2;
                break;
            case "Venti":
                cupSize = 3;
                break;
            default:
                break;
        }
        String[] add = new String[getAddins()];
        int x = 0;
        if (cream.isSelected()) {add[x] = "Cream"; x++;}
        if (milk.isSelected()) {add[x] = "Milk"; x++;}
        if (syrup.isSelected()) {add[x] = "Syrup"; x++;}
        if (whipped.isSelected()) {add[x] = "Whipped-Cream"; x++;}
        if (caramel.isSelected()) {add[x] = "Caramel"; x++;}
        Coffee temp = new Coffee("coffee", this.price, cupSize, add, Main.getCofID());
        Main.setCofID(Main.getCofID() + 1);
        Order tempOrder = Main.getOrder();
        tempOrder.add(temp);
        Main.setOrder(tempOrder);
    }
}
