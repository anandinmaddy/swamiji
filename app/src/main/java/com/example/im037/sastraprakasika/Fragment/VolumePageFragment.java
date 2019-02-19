package com.example.im037.sastraprakasika.Fragment;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.VolumeActivity;
import com.example.im037.sastraprakasika.Activity.VolumeDetailsActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.DiscoursesNewFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.DashBoardNewFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.VolumeDetailsFragment;
import com.example.im037.sastraprakasika.Fragment.dummy.DummyContent.DummyItem;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.Model.VolumeModel;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.utils.TypeConvertor;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class VolumePageFragment extends Fragment implements View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private View.OnClickListener mListener;
    ImageView img_view;
    //@BindView(R.id.recyclerView)
    // RecyclerView recyclerView;
    ArrayList<VolumeModel> volumeArrayList = new ArrayList<>();

    TextView desc;
    ArrayList<VolumeModel> arrayList = new ArrayList<>();
    List<VolumeModel> volumeModelsList;

    RelativeLayout common_dragview;
    Context context;
    String ParentID,catid= "";
    RecyclerView recyclerView;
    VolumeAdapter volumeAdapter ;
    DiscousesAppDatabase db1;
    ShimmerFrameLayout mShimmerViewContainer;
    TextView titleView;
    ImageView back;
    DiscousesAppDatabase db;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_volume, container, false);
        context = getActivity().getApplicationContext();
        img_view = (ImageView) view.findViewById(R.id.image1);
        desc = (TextView) view.findViewById(R.id.description1);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        titleView = (TextView) getActivity().findViewById(R.id.title);
        back = (ImageView) getActivity().findViewById(R.id.back);
        db1= Room.databaseBuilder(context,
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();
        Picasso.get().load(getArguments().getString("data3")).placeholder(R.drawable.placeholder_default)
                .into(img_view);
        desc.setText(getArguments().getString("data4"));
        ParentID = getArguments().getString("parentid");
        catid = getArguments().getString("data");
        mShimmerViewContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmer();
        if(db1 != null){
            volumeModelsList = db1.volumeModel().getAll();
        }
        back.setVisibility(View.VISIBLE);
        db = Room.databaseBuilder(getContext(),
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();
        if(getArguments() != null){
            titleView.setText(getArguments().getString("data1"));
        }else {
            titleView.setText("Vedanta Discouses");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle profileData = new Bundle();
                profileData.putString("data",ParentID);
                profileData.putString("data3",getArguments().getString("data3"));
                profileData.putString("data4",getArguments().getString("backCount"));

                DiscoursesNewFragment fragment2 = new DiscoursesNewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                back.setVisibility(View.GONE);
                fragment2.setArguments(profileData);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });
        desc.setMaxLines(Integer.MAX_VALUE);



        new WebServices(getContext(), VolumeActivity.class.getSimpleName()).getCategory_list(ParentID, "",catid, new VolleyResponseListerner() {
            List<VolumeModel> volumeModels = null;
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(response.optString("resultcode").equalsIgnoreCase("200")){
                    db.volumeModel().deleteAll();
                    volumeModels = TypeConvertor.stringToNestedDataVolume(response.optJSONArray("data").toString());
                    try {
                        for (VolumeModel volumeModel : volumeModels){
                            db.volumeModel().insertAll(volumeModel);
                        }
                        volumeArrayList.addAll(volumeModels);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onError(String message, String title) {

            }
        });


        volumeAdapter = new VolumeAdapter(context,ParentID, volumeArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(volumeAdapter);
        // Set the adapter

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
     /*   if (context instanceof View.OnClickListener) {
            mListener = (View.OnClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    private class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.CustomViewHolder> {
        Context context;
        ArrayList<VolumeModel> arrayList;
        String parentID;

        public VolumeAdapter(Context context, String parentID, ArrayList<VolumeModel> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
            this.parentID = parentID;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VolumeAdapter.CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.discourses_detailed_vedanta, parent, false));

        }


        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {

            Picasso.get().load(arrayList.get(position).getImage_url()).into(holder.image);
            holder.title.setText(arrayList.get(position).getName());
            holder.noOfVolcount.setText(arrayList.get(position).getTitlecount());
            //holder.trackcount.setText(arrayList.get(position).getTrackcount());
            holder.image.setAdjustViewBounds(true);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle profileData = new Bundle();
                    profileData.putString("data",parentID);
                    profileData.putString("data1",arrayList.get(position).getSubid());
                    profileData.putString("data2",arrayList.get(position).getName());
                    profileData.putString("data3",arrayList.get(position).getImage_url());
                    profileData.putString("data4",arrayList.get(position).getDescription());
                    //    mListener.onFragmentInteraction(PlayerConstants.VOLUME_FRAGMENT, profileData);

                    //  CommonActivity.startNewFragment(volumePageFragment, "volumeFragment");
                    VolumeDetailsFragment fragment2 = new VolumeDetailsFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragment2.setArguments(profileData);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();

                  //  CommonMethod.changeActivityData5(context, VolumeDetailsActivity.class, parentID,arrayList.get(position).getParentid(), arrayList.get(position).getName(),arrayList.get(position).getImage_url(),arrayList.get(position).getDescription());
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
