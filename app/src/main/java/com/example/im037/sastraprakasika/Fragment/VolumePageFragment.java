package com.example.im037.sastraprakasika.Fragment;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.IntentFilter;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.VolumeActivity;
import com.example.im037.sastraprakasika.Activity.VolumeDetailsActivity;
import com.example.im037.sastraprakasika.Adapter.Adapter_playlist_edit;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.DiscoursesNewFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.DashBoardNewFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.MyLibraryFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.VolumeDetailsFragment;
import com.example.im037.sastraprakasika.Fragment.dummy.DummyContent.DummyItem;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.Model.VolumeModel;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.mediareceiver.NetworkStateReceiverListener;
import com.example.im037.sastraprakasika.mediaservice.ConnectivityReceiver;
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
public class VolumePageFragment extends Fragment implements View.OnClickListener, NetworkStateReceiverListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private View.OnClickListener mListener;
    ImageView img_view;
    //@BindView(R.id.recyclerView)
    // RecyclerView recyclerView;
    ArrayList<VolumeModel> volumeArrayList = new ArrayList<>();

    //TextView desc;
    ArrayList<VolumeModel> arrayList = new ArrayList<>();
    List<VolumeModel> volumeModelsList;

    RelativeLayout common_dragview;
    Context context;
    String ParentID,catid= "";
    RecyclerView recyclerView;
    VolumeAdapter volumeAdapter ;
    DiscousesAppDatabase db1;
    ShimmerFrameLayout mShimmerViewContainer;
    TextView aboutTxt,knowMoreTxt,titleView;
    ImageView back;
    DiscousesAppDatabase db;
    String backTitle,backImg;
    String descStr;
    String imgTitle,titleTxt;
    LinearLayout offlineViewer;
    TextView offlineLink;


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
       aboutTxt = (TextView) view.findViewById(R.id.aboutTxt);
        knowMoreTxt = (TextView) view.findViewById(R.id.knowMoreTxt);
     //   desc = (TextView) view.findViewById(R.id.description1);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
    //    titleView = (TextView) getActivity().findViewById(R.id.aboutTxt);
        back = (ImageView) getActivity().findViewById(R.id.back);
        db1= Room.databaseBuilder(context,
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();
        imgTitle = getArguments().getString("data3");
       Picasso.get().load(imgTitle).placeholder(R.drawable.placeholder_default)
                .into(img_view);
        descStr = getArguments().getString("data4");
        titleTxt =getArguments().getString("data1");
     //   desc.setText(descStr);img_view
        //knowMoreTxt.setText(getArguments().getString("data4"));
        titleView = (TextView) getActivity().findViewById(R.id.title);
        titleView.setText(titleTxt);
        ParentID = getArguments().getString("parentid");
        catid = getArguments().getString("data");
        mShimmerViewContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        offlineViewer = (LinearLayout) view.findViewById(R.id.offlineViewer);
        offlineLink = view.findViewById(R.id.offlineLectureLink);


        mShimmerViewContainer.startShimmer();
        if(db1 != null){
            volumeModelsList = db1.volumeModel().getAll();
        }
        back.setVisibility(View.VISIBLE);
        db = Room.databaseBuilder(getContext(),
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();

        aboutTxt.setText(titleTxt);

        if(getArguments() != null){
            backTitle = getArguments().getString("title");
        }
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
     //   desc.setMaxLines(Integer.MAX_VALUE);

       knowMoreTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data",descStr);
                bundle.putString("data2",getArguments().getString("data3"));
                bundle.putString("data1",aboutTxt.getText().toString());
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

        new WebServices(getContext(), VolumeActivity.class.getSimpleName()).getCategory_list(ParentID, "",catid, new VolleyResponseListerner() {
            List<VolumeModel> volumeModels = null;
            @Override
            public void onResponse(JSONObject response) throws JSONException {

                if(response.optString("resultcode").equalsIgnoreCase("200")){
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    db.volumeModel().deleteAll();
                    volumeModels = TypeConvertor.stringToNestedDataVolume(response.optJSONArray("data").toString());
                    try {
                        for (VolumeModel volumeModel : volumeModels){
                            db.volumeModel().insertAll(volumeModel);
                        }
                        volumeArrayList.clear();
                        volumeArrayList.addAll(volumeModels);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);

                    VolumeAdapter volumeAdapter = new VolumeAdapter(context,ParentID, volumeArrayList);
                    recyclerView.setAdapter(volumeAdapter);
                    }

                }

                @Override
                public void onError(String message, String title) {

                }
            });



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

    @Override
    public void networkAvailable() {
        offlineViewer.setVisibility(View.GONE);

    }

    @Override
    public void networkUnavailable() {
        offlineViewer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.GONE);
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
                    fragmentTransaction.addToBackStack(null);
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

    @Override
    public void onResume() {
        super.onResume();


        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        connectivityReceiver.addListener(this);
        getActivity().registerReceiver(connectivityReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

    }
}
