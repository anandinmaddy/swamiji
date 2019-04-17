package com.example.im037.sastraprakasika.Fragment.NewFragments;


import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.Model.PlayList;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.mediareceiver.NetworkStateReceiverListener;
import com.example.im037.sastraprakasika.mediaservice.ConnectivityReceiver;
import com.example.im037.sastraprakasika.utils.TypeConvertor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPlaylistFragment extends Fragment implements NetworkStateReceiverListener {

    TextView cancel_txt, newplaylist_txt, done_txt;
    EditText playListTitle;
    LinearLayout addLayout;
    String title;
    ImageView back;
    TextView offlineLink;
    LinearLayout offlineViewer;


    //TextView titleView;
   // ImageView back;
    DiscousesAppDatabase db;
    public static final String TAG = NewPlaylistFragment.class.getSimpleName();


    public NewPlaylistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_new_playlist_details, container, false);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();


        playListTitle = (EditText) view.findViewById(R.id.album_image_title_next_edit);
        cancel_txt = (TextView) view.findViewById(R.id.cancel_newplaylist);
        newplaylist_txt = (TextView) view.findViewById(R.id.new_playlist);
        back = (ImageView) getActivity().findViewById(R.id.back);
        done_txt = (TextView) getActivity().findViewById(R.id.pageAction);
        addLayout = view.findViewById(R.id.addPlaylist);
        offlineLink = view.findViewById(R.id.offlineLectureLink);
        offlineViewer = view.findViewById(R.id.offlineViewer);

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
       // titleView = getActivity().findViewById(R.id.title);
     //   back = getActivity().findViewById(R.id.back);
        done_txt.setVisibility(View.GONE);
        done_txt.setText("Done");
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
        title = playListTitle.getText().toString();
        back.setVisibility(View.VISIBLE);
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

        done_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playListTitle.getText().toString().equalsIgnoreCase("")) {
                    CommonMethod.showSnackbar(playListTitle, "Please Enter Playlist Title", getActivity());
                } else {
                    CommonMethod.showSnackbar(playListTitle, "Please Click on Lectures", getActivity());

                    // callWebservice();

                 /*   MyLibraryFragment fragment2 = new MyLibraryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("from","playlist");
                    FragmentManager fragmentManager = getFragmentManager();
                    fragment2.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();*/

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
                    Bundle bundle = new Bundle();
                    bundle.putString("title",playListTitle.getText().toString());
                    Constant.titleName = title;
                    title = playListTitle.getText().toString();
                    MyLectureFragment fragment2 = new MyLectureFragment();
                    fragment2.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.addToBackStack(null);
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

    private void callWebservice() {
        final DiscousesAppDatabase db;

        db = Room.databaseBuilder(getContext(),
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();


        new WebServices(getContext(), TAG).getPlayLists(Session.getInstance(getContext(), TAG).getUserId(),  new VolleyResponseListerner() {
            List<PlayList> playLists ;

            @Override
            public void onResponse(JSONObject response) throws JSONException {

                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    mProgressDialog.dismiss();
                    db.playListDao().deleteAll();
                    playLists = TypeConvertor.stringToNestedDataPlayList(response.optJSONArray("data").toString());
                    try {
                        for (PlayList playList : playLists) {
                            db.playListDao().insertAll(playList);
                        }
                    } catch (Exception e) {
                        mProgressDialog.dismiss();

                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(String message, String title) {
                mProgressDialog.dismiss();

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        done_txt.setVisibility(View.GONE);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

    }

    @Override
    public void onResume() {
        super.onResume();
        done_txt.setVisibility(View.GONE);
        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        connectivityReceiver.addListener(this);
        getActivity().registerReceiver(connectivityReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));


    }

    @Override
    public void onDetach() {
        super.onDetach();
        MyLibraryFragment fragment2 = new MyLibraryFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Constant.currentTab = 3;
        Constant.backPress = true;
        fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
