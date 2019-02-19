package com.example.im037.sastraprakasika.Fragment.NewFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.VolumeDetailsActivity;
import com.example.im037.sastraprakasika.Adapter.ExpandableListAdapter;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Fragment.VolumePageFragment;
import com.example.im037.sastraprakasika.Model.VolumeDetailsModel;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Readmore.ReadMoreTextView;
import com.example.im037.sastraprakasika.SingletonClass.GetData;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
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
public class VolumeDetailsFragment extends Fragment {

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
    TextView titleView;
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
        back = (ImageView) getActivity().findViewById(R.id.back);        image = view.findViewById(R.id.image);
        expandableList = view.findViewById(R.id.expandable_list);
        description = view.findViewById(R.id.description);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmer();
        setCatergory();
        back.setVisibility(View.VISIBLE);

        if(getArguments() != null){
            titleView.setText(getArguments().getString("data2"));
        }else {
            titleView.setText("Introduction to Vedanta");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardNewFragment fragment2 = new DashBoardNewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                back.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
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

}
