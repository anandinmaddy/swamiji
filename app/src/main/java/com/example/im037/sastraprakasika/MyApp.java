package com.example.im037.sastraprakasika;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.im037.sastraprakasika.MusicPlayer.Constants;
import com.example.im037.sastraprakasika.MusicPlayer.PermissionManager;

import java.io.File;


/**
 * Created by Im033 on 5/9/2017.
 */

public class MyApp extends Application {
    public static String TAG = "MYAPP";
    private static MyApp sInstanse;
    private RequestQueue nRequestQueue;
    private static Context context;
    public MyApp() {

    }

    public static synchronized MyApp getInstanse() {
        return sInstanse;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstanse = this;
//        startService(new Intent(MyApp.this, RegistrationIntentService.class));
    }

    public RequestQueue getnRequestQueue() {
        if (nRequestQueue == null) {
            nRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return nRequestQueue;
    }


    public void addToRequestQueue(Request request) {
        getnRequestQueue().add(request);
    }
    public static void configureDevice() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionManager.getInstance(context).seek();
        }

        File dir = new File(Constants.DOWNLOAD_FILE_DIR);
        boolean s = false;
        if (!dir.exists()) {
            s = dir.mkdirs();
        }

        Log.d(TAG, "configureDevice : made directory " + s);

    }


}
