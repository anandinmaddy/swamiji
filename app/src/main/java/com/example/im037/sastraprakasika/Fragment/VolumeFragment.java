package com.example.im037.sastraprakasika.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.FragmentInteractionListener;
import com.example.im037.sastraprakasika.Activity.VolumeDetailsActivity;
import com.example.im037.sastraprakasika.Adapter.Searchdetailed_adapter;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
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

import butterknife.ButterKnife;


public class VolumeFragment extends Fragment {


    ImageView img_view;
    RecyclerView recyclerView;
    private LayoutInflater inflater;
    TextView desc;
    ArrayList<VolumeModel> arrayList = new ArrayList<>();
    RelativeLayout common_dragview;
    Bundle bundle;
    String ParentID;
    private Context context;
    private FragmentInteractionListener homeScreenListener;
    private onVolumeFragmentListener mListener;
    private LinearLayoutManager mLinearLayoutManager;
    VolumeAdapter volumeAdapter = new VolumeAdapter(getActivity(),"1", arrayList);
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        Log.d("sas","");
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        this.inflater = inflater;
        final View view = inflater.inflate(R.layout.content_volume, container, false);
        context = this.getActivity();
        ButterKnife.bind(getActivity());
        CommonActivity.setSelected(Selected.DISCOURSES);
        ReadMoreTextView description1 = (ReadMoreTextView) view.findViewById(R.id.description1);
        img_view = (ImageView) view.findViewById(R.id.image1);
        desc = (TextView) view.findViewById(R.id.description1);

        description1.setTrimLines(3);

        bundle = this.getArguments();

        if(bundle != null){
            Picasso.get().load(bundle.getString("data3")).into(img_view);
            desc.setText(bundle.getString("data4"));
            ParentID = bundle.getString("data");
        }

        mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(mLinearLayoutManager);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(volumeAdapter);

        new WebServices(context, "Volume").getCategory_list(ParentID, "", new VolleyResponseListerner() {
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
            /*        mLinearLayoutManager = new LinearLayoutManager(context);
                    mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

                    recyclerView.setLayoutManager(mLinearLayoutManager);
*/
                    VolumeAdapter searchdetailed_adapter = new VolumeAdapter(getActivity(),ParentID,arrayList);

                  //  recyclerView.setLayoutManager(new LinearLayoutManager(context));

                    recyclerView.setAdapter(searchdetailed_adapter);

                }

            }


            @Override
            public void onError(String message, String title) {

            }
        });

        //    Picasso.get().load(getIntent().getStringExtra("data3")).into(img_view);
        //    desc.setText(getIntent().getStringExtra("data4"));
        //  ParentID = getIntent().getStringExtra("data");
      /*  new WebServices(context, TAG).getCategory_list(getIntent().getStringExtra("data"), "", new VolleyResponseListerner() {
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
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new VolumeAdapter(context,ParentID, arrayList));
                }

            }

            @Override
            public void onError(String message, String title) {

            }
        });
*/



        return view;
    }


    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            mListener = (onVolumeFragmentListener) context;
            homeScreenListener = (FragmentInteractionListener) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(
                    "Activity must implement onVolomeListerner");
        }
    }

    public interface onVolumeFragmentListener {
        void onFragmentInteraction(String id);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        homeScreenListener = null;
    }


    private class VolumeAdapter extends RecyclerView.Adapter {
        Context context;
        ArrayList<VolumeModel> arrayList;
        String parentID;


        public VolumeAdapter(Context context, String parentID, ArrayList<VolumeModel> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
            this.parentID = parentID;
        }

        public VolumeAdapter() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.discourses_detailed_vedanta, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
           // Picasso.get().load(arrayList.get(position).getImage_url()).into(holder.title);
           // holder.title.setText(arrayList.get(position).getName());
          //  holder.noOfVolcount.setText(arrayList.get(position).getTitlecount());
            //holder.trackcount.setText(arrayList.get(position).getTrackcount());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonMethod.changeActivityData5(context, VolumeDetailsActivity.class, parentID,arrayList.get(position).getSubid(), arrayList.get(position).getName(),arrayList.get(position).getImage_url(),arrayList.get(position).getDescription());
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView title;
            TextView noOfVolcount;
//        @BindView(R.id.trackcount)
//        TextView trackcount;

            public CustomViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                title = (TextView) itemView.findViewById(R.id.title);
                noOfVolcount = (TextView) itemView.findViewById(R.id.noOfVolcount);
            }
        }

    }


}




