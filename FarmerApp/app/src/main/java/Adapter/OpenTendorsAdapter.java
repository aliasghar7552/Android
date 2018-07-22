package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aliasghar.farmerapp.CropsBid;
import com.example.aliasghar.farmerapp.R;
import com.example.aliasghar.farmerapp.ViewBIds;

import java.util.List;

import Model.ModelOpenTenders;

/**
 * Created by Aliasghar on 7/17/2018.
 */

public class OpenTendorsAdapter extends BaseAdapter{

    Context context;
    List<ModelOpenTenders> openTendorsList;
    public OpenTendorsAdapter(Context context, List<ModelOpenTenders> openTendorsList){

        this.context = context;
        this.openTendorsList = openTendorsList;

        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return openTendorsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder{
        protected TextView id, cropName, cropQuality, quantity, sDate, cDate, sPrice, fPrice, uPrice;
        protected Button bidButton, viewBidButton;


    }

    private static LayoutInflater layoutInflater = null;
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();

        View rootView = layoutInflater.inflate(R.layout.layout,null);

        viewHolder.id = (TextView) rootView.findViewById(R.id.id);
        viewHolder.cropName = (TextView) rootView.findViewById(R.id.t);
        viewHolder.cropQuality = (TextView) rootView.findViewById(R.id.tv);
        viewHolder.quantity = (TextView) rootView.findViewById(R.id.tv1);
        viewHolder.sDate = (TextView) rootView.findViewById(R.id.tv2);
        viewHolder.cDate = (TextView) rootView.findViewById(R.id.tv3);
        viewHolder.sPrice = (TextView) rootView.findViewById(R.id.tv4);
        viewHolder.fPrice = (TextView) rootView.findViewById(R.id.tv5);
        viewHolder.uPrice = (TextView) rootView.findViewById(R.id.tv6);

        viewHolder.bidButton = (Button) rootView.findViewById(R.id.bidButton);
        viewHolder.viewBidButton = (Button) rootView.findViewById(R.id.viewBidButton);

        final ModelOpenTenders openTendors = openTendorsList.get(i);

        viewHolder.id.setText(openTendors.getId());
        viewHolder.cropName.setText(openTendors.getCropName());
        viewHolder.cropQuality.setText(openTendors.getCropQuality());
        viewHolder.quantity.setText(openTendors.getQuantity());
        viewHolder.sDate.setText(openTendors.getsDate());
        viewHolder.cDate.setText(openTendors.getcDate());
        viewHolder.sPrice.setText(openTendors.getsPrice());
        viewHolder.fPrice.setText(openTendors.getfPrice());
        viewHolder.uPrice.setText(openTendors.getuPrice());

        viewHolder.bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, CropsBid.class);
                intent.putExtra("ID", openTendors.getId());
                context.startActivity(intent);

            }
        });

        viewHolder.viewBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ViewBIds.class);
                intent.putExtra("ID", openTendors.getId());
                context.startActivity(intent);

            }
        });
        return rootView;
    }
}
