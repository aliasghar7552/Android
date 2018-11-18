package com.example.aliasghar.mychat;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aliasghar.mychat.Models.Chats;
import com.example.aliasghar.mychat.Models.Friends;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    private View mainView;

    private RecyclerView chatsListView;
    private FirebaseAuth auth;
    private DatabaseReference friendRef;
    private DatabaseReference usersRef;

    String onlineUserId;


    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_chats, container, false);

        chatsListView = (RecyclerView) mainView.findViewById(R.id.rv_chatsList);

        auth = FirebaseAuth.getInstance();

        onlineUserId = auth.getCurrentUser().getUid();

        friendRef = FirebaseDatabase.getInstance().getReference().child("Friends").child(onlineUserId);
        friendRef.keepSynced(true);

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        usersRef.keepSynced(true);

        chatsListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        chatsListView.setLayoutManager(linearLayoutManager);

        return mainView;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Chats, ChatsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Chats, ChatsViewHolder>
                (Chats.class, R.layout.all_users_layout, ChatsViewHolder.class, friendRef) {
            @Override
            protected void populateViewHolder(final ChatsViewHolder viewHolder, Chats model, int position) {

                final String listUserId = getRef(position).getKey();


                usersRef.child(listUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

                        final String username = dataSnapshot.child("username").getValue().toString();
                        String userImage = dataSnapshot.child("thumb").getValue().toString();
                        final String status = dataSnapshot.child("status").getValue().toString();

                        if(dataSnapshot.hasChild("online")) {
                            String onlineStatus = (String) dataSnapshot.child("online").getValue().toString();

                            viewHolder.setOnlineIcon(onlineStatus);
                        }

                        viewHolder.setUsername(username);
                        viewHolder.setStatus(status);
                        viewHolder.setThumb(userImage, getContext());

                        viewHolder.view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(dataSnapshot.child("online").exists()) {
                                    Intent chatIntent = new Intent(getContext(), Chat.class);
                                    chatIntent.putExtra("LIST_USER_ID", listUserId);
                                    chatIntent.putExtra("USERNAME", username);
                                    startActivity(chatIntent);
                                }
                                else {
                                    usersRef.child(listUserId).child("online").setValue(ServerValue.TIMESTAMP)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    Intent chatIntent = new Intent(getContext(), Chat.class);
                                                    chatIntent.putExtra("LIST_USER_ID", listUserId);
                                                    chatIntent.putExtra("USERNAME", username);
                                                    startActivity(chatIntent);

                                                }
                                            });
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };

        chatsListView.setAdapter(firebaseRecyclerAdapter);
    }



    public static class ChatsViewHolder extends RecyclerView.ViewHolder {

        View view;
        public ChatsViewHolder(View itemView) {
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

        public void setThumb(final String userImage, final Context context) {

            final CircleImageView thumbImage = (CircleImageView) view.findViewById(R.id.allUsersProfileImage);
            Picasso.with(context).load(userImage).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.default_profile).into(thumbImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                    Picasso.with(context).load(userImage).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.default_profile).into(thumbImage);

                }
            });
        }

        public void setOnlineIcon(String onlineStatus) {

            ImageView onlineImage = (ImageView) view.findViewById(R.id.onlineIcon);

            if(onlineStatus.equals("true")) {
                onlineImage.setVisibility(View.VISIBLE);
            }
            else {
                onlineImage.setVisibility(View.INVISIBLE);
            }
        }
    }

}
