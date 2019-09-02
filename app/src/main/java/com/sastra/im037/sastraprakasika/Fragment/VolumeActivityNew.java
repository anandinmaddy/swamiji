
package com.sastra.im037.sastraprakasika.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Common.CommonActivity;
import com.sastra.im037.sastraprakasika.Model.VolumeModel;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.utils.Selected;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolumeActivityNew extends Fragment {
    public static final String TAG= VolumeActivityNew.class.getSimpleName();

    @BindView(R.id.image1)
    ImageView img_view;
//    @BindView(R.id.recyclerView)
  //  RecyclerView recyclerView;

    @BindView(R.id.description1)
    TextView desc;
    ArrayList<VolumeModel> arrayList = new ArrayList<>();
    RelativeLayout common_dragview;
    Context context;
    String ParentID;
    private LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        this.inflater = inflater;
        final View view = inflater.inflate(R.layout.content_volume, container, false);
    //    setView(R.layout.content_volume, getIntent().getStringExtra("data1"));
        ButterKnife.bind(this.getActivity());
        //common_dragview = (RelativeLayout) findViewById(R.id.dragView);
        //common_dragview.setVisibility(View.GONE);
        CommonActivity.setSelected(Selected.DISCOURSES);
       // ReadMoreTextView description1 = (ReadMoreTextView) findViewById(R.id.description1);
     //   description1.setTrimLines(3);
      /*  Picasso.get().load(getIntent().getStringExtra("data3")).into(img_view);
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
*/
//        arrayList.add(new VolumeModel(R.drawable.intro_vedanta, "Introduction to Vedanta", "Volumes - 2"));
//        arrayList.add(new VolumeModel(R.drawable.vedanta, "Bhagavad-gita", "Volumes - 24"));
//        arrayList.add(new VolumeModel(R.drawable.bagavad, "Bhagavad-gita Summary", "Volumes - 3"));
//        arrayList.add(new VolumeModel(R.drawable.ubanishad, "Mundaka-upanisad", "Volumes - 4"));
//        arrayList.add(new VolumeModel(R.drawable.kena, "Kena-upanisad", "Volumes - 1"));
return view;
    }
    private class VolumeAdapter extends RecyclerView.Adapter {
        public VolumeAdapter(VolumeActivityNew volumeActivity, String parentID, ArrayList<VolumeModel> arrayList) {
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

