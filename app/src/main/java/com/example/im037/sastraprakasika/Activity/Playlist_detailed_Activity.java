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

import com.example.im037.sastraprakasika.Adapter.Adapter_Playlist_Next;
import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Playlist_detailed_Activity extends AppCompatActivity{


    ImageView imageView;
    TextView txtview;
    RecyclerView recyclerView;
   // ImageView back;
    TextView edit_text;


    ArrayList titleImages_next = new ArrayList<>(Arrays.asList("An Overview Of Yoga", "Intoduction into Human Pursuits","Right Action and Attribute"));
    ArrayList img_song_next = new ArrayList<>(Arrays.asList(R.drawable.vedanta,R.drawable.intro_vedanta,R.drawable.bagavad));
    ArrayList class_type = new ArrayList<>(Arrays.asList("Class-1","Class-2","Class-3"));
    ArrayList dur = new ArrayList<>(Arrays.asList("0:56:04","0:58:06","0:55:05"));
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_detailed_listitems);
        recyclerView = (RecyclerView)findViewById(R.id.playListRecyclerView_next);
        imageView = (ImageView)findViewById(R.id.album_image_playlist_next);
        txtview = (TextView)findViewById(R.id.album_image_title_next);
        edit_text = (TextView)findViewById(R.id.edittext);

//        back = findViewById(R.id.back11);

      /*  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                         finish();
            }
        });
*/


        edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Playlist_detailed_Activity.this,ClickEdit_Activity.class);
                startActivity(intent);
                finish();
            }
        });



        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of CustomAdapter to send the reference and data to Adapter
        Adapter_Playlist_Next adapter_playlist_next = new Adapter_Playlist_Next(titleImages_next,img_song_next,class_type,dur,getApplicationContext());
        recyclerView.setAdapter(adapter_playlist_next); // set the Adapter to RecyclerView
    }
}
