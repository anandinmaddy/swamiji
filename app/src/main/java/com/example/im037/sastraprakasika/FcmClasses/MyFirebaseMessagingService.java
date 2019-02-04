package com.example.im037.sastraprakasika.FcmClasses;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.im037.sastraprakasika.Activity.DashBoardActivity;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG = "FCM Message";
    String channelId= "my_channel_02";
    Intent intent;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //sendNotification(remoteMessage.getNotification().getBody());
        String from = remoteMessage.getFrom();
        Log.d(TAG, "Body: "+remoteMessage.getNotification().getBody());
        Log.d(TAG,"Messge: "+remoteMessage.getNotification());
        String msg = remoteMessage.getData().get("message");
        String title = remoteMessage.getData().get("type");
        String Id = remoteMessage.getData().get("id");


        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + msg);
        Log.d(TAG, "title: " + title);
        Log.d(TAG, "Id: " + Id);
            sendNotification(msg, title, Id);

    }

    private void sendNotification(String message, String notititle, String userId) {
        newOrderNotification(message, notititle, userId);
    }

    private void newOrderNotification(String message, String notititle, String userId) {
//        if (type.equalsIgnoreCase("ridetaken")) {
//            NotifyCustomerSingleton.closActivity().ActionCompleted();
//        } else if (type.equalsIgnoreCase("customerrequestcancelled")) {
//            NotifyCustomerSingleton.closActivity().ActionCompleted();
//        } else {
//            Intent intent = getNotificationIntent(message, type, rideid, gender, lat, lang, sostype);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this,
////                    (int) Calendar.getInstance().getTimeInMillis() /* Request code */, intent,
//                    PendingIntent.FLAG_ONE_SHOT);
        intent = new Intent(this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.app_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(message)
                    .setAutoCancel(true)
//                    .setContentIntent(pendingIntent)
                    .setColor(getResources().getColor(R.color.colorPrimaryDark))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_launcher));
            mBuilder.setVibrate(new long[] { 200, 300, 500, 700 });
            mBuilder.setAutoCancel(true);
            mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//**added by IMO099**
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {

                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(channelId, getString(R.string.app_name), importance);
                notificationChannel.enableLights(true);
                notificationChannel.setName(getString(R.string.app_name));
                notificationChannel.setDescription(message);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                AudioAttributes att = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build();
                notificationChannel.setSound(defaultSoundUri,att);
                mBuilder.setChannelId(channelId);
                mBuilder.setAutoCancel(true);
//                mBuilder.setContentIntent(pendingIntent);
                notificationManager.createNotificationChannel(notificationChannel);

            }
            notificationManager.notify(0, mBuilder.build());
            notificationManager.cancel(0);
//        }
//        }
    }


}