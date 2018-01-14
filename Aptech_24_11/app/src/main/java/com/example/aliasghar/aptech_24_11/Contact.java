package com.example.aliasghar.aptech_24_11;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Contact extends AppCompatActivity {

    Button b1Call, b2Web, b3Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        b1Call = (Button) findViewById(R.id.button5);
        b2Web = (Button) findViewById(R.id.button6);
        b3Email = (Button) findViewById(R.id.button7);

        b1Call.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:03442522927"));
                startActivity(callIntent);
            }
        });

        b2Web.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
                startActivity(webIntent);
            }
        });

        b3Email.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                              Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: abc@abc.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello ");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "This is to.........");
                startActivity(emailIntent);

            }
        });
    }
}
