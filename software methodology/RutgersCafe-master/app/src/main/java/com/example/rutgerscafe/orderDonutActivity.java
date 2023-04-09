package com.example.rutgerscafe;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * @author Alexander Zhao
 * @author Vineel Reddy
 */
public class orderDonutActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView flavorSelection;
//    private static int position;
//    private static String flavor;

    /**
     * Intializes the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_donut);

        ArrayList<String> flavors = new ArrayList<>();
        flavors.add("Strawberry");
        flavors.add("Chocolate");
        flavors.add("Pumpkin");
        flavors.add("Glazed");
        flavors.add("Boston Creme");
        flavors.add("Birthday Cake");
        flavors.add("Blueberry");
        flavors.add("Vanilla");
        flavors.add("Jelly");

        flavorSelection = findViewById(R.id.flavorSelection);
        flavorSelection.setOnItemClickListener(this);
        ArrayAdapter<String> list = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, flavors);
        flavorSelection.setAdapter(list);
    }

    /**
     * Called by the listview listener and is called when an item within is clicked
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String flavor = flavorSelection.getItemAtPosition(i).toString();
        Intent intent = new Intent(this, donutQuantityActivity.class);
        intent.putExtra("flavor", flavor);
        startActivity(intent);
    }
}