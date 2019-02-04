package com.example.im037.sastraprakasika.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Adapter.createPlaylistAdapter;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.R;

public class MyLecture extends CommonActivity {

    ListView lectureList;
    TextView cancel, done;
    createPlaylistAdapter adapter;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getExtras().getString("Title");
        setView(R.layout.activity_my_lecture,title);
        lectureList = findViewById(R.id.lectureList);


        adapter = new createPlaylistAdapter(Constant.arrayListOfflineSongs, MyLecture.this);
        lectureList.setAdapter(adapter);

        cancel = findViewById(R.id.cancel_newplaylist);
        done = findViewById(R.id.done_playlist);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
