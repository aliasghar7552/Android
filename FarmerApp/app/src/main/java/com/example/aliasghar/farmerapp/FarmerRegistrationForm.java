package com.example.aliasghar.farmerapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.aliasghar.farmerapp.R.id.fullName;

public class FarmerRegistrationForm extends AppCompatActivity{

    Button farmerRegisterFormBackButton, farmerRegisterFormNextButton;

    EditText et_fullName, et_address, et_cnic, et_contact, et_crops, et_password;

    BackgroundWorker worker;

    Handler delayhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_registration_form);

        farmerRegisterFormBackButton = (Button)findViewById(R.id.farmerRegisterFormBackButton);
        farmerRegisterFormNextButton = (Button)findViewById(R.id.farmerRegisterFormNextButton);

        et_fullName = (EditText) findViewById(fullName);
        et_address = (EditText) findViewById(R.id.address);
        et_cnic = (EditText) findViewById(R.id.cnic);
        et_contact = (EditText) findViewById(R.id.contact);
        et_password = (EditText) findViewById(R.id.password);
        et_crops = (EditText) findViewById(R.id.crops);

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
                if(et_fullName.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter your full name",Toast.LENGTH_SHORT).show();
                }
                else if(et_address.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter your address",Toast.LENGTH_SHORT).show();
                }
                else if(et_cnic.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter your CNIC",Toast.LENGTH_SHORT).show();
                }
                else if(et_cnic.getText().toString().trim().length() < 13) {
                    Toast.makeText(getBaseContext(),"Enter complete CNIC number",Toast.LENGTH_SHORT).show();
                }
                else if(et_contact.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter your contact",Toast.LENGTH_SHORT).show();
                }
                else if(et_contact.getText().toString().trim().length() < 11) {
                    Toast.makeText(getBaseContext(),"Enter complete phone number",Toast.LENGTH_SHORT).show();
                }
                else if(et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter you password",Toast.LENGTH_SHORT).show();
                }
                else  if(et_password.getText().toString().trim().length() < 8) {
                    Toast.makeText(getBaseContext(),"Password should be minimum 8 digits",Toast.LENGTH_SHORT).show();
                }
                else if(et_crops.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter crops you cultivate",Toast.LENGTH_SHORT).show();
                }
                else {
                    registerFarmers();
                }
            }
        });
    }

    public void registerFarmers() {
        String name = et_fullName.getText().toString().trim();
        String address = et_address.getText().toString().trim();
        String cnic = et_cnic.getText().toString().trim();
        String contact = et_contact.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String crops = et_crops.getText().toString().trim();
        String type = "farmerRegister";

        worker = new BackgroundWorker(this);
        worker.execute(type, name, address, contact, cnic, password, crops);

        delayhandler.postDelayed(mUpdateTimeTask, 4000);

    }


    private Runnable mUpdateTimeTask = new Runnable()
    {   public void run() {
            et_fullName.setText("");
            et_address.setText("");
            et_cnic.setText("");
            et_contact.setText("");
            et_password.setText("");
            et_crops.setText("");
            startActivity(new Intent(getBaseContext(), MainActivity.class));

        }
    };

}
