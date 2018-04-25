package com.example.christopherfrey.barapp;

/**
 * Created by Christopher Frey on 4/24/2018.
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
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class findEmail extends AsyncTask<String, Void, String>{

    private ArrayList<String> found;
    private Context context;
    private TextView f;

    public findEmail(Context c){
        this.context = c;
    }

    protected void onPreExecute(){}

    @Override
    protected String doInBackground(String... arg0){
        try{
            String email = (String)arg0[0];

            String serverIP = "10.106.12.5";
            String link = "http://"+serverIP+"/MixerRoboto/find_email.php?email="+email;

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
        f = (TextView)((MainActivity)context).findViewById(R.id.found);

        f.setText(result);
        this.publishProgress();
    }
}
