package com.example.aliasghar.farmerapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import static com.example.aliasghar.farmerapp.R.id.bidButton;

/**
 * Created by Aliasghar on 7/3/2018.
 */

public class CustomListView extends ArrayAdapter<String> {

    private String[] id;
    private String[] cropName;
    private String[] cropQuality;
    private String[] quantity;
    private String[] sDate;
    private String[] cDate;
    private String[] sPrice;
    private String[] fPrice;
    private String[] uPrice;
    private Activity context;

    String postId = null;

    public CustomListView(Activity context, String[] id, String[] cropName, String[] cropQuality, String[] quantity, String[] sDate, String[] cDate, String[] sPrice, String[] fPrice, String[] uPrice) {
        super(context, R.layout.layout, quantity);
        this.context = context;
        this.id = id;
        this.cropName = cropName;
        this.cropQuality = cropQuality;
        this.quantity = quantity;
        this.sDate = sDate;
        this.cDate = cDate;
        this.sPrice = sPrice;
        this.fPrice = fPrice;
        this.uPrice = uPrice;
    }


    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.layout,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)r.getTag();

        }


        viewHolder.id.setText(id[position]);
        viewHolder.t.setText(cropName[position]);
        viewHolder.tv.setText(cropQuality[position]);
        viewHolder.tv1.setText(quantity[position]);
        viewHolder.tv2.setText(sDate[position]);
        viewHolder.tv3.setText(cDate[position]);
        viewHolder.tv4.setText(sPrice[position]);
        viewHolder.tv5.setText(fPrice[position]);
        viewHolder.tv6.setText(uPrice[position]);


        postId = (String) viewHolder.id.getText();



        viewHolder.bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context.getBaseContext(), CropsBid.class);
                intent.putExtra("ID", postId);
                Toast.makeText(context, "Hello "+ postId, Toast.LENGTH_LONG).show();
                context.startActivity(intent);
            }
        });

        return r;
    }

    class ViewHolder{

        TextView id, t, tv, tv1, tv2, tv3, tv4, tv5, tv6;
        Button bidButton;

        ViewHolder(View v){
            id=(TextView)v.findViewById(R.id.id);
            t=(TextView)v.findViewById(R.id.t);
            tv=(TextView)v.findViewById(R.id.tv);
            tv1=(TextView)v.findViewById(R.id.tv1);
            tv2=(TextView)v.findViewById(R.id.tv2);
            tv3=(TextView)v.findViewById(R.id.tv3);
            tv4=(TextView)v.findViewById(R.id.tv4);
            tv5=(TextView)v.findViewById(R.id.tv5);
            tv6=(TextView)v.findViewById(R.id.tv6);
            bidButton = (Button)v.findViewById(R.id.bidButton);
        }


    }

}
