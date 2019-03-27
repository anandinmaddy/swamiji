package com.example.im037.sastraprakasika.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Adapter.Adapter_playlist_edit;
import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ClickEdit_Activity extends AppCompatActivity {


    ImageView img_song;
    TextView img_title;
    ImageView add_btn;
    TextView add_text;
    ImageView back_btn;
    RecyclerView recyclerView_edit;
    TextView cancel_txt,done_txt;

    ArrayList titleImages_next = new ArrayList<>(Arrays.asList("An Overview Of Yoga", "Intoduction into Human Pursuits","Right Action and Attribute"));
    ArrayList img_song_next = new ArrayList<>(Arrays.asList(R.drawable.vedanta,R.drawable.intro_vedanta,R.drawable.bagavad));
    ArrayList class_type = new ArrayList<>(Arrays.asList("Class-1","Class-2","Class-3"));
    ArrayList dur = new ArrayList<>(Arrays.asList("0:56:04","0:58:06","0:55:05"));


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        img_song = (ImageView)findViewById(R.id.album_image_playlist_next_edit);
        img_title = (TextView)findViewById(R.id.album_image_title_next_edit);
        add_btn = (ImageView)findViewById(R.id.add_edit);
        add_text = (TextView)findViewById(R.id.text_edit);
        back_btn = (ImageView)findViewById(R.id.back12);
        recyclerView_edit = (RecyclerView)findViewById(R.id.playListRecyclerView_next_edit);
        cancel_txt = (TextView)findViewById(R.id.cancel_edit);
        done_txt = (TextView)findViewById(R.id.doneedit);



        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClickEdit_Activity.this,Playlist_detailed_Activity.class);
                startActivity(intent);
                finish();

            }
        });

        done_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClickEdit_Activity.this,Playlist_detailed_Activity.class);
                startActivity(intent);
                finish();



            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_edit.setLayoutManager(linearLayoutManager);
        // call the constructor of CustomAdapter to send the reference and data to Adapter
/*
        Adapter_playlist_edit adapter_playlist_next = new Adapter_playlist_edit(titleImages_next,img_song_next,class_type,dur,getApplicationContext());
        recyclerView_edit.setAdapter(adapter_playlist_next); // set the Adapter to RecyclerView
*/

    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }
}
