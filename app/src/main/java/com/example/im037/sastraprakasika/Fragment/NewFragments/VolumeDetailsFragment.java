package com.example.im037.sastraprakasika.Fragment.NewFragments;


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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.VolumeDetailsActivity;
import com.example.im037.sastraprakasika.Adapter.ExpandableListAdapter;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Fragment.AboutDetailFragment;
import com.example.im037.sastraprakasika.Fragment.VolumePageFragment;
import com.example.im037.sastraprakasika.Model.VolumeDetailsModel;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Readmore.ReadMoreTextView;
import com.example.im037.sastraprakasika.SingletonClass.GetData;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.mediareceiver.NetworkStateReceiverListener;
import com.example.im037.sastraprakasika.mediaservice.ConnectivityReceiver;
import com.example.im037.sastraprakasika.utils.Selected;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.sql.CommonDataSource;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolumeDetailsFragment extends Fragment implements NetworkStateReceiverListener {

    Context context;
    ImageView image;

    RelativeLayout common_dragview;
    public static final String TAG = VolumeDetailsActivity.class.getSimpleName();
    ArrayList<VolumeDetailsModel> arrayList = new ArrayList<>();
    ExpandableListView expandableList;
    ArrayList<VolumeDetailsModel.FileDetailsModel> fileDetailsModels = new ArrayList<>();
    ReadMoreTextView description;
    private int lastExpandedPosition = -1;
    ShimmerFrameLayout mShimmerViewContainer;
    ImageView back;
    TextView titleView,knowMoretxt,pageTitle;
    String title,desc,imagetxt;
    LinearLayout offlineViewer;
    TextView offlineLink;

    public VolumeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_volume_details, container, false);
        context = getActivity().getApplicationContext();
        CommonActivity.setSelected(Selected.DISCOURSES);
        titleView = (TextView) getActivity().findViewById(R.id.title);
        knowMoretxt = (TextView) view.findViewById(R.id.knowMoreTxt);
        pageTitle = (TextView) view.findViewById(R.id.aboutTxt);
        back = (ImageView) getActivity().findViewById(R.id.back);
        image = view.findViewById(R.id.image);
        expandableList = view.findViewById(R.id.expandable_list);
        description = view.findViewById(R.id.description);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmer();
        offlineViewer = (LinearLayout) view.findViewById(R.id.offlineViewer);
        offlineLink = view.findViewById(R.id.offlineLectureLink);

        setCatergory();
        back.setVisibility(View.VISIBLE);

        if(getArguments() != null){
            title = getArguments().getString("data2");
            pageTitle.setText(title);
            desc = getArguments().getString("data4");
            imagetxt = getArguments().getString("data3");
            titleView.setText(getArguments().getString("data2"));
        }else {
            titleView.setText("Introduction to Vedanta");
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

        knowMoretxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data",desc);
                bundle.putString("data2",imagetxt);
                bundle.putString("data1",title);
                bundle.putString("from","volume");

                AboutDetailFragment fragment2 = new AboutDetailFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragment2.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        description.setTrimLines(6);
        Picasso.get().load(getArguments().getString("data3")).placeholder(R.drawable.placeholder_default)
                .into(image);

        description.setText(getArguments().getString("data4"));


        return view;
    }

    private void setCatergory() {
        new WebServices(context, TAG).getCategory_list(getArguments().getString("data"), getArguments().getString("data1"),"", new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
                GetData.getInstance().clearData();
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    for (int i = 0; i < response.optJSONObject("data").optJSONArray("datacontent").length(); i++) {
                        ArrayList<VolumeDetailsModel.FileDetailsModel> subCatergory = new ArrayList<>();
                        for (int j = 0; j < response.optJSONObject("data").optJSONArray("datacontent").optJSONObject(i).optJSONArray("track").length(); j++) {

                            VolumeDetailsModel.FileDetailsModel subCategoryModel = new VolumeDetailsModel.FileDetailsModel(
                                    response.optJSONObject("data").optJSONArray("datacontent").optJSONObject(i).optJSONArray("track").optJSONObject(j).optString("title"),
                                    response.optJSONObject("data").optJSONArray("datacontent").optJSONObject(i).optJSONArray("track").optJSONObject(j).optString("classname"),
                                    response.optJSONObject("data").optJSONArray("datacontent").optJSONObject(i).optJSONArray("track").optJSONObject(j).optString("time"));

                            subCatergory.add(subCategoryModel);

                            GetData.getInstance().addTosubCatergoryArray(subCategoryModel);
                        }
                        GetData.getInstance().addToCatergoryArray(new VolumeDetailsModel(
                                response.optJSONObject("data").optJSONArray("datacontent").optJSONObject(i).optString("postid"),
                                response.optJSONObject("data").optJSONArray("datacontent").optJSONObject(i).optString("subtitle"),
                                response.optJSONObject("data").optJSONArray("datacontent").optJSONObject(i).optString("volume_name"),
                                response.optJSONObject("data").optJSONArray("datacontent").optJSONObject(i).optString("trackcount"),
                                response.optJSONObject("data").optJSONArray("datacontent").optJSONObject(i).optString("songsamount"),
                                subCatergory));
                    }

                    expandableList.setAdapter(new ExpandableListAdapter(context, GetData.getInstance().getCategorylist()));
                    lastExpandedPosition = 0;

                    expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                        @Override
                        public void onGroupExpand(int groupPosition) {
                            if (lastExpandedPosition != -1
                                    && groupPosition != lastExpandedPosition) {
                                expandableList.collapseGroup(lastExpandedPosition);
                            }
                            lastExpandedPosition = groupPosition;
                        }
                    });
//                    expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//                        @Override
//                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                            Log.d("onGroupClick:", "worked");
//                            parent.expandGroup(groupPosition);
//                            return false;
//                        }
//                    });


                } else
                    CommonMethod.showSnackbar(image, response, getActivity());
            }

            @Override
            public void onError(String message, String title) {
                CommonMethod.showSnackbar(image, message, getActivity());
            }
        });
    }


    @Override
    public void networkAvailable() {
        offlineViewer.setVisibility(View.GONE);

    }

    @Override
    public void networkUnavailable() {
        offlineViewer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        connectivityReceiver.addListener(this);
        getActivity().registerReceiver(connectivityReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }
}
