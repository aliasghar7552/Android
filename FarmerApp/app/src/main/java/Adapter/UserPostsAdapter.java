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

import com.example.aliasghar.farmerapp.CropsBid;
import com.example.aliasghar.farmerapp.R;
import com.example.aliasghar.farmerapp.ViewBIds;

import java.util.List;

import Model.ModelOpenTenders;
import Model.ModelUserPosts;

/**
 * Created by Aliasghar on 7/17/2018.
 */

public class UserPostsAdapter extends BaseAdapter {

    Context context;
    List<ModelUserPosts> userPostsList;
    public UserPostsAdapter(Context context, List<ModelUserPosts> userPostsList){

        this.context = context;
        this.userPostsList = userPostsList;

        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userPostsList.size();
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

        final ModelUserPosts userPosts = userPostsList.get(i);

        viewHolder.id.setText(userPosts.getId());
        viewHolder.cropName.setText(userPosts.getCropName());
        viewHolder.cropQuality.setText(userPosts.getCropQuality());
        viewHolder.quantity.setText(userPosts.getQuantity());
        viewHolder.sDate.setText(userPosts.getsDate());
        viewHolder.cDate.setText(userPosts.getcDate());
        viewHolder.sPrice.setText(userPosts.getsPrice());
        viewHolder.fPrice.setText(userPosts.getfPrice());
        viewHolder.uPrice.setText(userPosts.getuPrice());

        viewHolder.bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked on " + userPosts.getCropName(),Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.viewBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ViewBIds.class);
                intent.putExtra("ID", userPosts.getId());
                context.startActivity(intent);

            }
        });
        return rootView;
    }
}
