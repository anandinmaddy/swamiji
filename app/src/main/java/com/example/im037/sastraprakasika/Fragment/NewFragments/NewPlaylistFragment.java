package com.example.im037.sastraprakasika.Fragment.NewFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.MyLecture;
import com.example.im037.sastraprakasika.Activity.NewPlaylist_activity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Fragment.PlaylistsFragment;
import com.example.im037.sastraprakasika.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPlaylistFragment extends Fragment {

    TextView cancel_txt, newplaylist_txt, done_txt;
    EditText playListTitle;
    LinearLayout addLayout;
    String title;
    //TextView titleView;
   // ImageView back;


    public NewPlaylistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_new_playlist_details, container, false);

        playListTitle = (EditText) view.findViewById(R.id.album_image_title_next_edit);
        cancel_txt = (TextView) view.findViewById(R.id.cancel_newplaylist);
        newplaylist_txt = (TextView) view.findViewById(R.id.new_playlist);
        done_txt = (TextView) view.findViewById(R.id.done_playlist);
        addLayout = view.findViewById(R.id.addPlaylist);
       // titleView = getActivity().findViewById(R.id.title);
     //   back = getActivity().findViewById(R.id.back);


       /* back.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLibraryFragment fragment2 = new MyLibraryFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                back.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });*/


        done_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playListTitle.getText().toString().equalsIgnoreCase("")) {
                    CommonMethod.showSnackbar(playListTitle, "Please Enter Playlist Title", getActivity());
                } else {
                    title = playListTitle.getText().toString();
                    MyLibraryFragment fragment2 = new MyLibraryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from","playlist");
                    FragmentManager fragmentManager = getFragmentManager();
                    fragment2.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();
                }
            }
        });

        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLibraryFragment fragment2 = new MyLibraryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("from","playlist");
                FragmentManager fragmentManager = getFragmentManager();
                fragment2.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });

        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playListTitle.getText().toString().equalsIgnoreCase("")) {
                    CommonMethod.showSnackbar(playListTitle, "Please Enter Playlist Title", getActivity());
                } else {
                    title = playListTitle.getText().toString();

                    MyLectureFragment fragment2 = new MyLectureFragment();

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();

                    /*
                    Intent intent = new Intent(NewPlaylist_activity.this, MyLecture.class);
                    intent.putExtra("Title", title);
                    startActivity(intent);*/
                }

            }
        });


        return view;
    }

}
