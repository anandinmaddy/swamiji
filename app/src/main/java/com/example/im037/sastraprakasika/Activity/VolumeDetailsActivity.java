package com.example.im037.sastraprakasika.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Adapter.ExpandableListAdapter;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Model.VolumeDetailsModel;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.SingletonClass.GetData;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.utils.Selected;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolumeDetailsActivity extends CommonActivity {

    @BindView(R.id.image)
    ImageView image;
    RelativeLayout common_dragview;
    public static final String TAG = VolumeDetailsActivity.class.getSimpleName();
    ArrayList<VolumeDetailsModel> arrayList = new ArrayList<>();
    @BindView(R.id.expandable_list)
    ExpandableListView expandableList;
    ArrayList<VolumeDetailsModel.FileDetailsModel> fileDetailsModels = new ArrayList<>();
    @BindView(R.id.description)
    TextView description;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_volume_details, getIntent().getStringExtra("data2"));
        ButterKnife.bind(this);
        GetData.getInstance().clearData();
        setSelected(Selected.DISCOURSES);
        //common_dragview = (RelativeLayout) findViewById(R.id.dragView);
        //common_dragview.setVisibility(View.GONE);
//
        setCatergory();


        Picasso.get().load(getIntent().getStringExtra("data3")).into(image);
        description.setText(getIntent().getStringExtra("data4"));

    }

    private void setCatergory() {
        new WebServices(VolumeDetailsActivity.this, TAG).getCategory_list(getIntent().getStringExtra("data"), getIntent().getStringExtra("data1"), new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
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
                    expandableList.setAdapter(new ExpandableListAdapter(VolumeDetailsActivity.this, GetData.getInstance().getCategorylist()));
                    lastExpandedPosition = 0;
                    expandableList.setFocusable(false);

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
                    CommonMethod.showSnackbar(image, response, VolumeDetailsActivity.this);
            }

            @Override
            public void onError(String message, String title) {
                CommonMethod.showSnackbar(image, message, VolumeDetailsActivity.this);
            }
        });
    }


}
