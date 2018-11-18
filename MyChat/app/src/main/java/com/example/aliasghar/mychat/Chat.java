package com.example.aliasghar.mychat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aliasghar.mychat.Adapters.MessagesAdapter;
import com.example.aliasghar.mychat.Models.Friends;
import com.example.aliasghar.mychat.Models.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    String messageReceiverId, messageReceiverName;

    Toolbar toolbar;
    TextView username, lastSeen;
    CircleImageView profileImage;

    EditText userMessage;
    ImageButton selectImageButton, sendMessageButton;

    private DatabaseReference rootRef;
    private FirebaseAuth auth;
    String messageSenderId;

    RecyclerView userMessageRecyclerView;

    private List<Messages> arrayList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageReceiverId = getIntent().getExtras().get("LIST_USER_ID").toString();
        messageReceiverName = getIntent().getExtras().get("USERNAME").toString();

        auth = FirebaseAuth.getInstance();
        messageSenderId = auth.getCurrentUser().getUid();

        rootRef = FirebaseDatabase.getInstance().getReference();

        toolbar = (Toolbar) findViewById(R.id.chatTab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.chat_custom_layout, null);
        actionBar.setCustomView(view);

        username = (TextView) findViewById(R.id.tv_username);
        lastSeen = (TextView) findViewById(R.id.tv_lastSeen);
        profileImage = (CircleImageView) findViewById(R.id.iv_profileImage);

        userMessage = (EditText) findViewById(R.id.et_userMessage);
        selectImageButton = (ImageButton) findViewById(R.id.btn_selectImage);
        sendMessageButton = (ImageButton) findViewById(R.id.btn_sendMessage);

        messagesAdapter = new MessagesAdapter(arrayList);

        userMessageRecyclerView = (RecyclerView) findViewById(R.id.usersMessageList);

        linearLayoutManager = new LinearLayoutManager(this);

        userMessageRecyclerView.setHasFixedSize(true);
        userMessageRecyclerView.setLayoutManager(linearLayoutManager);
        userMessageRecyclerView.setAdapter(messagesAdapter);
        fetchMessges();

        username.setText(messageReceiverName);

        rootRef.child("Users").child(messageReceiverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String online = dataSnapshot.child("online").getValue().toString();
                final String thumbImage = dataSnapshot.child("thumb").getValue().toString();

                Picasso.with(getBaseContext()).load(thumbImage).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.default_profile).into(profileImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                        Picasso.with(getBaseContext()).load(thumbImage).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.default_profile).into(profileImage);

                    }
                });

                if(online.equals("true")) {
                    lastSeen.setText("Online");
                }
                else {
                    LastSeenTime getTime = new LastSeenTime();
                    Long parseLastSeen = Long.parseLong(online);
                    String displayLastSeen = getTime.getTimeAgo(parseLastSeen, getApplicationContext()).toString();
                    lastSeen.setText(displayLastSeen);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }

            private void sendMessage() {
                String message = userMessage.getText().toString();

                if(message.equals("")) {

                }
                else {
                    String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverId;
                    String messageReceiverRef = "Messages/" + messageReceiverId + "/" + messageSenderId;

                    DatabaseReference userMessageKey = rootRef.child("Messages").child(messageSenderId)
                            .child(messageReceiverId).push();

                    String messagePushId = userMessageKey.getKey();

                    Map messageTextBody = new HashMap();

                    messageTextBody.put("message", message);
                    messageTextBody.put("seen", false);
                    messageTextBody.put("type", "text");
                    messageTextBody.put("time", ServerValue.TIMESTAMP);
                    messageTextBody.put("from", messageSenderId);

                    Map messageTextDetails = new HashMap();

                    messageTextDetails.put(messageSenderRef + "/" + messagePushId, messageTextBody);
                    messageTextDetails.put(messageReceiverRef + "/" + messagePushId, messageTextBody);

                    rootRef.updateChildren(messageTextDetails, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError != null) {
                                Log.d("Chat error", databaseError.getMessage().toString());
                            }
                            userMessage.setText("");
                        }
                    });



                }
            }
        });

    }

    private void fetchMessges() {

        rootRef.child("Messages").child(messageSenderId).child(messageReceiverId)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Messages messages = dataSnapshot.getValue(Messages.class);
                        arrayList.add(messages);
                        messagesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
