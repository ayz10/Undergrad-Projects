package com.example.project4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * This class is the controller for the storeOrder view and has all the code to respond to user events
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class StoreOrdersController implements Initializable {

    @FXML
    private Button cancelOrderButton;

    @FXML
    private Button exportButton;

    @FXML
    private ListView<String> storeOrdersOutput;

    @FXML
    private TextArea errorMessage;

//    @FXML
//    private TextArea storeOrdersOutputTest;

    @FXML
    private Button showOrdersButton;


    /**
     * Cancels the selected order when the cancel order button is pressed
     * @param event
     */
    @FXML
    void cancelOrder(ActionEvent event) {
        errorMessage.clear();
        try{
            String toDelete = storeOrdersOutput.getSelectionModel().getSelectedItem();
            String[] fields = toDelete.split("\\|");
            String[] first = fields[0].split(":");
            String second = first[1].trim();
            String orderid = second.split("\\n")[0];
            int id = Integer.parseInt(orderid);
            Order temp = new Order(id);
            StoreOrders orders = Main.getStoreOrders();
            orders.remove(temp);
            Main.setStoreOrders(orders);
            refreshList();
        }
        catch(NullPointerException e){
            errorMessage.setText("No Orders Yet!");
        }

    }

    /**
     * creates a text file containing the store orders called "OrdersPrinted.txt."
     * @param event
     * @throws FileNotFoundException
     */
    @FXML
    void exportToText(ActionEvent event) throws FileNotFoundException {
        errorMessage.clear();
        if(storeOrdersOutput.getItems().isEmpty()){
            errorMessage.setText("No Orders Yet!");
            return;
        }
        File file = new File("OrdersPrinted");
        if(file.exists()) {}
        PrintWriter pw = new PrintWriter(file);
        pw.print(Main.getStoreOrders().storeOrderToString());
        pw.close();
    }

    /**
     * refreshes the displayed orders
     */
    private void refreshList(){
        if(!storeOrdersOutput.getItems().isEmpty()){
            storeOrdersOutput.getItems().removeAll(storeOrdersOutput.getItems());
        }
        StoreOrders newSO = Main.getStoreOrders();
        Order[] allOrders = newSO.getAllOrders();
        if(allOrders!=null){
            for(int i=0; i<allOrders.length;i++){
                if(allOrders[i]==null){
                    continue;
                }
                String order = allOrders[i].printOrder();
                double total = allOrders[i].getTotalCost();
                DecimalFormat df = new DecimalFormat("###.##");
                storeOrdersOutput.getItems().add("OrderID: "+ allOrders[i].getOrderID() +"\n"+order
                    +"Total Amount: $"+df.format(total));
            }
        }
        else{
            storeOrdersOutput.getItems().add("*No Orders Yet*");
        }
    }

    /**
     * initializes the GUI on startup
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshList();
    }
}
