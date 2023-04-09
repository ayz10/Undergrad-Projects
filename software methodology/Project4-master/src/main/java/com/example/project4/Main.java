package com.example.project4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the main class that contains the code for the main-view and starts the GUI
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class Main extends Application {
    private static StoreOrders storeOrders;
    private static Order order;
    private static int cofID = 1;
    private static int donID = 1;
    private static int orderID = 1;

    /**
     * Creates main view and sets up the environment
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        stage.setTitle("Rutgers Cafe");
        stage.setScene(scene);
        stage.show();

        Main.order = new Order();

        Order[] allOrders = new Order[4];
        //allOrders[0] = order;
        int numOrders = 0;
        Main.storeOrders = new StoreOrders(allOrders, numOrders);
    }

    /**
     * returns the current coffeeID counter
     * @return
     */
    public static int getCofID() {
        return Main.cofID;
    }

    /**
     * sets the current coffeeID counter
     * @param cofID
     */
    public static void setCofID(int cofID) {
        Main.cofID = cofID;
    }

    /**
     * gets the current donutID counter
     * @return
     */
    public static int getDonID() {
        return donID;
    }

    /**
     * sets the current donutID counter
     * @param cofID
     */
    public static void setDonID(int cofID) {
        Main.donID = cofID;
    }

    /**
     * gets the current OrderID counter
     * @return
     */
    public static int getOrderID(){
        return Main.orderID;
    }

    /**
     * sets the current OrderID counter
     * @param orderID
     */
    public static void setOrderId(int orderID){
        Main.orderID = orderID;
    }


    /**
     * adds to the current order
     * @param item
     */
    public void addToOrder(MenuItem item){ order.add(item);
    }

    /**
     * removes a menuitem from the current order
     * @param item
     */
    public void removeFromOrder(MenuItem item){
        order.remove(item);
    }

    /**
     * returns the StoreOrders variable held
     * @return
     */
    public static StoreOrders getStoreOrders(){
        return Main.storeOrders;
    }

    /**
     * set the Store Orders variable to a new StoreOrders object
     * @param storeOrders
     */
    public static void setStoreOrders(StoreOrders storeOrders){
        Main.storeOrders = storeOrders;
    }

    /**
     * gets the current order object
     * @return
     */
    public static Order getOrder(){
        return Main.order;
    }

    /**
     * sets the current order object
     * @param order
     */
    public static void setOrder(Order order){
        Main.order = order;
    }


    /**
     * runs the application
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}