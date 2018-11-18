package com.example.aliasghar.mychat.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aliasghar.mychat.Models.Messages;
import com.example.aliasghar.mychat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aliasghar on 11/10/2018.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>{

    private List<Messages> userMessagesList;
    private FirebaseAuth auth;


    public MessagesAdapter(List<Messages> userMessagesList) {
        this.userMessagesList = userMessagesList;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        Messages messages = userMessagesList.get(position);
        holder.userMessages.setText(messages.getMessage());

        auth = FirebaseAuth.getInstance();
        String onlineUserId = auth.getCurrentUser().getUid();
        String messageSenderId = messages.getFrom();

        if(onlineUserId.equals(messageSenderId)) {
            holder.userMessages.setBackgroundResource(R.drawable.message_background_two);
            holder.userMessages.setTextColor(Color.BLACK);
            holder.userMessages.setGravity(Gravity.RIGHT);
        }

        else {
            holder.userMessages.setBackgroundResource(R.drawable.message_background);
            holder.userMessages.setTextColor(Color.WHITE);
            holder.userMessages.setGravity(Gravity.LEFT);
        }

    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_message_layout, parent, false);
        return new MessageViewHolder(view);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView userMessages;
//        CircleImageView userProfileImage;

        public MessageViewHolder(View itemView) {
            super(itemView);

            userMessages = (TextView) itemView.findViewById(R.id.tv_userMessage);
//            userProfileImage = (CircleImageView) itemView.findViewById(R.id.circleImageView);
        }

    }

}
