package com.example.aliasghar.farmerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
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

import static android.R.attr.start;



public class BackgroundWorker extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alert;

    public BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        //String login_url = "http://apnakisaan.000webhostapp.com/scripts/login.php";
        String farmerRegister_url = "http://apnakisaan.000webhostapp.com/scripts/add-farmers.php";
        String wholesalerRegister_url = "http://apnakisaan.000webhostapp.com/scripts/add-wholesalers.php";
        String homebuyerRegister_url = "http://apnakisaan.000webhostapp.com/scripts/add-homebuyers.php";
        String millRegister_url = "http://apnakisaan.000webhostapp.com/scripts/add-mills.php";
        String postCrops_url = "http://apnakisaan.000webhostapp.com/scripts/post-crops.php";
        String bid_url = "http://apnakisaan.000webhostapp.com/scripts/bid.php";


        if (type.equals("farmerRegister")) {
            try {

                String name = params[1];
                String address = params[2];
                String contact = params[3];
                String cnic = params[4];
                String password = params[5];
                String crops = params[6];

                URL url = new URL(farmerRegister_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                                + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&"
                                + URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&"
                                + URLEncoder.encode("cnic", "UTF-8") + "=" + URLEncoder.encode(cnic, "UTF-8") + "&"
                                + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                                + URLEncoder.encode("crops", "UTF-8") + "=" + URLEncoder.encode(crops, "UTF-8");

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
        } else if (type.equals("wholesalerRegister")) {
            try {

                String name = params[1];
                String address = params[2];
                String contact = params[3];
                String cnic = params[4];
                String password = params[5];

                URL url = new URL(wholesalerRegister_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                                + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&"
                                + URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&"
                                + URLEncoder.encode("cnic", "UTF-8") + "=" + URLEncoder.encode(cnic, "UTF-8") + "&"
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
        } else if (type.equals("homebuyerRegister")) {
            try {

                String name = params[1];
                String address = params[2];
                String contact = params[3];
                String cnic = params[4];
                String password = params[5];

                URL url = new URL(homebuyerRegister_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                                + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&"
                                + URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&"
                                + URLEncoder.encode("cnic", "UTF-8") + "=" + URLEncoder.encode(cnic, "UTF-8") + "&"
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
        } else if (type.equals("millsRegister")) {
            try {

                String name = params[1];
                String address = params[2];
                String contact = params[3];
                String cnic = params[4];
                String password = params[5];
                String millName = params[6];
                String email = params[7];

                URL url = new URL(millRegister_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                                + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&"
                                + URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&"
                                + URLEncoder.encode("cnic", "UTF-8") + "=" + URLEncoder.encode(cnic, "UTF-8") + "&"
                                + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                                + URLEncoder.encode("millName", "UTF-8") + "=" + URLEncoder.encode(millName, "UTF-8") + "&"
                                + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

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
        } else if (type.equals("postCrops")) {
            try {

                String farmerId = params[1];
                String cropsName = params[2];
                String cropsQuality = params[3];
                String quantity = params[4];
                String openDate = params[5];
                String closeDate = params[6];
                String startPrice = params[7];
                String finalPrice = params[8];
                String unitPrice = params[9];

                URL url = new URL(postCrops_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("farmerId", "UTF-8") + "=" + URLEncoder.encode(farmerId, "UTF-8") + "&"
                                + URLEncoder.encode("cropsName", "UTF-8") + "=" + URLEncoder.encode(cropsName, "UTF-8") + "&"
                                + URLEncoder.encode("cropsQuality", "UTF-8") + "=" + URLEncoder.encode(cropsQuality, "UTF-8") + "&"
                                + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8") + "&"
                                + URLEncoder.encode("openDate", "UTF-8") + "=" + URLEncoder.encode(openDate, "UTF-8") + "&"
                                + URLEncoder.encode("closeDate", "UTF-8") + "=" + URLEncoder.encode(closeDate, "UTF-8") + "&"
                                + URLEncoder.encode("startPrice", "UTF-8") + "=" + URLEncoder.encode(startPrice, "UTF-8") + "&"
                                + URLEncoder.encode("finalPrice", "UTF-8") + "=" + URLEncoder.encode(finalPrice, "UTF-8") + "&"
                                + URLEncoder.encode("unitPrice", "UTF-8") + "=" + URLEncoder.encode(unitPrice, "UTF-8");
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
        } else if (type.equals("bid")) {
            try {

                String postId = params[1];
                String buyerId   = params[2];
                String quantity = params[3];
                String unitRequest = params[4];
                String unitPrice = params[5];
                String totalPrice = params[6];
                String bidPrice = params[7];

                URL url = new URL(bid_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("postId", "UTF-8") + "=" + URLEncoder.encode(postId, "UTF-8") + "&"
                                + URLEncoder.encode("buyerId", "UTF-8") + "=" + URLEncoder.encode(buyerId, "UTF-8") + "&"
                                + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8") + "&"
                                + URLEncoder.encode("unitRequest", "UTF-8") + "=" + URLEncoder.encode(unitRequest, "UTF-8") + "&"
                                + URLEncoder.encode("unitPrice", "UTF-8") + "=" + URLEncoder.encode(unitPrice, "UTF-8") + "&"
                                + URLEncoder.encode("totalPrice", "UTF-8") + "=" + URLEncoder.encode(totalPrice, "UTF-8") + "&"
                                + URLEncoder.encode("bidPrice", "UTF-8") + "=" + URLEncoder.encode(bidPrice, "UTF-8");

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

        if (result.equalsIgnoreCase("Crops posted successfully")) {
            Toast.makeText(context, "Crops posted successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Dashboard.class);
            context.startActivity(intent);
        }
        else if(result.equalsIgnoreCase("You are successfully registered")) {
            Toast.makeText(context, "You are successfully registered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
        else if(result.equalsIgnoreCase("Your bid is placed successfully")) {
            Toast.makeText(context, "Your bid is placed successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, OpenTendors.class);
            context.startActivity(intent);
        }

        else {
        alert.setMessage(result);
        alert.show();
        }

    }


    @Override
    protected void onPreExecute() {
        alert = new AlertDialog.Builder(context).create();
        alert.setTitle("Status");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
