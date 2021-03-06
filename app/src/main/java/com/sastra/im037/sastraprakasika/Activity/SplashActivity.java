package com.sastra.im037.sastraprakasika.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;


public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();
    String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Session.getInstance(SplashActivity.this, TAG).getIsLogin()) {
                    startActivity(new Intent(SplashActivity.this, DashBoardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));

                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 2000);


    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

}
