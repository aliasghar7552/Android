package com.example.aliasghar.farmerapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import Adapter.ViewUserBidsAdapter;
import Model.ModelUserPosts;
import Model.ModelViewUserBids;

public class ViewUserBids extends AppCompatActivity {

    SessionManagement session;

    String buyerId;

    ListView listView;

    Background worker;

    String type = "viewUserBids";

    String viewUserBids_url = "http://apnakisaan.000webhostapp.com/scripts/viewUserBids.php";

    ArrayList<ModelViewUserBids> userBidsArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_bids);

        session = new SessionManagement(this);
        buyerId =  session.sharedpreferences.getString("ID","ID");

        worker = new Background();
        worker.execute(type, buyerId);

        listView = (ListView) findViewById(R.id.listview);
    }

    public class Background extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {

            String type = params[0];

            if (type.equals("viewUserBids")) {
                try {
                    String id = params[1];

                    URL url = new URL(viewUserBids_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data =
                            URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    Log.i("Hello", post_data);
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
                    Log.i("Hello 2", result);
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
                if (ja.length() > 0) {
                    Log.i("Hello 3", result);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        ModelViewUserBids viewUserBids = new ModelViewUserBids();
                        viewUserBids.setId(jo.getString("id"));
                        viewUserBids.setCropName(jo.getString("cropName"));
                        viewUserBids.setCropQuality(jo.getString("quality"));
                        viewUserBids.setQuantity_kg(jo.getString("quantity_kg"));
                        viewUserBids.setsDate(jo.getString("oDate"));
                        viewUserBids.setcDate(jo.getString("cDate"));
                        viewUserBids.setsPrice(jo.getString("sPrice"));
                        viewUserBids.setfPrice(jo.getString("fPrice"));
                        viewUserBids.setuPrice(jo.getString("uPrice"));

                        viewUserBids.setFarmerCropsId(jo.getString("farmerCropsId"));
                        viewUserBids.setBuyerId(jo.getString("buyerId"));
                        viewUserBids.setQuantity(jo.getString("quantity"));
                        viewUserBids.setUnitRequest(jo.getString("unitRequest"));
                        viewUserBids.setUnitPrice(jo.getString("unitPrice"));
                        viewUserBids.setTotalPrice(jo.getString("totalPrice"));
                        viewUserBids.setBidPrice(jo.getString("bidPrice"));
                        viewUserBids.setBidStatus(jo.getString("bidStatus"));

                        userBidsArrayList.add(viewUserBids);
                    }
                    ViewUserBidsAdapter adapter = new ViewUserBidsAdapter(ViewUserBids.this, userBidsArrayList);
                    listView.setAdapter(adapter);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
