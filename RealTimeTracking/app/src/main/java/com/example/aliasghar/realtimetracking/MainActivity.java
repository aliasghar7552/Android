package com.example.aliasghar.realtimetracking;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbRef;

    Button btnLocation;
    TextView tvLocation;

    GPSTracker gps;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String permissin = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if(ActivityCompat.checkSelfPermission(this, permissin) != MockPackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {permissin}, REQUEST_CODE_PERMISSION);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        btnLocation = (Button) findViewById(R.id.button);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gps = new GPSTracker(MainActivity.this);
                tvLocation = (TextView) findViewById(R.id.textView);
                if(gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    tvLocation.setText(latitude + " " + longitude);

                    database = FirebaseDatabase.getInstance();
                    dbRef = database.getReference("Location");
                    dbRef.setValue(latitude + ", " + longitude);
                }
                else {
                    gps.showSettingsAlert();
                }
            }
        });

    }
}
