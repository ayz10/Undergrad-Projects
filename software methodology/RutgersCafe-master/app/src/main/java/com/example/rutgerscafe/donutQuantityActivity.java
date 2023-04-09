package com.example.rutgerscafe;

import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Methods for Donut Quantity Activity screen
 * @authot Vineel Reddy
 * @author Alexander Zhao
 */
public class donutQuantityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner quantity;
    private TextView subTotalOutput;
    private Button addToOrderButton;
    TextView flavorSelected;

    /**
     * Initializes Donut Quantity activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut_quantity);

        quantity = findViewById(R.id.quantity);
        quantity.setOnItemSelectedListener(this);

        String[] values = {"1", "2", "3", "4", "5"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, values );
        quantity.setAdapter(spinnerArrayAdapter);
        Bundle b = getIntent().getExtras();
        String flavor = b.getString("flavor");
        ((TextView)findViewById(R.id.flavorSelected)).setText(flavor);
    }

    /**
     * Update subtotal when selecting quantity
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int quant = Integer.parseInt(quantity.getItemAtPosition(i).toString());
        String flavor = getIntent().getExtras().getString("flavor");
        double price = 0;
        switch(flavor){
            case "Boston Creme":
            case "Pumpkin":
            case "Blueberry":
                price = 1.59;
                break;
            case "Birthday Cake":
            case "Jelly":
            case "Glazed":
                price = 1.79;
                break;
            case "Strawberry":
            case "Chocolate":
            case "Vanilla":
                price = 0.30;
                break;
        }
        price = price * quant;
        NumberFormat format = new DecimalFormat("#0.00");
        ((TextView)findViewById(R.id.subTotalOutput)).setText(String.valueOf(format.format(price)));
    }

    /**
     * When nothing is selected
     * @param adapterView
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Add donut to order
     * @param view
     */
    public void addToOrder(View view){
        Order tempOrder = MainActivity.getOrder();
        int quant = Integer.parseInt(quantity.getSelectedItem().toString());
        String flavor = getIntent().getExtras().getString("flavor");
        double price = 0;
        String type = "";
        switch(flavor){
            case "Boston Creme":
            case "Pumpkin":
            case "Blueberry":
                price = 1.59;
                type = "Yeast";
                break;
            case "Birthday Cake":
            case "Jelly":
            case "Glazed":
                price = 1.79;
                type = "Cake";
                break;
            case "Strawberry":
            case "Chocolate":
            case "Vanilla":
                price = 0.30;
                type = "Hole";
                break;
        }
        Donut temp1 = new Donut("donut", price, type, flavor, MainActivity.getDonID());
        for(int j = 0; j < quant; j++){
            tempOrder.add(temp1);
        }
        MainActivity.setOrder(tempOrder);
        MainActivity.setDonID(MainActivity.getDonID()+1);
    }
}