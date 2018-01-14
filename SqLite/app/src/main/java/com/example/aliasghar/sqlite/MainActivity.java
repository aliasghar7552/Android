package com.example.aliasghar.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b1Add, b2Delete, b3Update, b4Search, b5GetData;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1Add = (Button) findViewById(R.id.btn_add);
        b2Delete = (Button) findViewById(R.id.btn_delete);
        b3Update = (Button) findViewById(R.id.btn_update);
        b4Search = (Button) findViewById(R.id.btn_search);
        b5GetData = (Button) findViewById(R.id.btn_getData);


        helper = new DatabaseHelper(this);

        b1Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AddIntent = new Intent(getBaseContext(), AddContent.class);
                startActivity(AddIntent);
            }
        });

        b2Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deleteIntent = new Intent(getBaseContext(), DeleteContent.class);
                startActivity(deleteIntent);
            }
        });

        b3Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updataIntent = new Intent(getBaseContext(), UpdateContact.class);
                startActivity(updataIntent);
            }
        });

        b4Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent searchContact = new Intent(getBaseContext(), SearchContent.class);
                startActivity(searchContact);
            }
        });

        b5GetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getData = new Intent(MainActivity.this, GetData.class);
                startActivity(getData);

            }
        });


    }
}
