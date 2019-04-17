package com.example.im037.sastraprakasika.Fragment;


import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.DiscoursesNewFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.DashBoardNewFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.MyLibraryFragment;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.mediareceiver.NetworkStateReceiverListener;
import com.example.im037.sastraprakasika.mediaservice.ConnectivityReceiver;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutDetailFragment extends Fragment implements NetworkStateReceiverListener {

    ImageView back,contentImg;

    TextView contentTxt,titleTxt;
    Fragment fragment2 ;
    CardView headerCard;
    ImageView swamiJiImg;
    LinearLayout offlineViewer;
    TextView offlineLink;
    ScrollView fullview;


    public AboutDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_detail, container, false);

        TextView title = getActivity().findViewById(R.id.title);
        back = getActivity().findViewById(R.id.back);
        contentImg = (ImageView) view.findViewById(R.id.headerImage);
        contentTxt = (TextView) view.findViewById(R.id.headerTxt);
        titleTxt = (TextView) view.findViewById(R.id.textContent);
        headerCard = (CardView) view.findViewById(R.id.headerCard);
        swamiJiImg = (ImageView) view.findViewById(R.id.swamijiImg);
        back.setVisibility(View.VISIBLE);
        offlineViewer = (LinearLayout) view.findViewById(R.id.offlineViewer);
        offlineLink = view.findViewById(R.id.offlineLectureLink);
        fullview = view.findViewById(R.id.fullview);

        if(getArguments() != null && getArguments().getString("data") != null){
            titleTxt.setText(getArguments().getString("data"));
        }
        if(getArguments() != null && getArguments().getString("from") != null && getArguments().getString("from").equalsIgnoreCase("home")){
            headerCard.setVisibility(View.GONE);
            swamiJiImg.setVisibility(View.VISIBLE);
        }else{
            headerCard.setVisibility(View.VISIBLE);
            swamiJiImg.setVisibility(View.GONE);
        }
        if(getArguments() != null && getArguments().getString("data2") != null){

            Picasso.get()
                    .load(getArguments().getString("data2"))
                    .placeholder(R.drawable.placeholder_default)
                    .into(contentImg);
        }
        if(getArguments() != null && getArguments().getString("data1") != null){
            contentTxt.setText(getArguments().getString("data1"));
        }
        if(getArguments() != null && getArguments().getString("from") != null){
            if (getArguments().getString("from").equalsIgnoreCase("volume")){
                fragment2 = new VolumePageFragment();
            }else if(getArguments().getString("from").equalsIgnoreCase("dashboard")){
                fragment2 = new DiscoursesNewFragment();
            }
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

        return view;
    }

    @Override
    public void networkAvailable() {
        offlineViewer.setVisibility(View.GONE);
        fullview.setVisibility(View.VISIBLE);
    }

    @Override
    public void networkUnavailable() {
        offlineViewer.setVisibility(View.VISIBLE);
        fullview.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        connectivityReceiver.addListener(this);
        getActivity().registerReceiver(connectivityReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }
}
