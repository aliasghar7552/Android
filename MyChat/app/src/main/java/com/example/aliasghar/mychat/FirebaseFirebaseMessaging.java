package com.example.aliasghar.mychat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.*;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Aliasghar on 10/28/2018.
 */

public class FirebaseFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        String notificationTitle = remoteMessage.getNotification().getTitle();
        String notificationBody = remoteMessage.getNotification().getBody();

        String clickAction = remoteMessage.getNotification().getClickAction();
        String senderId = remoteMessage.getData().get("fromSenderId").toString();


        //getting notification when app is running
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.chatapp)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody);

        Intent resutIntent = new Intent(clickAction);
        resutIntent.putExtra("VISITUSERID", senderId);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resutIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        int notificationId = (int) System.currentTimeMillis();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(notificationId, builder.build());
    }
}
