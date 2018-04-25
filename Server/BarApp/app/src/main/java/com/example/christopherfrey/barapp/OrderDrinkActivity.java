package com.example.christopherfrey.barapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderDrinkActivity extends AppCompatActivity {

    String[] menu = {};

    //arrList will hold names of the drinks and idList will hold ids of the drinks
    //ex: position 2 in idList is the id for the drink in position 2 or arrList
    ArrayList<String> arrList;
    ArrayList<String> idList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_drink);

        arrList = new ArrayList<>(Arrays.asList(menu));
        idList = new ArrayList<>(Arrays.asList(menu));

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrList);
        ListView lv = (ListView) findViewById(R.id.menu_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OrderDrinkActivity.this, arrList.get(position) + " ordered!", Toast.LENGTH_SHORT).show();

                new createTransactionActivity().execute(idList.get(position), getIntent().getStringExtra("EMAIL"));
            }
        });

        getMenu();
    }

    public void getMenu(){

        new getMenuActivity(this, arrList, adapter, idList).execute("");

    }
}
