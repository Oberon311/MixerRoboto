package com.example.christopherfrey.orderdrink;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

/**
 * Created by Christopher Frey on 3/17/2018.
 */

public class createTransactionActivity extends AsyncTask<String,Void,String> {

    protected void onPreExecute(){}

    @Override
    protected String doInBackground(String... arg0){
        try{
            String drink = (String)arg0[0];

            String link="http://192.168.1.149/MixerRoboto/create_transaction.php";
            String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(drink, "UTF-8");
            //data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
            //       URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            return sb.toString();

        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result){

    }
}
