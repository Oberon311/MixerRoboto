package com.example.christopherfrey.orderdrink;

/**
 * Created by Christopher Frey on 3/14/2018.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

public class getMenuActivity extends AsyncTask<String, Void, String>{

    private Context context;
    private ArrayList<String> arraylist;
    private ArrayAdapter adapter;
    private String[] drinks;
    private String drink1;
    private String drink2;
    private String drink3;
    private String drink4;

    public getMenuActivity(Context context, ArrayList<String> ar, ArrayAdapter aa){
        this.context = context;
        this.arraylist = ar;
        this.adapter = aa;
    }

    protected void onPreExecute(){}

    @Override
    protected String doInBackground(String... arg0){
        try{
            //String username = (String)arg0[0];
            String link = "http://192.168.1.149/MixerRoboto/get_menu.php?username=username";

            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line="";

            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }

            in.close();
            return sb.toString();

        }catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result){
        drinks = result.split("<br>");
        drink1 = drinks[0];
        drink2 = drinks[1];
        drink3 = drinks[2];
        drink4 = drinks[3];

        arraylist.set(0,drink1);
        arraylist.set(1, drink2);
        arraylist.set(2, drink3);
        arraylist.set(3,drink4);

        adapter.notifyDataSetChanged();
    }
}
