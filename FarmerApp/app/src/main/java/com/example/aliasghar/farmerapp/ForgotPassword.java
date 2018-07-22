package com.example.aliasghar.farmerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button forgottenpasswordpagebackbutton = (Button)findViewById(R.id.forgottenpasswordpagebackbutton);

        forgottenpasswordpagebackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgottenpasswordpageback = new Intent(ForgotPassword.this,MainActivity.class);
                startActivity(forgottenpasswordpageback);
            }
        });
    }
}
