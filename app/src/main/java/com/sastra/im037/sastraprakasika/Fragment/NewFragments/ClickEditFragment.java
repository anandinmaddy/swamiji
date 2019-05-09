package com.sastra.im037.sastraprakasika.Fragment.NewFragments;


import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sastra.im037.sastraprakasika.Adapter.Adapter_playlist_edit;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;
import com.sastra.im037.sastraprakasika.mediareceiver.NetworkStateReceiverListener;
import com.sastra.im037.sastraprakasika.mediaservice.ConnectivityReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClickEditFragment extends Fragment implements NetworkStateReceiverListener {


    ImageView img_song;
    TextView img_title;
    ImageView add_btn;
    TextView add_text;
    ImageView back_btn;
    RecyclerView recyclerView_edit;
    TextView cancel_txt,done_txt;
    ImageView deleteplaylist;
    LinearLayout addlectu;
    TextView offlineLink;
    LinearLayout offlineViewer;

    ArrayList titleImages_next = new ArrayList<>(Arrays.asList("An Overview Of Yoga", "Intoduction into Human Pursuits","Right Action and Attribute"));
    ArrayList img_song_next = new ArrayList<>(Arrays.asList(R.drawable.vedanta,R.drawable.intro_vedanta,R.drawable.bagavad));
    ArrayList class_type = new ArrayList<>(Arrays.asList("Class-1","Class-2","Class-3"));
    ArrayList dur = new ArrayList<>(Arrays.asList("0:56:04","0:58:06","0:55:05"));
    String titleTxt= "";
    String playerId= "";
    public static final String TAG = ClickEditFragment.class.getSimpleName();



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
        back_btn = getActivity().findViewById(R.id.back);
        recyclerView_edit = (RecyclerView)view.findViewById(R.id.playListRecyclerView_next_edit);
        cancel_txt = (TextView)view.findViewById(R.id.cancel_edit);
        done_txt = (TextView)getActivity().findViewById(R.id.pageAction);
        deleteplaylist = (ImageView) view.findViewById(R.id.deleteplaylist);
        addlectu = (LinearLayout) view.findViewById(R.id.addlectu);
        offlineLink = view.findViewById(R.id.offlineLectureLink);
        offlineViewer = view.findViewById(R.id.offlineViewer);

        if(getArguments() != null){
            titleTxt= getArguments().getString("data");
            img_title.setText(titleTxt);
            playerId = getArguments().getString("player_id");
        }
        done_txt.setVisibility(View.VISIBLE);
        done_txt.setText("Done");

        offlineLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("offline",true);
                MyLibraryFragment fragment2 = new MyLibraryFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Constant.currentTab = 2;
                Constant.backPress = true;
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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
                Constant.currentTab = 3;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        deleteplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWebservice(playerId);
            }
        });

        done_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callWebservice(playerId,titleTxt);



            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
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

        addlectu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title",titleTxt);
                bundle.putString("from","edit");
                bundle.putString("player_id",playerId);
                Constant.titleName = titleTxt;
                Constant.currentTab = 3;
                MyLectureFragment fragment2 = new MyLectureFragment();
                fragment2.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_edit.setLayoutManager(linearLayoutManager);
        // call the constructor of CustomAdapter to send the reference and data to Adapter
        Adapter_playlist_edit adapter_playlist_next = new Adapter_playlist_edit(Constant.playListSongSync,playerId,getContext(),getFragmentManager());
        recyclerView_edit.setAdapter(adapter_playlist_next); // set the Adapter to RecyclerView


        return view;
    }

    private void callWebservice(final String playerId, final String titleTxt) {
        new WebServices(getContext(), TAG).getPlaylistSongs(Session.getInstance(getContext(), TAG).getUserId(),playerId, new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {

                Constant.trackList.clear();
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    JSONArray contentArray = response.optJSONArray("data");
                    for (int i = 0; i < contentArray.length(); i++) {
                        JSONObject jsonObject = contentArray.optJSONObject(i);
                        Constant.trackList.add(Integer.parseInt(jsonObject.optString("track_id")));
                    }
                }

                Bundle profileData = new Bundle();
                profileData.putString("data",titleTxt);
                profileData.putString("player_id",playerId);
                PlayListDetailFragment fragment2 = new PlayListDetailFragment();
                fragment2.setArguments(profileData);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();




            }

            @Override
            public void onError(String message, String title) {

            }
        });

    }

    private void callWebservice(String playerId) {
        new WebServices(getActivity().getApplicationContext(), TAG).deletePlayLists(Session.getInstance(getContext(), TAG).getUserId(),playerId, new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {

                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    MyLibraryFragment fragment2 = new MyLibraryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from","playlist");
                    FragmentManager fragmentManager = getFragmentManager();
                    fragment2.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    Toast.makeText(getContext(),"Playlist deleted",Toast.LENGTH_SHORT).show();

                }




            }

            @Override
            public void onError(String message, String title) {
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        done_txt.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        done_txt.setVisibility(View.VISIBLE);

        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        connectivityReceiver.addListener(this);
        getActivity().registerReceiver(connectivityReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    public void networkAvailable() {
        offlineViewer.setVisibility(View.GONE);
    }

    @Override
    public void networkUnavailable() {
        offlineViewer.setVisibility(View.VISIBLE);

    }

}
