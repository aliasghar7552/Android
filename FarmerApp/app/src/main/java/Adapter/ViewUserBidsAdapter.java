package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aliasghar.farmerapp.R;
import com.example.aliasghar.farmerapp.ViewBIds;

import java.util.List;

import Model.ModelUserPosts;
import Model.ModelViewUserBids;

/**
 * Created by Aliasghar on 7/19/2018.
 */

public class ViewUserBidsAdapter extends BaseAdapter{

    Context context;
    List<ModelViewUserBids> viewUserBidsList;

    public ViewUserBidsAdapter(Context context, List<ModelViewUserBids> viewUserBidsList) {

        this.context = context;
        this.viewUserBidsList = viewUserBidsList;

        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return viewUserBidsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder {
        protected TextView id, cropName, cropQuality, quantity_kg, sDate, cDate, sPrice, fPrice, uPrice, quantity, unitRequest, unitPrice, totalPrice, bidPrice;
    }

    private static LayoutInflater layoutInflater = null;

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewUserBidsAdapter.ViewHolder viewHolder = new ViewUserBidsAdapter.ViewHolder();

        View rootView = layoutInflater.inflate(R.layout.view_bids_on_crop_layout, null);

        viewHolder.id = (TextView) rootView.findViewById(R.id.id);
        viewHolder.cropName = (TextView) rootView.findViewById(R.id.t);
        viewHolder.cropQuality = (TextView) rootView.findViewById(R.id.tv);
        viewHolder.quantity_kg = (TextView) rootView.findViewById(R.id.tv1);
        viewHolder.sDate = (TextView) rootView.findViewById(R.id.tv2);
        viewHolder.cDate = (TextView) rootView.findViewById(R.id.tv3);
        viewHolder.sPrice = (TextView) rootView.findViewById(R.id.tv4);
        viewHolder.fPrice = (TextView) rootView.findViewById(R.id.tv5);
        viewHolder.uPrice = (TextView) rootView.findViewById(R.id.tv6);

        viewHolder.quantity = (TextView) rootView.findViewById(R.id.quantity);
        viewHolder.unitRequest = (TextView) rootView.findViewById(R.id.unit_request);
        viewHolder.unitPrice = (TextView) rootView.findViewById(R.id.unit_price);
        viewHolder.totalPrice = (TextView) rootView.findViewById(R.id.total_price);
        viewHolder.bidPrice = (TextView) rootView.findViewById(R.id.bid_price);

        final ModelViewUserBids viewUserBids = viewUserBidsList.get(i);

        viewHolder.id.setText(viewUserBids.getId());
        viewHolder.cropName.setText(viewUserBids.getCropName());
        viewHolder.cropQuality.setText(viewUserBids.getCropQuality());
        viewHolder.quantity_kg.setText(viewUserBids.getQuantity_kg());
        viewHolder.sDate.setText(viewUserBids.getsDate());
        viewHolder.cDate.setText(viewUserBids.getcDate());
        viewHolder.sPrice.setText(viewUserBids.getsPrice());
        viewHolder.fPrice.setText(viewUserBids.getfPrice());
        viewHolder.uPrice.setText(viewUserBids.getuPrice());

        viewHolder.quantity.setText(viewUserBids.getQuantity());
        viewHolder.unitRequest.setText(viewUserBids.getUnitRequest());
        viewHolder.unitPrice.setText(viewUserBids.getUnitPrice());
        viewHolder.totalPrice.setText(viewUserBids.getTotalPrice());
        viewHolder.bidPrice.setText(viewUserBids.getBidPrice());

        return rootView;
    }
}
