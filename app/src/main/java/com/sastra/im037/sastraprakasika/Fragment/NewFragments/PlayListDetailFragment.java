package com.sastra.im037.sastraprakasika.Fragment.NewFragments;


import android.content.IntentFilter;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Adapter.Adapter_Playlist_Next;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;
import com.sastra.im037.sastraprakasika.mediareceiver.NetworkStateReceiverListener;
import com.sastra.im037.sastraprakasika.mediaservice.ConnectivityReceiver;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListDetailFragment extends Fragment implements NetworkStateReceiverListener {
    ImageView imageView,back;
    TextView txtview,nosongs;
    RecyclerView recyclerView;
    // ImageView back;
    TextView titleView,playlistTxt;
    ShimmerFrameLayout shimmerFrameLayout;
    String player_id = "";
    TextView pageAction;
    TextView offlineLink;
    LinearLayout offlineViewer;
    public static final String TAG = PlayListDetailFragment.class.getSimpleName();

    ArrayList titleImages_next = new ArrayList<>(Arrays.asList("An Overview Of Yoga", "Intoduction into Human Pursuits","Right Action and Attribute"));
    ArrayList img_song_next = new ArrayList<>(Arrays.asList(R.drawable.vedanta,R.drawable.intro_vedanta,R.drawable.bagavad));
    ArrayList class_type = new ArrayList<>(Arrays.asList("Class-1","Class-2","Class-3"));
    ArrayList dur = new ArrayList<>(Arrays.asList("0:56:04","0:58:06","0:55:05"));

    public PlayListDetailFragment() {
        // Required empty public constplayListRecyclerView_nextructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playlist_detailed_listitems, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.playListRecyclerView_next);
        imageView = (ImageView)view.findViewById(R.id.album_image_playlist_next);
        txtview = (TextView)view.findViewById(R.id.album_image_title_next);
        playlistTxt = (TextView) view.findViewById(R.id.playList);
        shimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        nosongs = (TextView) view.findViewById(R.id.nosongs);
        pageAction = (TextView) getActivity().findViewById(R.id.pageAction);
        offlineLink = view.findViewById(R.id.offlineLectureLink);
        offlineViewer = view.findViewById(R.id.offlineViewer);

        if (getArguments()!= null && getArguments().getString("data") != null){
            txtview.setText(getArguments().getString("data"));
            player_id =getArguments().getString("player_id");

        }else {
            txtview.setText("Playlist");

        }


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


        shimmerFrameLayout.startShimmer();

        playlistTxt.setOnClickListener(new View.OnClickListener() {
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


      //  callWebService();

        pageAction.setVisibility(View.VISIBLE);
        pageAction.setText("Edit");

        pageAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickEditFragment fragment2 = new ClickEditFragment();
                Bundle bundle = new Bundle();
                bundle.putString("from","playlist");
                bundle.putString("data",txtview.getText().toString());
                bundle.putString("player_id",player_id);
                FragmentManager fragmentManager = getFragmentManager();
                fragment2.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        titleView = getActivity().findViewById(R.id.title);
        back = getActivity().findViewById(R.id.back);
       // back = getActivity().findViewById(R.id.back);

        back.setVisibility(View.VISIBLE);
        titleView.setText("Playlist");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.GONE);
                MyLibraryFragment fragment2 = new MyLibraryFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Constant.currentTab = 3;
                Constant.backPress = true;
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Constant.playListSongSync.clear();
        if (Constant.trackList.size() > 0){
            Constant.playListSongSync.clear();
            for (ItemSong track : Constant.arrayList_play){
                for (Integer trackno : Constant.trackList){
                    if (track.getTrackId().equalsIgnoreCase(String.valueOf(trackno))){
                        Constant.playListSongSync.add(track);
                    }
                }
            }

        }

        if(Constant.playListSongSync.size() <=0){
            recyclerView.setVisibility(View.GONE);
            nosongs.setVisibility(View.VISIBLE);
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Adapter_Playlist_Next adapter_playlist_next = new Adapter_Playlist_Next(Constant.playListSongSync,getContext());
        recyclerView.setAdapter(adapter_playlist_next);
        adapter_playlist_next.notifyDataSetChanged();

        // set a LinearLayoutManager with default vertical orientation

        return view;
    }





    private void callWebService() {
        new WebServices(getActivity().getApplicationContext(), TAG).getPlaylistSongs(Session.getInstance(getContext(), TAG).getUserId(),player_id, new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {

                Constant.trackList.clear();
                    if (response.optString("resultcode").equalsIgnoreCase("200")) {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        JSONArray contentArray = response.optJSONArray("data");
                        for (int i = 0; i < contentArray.length(); i++) {
                            JSONObject jsonObject = contentArray.optJSONObject(i);
                            Constant.trackList.add(Integer.parseInt(jsonObject.optString("track_id")));
                        }
                    }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(linearLayoutManager);

                // call the constructor of CustomAdapter to send the reference and data to Adapter
                Adapter_Playlist_Next adapter_playlist_next = new Adapter_Playlist_Next(Constant.playListSongSync,getContext());
                recyclerView.setAdapter(adapter_playlist_next);
                recyclerView.invalidate();



            }

            @Override
            public void onError(String message, String title) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        pageAction.setVisibility(View.GONE);
    }
    @Override
    public void onResume() {
        super.onResume();
        pageAction.setVisibility(View.VISIBLE);

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
