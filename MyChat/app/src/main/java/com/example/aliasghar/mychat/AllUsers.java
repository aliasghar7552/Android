package com.example.aliasghar.mychat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.aliasghar.mychat.Models.AllUsersModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUsers extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView allUsersList;

    private DatabaseReference allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        allUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        allUsers.keepSynced(true);

        toolbar = (Toolbar) findViewById(R.id.allUserAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allUsersList = (RecyclerView) findViewById(R.id.allUsersList);
        allUsersList.setHasFixedSize(true);
        allUsersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<AllUsersModel, AllUserViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<AllUsersModel, AllUserViewHolder>
                (AllUsersModel.class, R.layout.all_users_layout, AllUserViewHolder.class, allUsers) {
            @Override
            protected void populateViewHolder(AllUserViewHolder viewHolder, AllUsersModel model, final int position) {
                viewHolder.setUsername(model.getUsername());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setThumb(getApplicationContext(), model.getThumb());

                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String visitUserId = getRef(position).getKey();
                        Intent intent = new Intent(getBaseContext(), Profile.class);
                        intent.putExtra("VISITUSERID", visitUserId);
                        startActivity(intent);
                    }
                });
            }
        };

        allUsersList.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }


    public static class AllUserViewHolder extends RecyclerView.ViewHolder{

        View view;
        public AllUserViewHolder(View itemView) {
            super(itemView);

            view = itemView;
        }

        public void setUsername(String username) {

            TextView name = (TextView) view.findViewById(R.id.allUsersUsername);
            name.setText(username);
        }

        public void setStatus(String status) {

            TextView userStatus = (TextView) view.findViewById(R.id.allUsersStatus);
            userStatus.setText(status);
        }

        public void setThumb(final Context context, final String thumb) {

            final CircleImageView thimbImage = (CircleImageView) view.findViewById(R.id.allUsersProfileImage);
            Picasso.with(context).load(thumb).placeholder(R.drawable.default_profile).into(thimbImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                    Picasso.with(context).load(thumb).placeholder(R.drawable.default_profile).into(thimbImage);

                }
            });
        }
    }
}