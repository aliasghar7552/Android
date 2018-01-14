package com.example.aliasghar.aptech_24_11;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    Button b1Home, b2Contact, b3Product, b4About, b5Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1Home = (Button) findViewById(R.id.button3);
        b2Contact = (Button) findViewById(R.id.button4);
        b3Product = (Button) findViewById(R.id.button);
        b4About = (Button) findViewById(R.id.button2);
        b5Login = (Button) findViewById(R.id.button9);



        b1Home.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Intent homeIntent = new Intent(MainActivity.this, Home.class);
                startActivity(homeIntent);
            }
        });

        b2Contact.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent contactIntent = new Intent(MainActivity.this, Contact.class);
                startActivity(contactIntent);
            }
        });

        b3Product.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent productIntent = new Intent(MainActivity.this, Product.class);
                startActivity(productIntent);
            }
        });

        b4About.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent aboutIntent = new Intent(MainActivity.this, About.class);
                startActivity(aboutIntent);
            }
        });
        b5Login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent loginIntent = new Intent(MainActivity.this, Login.class);
                startActivity(loginIntent);
            }
        });
    }
}
