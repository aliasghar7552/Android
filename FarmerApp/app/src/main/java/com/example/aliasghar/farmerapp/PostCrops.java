package com.example.aliasghar.farmerapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.aliasghar.farmerapp.R.id.fullName;
import static com.example.aliasghar.farmerapp.R.id.parent;
import static com.example.aliasghar.farmerapp.R.id.quantity;
import static com.example.aliasghar.farmerapp.R.id.txt;

public class PostCrops extends AppCompatActivity  {

    Button farmerRegisterFormBackButton, farmerRegisterFormNextButton;

    EditText et_quantity, et_openDate, et_closeDate, et_startPrice, et_finalPrice, et_unitPrice;

    BackgroundWorker worker;

    SessionManagement session;
    String farmerID;

    Handler delayhandler = new Handler();

    Calendar mCurrentDate;

    int day, month, year;

    ArrayList<String> listItems = new ArrayList<>();
    ArrayList<String> listItems2 = new ArrayList<>();
    ArrayAdapter<String> adapter, adapter2;
    Spinner et_spinnerNames, et_spinnerQuality;
    String getCropsInSpinner_url = "http://apnakisaan.000webhostapp.com/scripts/getCropsInSpinner.php";

    String itemName, itemQuality;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_crops);

        session = new SessionManagement(this);
        farmerID =  session.sharedpreferences.getString("ID","ID");

        et_quantity = (EditText) findViewById(R.id.quantity);
        et_openDate = (EditText) findViewById(R.id.openDate);
        et_closeDate = (EditText) findViewById(R.id.closeDate);
        et_startPrice = (EditText) findViewById(R.id.startPrice);
        et_finalPrice = (EditText) findViewById(R.id.finalPrice);
        et_unitPrice = (EditText) findViewById(R.id.unitPrice);
        et_spinnerNames = (Spinner)findViewById(R.id.names);
        et_spinnerQuality = (Spinner)findViewById(R.id.quality);


        et_spinnerNames.setOnItemSelectedListener(onItemSelectedListener1);
        adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItems);
        et_spinnerNames.setAdapter(adapter);


        et_spinnerQuality.setOnItemSelectedListener(onItemSelectedListener2);
        adapter2 = new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItems2);
        et_spinnerQuality.setAdapter(adapter2);

        farmerRegisterFormBackButton = (Button)findViewById(R.id.farmerRegisterFormBackButton);
        farmerRegisterFormNextButton = (Button)findViewById(R.id.farmerRegisterFormNextButton);


        mCurrentDate = Calendar.getInstance();

        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        et_openDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(PostCrops.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear += 1;
                        et_openDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        et_closeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(PostCrops.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear += 1;
                        et_closeDate.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        farmerRegisterFormBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),RegisterAs.class);
                startActivity(intent);
            }
        });

        farmerRegisterFormNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_spinnerNames.getSelectedItem().equals("")) {
                    Toast.makeText(getBaseContext(),"Enter Crops for sale",Toast.LENGTH_SHORT).show();
                }
                else if(et_spinnerQuality.getSelectedItem().equals("")) {
                    Toast.makeText(getBaseContext(),"Enter quality of that crop",Toast.LENGTH_SHORT).show();
                }
                else if(et_quantity.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter quantity",Toast.LENGTH_SHORT).show();
                }
                else if(et_openDate.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter opening date",Toast.LENGTH_SHORT).show();
                }
                else if(et_closeDate.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter closing date",Toast.LENGTH_SHORT).show();
                }
                else if(et_startPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter starting price",Toast.LENGTH_SHORT).show();
                }
                else if(et_finalPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter your final price",Toast.LENGTH_SHORT).show();
                }
                else if(et_unitPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter per unit price",Toast.LENGTH_SHORT).show();
                }
                else {
                    postCrops();

                }
            }
        });
    }



    AdapterView.OnItemSelectedListener onItemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            itemName = et_spinnerNames.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener onItemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            itemQuality = et_spinnerQuality.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    public void onStart(){
        super.onStart();
        Background worker = new Background();
        worker.execute();
    }


    public void postCrops() {

        String quantity = et_quantity.getText().toString().trim();
        String openDate = et_openDate.getText().toString().trim();
        String closeDate = et_closeDate.getText().toString().trim();
        String startPrice = et_startPrice.getText().toString().trim();
        String finalPrice = et_finalPrice.getText().toString().trim();
        String unitPrice = et_unitPrice.getText().toString().trim();
        String type = "postCrops";

        worker = new BackgroundWorker(this);
        worker.execute(type, farmerID, itemName, itemQuality, quantity, openDate, closeDate, startPrice, finalPrice, unitPrice);

        delayhandler.postDelayed(mUpdateTimeTask, 4000);
    }


    private Runnable mUpdateTimeTask = new Runnable()
    {   public void run() {

        et_quantity.setText("");
        et_openDate.setText("");
        et_closeDate.setText("");
        et_startPrice.setText("");
        et_finalPrice.setText("");
        et_unitPrice.setText("");

    }
    };



    private class Background extends AsyncTask<Void,Void,Void> {
        ArrayList<String> list, list2;

        protected void onPreExecute(){
            super.onPreExecute();
            list = new ArrayList<>();
            list2 = new ArrayList<>();
        }

        protected Void doInBackground(Void...params){
            InputStream inputStream = null;
            String result = "";
            try{
                URL url = new URL(getCropsInSpinner_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                inputStream = httpURLConnection.getInputStream();
            }catch(IOException e){
                e.printStackTrace();
            }

            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result+=line;
                }
                inputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                JSONArray jArray =new JSONArray(result);
                for(int i = 0; i < jArray.length(); i++){
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    list.add(jsonObject.getString("crops_name"));
                    list2.add(jsonObject.getString("quality"));
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result){
            listItems.addAll(list);
            listItems2.addAll(list2);
            adapter.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
        }
    }

}
