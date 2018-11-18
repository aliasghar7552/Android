package com.example.aliasghar.mychat;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aliasghar.mychat.Models.Chats;
import com.example.aliasghar.mychat.Models.Requests;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.squareup.picasso.Request;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    private RecyclerView requestListView;
    private FirebaseAuth auth;
    private DatabaseReference friendRequestRef;
    private DatabaseReference usersRef;

    String onlineUserId;

    private View mainView;

    private DatabaseReference newFriendRequestRef;
    private DatabaseReference newFriendsRef;


    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mainView = inflater.inflate(R.layout.fragment_request, container, false);

        requestListView = (RecyclerView) mainView.findViewById(R.id.requestRecyclerView);

        auth = FirebaseAuth.getInstance();

        onlineUserId = auth.getCurrentUser().getUid();

        friendRequestRef = FirebaseDatabase.getInstance().getReference().child("Friend_Requests").child(onlineUserId);
        friendRequestRef.keepSynced(true);

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        usersRef.keepSynced(true);

        newFriendRequestRef = FirebaseDatabase.getInstance().getReference().child("Friend_Requests");
        newFriendsRef = FirebaseDatabase.getInstance().getReference().child("Friends");

        requestListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        requestListView.setLayoutManager(linearLayoutManager);

        return mainView;
    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Requests, RequestsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Requests, RequestsViewHolder>
                (Requests.class, R.layout.friend_request_layout, RequestsViewHolder.class, friendRequestRef) {
            @Override
            protected void populateViewHolder(final RequestsViewHolder viewHolder, Requests model, int position) {

                final String listUserId = getRef(position).getKey();

                DatabaseReference requestTypeRef = getRef(position).child("request_type").getRef();

                requestTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            String reqType = dataSnapshot.getValue().toString();

                            if(reqType.equals("received")) {
                                usersRef.child(listUserId).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(final DataSnapshot dataSnapshot) {

                                        final String username = dataSnapshot.child("username").getValue().toString();
                                        String userImage = dataSnapshot.child("thumb").getValue().toString();
                                        final String status = dataSnapshot.child("status").getValue().toString();

                                        viewHolder.setUsername(username);
                                        viewHolder.setStatus(status);
                                        viewHolder.setThumb(userImage, getContext());

                                        viewHolder.view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                CharSequence options[] = new CharSequence[]
                                                        {
                                                                "Accept Friend Request",
                                                                "Cancel Friend Request"

                                                        };

                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle("Friend Request");
                                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int position) {

                                                        if(position == 0) {

                                                            Calendar calender = Calendar.getInstance();
                                                            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                                                            final String date = currentDate.format(calender.getTime());

                                                            newFriendsRef.child(onlineUserId).child(listUserId).child("date").setValue(date)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            newFriendsRef.child(listUserId).child(onlineUserId).child("date").setValue(date)
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {

                                                                                            newFriendRequestRef.child(onlineUserId).child(listUserId).removeValue()
                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                            if(task.isSuccessful()) {
                                                                                                                newFriendRequestRef.child(listUserId).child(onlineUserId).removeValue()
                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                if(task.isSuccessful()) {
                                                                                                                                    Toast.makeText(getContext(), "Friend Request Accepted", Toast.LENGTH_SHORT).show();

                                                                                                                                }
                                                                                                                            }
                                                                                                                        });
                                                                                                            }
                                                                                                        }
                                                                                                    });
                                                                                        }
                                                                                    });
                                                                        }
                                                                    });

                                                        }

                                                        if(position == 1) {
//
                                                            newFriendRequestRef.child(onlineUserId).child(listUserId).removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()) {
                                                                                newFriendRequestRef.child(listUserId).child(onlineUserId).removeValue()
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if(task.isSuccessful()) {
                                                                                                    Toast.makeText(getContext(), "Friend Request Cancelled", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    });

                                                        }
                                                    }
                                                });
                                                builder.show();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                            else if(reqType.equals("sent")) {

                                usersRef.child(listUserId).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(final DataSnapshot dataSnapshot) {

                                        final String username = dataSnapshot.child("username").getValue().toString();
                                        String userImage = dataSnapshot.child("thumb").getValue().toString();
                                        final String status = dataSnapshot.child("status").getValue().toString();

                                        viewHolder.setUsername(username);
                                        viewHolder.setStatus(status);
                                        viewHolder.setThumb(userImage, getContext());

                                        Button accept = viewHolder.view.findViewById(R.id.btn_acceptFriendRequest);
                                        accept.setText("Request Sent");

                                        viewHolder.view.findViewById(R.id.btn_cancelFriendRequest).setVisibility(View.INVISIBLE);

                                        viewHolder.view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                CharSequence options[] = new CharSequence[]
                                                        {
                                                                "Cancel Friend Request"

                                                        };

                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setTitle("Friend Request Sent");
                                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int position) {

                                                        if(position == 0) {
//
                                                            newFriendRequestRef.child(onlineUserId).child(listUserId).removeValue()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful()) {
                                                                                newFriendRequestRef.child(listUserId).child(onlineUserId).removeValue()
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if(task.isSuccessful()) {
                                                                                                    Toast.makeText(getContext(), "Friend Request Cancelled", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }
                                                                        }
                                                                    });

                                                        }
                                                    }
                                                });
                                                builder.show();

                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





            }
        };

        requestListView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class RequestsViewHolder extends RecyclerView.ViewHolder {

        View view;
        public RequestsViewHolder(View itemView) {
            super(itemView);

            view = itemView;
        }

        public void setUsername(String username) {

            TextView name = (TextView) view.findViewById(R.id.tv_allUsersUsername);
            name.setText(username);
        }

        public void setStatus(String status) {
            TextView userStatus = (TextView) view.findViewById(R.id.tv_allUsersStatus);
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

    }

}
