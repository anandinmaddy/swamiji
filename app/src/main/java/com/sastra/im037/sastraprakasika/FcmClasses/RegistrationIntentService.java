package com.sastra.im037.sastraprakasika.FcmClasses;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationIntentService extends IntentService {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String token = "";

        try {

            token = FirebaseInstanceId.getInstance().getToken();
            Log.i(TAG, "GCM Registration Token: " + token);
            //MyApplication.deviceId = token;
            SendDeviceidTOServer(token, this);
        } catch (Exception e) {
            Log.e(TAG, "Failed to complete token refresh", e);
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i(TAG, "FCM Registration Token: " + token);
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString("SendBirdFCMToken", token);
        e.commit();
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify con method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param deviceId The new token.
     */

    private void SendDeviceidTOServer(String deviceId, final Context context) {
        Log.d("TAG", deviceId);
        if (Session.getInstance(context, TAG).getIsLogin())
            new WebServices(context, TAG).saveDeviceId(Session.getInstance(context, TAG).getUserId(), deviceId, new VolleyResponseListerner() {
                @Override
                public void onResponse(JSONObject response) throws JSONException {

                }

                @Override
                public void onError(String message, String title) {

                }
            });
    }

}
