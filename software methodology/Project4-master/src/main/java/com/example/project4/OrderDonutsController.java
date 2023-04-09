package com.example.project4;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * This class is the controller for the orderDonut view and contains all the code to respond to user events
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class OrderDonutsController implements Initializable {
    @FXML
    private Button addDonutButton;

    @FXML
    private Button addToBasketButton;

    @FXML
    private ComboBox<String> donutType;

    @FXML
    private ListView<String> flavorsAvailable;

    @FXML
    private ListView<String> flavorsSelected;


    @FXML
    private ComboBox<Integer> quantity;

    @FXML
    private Button removeDonutButton;

    @FXML
    private TextField subTotal;

    @FXML
    private TextArea errorMessage;

    private ArrayList<String> selectedflavors = new ArrayList<>();
    ArrayList<String> yeastFlavors = new ArrayList<String>();
    ArrayList<String> cakeFlavors = new ArrayList<String>();
    ArrayList<String> holeFlavors = new ArrayList<String>();

    /**
     * intializes the GUI on startup
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        donutType.getItems().removeAll(donutType.getItems());
        donutType.getItems().addAll("Yeast", "Cake", "Hole");
        quantity.getItems().removeAll(quantity.getItems());
        quantity.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9);
        flavorsAvailable.getItems().removeAll(flavorsAvailable.getItems());
        flavorsSelected.getItems().removeAll(flavorsAvailable.getItems());
        yeastFlavors.add("Boston Creme");
        yeastFlavors.add("Pumpkin");
        yeastFlavors.add("Blueberry");
        cakeFlavors.add("Birthday Cake");
        cakeFlavors.add("Jelly");
        cakeFlavors.add("Glazed");
        holeFlavors.add("Strawberry");
        holeFlavors.add("Chocolate");
        holeFlavors.add("Vanilla");
        donutType.getSelectionModel().selectFirst();
        quantity.getSelectionModel().selectFirst();
        flavorsAvailable.getItems().addAll(yeastFlavors);
        flavorsAvailable.getSelectionModel().selectFirst();
    }

    /**
     * adds the donuts to the right hand side
     * @param event
     */
    @FXML
    void addDonut(ActionEvent event) {
        errorMessage.clear();
        try{
            int quantityValue = quantity.getValue();
            String flavor = flavorsAvailable.getSelectionModel().getSelectedItem();
            String type = donutType.getSelectionModel().getSelectedItem();
            if(flavor.isBlank() || type.isBlank()){
                return;
            }
            flavorsSelected.getItems().add(type + "/" + flavor + "/" + quantityValue);
            selectedflavors.add(flavor);
            flavorsAvailable.getItems().remove(flavor);
            DecimalFormat df = new DecimalFormat("###.##");
            subTotal.setText("$"+ df.format(getSubtotal()));
        }
        catch(NullPointerException e){
            errorMessage.setText("Missing Information");
        }

    }

    /**
     * takes the selected donuts to the left hand side
     * @param event
     */
    @FXML
    void removeDonut(ActionEvent event) {
        errorMessage.clear();
        try{String donut = flavorsSelected.getSelectionModel().getSelectedItem();
            flavorsSelected.getItems().remove(donut);
            String[] values = donut.split("/");
            selectedflavors.remove(values[1]);

            if(values[0].equals(donutType.getValue()) && !flavorsAvailable.getItems().contains(values[0])){
                flavorsAvailable.getItems().add(values[1]);
            }
            flavorsAvailable.getSelectionModel().selectFirst();
            DecimalFormat df = new DecimalFormat("###.##");
            subTotal.setText("$"+ df.format(getSubtotal()));

        }
        catch(NullPointerException e){
            errorMessage.setText("*Cannot Remove - Missing Information*");
        }


    }

    /**
     * adds the donuts on the righthand side to the current order
     * @param event
     */
    @FXML
    void addToBasket(ActionEvent event) {
        errorMessage.clear();
        try{
            Order tempOrder = Main.getOrder();
            ObservableList<String> donutsSelected = flavorsSelected.getItems();
            int i = 0;
            for(String s: donutsSelected){
                String[] fields = s.split("/");
                String type = fields[0];
                String flavor = fields[1];
                int quantity = Integer.parseInt(fields[2]);
                Donut temp = new Donut(type);
                Donut temp1 = new Donut("donut", temp.itemPrice(),type, flavor, Main.getDonID());
                for(int j = 0; j < quantity; j++){
                    tempOrder.add(temp1);
                }
                Main.setOrder(tempOrder);
                Main.setDonID(Main.getDonID()+1);
                i++;
            }
            flavorsSelected.getItems().removeAll(flavorsSelected.getItems());
            displayFlavors();
        }
        catch (NullPointerException e){
            errorMessage.setText("*Cannot Add - Missing Information*");
        }
        catch (IndexOutOfBoundsException e){
            errorMessage.setText("*Input Mismatch");
        }
    }

    /**
     * shows the flavors available when the donut type is selcted
     * @param event
     */
    @FXML
    void displayFlavors(ActionEvent event) {
        flavorsAvailable.getItems().removeAll(flavorsAvailable.getItems());
        String type = donutType.getValue();
        switch(type){
            case "Yeast":
                int i = 0;
                for(String s: yeastFlavors){
                    if(!selectedflavors.contains(yeastFlavors.get(i))){
                        flavorsAvailable.getItems().add(s);
                    }
                    i++;
                }
                break;
            case "Cake":
                int j = 0;
                for(String s: cakeFlavors){
                    if(!selectedflavors.contains(cakeFlavors.get(j))){
                        flavorsAvailable.getItems().add(s);
                    }
                    j++;
                }
                break;
            case "Hole":
                int k = 0;
                for(String s: holeFlavors){
                    if(!selectedflavors.contains(holeFlavors.get(k))){
                        flavorsAvailable.getItems().add(s);
                    }
                    k++;
                }
                break;
        }
        flavorsAvailable.getSelectionModel().selectFirst();
    }

    /**
     * shows the flavors available when the donut type is selcted
     */
    private void displayFlavors(){
        flavorsAvailable.getItems().removeAll(flavorsAvailable.getItems());
        String type = donutType.getValue();
        switch(type){
            case "Yeast":
                flavorsAvailable.getItems().addAll(yeastFlavors);
                break;
            case "Cake":
                flavorsAvailable.getItems().addAll(cakeFlavors);
                break;
            case "Hole":
                flavorsAvailable.getItems().addAll(holeFlavors);
                break;
        }
        flavorsAvailable.getSelectionModel().selectFirst();
    }

    /**
     * determines the subtotal for these items
     * @return
     */
    private double getSubtotal(){
        ObservableList<String> selected = flavorsSelected.getItems();
        int i = 0;
        double subTotal = 0;
        for(String s: selected){
            String[]values = selected.get(i).split("/");
            String type = values[0];
//            String flavor = values[1];
            int quantity = Integer.parseInt(values[2]);
            Donut temp = new Donut(type);
            subTotal = subTotal + (quantity*temp.itemPrice());
            i++;
        }
        return subTotal;
    }


}
