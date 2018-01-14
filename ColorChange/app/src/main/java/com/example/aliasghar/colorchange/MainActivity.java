package com.example.aliasghar.colorchange;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RadioButton white, green, blue, red;
    RelativeLayout r1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red = (RadioButton) findViewById(R.id.radioButton);
        blue = (RadioButton) findViewById(R.id.radioButton2);
        green = (RadioButton) findViewById(R.id.radioButton3);
        white = (RadioButton) findViewById(R.id.radioButton4);
        r1 = (RelativeLayout) findViewById(R.id.r1);


    }

    public void pickColor(View view) {
        switch (view.getId()) {
            case R.id.radioButton:
                r1.setBackgroundColor(Color.RED);
                break;
            case R.id.radioButton2:
                r1.setBackgroundColor(Color.BLUE);
                break;
            case R.id.radioButton3:
                r1.setBackgroundColor(Color.GREEN);
                break;
            case R.id.radioButton4:
                r1.setBackgroundColor(Color.WHITE);
                break;


        }
    }
}
