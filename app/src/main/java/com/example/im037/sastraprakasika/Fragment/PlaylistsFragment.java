package com.example.im037.sastraprakasika.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.im037.sastraprakasika.Activity.NewPlaylist_activity;
import com.example.im037.sastraprakasika.Adapter.Adapter_playlist;
import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PlaylistsFragment extends Fragment {

    LinearLayout linearLayout;
    ArrayList titleImages = new ArrayList<>(Arrays.asList("Bhagavad-gita","Upanished"));
    ArrayList imageView = new ArrayList<>(Arrays.asList(R.drawable.intro_vedanta,R.drawable.bagavad));
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist,container,false);

        linearLayout = (LinearLayout)view.findViewById(R.id.playlist_layout_main);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NewPlaylist_activity.class);
                startActivity(intent);

            }
        });
        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.playListRecyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of CustomAdapter to send the reference and data to Adapter
        Adapter_playlist adapter_playlist = new Adapter_playlist(titleImages,imageView,getActivity());
        recyclerView.setAdapter(adapter_playlist); // set the Adapter to RecyclerView
        return view;
    }
}
