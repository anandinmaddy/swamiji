package com.example.im037.sastraprakasika.FcmClasses;

import android.app.Service;
import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        //String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d("FCM TOKEN: ", refreshedToken);
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
