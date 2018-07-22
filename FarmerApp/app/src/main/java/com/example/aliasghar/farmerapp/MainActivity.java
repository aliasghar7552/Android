package com.example.aliasghar.farmerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    Button loginPageNextButton;

    TextView et_phone, et_password, newUser, forgotPassword;

    SessionManagement session;

    Handler delayhandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManagement(this);

        loginPageNextButton = (Button)findViewById(R.id.loginPageNextButton);

        et_phone = (TextView)findViewById(R.id.phone);
        et_password = (TextView)findViewById(R.id.password);

        newUser= (TextView)findViewById(R.id.newUser);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);

        if(session.isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        }


        loginPageNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_phone.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter your phone number",Toast.LENGTH_SHORT).show();
                }
                else if(et_phone.getText().toString().trim().length() < 11) {
                    Toast.makeText(getBaseContext(),"Enter valid phone number",Toast.LENGTH_SHORT).show();
                }
                else if(et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter your 8 digits password",Toast.LENGTH_SHORT).show();
                }
                else if(et_password.getText().toString().trim().length() < 8) {
                    Toast.makeText(getBaseContext(),"Password is incomplete",Toast.LENGTH_SHORT).show();
                }
                else {
                    login();
                }
            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),ForgotPassword.class);
                startActivity(intent);

            }
        });


        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),RegisterAs.class);
                startActivity(intent);
            }
        });
    }

    public void login() {
        String contact = et_phone.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String type = "login";

        session.execute(type, contact, password);

        delayhandler.postDelayed(mUpdateTimeTask, 3000);

    }


    private Runnable mUpdateTimeTask = new Runnable()
    {   public void run() {
            et_phone.setText("");
            et_password.setText("");


        }
    };
}
