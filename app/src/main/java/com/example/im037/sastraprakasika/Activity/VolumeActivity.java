/*
package com.example.im037.sastraprakasika.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Model.VolumeModel;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Readmore.ReadMoreTextView;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.utils.Selected;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolumeActivity extends CommonActivity {
    public static final String TAG=VolumeActivity.class.getSimpleName();

    @BindView(R.id.image1)
    ImageView img_view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.description1)
    TextView desc;
    ArrayList<VolumeModel> arrayList = new ArrayList<>();
    RelativeLayout common_dragview;
    Context context;
    String ParentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.content_volume, getIntent().getStringExtra("data1"));
        ButterKnife.bind(this);
        //common_dragview = (RelativeLayout) findViewById(R.id.dragView);
        //common_dragview.setVisibility(View.GONE);
        setSelected(Selected.DISCOURSES);
        ReadMoreTextView description1 = (ReadMoreTextView) findViewById(R.id.description1);
        description1.setTrimLines(3);
        Picasso.get().load(getIntent().getStringExtra("data3")).into(img_view);
        desc.setText(getIntent().getStringExtra("data4"));
        ParentID = getIntent().getStringExtra("data");
        new WebServices(VolumeActivity.this, TAG).getCategory_list(getIntent().getStringExtra("data"), "", new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if(response.optString("resultcode").equalsIgnoreCase("200")){
                    for (int i = 0; i < response.optJSONArray("data").length(); i++) {
                        arrayList.add(new VolumeModel(
                                response.optJSONArray("data").optJSONObject(i).optString("name"),
                                response.optJSONArray("data").optJSONObject(i).optString("slug"),
                                response.optJSONArray("data").optJSONObject(i).optString("subid"),
                                response.optJSONArray("data").optJSONObject(i).optString("titlecount"),
                                response.optJSONArray("data").optJSONObject(i).optString("trackcount"),
                                response.optJSONArray("data").optJSONObject(i).optString("image_url"),
                                response.optJSONArray("data").optJSONObject(i).optString("description")));

                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(new VolumeAdapter(VolumeActivity.this,ParentID, arrayList));
                }

            }

            @Override
            public void onError(String message, String title) {

            }
        });

//        arrayList.add(new VolumeModel(R.drawable.intro_vedanta, "Introduction to Vedanta", "Volumes - 2"));
//        arrayList.add(new VolumeModel(R.drawable.vedanta, "Bhagavad-gita", "Volumes - 24"));
//        arrayList.add(new VolumeModel(R.drawable.bagavad, "Bhagavad-gita Summary", "Volumes - 3"));
//        arrayList.add(new VolumeModel(R.drawable.ubanishad, "Mundaka-upanisad", "Volumes - 4"));
//        arrayList.add(new VolumeModel(R.drawable.kena, "Kena-upanisad", "Volumes - 1"));


    }

    private class VolumeAdapter extends RecyclerView.Adapter {
        public VolumeAdapter(VolumeActivity volumeActivity, String parentID, ArrayList<VolumeModel> arrayList) {
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
*/
