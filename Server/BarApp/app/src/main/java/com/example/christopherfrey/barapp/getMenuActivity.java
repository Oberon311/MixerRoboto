package com.example.christopherfrey.barapp;

/**
 * Created by Christopher Frey on 4/15/2018.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

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
    private ArrayList<String> idList;
    private ArrayAdapter adapter;
    private String[] query;
    private Vector<String> drinks = new Vector<>();
    private Vector<String> ids = new Vector<>();

    public getMenuActivity(Context context, ArrayList<String> ar, ArrayAdapter aa, ArrayList idl){
        this.context = context;
        this.arraylist = ar;
        this.adapter = aa;
        this.idList = idl;
    }

    protected void onPreExecute(){}

    @Override
    protected String doInBackground(String... arg0){
        try{
            //String username = (String)arg0[0];
            String serverIP = "10.106.12.5";
            String link = "http://"+serverIP+"/MixerRoboto/get_menu.php?username=username";

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
        query = result.split("<br>");

        //query returns id of 1st drink, name of 1st drink, id of 2nd drink, name of 2nd drink, etc.
        for(int i=0; i<query.length; i++) {
            ids.add(query[i]);
            i++;
            drinks.add(query[i]);
        }

        //since query's size is 2x the amount of drinks, divide by two
        for(int i=0; i<(query.length)/2; i++) {
            arraylist.add(drinks.get(i));
            idList.add(ids.get(i));
        }

        adapter.notifyDataSetChanged();
    }
}
