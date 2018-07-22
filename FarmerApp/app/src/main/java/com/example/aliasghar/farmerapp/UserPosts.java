package com.example.aliasghar.farmerapp;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
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
import Model.ModelOpenTenders;
import Model.ModelUserPosts;

public class UserPosts extends AppCompatActivity{

    SessionManagement session;
    public static final String Id = "id";
    String value;

    ListView listView;

    Background worker;
    String type = "getUserPosts";

    String getUserPosts_url = "http://apnakisaan.000webhostapp.com/scripts/getUserPosts.php";

    ArrayList<ModelUserPosts> userPostsArrayList = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        session = new SessionManagement(this);
        value =  session.sharedpreferences.getString("ID","ID");

        worker = new Background();
        worker.execute(type, value);

        listView = (ListView) findViewById(R.id.listview);

    }

    public class Background extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {

            String type = params[0];

            if (type.equals("getUserPosts")) {
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
                        ModelUserPosts userPosts = new ModelUserPosts();
                        userPosts.setId(jo.getString("id"));
                        userPosts.setCropName(jo.getString("cropName"));
                        userPosts.setCropQuality(jo.getString("quality"));
                        userPosts.setQuantity(jo.getString("quantity"));
                        userPosts.setsDate(jo.getString("oDate"));
                        userPosts.setcDate(jo.getString("cDate"));
                        userPosts.setsPrice(jo.getString("sPrice"));
                        userPosts.setfPrice(jo.getString("fPrice"));
                        userPosts.setuPrice(jo.getString("uPrice"));

                        userPostsArrayList.add(userPosts);
                    }
                    UserPostsAdapter adapter = new UserPostsAdapter(UserPosts.this, userPostsArrayList);
                    listView.setAdapter(adapter);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
