package com.example.im037.sastraprakasika.Activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.R;

import butterknife.BindView;

public class NotificationSettingActivity extends CommonActivity {
    RelativeLayout common_dragview;
    @BindView(R.id.allow_noti_switch)
    Switch allowNotiSwitch;
    @BindView(R.id.sound_noti_switch)
    Switch soundNotiSwitch;
    @BindView(R.id.vibe_noti_switch)
    Switch vibeNotiSwitch;
    @BindView(R.id.donot_noti_switch)
    Switch donotNotiSwitch;
    @BindView(R.id.primary_content_switch)
    Switch primaryContentSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_notification_setting, "Notification Settings");
        //common_dragview = (RelativeLayout) findViewById(R.id.dragView);
        //common_dragview.setVisibility(View.GONE);
//        soundNotiSwitch.setTextOff(getString(R.string.sound_available_status));
//        soundNotiSwitch.setTextOn(getString(R.string.mute_status));
        //default to being available
//        soundNotiSwitch.setChecked(false);
        // attach an OnClickListener
//        soundNotiSwitch.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                // your click actions go here
//                Log.i("onToggleClicked", "ToggleClick Event Started");
//                //an AudioManager object, to change the volume settings
//                AudioManager amanager;
//                amanager = (AudioManager)getSystemService(AUDIO_SERVICE);
//
//                // Is the toggle on?
//                boolean on = ((ToggleButton)v).isChecked();
//
//                if (on) {
//                    Log.i("onToggleIsChecked", "ToggleClick Is On");
//                    //turn ringer silent
//                    amanager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                    Log.i("RINGER_MODE_SILENT", "Set to true");
//
//                    //turn off sound, disable notifications
//                    amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
//                    Log.i("STREAM_SYSTEM", "Set to true");
//                    //notifications
//                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
//                    Log.i("STREAM_NOTIFICATION", "Set to true");
//                    //alarm
//                    amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
//                    Log.i("STREAM_ALARM", "Set to true");
//                    //ringer
//                    amanager.setStreamMute(AudioManager.STREAM_RING, true);
//                    Log.i("STREAM_RING", "Set to true");
//                    //media
//                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
//                    Log.i("STREAM_MUSIC", "Set to true");
//
//                } else {
//                    Log.i("onToggleIsChecked", "ToggleClick Is Off");
//
//                    //turn ringer silent
//                    amanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                    Log.i(".RINGER_MODE_NORMAL", "Set to true");
//
//                    // turn on sound, enable notifications
//                    amanager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
//                    Log.i("STREAM_SYSTEM", "Set to False");
//                    //notifications
//                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
//                    Log.i("STREAM_NOTIFICATION", "Set to False");
//                    //alarm
//                    amanager.setStreamMute(AudioManager.STREAM_ALARM, false);
//                    Log.i("STREAM_ALARM", "Set to False");
//                    //ringer
//                    amanager.setStreamMute(AudioManager.STREAM_RING, false);
//                    Log.i("STREAM_RING", "Set to False");
//                    //media
//                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
//                    Log.i("STREAM_MUSIC", "Set to False");
//                }
//                Log.i("onToggleClicked", "ToggleClick Event Ended");
//            }
//        });
    }
}
