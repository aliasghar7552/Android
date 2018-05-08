package com.example.aliasghar.realtimetrackingclientapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbRef;

    String value = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("Location");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                TextView tvLocation = (TextView) findViewById(R.id.location);
                tvLocation.setText(value);

                String [] seperated = value.split(",");
                String latitudePos = seperated[0].trim();
                String longitudePos = seperated[1].trim();

                double dlatitude = Double.parseDouble(latitudePos);
                double dlongitude = Double.parseDouble(longitudePos);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(getBaseContext(), MapsActivity.class);
        intent.putExtra("LOCVAL", value);
        startActivity(intent);
    }
}
