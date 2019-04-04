package com.example.im037.sastraprakasika.Fragment.NewFragments;


import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.MyLecture;
import com.example.im037.sastraprakasika.Adapter.Lectures_audio_list_adapter;
import com.example.im037.sastraprakasika.Adapter.createPlaylistAdapter;
import com.example.im037.sastraprakasika.Fragment.PlaylistsFragment;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLectureFragment extends Fragment {

    ListView lectureList;
    TextView done, new_playlist;
    ImageView back;
    createPlaylistAdapter adapter;
    String title;
    String playerId = "";
    DiscousesAppDatabase db;
    List<ItemSong> playListSongs = new ArrayList<>();
    public static final String TAG = MyLectureFragment.class.getSimpleName();
    ArrayList<Integer> trackSongs = new ArrayList<>();
    boolean isFromEdit = false;
    public MyLectureFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_lecture, container, false);

        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();

        lectureList = view.findViewById(R.id.lectureList);

        done = getActivity().findViewById(R.id.pageAction);
        new_playlist = view.findViewById(R.id.new_playlist);
        playListSongs = db.itemSongDao().getAll();
        back = (ImageView) getActivity().findViewById(R.id.back);
        Constant.playListSongsList.clear();
        Constant.playListSongsList.addAll(playListSongs);
        if (getArguments() != null){
            new_playlist.setText(getArguments().get("title").toString());
            if (getArguments().getString("from") != null && getArguments().getString("from").equalsIgnoreCase("edit")){
                isFromEdit = true;
                playerId = getArguments().getString("player_id");
            }else {
                isFromEdit = false;
            }
        }
        adapter = new createPlaylistAdapter(Constant.playListSongsList, getActivity());
        lectureList.setAdapter(adapter);
        done.setVisibility(View.VISIBLE);
        done.setText("Done");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.playListMap.clear();
                Constant.playListMap.put(new_playlist.getText().toString(), Constant.playListSongs1);
                //Constant.playListSongs1.clear();
                if (isFromEdit){
                    callWebserviceAdd();
                }else {
                    callWebservice();
                }
                MyLibraryFragment fragment2 = new MyLibraryFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Constant.currentTab = 3;
                Constant.backPress = true;
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

        return view;
    }

    private void callWebserviceAdd() {
        JSONArray jsonArray = new JSONArray();

        for (ItemSong tracks : Constant.playListSongs1){
            if(tracks.getTrackId() != null && !tracks.getTrackId().isEmpty()){
                //  trackSongs.add(Integer.valueOf(tracks.getTrackId()));
                jsonArray.put(tracks.getTrackId());
            }
        }

        new WebServices(getActivity().getApplicationContext(), TAG).addtoPlayLists(Session.getInstance(getContext(), TAG).getUserId(),playerId,jsonArray , new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    Log.d("ANDRO_ASYNC", "Lenght of file: " );

                }
            }

            @Override
            public void onError(String message, String title) {
                Log.d("ANDRO_ASYNC", "Lenght of file: 2");

            }
        });
    }

    private void callWebservice() {
        JSONArray jsonArray = new JSONArray();

        for (ItemSong tracks : Constant.playListSongs1){
                if(tracks.getTrackId() != null && !tracks.getTrackId().isEmpty()){
                  //  trackSongs.add(Integer.valueOf(tracks.getTrackId()));
                    jsonArray.put(tracks.getTrackId());
                }
              }

            new WebServices(getActivity().getApplicationContext(), TAG).savePlayLists(Session.getInstance(getContext(), TAG).getUserId(),"",new_playlist.getText().toString(),jsonArray , new VolleyResponseListerner() {

                @Override
                public void onResponse(JSONObject response) throws JSONException {
                    if (response.optString("resultcode").equalsIgnoreCase("200")) {
                        Log.d("ANDRO_ASYNC", "Lenght of file: " );

                        }
                    }

                @Override
                public void onError(String message, String title) {
                    Log.d("ANDRO_ASYNC", "Lenght of file: 2");

                }
            });
        }

    @Override
    public void onPause() {
        super.onPause();
        done.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        done.setVisibility(View.VISIBLE);
    }
}
