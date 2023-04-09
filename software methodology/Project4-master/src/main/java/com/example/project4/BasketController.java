package com.example.project4;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 *This class is the controller for the basket-view
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class BasketController implements Initializable {

    @FXML
    private ListView<String > basketOutput;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Button removeItemButton;

    @FXML
    private TextField salesTax;

//    @FXML
//    private Button showBasketButton;

    @FXML
    private TextField subTotal;

    @FXML
    private TextField totalPrice;

    /***
     * Remove an item from the basket when the remove item button is clicked
     * @param event
     */
    @FXML
    private TextArea errorMessage;

    @FXML
    void removeItem(ActionEvent event) {
        errorMessage.clear();
        try{
            String deleteOrder = basketOutput.getSelectionModel().getSelectedItem();
            String[] fields = deleteOrder.split("\\|");
            String[] idFields = fields[0].split(":");
            String id = idFields[1];
            int productID = Integer.parseInt(id.trim());
            String[] nameFields = fields[1].split(",");
            String itemName = nameFields[0].trim();
            if(itemName.equals("Donut")){
                Donut tempDonut = new Donut(productID);
                Order tempOrder = Main.getOrder();
                if(tempOrder.remove(tempDonut)){
                }
                Main.setOrder(tempOrder);
            }
            else{
                Coffee tempDonut = new Coffee(productID);
                Order tempOrder = Main.getOrder();
                tempOrder.remove(tempDonut);
                Main.setOrder(tempOrder);

            }
            refresh();
            double sub = getSubtotal();
            DecimalFormat df = new DecimalFormat("###.##");
            subTotal.setText("$"+ df.format(sub));
            double tax = getTax(sub);
            salesTax.setText("$"+ df.format(tax));
            double total = sub + tax;
            totalPrice.setText("$"+ df.format(total));
        }
        catch(NullPointerException e){
            errorMessage.setText("No Order Selected");
        }

    }

    /***
     * Add the current order to the store order list when place order button is clicked, removing it from the current screen
     * @param event
     */
    @FXML
    void placeOrder(ActionEvent event) {
        errorMessage.clear();
        if(basketOutput.getItems().isEmpty()){
            errorMessage.setText("This Order is Empty!");
            return;
        }
        ObservableList<String> items = basketOutput.getItems();
        MenuItem[] menuItems = new MenuItem[items.size()];
        int[] quantityArray = new int[items.size()];
        int numItems = 0;
        Order order = new Order(menuItems, numItems, quantityArray, Main.getOrderID());
        for(String s: items) {
            String[] fields = s.split("\\|");
            String[] quantityString = fields[3].split(" ");
            int quantity = Integer.parseInt(quantityString[2]);
            String[] typeFields = fields[1].split(":");
            double price = 0;
            switch (typeFields[0]) {
                case " Coffee, size":
                    String sizeString = typeFields[1].trim();
                    int size = 0;
                    switch(sizeString){
                        case "Short":
                            size = 0;
                            break;
                        case "Tall":
                            size = 1;
                            break;
                        case "Grande":
                            size = 2;
                            break;
                        case "Venti":
                            size = 3;
                            break;
                    }
                    String[] addInFields = fields[2].split(" ");
                    Coffee ex;
                    String[] add = new String[5];

                    if(addInFields.length >= 2){
                        String[] addIns = addInFields[2].trim().split("/");
                        for(int i = 1; i < addIns.length; i++){
                            add[i-1] = addIns[i];
                        }
                    }
                    ex = new Coffee(size, add);

                    price = ex.itemPrice();

                    Coffee tempCof = new Coffee("Coffee", price, size, add, 0);
                    order.getItems()[numItems] = tempCof;
                    order.getQuantity()[numItems] = quantity;
                    numItems++;
                    break;
                case " Donut, Type":
                    String type = typeFields[1].trim();
                    Donut del = new Donut(type);
                    price = del.itemPrice();

                    String[] flavorFields = fields[2].split(":");
                    String flavor = flavorFields[1].trim();

                    Donut tempDon = new Donut("Donut", price, type, flavor, 0);
                    order.getItems()[numItems] = tempDon;
                    order.getQuantity()[numItems] = quantity;
                    numItems++;
                    break;
            }
        }

        double sub = getSubtotal();
        double tax = getTax(sub);
        double total = sub + tax;
        order.setTotalCost(total);
        StoreOrders newSO = Main.getStoreOrders();
        newSO.add(order);
        Main.setStoreOrders(newSO);
        Order temp = Main.getOrder();
        temp.finishOrder();
        Main.setOrder(temp);
//        Main.storeOrders.add(order);
//        Main.order.finishOrder();
        basketOutput.getItems().removeAll(basketOutput.getItems());

        sub = getSubtotal();
        DecimalFormat df = new DecimalFormat("###.##");
        subTotal.setText("$"+ df.format(sub));
        tax = getTax(sub);
        salesTax.setText("$"+ df.format(tax));
        total = sub + tax;
        totalPrice.setText("$"+ df.format(total));
    }

    /**
     *
     * @return The subtotal of the basket
     */
    private double getSubtotal(){
        ObservableList<String> items = basketOutput.getItems();
        int i = 0;
        double subTotal = 0;
        for(String s: items){
            String[]values = items.get(i).split("\\|");
            String[] quantityString = values[3].split(" ");
            int quantity = Integer.parseInt(quantityString[2]);
            String[] typeFields = values[1].split(":");
            String type = "";
            switch(typeFields[1]){
                case " Yeast ":
                    subTotal = subTotal + (quantity * 1.59);
                    continue;
                case " Cake ":
                    subTotal = subTotal + (quantity * 1.79);
                    continue;
                case " Hole":
                    subTotal = subTotal + (quantity * 0.30);
                    continue;
            }
            i++;
        }
        return subTotal;
    }

    /**
     * returns the tax owed
     * @param subTotal
     * @return
     */
    private double getTax(double subTotal){
        double tax = 0.0625;
        return subTotal * tax;
    }

    /**
     * refreshes the displayed items in the current order
     */
    private void refresh(){
        if(!basketOutput.getItems().isEmpty()){
            basketOutput.getItems().removeAll(basketOutput.getItems());
        }
        Order tempOrder = Main.getOrder();
        if(tempOrder.getItems()!=null){
            MenuItem[] items = tempOrder.getItems();
            for(int i=0; i<items.length;i++){
                if(items[i]==null){
                    continue;
                }
                MenuItem temp = (MenuItem) tempOrder.getItems()[i];
                int tempID = 0;
                if(temp instanceof Donut){
                    tempID = ((Donut) temp).getDonutID();
                }
                else if(temp instanceof  Coffee){
                    tempID = ((Coffee) temp).getCoffeeID();
                }
                String item = "ProductID: "+ tempID + " | " + tempOrder.getItems()[i].toString() +
                        " | Quantity: " + tempOrder.getQuantity()[i];
                basketOutput.getItems().add(item);
            }
        }
        else{
            basketOutput.getItems().add("*No Items Yet*");
        }


        double sub = getSubtotal();
        DecimalFormat df = new DecimalFormat("###.##");
        subTotal.setText("$"+df.format(sub));
        double tax = getTax(sub);
        salesTax.setText("$"+ df.format(tax));
        double total = sub + tax;
        totalPrice.setText("$"+ df.format(total));
    }

    /**
     * Initializes the GUI
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
//        Order tempOrder = Main.getOrder();
//        if(tempOrder.getItems()!=null){
//            MenuItem[] items = tempOrder.getItems();
//            for(int i=0; i<items.length;i++){
//                if(items[i]==null){
//                    continue;
//                }
//                Donut temp = (Donut)tempOrder.getItems()[i];
//                int tempID = temp.getDonutID();
//                String item = "ProductID: "+ tempID + " | " + tempOrder.getItems()[i].toString() +
//                        " | Quantity: " + tempOrder.getQuantity()[i];
//                basketOutput.getItems().add(item);
//            }
//        }
//        else{
//            basketOutput.getItems().add("*No Items Yet*");
//        }
//
//
//        double sub = getSubtotal();
//        DecimalFormat df = new DecimalFormat("###.##");
//        subTotal.setText("$"+df.format(sub));
//        double tax = getTax(sub);
//        salesTax.setText("$"+ df.format(tax));
//        double total = sub + tax;
//        totalPrice.setText("$"+ df.format(total));
    }
}
