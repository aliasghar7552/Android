package com.example.aliasghar.mychat;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Profile extends AppCompatActivity {

    Button sendFriendRequest, declineFriendRequest;
    TextView profileUsername, profileStatus;
    ImageView profileImage;

    private DatabaseReference userDataRef;
    private DatabaseReference friendRequestRef;
    private DatabaseReference friendsRef;
    private DatabaseReference notificationsRef;
    private FirebaseAuth auth;

    private String currentState;
    String senderUserId, receiverUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        userDataRef = FirebaseDatabase.getInstance().getReference().child("Users");
        receiverUserId = getIntent().getExtras().get("VISITUSERID").toString();

        friendRequestRef = FirebaseDatabase.getInstance().getReference().child("Friend_Requests");
        friendRequestRef.keepSynced(true);
        auth = FirebaseAuth.getInstance();
        senderUserId = auth.getCurrentUser().getUid();

        friendsRef = FirebaseDatabase.getInstance().getReference().child("Friends");
        friendsRef.keepSynced(true);

        notificationsRef = FirebaseDatabase.getInstance().getReference().child("Notifications");
        notificationsRef.keepSynced(true);

        sendFriendRequest = (Button) findViewById(R.id.btn_sendFriendRequest);
        declineFriendRequest = (Button) findViewById(R.id.btn_declineFriendRequest);

        profileUsername = (TextView) findViewById(R.id.tv_profileUsername);
        profileStatus = (TextView) findViewById(R.id.tv_profileStatus);

        profileImage = (ImageView) findViewById(R.id.userProfileImage);

        currentState = "NOT_FRIENDS";

        //getting data from database to profile
        userDataRef.child(receiverUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("username").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                profileUsername.setText(name);
                profileStatus.setText(status);
                Picasso.with(getBaseContext()).load(image).placeholder(R.drawable.default_profile).into(profileImage);

                //sending friend request button changing
                friendRequestRef.child(senderUserId)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.hasChild(receiverUserId)) {
                                        String req_type = dataSnapshot.child(receiverUserId).child("request_type").getValue().toString();

                                        if (req_type.equals("sent")) {
                                            currentState = "REQUEST_SENT";
                                            sendFriendRequest.setText("Cancel Friend Request");
                                        } else if (req_type.equals("received")) {
                                            currentState = "REQUEST_RECEIVED";
                                            sendFriendRequest.setText("Acccept Friend Request");
                                        }
                                    }
                                 else {
                                    friendsRef.child(senderUserId)
                                            .addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.hasChild(receiverUserId)) {
                                                        currentState = "FRIENDS";
                                                        sendFriendRequest.setText("UnFriend");
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if( ! senderUserId.equals(receiverUserId)) {
            //sending friend request to other users
            sendFriendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sendFriendRequest.setEnabled(false);

                    if (currentState.equals("NOT_FRIENDS")) {
                        sendFriendRequestMethod();
                    }

                    if (currentState.equals("REQUEST_SENT")) {
                        cancelFriendRequest();
                    }

                    if (currentState.equals("REQUEST_RECEIVED")) {
                        acceptFriendRequest();
                    }

                    if (currentState.equals("FRIENDS")) {
                        unFriend();
                    }
                }
            });
        }
        else {
            declineFriendRequest.setVisibility(View.INVISIBLE);
            sendFriendRequest.setVisibility(View.INVISIBLE);
        }
    }

            private void unFriend() {

                friendsRef.child(senderUserId).child(receiverUserId).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    friendsRef.child(receiverUserId).child(senderUserId).removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()) {
                                                        sendFriendRequest.setEnabled(true);
                                                        currentState = "NOT_FRIENDS";
                                                        sendFriendRequest.setText("Send Friend Request");
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }

            private void acceptFriendRequest() {

                Calendar calender = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                final String date = currentDate.format(calender.getTime());

                friendsRef.child(senderUserId).child(receiverUserId).child("date").setValue(date)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                friendsRef.child(receiverUserId).child(senderUserId).child("date").setValue(date)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                friendRequestRef.child(senderUserId).child(receiverUserId).removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()) {
                                                                    friendRequestRef.child(receiverUserId).child(senderUserId).removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if(task.isSuccessful()) {
                                                                                        sendFriendRequest.setEnabled(true);
                                                                                        currentState = "FRIENDS";
                                                                                        sendFriendRequest.setText("UnFriend");
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

            private void cancelFriendRequest() {

                friendRequestRef.child(senderUserId).child(receiverUserId).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    friendRequestRef.child(receiverUserId).child(senderUserId).removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()) {
                                                        sendFriendRequest.setEnabled(true);
                                                        currentState = "NOT_FRIENDS";
                                                        sendFriendRequest.setText("Send Friend Request");

                                                        declineFriendRequest.setVisibility(View.INVISIBLE);
                                                        declineFriendRequest.setEnabled(false);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }

            private void sendFriendRequestMethod() {

                friendRequestRef.child(senderUserId).child(receiverUserId).child("request_type").setValue("sent")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    friendRequestRef.child(receiverUserId).child(senderUserId).child("request_type").setValue("received")
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()) {

                                                        //notification on sending friend request
                                                        HashMap<String, String> notificationData = new HashMap<String, String>();
                                                        notificationData.put("from", senderUserId);
                                                        notificationData.put("type", "request");

                                                        notificationsRef.child(receiverUserId).push().setValue(notificationData)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()) {
                                                                            sendFriendRequest.setEnabled(true);
                                                                            currentState = "REQUEST_SENT";
                                                                            sendFriendRequest.setText("Cancel Friend Request");

                                                                            declineFriendRequest.setVisibility(View.INVISIBLE);
                                                                            declineFriendRequest.setEnabled(false);
                                                                        }
                                                                    }
                                                                });

                                                    }
                                                }
                                            });
                                }
                            }
                        });

            }
}
