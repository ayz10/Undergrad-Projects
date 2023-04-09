package com.example.rutgerscafe;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *This class is the controller for the basket activity
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class basketActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    /**
     *Initializes Basket Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        TextView subTotal = findViewById(R.id.subtotal);
        subTotal.setText("Subtotal: ");
        TextView salesTax = findViewById(R.id.salesTax);
        salesTax.setText("Sales Tax: ");
        TextView total = findViewById(R.id.total);
        total.setText   ("Total: ");
    }

    /**
     * Remove item from order when click on item in list
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int toDelete = i;
        MenuItem[] temp = MainActivity.getOrder().getItems();
        MenuItem delete = temp[i];
        MainActivity.getOrder().remove(delete);
        refresh();
    }

    /**
     * Add current order to store orders
     * @param view
     */
    public void placeOrder(View view){
        Order temp = MainActivity.getOrder();
        StoreOrders tempSO = MainActivity.getStoreOrders();
        tempSO.add(temp);
        MainActivity.setStoreOrders(tempSO);
        Order newOrder = new Order();
        MainActivity.setOrderId(MainActivity.getOrderID()+1);
        MainActivity.getOrder().setOrderID(MainActivity.getOrderID());
        MainActivity.setOrder(newOrder);
    }

    /**
     * Refresh button calls refresh method
     * @param view
     */
    public void refresh(View view){
        refresh();
    }

    /**
     * refresh order display and subtotal/sales tax/total
     */
    private void refresh(){
        Order tempOrder = MainActivity.getOrder();
        ArrayList<String> Items = new ArrayList<>();
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
                Items.add(item);
            }
        }
        ListView currentOrder = findViewById(R.id.currentOrder);
        currentOrder.setOnItemClickListener(this);
        ArrayAdapter<String> list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Items);
        currentOrder.setAdapter(list);

        double sub = getSubtotal();
        DecimalFormat df = new DecimalFormat("###.##");
        ((TextView)findViewById(R.id.subtotal)).setText("Subtotal: $"+df.format(sub));
        double tax = getTax(sub);
        ((TextView)findViewById(R.id.salesTax)).setText("Sales Tax: $"+df.format(tax));
        double total = sub + tax;
        ((TextView)findViewById(R.id.total)).setText("Total: $"+df.format(total));
    }

    /**
     * Get subtotal of order
     * @return double subtotal
     */
    private double getSubtotal(){
        double ret = 0;
        MenuItem[] items = MainActivity.getOrder().getItems();
        for(int i = 0; i<items.length; i++){
            if(items[i]==null){
                continue;
            }
            ret = ret+items[i].itemPrice();
        }
        return ret;
    }

    /**
     * Get sales tax of order
     * @param subTotal
     * @return double sales tax
     */
    private double getTax(double subTotal){
        double tax = 0.0625;
        return subTotal* tax;
    }
}