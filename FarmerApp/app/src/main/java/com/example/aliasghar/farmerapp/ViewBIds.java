package com.example.aliasghar.farmerapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import Adapter.UserPostsAdapter;
import Adapter.ViewBidsAdapter;
import Model.ModelUserPosts;
import Model.ModelViewBids;

public class ViewBIds extends AppCompatActivity {

    ListView listView;

    String type = "getBids";

    String getUserPosts_url = "http://apnakisaan.000webhostapp.com/scripts/viewBids.php";

    ArrayList<ModelViewBids> viewBidsArrayList = new ArrayList<>();

    Background worker;

    String postId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b != null) {
            postId = (String) b.get("ID");
        }

        worker = new Background();
        worker.execute(type, postId);

        listView = (ListView) findViewById(R.id.listview);
    }


    public class Background extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {

            String type = params[0];

            if (type.equals("getBids")) {
                try {
                    String id = params[1];

                    URL url = new URL(getUserPosts_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data =
                            URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    result = stringBuilder.toString();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONArray ja = new JSONArray(result);
                if(ja.length() > 0) {

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        ModelViewBids viewBids = new ModelViewBids();
                        viewBids.setFarmerCropsId(jo.getString("farmerCropsId"));
                        viewBids.setBuyerId(jo.getString("buyerId"));
                        viewBids.setQuantity(jo.getString("quantity"));
                        viewBids.setUnitRequest(jo.getString("unitRequest"));
                        viewBids.setUnitPrice(jo.getString("unitPrice"));
                        viewBids.setTotalPrice(jo.getString("totalPrice"));
                        viewBids.setBidPrice(jo.getString("bidPrice"));
                        viewBids.setBidStatus(jo.getString("bidStatus"));

                        viewBidsArrayList.add(viewBids);
                    }
                    ViewBidsAdapter adapter = new ViewBidsAdapter(ViewBIds.this, viewBidsArrayList);
                    listView.setAdapter(adapter);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
