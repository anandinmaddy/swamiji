package com.example.im037.sastraprakasika.Fragment.NewFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.MyLecture;
import com.example.im037.sastraprakasika.Adapter.createPlaylistAdapter;
import com.example.im037.sastraprakasika.Fragment.PlaylistsFragment;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLectureFragment extends Fragment {

    ListView lectureList;
    TextView cancel, done;
    createPlaylistAdapter adapter;
    String title;

    public MyLectureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_lecture, container, false);

        lectureList = view.findViewById(R.id.lectureList);

        cancel = view.findViewById(R.id.cancel_newplaylist);
        done = view.findViewById(R.id.done_playlist);

        adapter = new createPlaylistAdapter(Constant.arrayListOfflineSongs, getActivity());
        lectureList.setAdapter(adapter);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPlaylistFragment fragment2 = new NewPlaylistFragment();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPlaylistFragment fragment2 = new NewPlaylistFragment();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
