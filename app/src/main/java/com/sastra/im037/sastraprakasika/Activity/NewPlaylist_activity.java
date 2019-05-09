package com.sastra.im037.sastraprakasika.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Common.CommonActivity;
import com.sastra.im037.sastraprakasika.Common.CommonMethod;
import com.sastra.im037.sastraprakasika.R;

public class NewPlaylist_activity extends CommonActivity {

    TextView cancel_txt, newplaylist_txt, done_txt;
    EditText playListTitle;
    LinearLayout addLayout;
    String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_new_playlist_details, "New Playlist");
        playListTitle = (EditText) findViewById(R.id.album_image_title_next_edit);
        cancel_txt = (TextView) findViewById(R.id.cancel_newplaylist);
        newplaylist_txt = (TextView) findViewById(R.id.new_playlist);
        done_txt = (TextView) findViewById(R.id.done_playlist);
        addLayout = findViewById(R.id.addPlaylist);


        done_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playListTitle.getText().toString().equalsIgnoreCase("")) {
                    CommonMethod.showSnackbar(playListTitle, "Please Enter Playlist Title", NewPlaylist_activity.this);
                } else {
                    title = playListTitle.getText().toString();
                    finish();
                }
            }
        });

        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playListTitle.getText().toString().equalsIgnoreCase("")) {
                    CommonMethod.showSnackbar(playListTitle, "Please Enter Playlist Title", NewPlaylist_activity.this);
                } else {
                    title = playListTitle.getText().toString();
                    Intent intent = new Intent(NewPlaylist_activity.this, MyLecture.class);
                    intent.putExtra("Title", title);
                    startActivity(intent);
                }

            }
        });


    }
}