package com.example.aliasghar.aptech_24_11;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText userName, password;
    Button loginClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        loginClick = (Button) findViewById(R.id.button8);

    }



    public void login(View view) {
        if(userName.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "PLEASE FILL USERNAME FIELDS", Toast.LENGTH_SHORT).show();
        } else if(password.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(), "PLEASE FILL PASSWORD FIELDS", Toast.LENGTH_SHORT).show();
        }
        else if (userName.getText().toString().equals("aliasghar") && password.getText().toString().equals("password")) {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage("Are you sure you want to continue?");

            alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(Login.this, "You clicked YES", Toast.LENGTH_LONG).show();
                }

            });

            alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(Login.this, "You clicked NO", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
        }
    }



 }

