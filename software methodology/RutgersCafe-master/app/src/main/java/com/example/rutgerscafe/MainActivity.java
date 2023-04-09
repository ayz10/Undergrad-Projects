package com.example.rutgerscafe;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
//    private ImageButton orderDonutButton;
    private static StoreOrders storeOrders;
    private static Order order;
    private static int cofID = 1;
    private static int donID = 1;
    private static int orderID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.order = new Order();

        Order[] allOrders = new Order[4];
        //allOrders[0] = order;
        int numOrders = 0;
        MainActivity.storeOrders = new StoreOrders(allOrders, numOrders);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickOrderDonutButton(View view){
        Intent intent = new Intent(this, orderDonutActivity.class);
        startActivity(intent);
    }

    public void clickOrderCoffeeButton(View view){
        Intent intent = new Intent(this, orderCoffeeActivity.class);
        startActivity(intent);
    }

    public void clickStoreOrderButton(View view){
        Intent intent = new Intent(this, storeOrderActivity.class);
        startActivity(intent);
    }

    public void clickBasketButton(View view){
        Intent intent = new Intent(this, basketActivity.class);
        startActivity(intent);
    }

    /**
     * returns the current coffeeID counter
     * @return
     */
    public static int getCofID() {
        return MainActivity.cofID;
    }

    /**
     * sets the current coffeeID counter
     * @param cofID
     */
    public static void setCofID(int cofID) {
        MainActivity.cofID = cofID;
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
        MainActivity.donID = cofID;
    }

    /**
     * gets the current OrderID counter
     * @return
     */
    public static int getOrderID(){
        return MainActivity.orderID;
    }

    /**
     * sets the current OrderID counter
     * @param orderID
     */
    public static void setOrderId(int orderID){
        MainActivity.orderID = orderID;
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
        return MainActivity.storeOrders;
    }

    /**
     * set the Store Orders variable to a new StoreOrders object
     * @param storeOrders
     */
    public static void setStoreOrders(StoreOrders storeOrders){
        MainActivity.storeOrders = storeOrders;
    }

    /**
     * gets the current order object
     * @return
     */
    public static Order getOrder(){
        return MainActivity.order;
    }

    /**
     * sets the current order object
     * @param order
     */
    public static void setOrder(Order order){
        MainActivity.order = order;
    }

}