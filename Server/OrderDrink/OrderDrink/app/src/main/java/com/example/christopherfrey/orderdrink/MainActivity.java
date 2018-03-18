package com.example.christopherfrey.orderdrink;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    String[] menu = {"drink1", "drink2", "drink3", "drink4"};
    ArrayList<String> arrList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrList = new ArrayList<>(Arrays.asList(menu));

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrList);
        ListView lv = (ListView) findViewById(R.id.menu_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(MainActivity.this, arrList.get(position) + " ordered!", Toast.LENGTH_SHORT).show();
               new createTransactionActivity().execute(arrList.get(position));
            }
        });

        getMenu();
    }

    public void getMenu(){
        new getMenuActivity(this, arrList, adapter).execute("");
    }
}
