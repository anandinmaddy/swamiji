package com.example.im037.sastraprakasika.Fragment;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Adapter.Adapter_playlist;
import com.example.im037.sastraprakasika.Fragment.NewFragments.NewPlaylistFragment;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.Model.PlayList;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.utils.TypeConvertor;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PlaylistsFragment extends Fragment {

    LinearLayout linearLayout;
    ArrayList titleImages = new ArrayList<>(Arrays.asList("Bhagavad-gita","Upanished"));
    ArrayList imageView = new ArrayList<>(Arrays.asList(R.drawable.intro_vedanta,R.drawable.bagavad));
    View view;
    TextView titleView;
    DiscousesAppDatabase db;
    List<PlayList> playLists;
    public static final String TAG = PlaylistsFragment.class.getSimpleName();

    ArrayList<ArrayList<ItemSong>> playList = new ArrayList<ArrayList<ItemSong>>();
    ArrayList<ItemSong> playlistnew = new ArrayList<>();
    ShimmerFrameLayout shimmerFrameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist,container,false);

        linearLayout = (LinearLayout)view.findViewById(R.id.playlist_layout_main);
        titleView = getActivity().findViewById(R.id.title);
       // titleView.setVisibility(View.GONE);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();

        shimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        for(Map.Entry<String, ArrayList<ItemSong>> listEntry : Constant.playListMap.entrySet()){
            playList.add(listEntry.getValue());
        }
        playlistnew.clear();
        playlistnew.addAll(Constant.playListSongs1);
        //Constant.playListSongs1.clear();
            linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPlaylistFragment fragment2 = new NewPlaylistFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        callWebservice();


        return view;
    }

    private void callWebservice() {

        new WebServices(getActivity().getApplicationContext(), TAG).getPlayLists(Session.getInstance(getContext(), TAG).getUserId(),  new VolleyResponseListerner() {
            List<PlayList> playLists ;

                @Override
                public void onResponse(JSONObject response) throws JSONException {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    if (response.optString("resultcode").equalsIgnoreCase("200")) {
                        db.playListDao().deleteAll();
                        playLists = TypeConvertor.stringToNestedDataPlayList(response.optJSONArray("data").toString());
                        try {
                            for (PlayList playList : playLists) {
                                db.playListDao().insertAll(playList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.playListRecyclerView);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        Adapter_playlist adapter_playlist = new Adapter_playlist(Constant.playListArray,getActivity(),getFragmentManager());
                        recyclerView.setAdapter(adapter_playlist); // set the Adapter to RecyclerView
                        recyclerView.invalidate();
                        Constant.playListSongs1.size();
                        Constant.playListArray.clear();
                        playLists = db.playListDao().getAll();

                        if (playLists.size() > 0){
                            Constant.playListArray.addAll(playLists);
                        }


                    }

                    }

                @Override
                public void onError(String message, String title) {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            });

        }


}
