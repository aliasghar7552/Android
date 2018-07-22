package com.example.aliasghar.farmerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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

/**
 * Created by Aliasghar on 7/7/2018.
 */

public class SessionManagement extends AsyncTask<String, Void, String> {


    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Id = "id";
    private static final String IS_LOGIN = "IsLoggedIn";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    String login_url = "http://apnakisaan.000webhostapp.com/scripts/login2.php";

    String[] data;

    AlertDialog alert;

    Context context;
    public SessionManagement(Context ctx) {
        context = ctx;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    @Override
    protected void onPreExecute() {
        alert = new AlertDialog.Builder(context).create();
        alert.setTitle("Status");
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        if (type.equals("login")) {

            try {

                String contact = params[1];
                String password = params[2];

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&"
                                + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
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


        String jsonStr = result; //turn object into string
        if (jsonStr != null) {
            try {
                JSONArray ja = new JSONArray(jsonStr);
                JSONObject jo = null;
                String userid = null;
                data = new String[ja.length()];
                for(int i = 0; i<ja.length(); i++) {
                    jo = ja.getJSONObject(i);
                    userid = jo.getString("id");
                }

                Toast.makeText(context, "Sign In successful.", Toast.LENGTH_SHORT).show();

                editor.putBoolean(IS_LOGIN, true);
                editor.putString("ID", userid);
                editor.commit();

                //String value = sharedpreferences.getString("ID", userid);

                Intent intent = new Intent(context, Dashboard.class);
                context.startActivity(intent);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }

    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent intent = new Intent(context, MainActivity.class);
            // Closing all the Activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            context.startActivity(intent);

        }
    }

    public boolean isLoggedIn(){
        return sharedpreferences.getBoolean(IS_LOGIN, false);
    }

    public  void logout(){

        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


}
