package com.example.im037.sastraprakasika.Fragment.NewFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.im037.sastraprakasika.Adapter.Adapter_Playlist_Next;
import com.example.im037.sastraprakasika.Fragment.NewFragments.dummy.TopicsDetailsFragment;
import com.example.im037.sastraprakasika.Fragment.PlaylistsFragment;
import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListDetailFragment extends Fragment {
    ImageView imageView,back;
    TextView txtview;
    RecyclerView recyclerView;
    // ImageView back;
    TextView edit_text,titleView;


    ArrayList titleImages_next = new ArrayList<>(Arrays.asList("An Overview Of Yoga", "Intoduction into Human Pursuits","Right Action and Attribute"));
    ArrayList img_song_next = new ArrayList<>(Arrays.asList(R.drawable.vedanta,R.drawable.intro_vedanta,R.drawable.bagavad));
    ArrayList class_type = new ArrayList<>(Arrays.asList("Class-1","Class-2","Class-3"));
    ArrayList dur = new ArrayList<>(Arrays.asList("0:56:04","0:58:06","0:55:05"));

    public PlayListDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playlist_detailed_listitems, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.playListRecyclerView_next);
        imageView = (ImageView)view.findViewById(R.id.album_image_playlist_next);
        txtview = (TextView)view.findViewById(R.id.album_image_title_next);
        edit_text = (TextView)view.findViewById(R.id.edittext);

        if (getArguments()!= null && getArguments().getString("data") != null){
            txtview.setText(getArguments().getString("data"));

        }else {
            txtview.setText("Playlist");

        }
        edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickEditFragment fragment2 = new ClickEditFragment();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();

            }
        });

        titleView = getActivity().findViewById(R.id.title);
        back = getActivity().findViewById(R.id.back);


        titleView.setText("Playlist");




        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of CustomAdapter to send the reference and data to Adapter
        Adapter_Playlist_Next adapter_playlist_next = new Adapter_Playlist_Next(titleImages_next,img_song_next,class_type,dur,getContext());
        recyclerView.setAdapter(adapter_playlist_next);

        return view;
    }


}
