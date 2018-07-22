package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aliasghar.farmerapp.R;

import java.util.List;

import Model.ModelViewBids;

/**
 * Created by Aliasghar on 7/18/2018.
 */

public class ViewBidsAdapter extends BaseAdapter{

    Context context;
    List<ModelViewBids> viewBidsList;
    public ViewBidsAdapter(Context context, List<ModelViewBids> viewBidsList){

        this.context = context;
        this.viewBidsList = viewBidsList;

        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return viewBidsList.size();
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
        protected TextView quantity, unitRequest, unitPrice, totalPrice, bidPrice;
    }

    private static LayoutInflater layoutInflater = null;
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewBidsAdapter.ViewHolder viewHolder = new ViewBidsAdapter.ViewHolder();

        View rootView = layoutInflater.inflate(R.layout.bid_layout,null);

        viewHolder.quantity = (TextView) rootView.findViewById(R.id.quantity);
        viewHolder.unitRequest = (TextView) rootView.findViewById(R.id.unit_request);
        viewHolder.unitPrice = (TextView) rootView.findViewById(R.id.unit_price);
        viewHolder.totalPrice = (TextView) rootView.findViewById(R.id.total_price);
        viewHolder.bidPrice = (TextView) rootView.findViewById(R.id.bid_price);

        final ModelViewBids viewBids = viewBidsList.get(i);

        viewHolder.quantity.setText(viewBids.getQuantity());
        viewHolder.unitRequest.setText(viewBids.getUnitRequest());
        viewHolder.unitPrice.setText(viewBids.getUnitPrice());
        viewHolder.totalPrice.setText(viewBids.getTotalPrice());
        viewHolder.bidPrice.setText(viewBids.getBidPrice());

        return rootView;
    }
}
