package com.example.im037.sastraprakasika.Fragment.NewFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.ClickEdit_Activity;
import com.example.im037.sastraprakasika.Activity.Playlist_detailed_Activity;
import com.example.im037.sastraprakasika.Adapter.Adapter_playlist_edit;
import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClickEditFragment extends Fragment {


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


    public ClickEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.edit_activity, container, false);


        img_song = (ImageView)view.findViewById(R.id.album_image_playlist_next_edit);
        img_title = (TextView)view.findViewById(R.id.album_image_title_next_edit);
        add_btn = (ImageView)view.findViewById(R.id.add_edit);
        add_text = (TextView)view.findViewById(R.id.text_edit);
        back_btn = (ImageView)view.findViewById(R.id.back12);
        recyclerView_edit = (RecyclerView)view.findViewById(R.id.playListRecyclerView_next_edit);
        cancel_txt = (TextView)view.findViewById(R.id.cancel_edit);
        done_txt = (TextView)view.findViewById(R.id.doneedit);



        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlayListDetailFragment fragment2 = new PlayListDetailFragment();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();

            }
        });

        done_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlayListDetailFragment fragment2 = new PlayListDetailFragment();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();


            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayListDetailFragment fragment2 = new PlayListDetailFragment();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();

            }
        });

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_edit.setLayoutManager(linearLayoutManager);
        // call the constructor of CustomAdapter to send the reference and data to Adapter
        Adapter_playlist_edit adapter_playlist_next = new Adapter_playlist_edit(titleImages_next,img_song_next,class_type,dur,getContext());
        recyclerView_edit.setAdapter(adapter_playlist_next); // set the Adapter to RecyclerView


        return view;
    }

}
