package com.example.aliasghar.farmerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterAs extends AppCompatActivity {

    Button farmer, wholesaler, mills, homebuyer, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);

        farmer = (Button) findViewById(R.id.farmer);
        wholesaler = (Button) findViewById(R.id.wholesaler);
        mills = (Button) findViewById(R.id.mills);
        homebuyer = (Button) findViewById(R.id.homebuyer);
        backButton = (Button)findViewById(R.id.backButton);


        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent farmerform = new Intent(getBaseContext(),FarmerRegistrationForm.class);
                startActivity(farmerform);
            }});

        wholesaler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wholesellerform = new Intent(getBaseContext(),WholesalerRegistrationForm.class);
                startActivity(wholesellerform);
            }});

        mills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent millsrepresentform = new Intent(getBaseContext(),MillsRepresentativeRegistrationForm.class);
                startActivity(millsrepresentform);
            }});

        homebuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homebuyerform = new Intent(getBaseContext(),HomebuyerRegistrationForm.class);
                startActivity(homebuyerform);
            }});

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerasback = new Intent(getBaseContext(),MainActivity.class);
                startActivity(registerasback);
            }
        });
    }
}
