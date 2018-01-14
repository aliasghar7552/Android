package com.example.aliasghar.nov_8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    String msg = "Andorid Class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(msg, "onStart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, " onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(msg, "onDestroy()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "onResume");
    }
}
