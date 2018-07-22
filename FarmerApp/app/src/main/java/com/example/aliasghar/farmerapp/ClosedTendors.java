package com.example.aliasghar.farmerapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Adapter.ClosedTendersAdapter;
import Adapter.OpenTendorsAdapter;
import Model.ModelClosedTenders;
import Model.ModelOpenTenders;

public class ClosedTendors extends AppCompatActivity {

    String getClosedTendors_url = "http://apnakisaan.000webhostapp.com/scripts/getClosedTendors.php";

    ListView listView;
    BufferedInputStream inputStream;
    String line = null;
    String result = null;

    ArrayList<ModelClosedTenders> closedTendersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closed_tendors);

        listView = (ListView)findViewById(R.id.listview);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData(getClosedTendors_url);
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

        try {
            JSONArray ja = new JSONArray(result);
            if (ja.length() > 0) {
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    ModelClosedTenders closedTenders = new ModelClosedTenders();
                    closedTenders.setId(jo.getString("id"));
                    closedTenders.setCropName(jo.getString("cropName"));
                    closedTenders.setCropQuality(jo.getString("quality"));
                    closedTenders.setQuantity(jo.getString("quantity"));
                    closedTenders.setsDate(jo.getString("oDate"));
                    closedTenders.setcDate(jo.getString("cDate"));
                    closedTenders.setsPrice(jo.getString("sPrice"));
                    closedTenders.setfPrice(jo.getString("fPrice"));
                    closedTenders.setuPrice(jo.getString("uPrice"));

                    closedTendersList.add(closedTenders);

                }
            ClosedTendersAdapter adapter = new ClosedTendersAdapter(ClosedTendors.this, closedTendersList);
            listView.setAdapter(adapter);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
