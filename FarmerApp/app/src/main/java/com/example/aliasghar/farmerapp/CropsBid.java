package com.example.aliasghar.farmerapp;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CropsBid extends AppCompatActivity {

    String postId, buyerId;

    SessionManagement session;
    BackgroundWorker worker;

    Button bt_bidBackButton, bt_bidSubmitButton;
    EditText et_quantity, et_unitRequest, et_unitPrice, et_totalprice, et_bidPrice;

    Handler delayhandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_bid);

        session = new SessionManagement(this);
        buyerId = session.sharedpreferences.getString("ID", "ID");

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b != null) {
            postId = (String) b.get("ID");
        }


        et_quantity = (EditText) findViewById(R.id.quantity);
        et_unitRequest = (EditText) findViewById(R.id.unitRequest);
        et_unitPrice = (EditText) findViewById(R.id.unitPrice);
        et_totalprice = (EditText) findViewById(R.id.totalprice);
        et_bidPrice = (EditText) findViewById(R.id.bidPrice);

        bt_bidBackButton = (Button)findViewById(R.id.bidBackButton);
        bt_bidSubmitButton = (Button)findViewById(R.id.bidSubmitButton);

        bt_bidSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_quantity.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter quantity you want to bid for",Toast.LENGTH_SHORT).show();
                }
                else if(et_unitRequest.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter total unit in kg you want to bid for",Toast.LENGTH_SHORT).show();
                }
                else if(et_unitPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter per unit price",Toast.LENGTH_SHORT).show();
                }
                else if(et_totalprice.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter total price",Toast.LENGTH_SHORT).show();
                }
                else if(et_bidPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(),"Enter bid price",Toast.LENGTH_SHORT).show();
                }
                else {
                    bid();

                }
            }
        });
    }

    public void bid() {

        String quantity = et_quantity.getText().toString().trim();
        String unitRequest = et_unitRequest.getText().toString().trim();
        String unitPrice = et_unitPrice.getText().toString().trim();
        String totalPrice = et_totalprice.getText().toString().trim();
        String bidPrice = et_bidPrice.getText().toString().trim();
        String type = "bid";

        worker = new BackgroundWorker(this);
        worker.execute(type, postId, buyerId ,quantity, unitRequest, unitPrice, totalPrice, bidPrice);

        delayhandler.postDelayed(mUpdateTimeTask, 4000);
    }


    private Runnable mUpdateTimeTask = new Runnable()
    {   public void run() {

        et_quantity.setText("");
        et_unitRequest.setText("");
        et_unitPrice.setText("");
        et_totalprice.setText("");
        et_bidPrice.setText("");
    }
    };

}
