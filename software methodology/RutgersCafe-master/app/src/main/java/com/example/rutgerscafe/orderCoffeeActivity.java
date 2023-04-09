package com.example.rutgerscafe;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * @author Alexander Zhao
 * @author Vineel Reddy
 */
public class orderCoffeeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView size;
    private ListView quantity;
    private CheckBox cream;
    private CheckBox milk;
    private CheckBox syrup;
    private CheckBox caramel;
    private CheckBox whipped;
    private Button submit;

    private String currentSize;
    private String currentQuantity;
    private double p;
    private int sizeAsInt;

    /**
     * Initializes the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_coffee);
        ArrayList<String> sizes = new ArrayList<>();
        sizes.add("Short");
        sizes.add("Tall");
        sizes.add("Grande");
        sizes.add("Venti");

        size = findViewById(R.id.size);
        size.setOnItemClickListener(this);
        ArrayAdapter<String> list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, sizes);
        size.setAdapter(list);


        ArrayList<String> quants = new ArrayList<>();
        quants.add("1");
        quants.add("2");
        quants.add("3");
        quants.add("4");

        quantity = findViewById(R.id.quantity);
        quantity.setOnItemClickListener(this);
        ArrayAdapter<String> list2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, quants);
        quantity.setAdapter(list2);

        cream = findViewById(R.id.cream);
        milk = findViewById(R.id.milk);
        syrup = findViewById(R.id.syrup);
        whipped = findViewById(R.id.whipped);
        caramel = findViewById(R.id.caramel);
        submit =  findViewById(R.id.submit);
        size.setItemChecked(0, true);
        quantity.setItemChecked(0, true);
        String cSize = "Short";
        String cQuant = "1";
        currentSize = cSize;
        currentQuantity = cQuant;
    }

    /**
     * Called by the listview listener whenever an item in the listview is clicked
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.size){
            currentSize = size.getItemAtPosition(i).toString();

        }
        else{
            currentQuantity = quantity.getItemAtPosition(i).toString();

        }
        updateOutput(view);

    }

    /**
     * Outputs the prices as a Toast when a button is clicked
     * @param v
     */
    public void updateOutput(View v){
        double price = 0;
        if(cream.isChecked()){
            price += 0.30;
        }
        if(syrup.isChecked()){
            price += 0.30;
        }
        if(milk.isChecked()){
            price += 0.30;
        }
        if(caramel.isChecked()){
            price += 0.30;
        }
        if(whipped.isChecked()){
            price += 0.30;
        }


        switch(currentSize){
            case "Short": price += 1.69;
                sizeAsInt = 0;
                break;
            case "Tall": price += 2.09;
                sizeAsInt = 1;
                break;
            case "Grande": price += 2.49;
                sizeAsInt = 2;
                break;
            case "Venti": price += 2.89;
                sizeAsInt = 3;
                break;
            default:
                break;
        }
        int q = Integer.parseInt(currentQuantity);
        price *= q;

        NumberFormat format = new DecimalFormat("#0.00");
        price = Double.parseDouble(format.format(price));
        this.p = price;
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence out = "Price is " + price;
        Toast.makeText(context, out, duration).show();
    }

    /**
     * Gets the number of addins selected
     * @return number of addins
     */
    private int getAddins(){
        int addins = 0;
        if (cream.isChecked()) addins += 1;
        if (milk.isChecked()) addins += 1;
        if (syrup.isChecked()) addins += 1;
        if (whipped.isChecked()) addins += 1;
        if (caramel.isChecked()) addins += 1;
        return addins;
    }

    /**
     * Places the order when the order button is clicked
     * @param v
     */
    public void toOrder(View v){

        String[] add = new String[getAddins()];
        int x = 0;
        if (cream.isChecked()) {add[x] = "Cream" ; x++;}
        if (milk.isChecked()) {add[x] = "Milk"; x++;}
        if (syrup.isChecked()) {add[x] = "Syrup"; x++;}
        if (whipped.isChecked()) {add[x] = "Whipped-Cream"; x++;}
        if (caramel.isChecked()) {add[x] = "Caramel"; x++;}
        Coffee temp = new Coffee("coffee", this.p, this.sizeAsInt, add, MainActivity.getCofID());
        MainActivity.setCofID(MainActivity.getCofID() + 1);
        Order tempOrder = MainActivity.getOrder();
        tempOrder.add(temp);
        MainActivity.setOrder(tempOrder);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        CharSequence out = "Added to Order";
        Toast.makeText(context, out, duration).show();
    }

}