package com.example.rutgerscafe;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class storeOrderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listOrders;
    private TextView head;
    private TextView help;

    /**
     * Intializes the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);
        listOrders = findViewById(R.id.orders);
        head = findViewById(R.id.head);
        help = findViewById(R.id.help);
        if(!MainActivity.getStoreOrders().isEmpty()){
            ArrayList<String> listO = new ArrayList<>();
            Order[] temp = MainActivity.getStoreOrders().getAllOrders();
            for(int i = 0; i < MainActivity.getStoreOrders().getAllOrders().length; i++){
                if(temp[i] != null){
                    listO.add(temp[i].printOrder());
                }
            }


            listOrders.setOnItemClickListener(this);
            ArrayAdapter<String> list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, listO);
            listOrders.setAdapter(list);
        }

    }

    /**
     * Called by the listview listener when an item within it is clicked
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Would You like to confirm the deletion of this order?").setTitle("Confirmation");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int toDelete = i;
                Order[] temp = MainActivity.getStoreOrders().getAllOrders();
                Order delete = temp[i];
                MainActivity.getStoreOrders().remove(delete);
                refresh();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    /**
     * recreates the activity by calling the onCreate method
     */
    private void refresh(){
        this.recreate();
    }
}