package com.example.aliasghar.farmerapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Adapter.OpenTendorsAdapter;
import Model.ModelOpenTenders;

public class OpenTendors extends AppCompatActivity {


    String getOpenTendors_url = "http://apnakisaan.000webhostapp.com/scripts/getOpenTendors.php";
    ListView listView;
    BufferedInputStream inputStream;
    String line = null;
    String result = null;

    ArrayList<ModelOpenTenders> openTendersList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_tendors);

        listView = (ListView)findViewById(R.id.listview);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData(getOpenTendors_url);

    }

    private void collectData(String tendorURL) {

        try{
            URL url = new URL(tendorURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            inputStream = new BufferedInputStream(con.getInputStream());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line+"\n");
            }
            inputStream.close();
            result = stringBuilder.toString();

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        try{
            JSONArray ja = new JSONArray(result);
            if(ja.length() > 0) {


                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    ModelOpenTenders openTendors = new ModelOpenTenders();
                    openTendors.setId(jo.getString("id"));
                    openTendors.setCropName(jo.getString("cropName"));
                    openTendors.setCropQuality(jo.getString("quality"));
                    openTendors.setQuantity(jo.getString("quantity"));
                    openTendors.setsDate(jo.getString("oDate"));
                    openTendors.setcDate(jo.getString("cDate"));
                    openTendors.setsPrice(jo.getString("sPrice"));
                    openTendors.setfPrice(jo.getString("fPrice"));
                    openTendors.setuPrice(jo.getString("uPrice"));

                    openTendersList.add(openTendors);

                }


                OpenTendorsAdapter adapter = new OpenTendorsAdapter(OpenTendors.this, openTendersList);
                listView.setAdapter(adapter);

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
