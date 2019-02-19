package com.example.im037.sastraprakasika.Fragment.NewFragments;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.DashBoardActivity;
import com.example.im037.sastraprakasika.Activity.FragmentInteractionListener;
import com.example.im037.sastraprakasika.Activity.SpaceItemdecoration;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.DiscoursesNewFragment;
import com.example.im037.sastraprakasika.Fragment.VolumePageFragment;
import com.example.im037.sastraprakasika.Model.DiscoursesModel;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.Model.VolumeModel;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.utils.AppPreference;
import com.example.im037.sastraprakasika.utils.Selected;
import com.example.im037.sastraprakasika.utils.TypeConvertor;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class DashBoardNewFragment extends Fragment  {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private View.OnClickListener mListener;

    String parentID;
    ImageView vedanta, discourses, back;
    RecyclerView discourseView;
    TextView content;
    boolean isTextViewClicked = false;
    LinearLayout playerLayout;
    boolean doubleBackToExitPressedOnce = false;
    ArrayList<DiscoursesModel> discoursesModels = new ArrayList<>();
    ArrayList<VolumeModel> volumeArrayList = new ArrayList<>();

    RelativeLayout common_dragview;
    public static final String TAG = DashBoardNewFragment.class.getSimpleName();
    Handler mHandler = new Handler();
    private static boolean isHomeActivityRunning;
    List<DiscoursesModel> discoursesModelsList;
    DiscousesAppDatabase db;
    LinearLayout homeView;
    ShimmerFrameLayout mShimmerViewContainer;
    ScrollView itemViewlayout;
    TextView titleView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DashBoardNewFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dash_board, container, false);

        isHomeActivityRunning = true;
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();
        CommonActivity.setSelected(Selected.DISCOURSES);
        titleView = (TextView) getActivity().findViewById(R.id.title);

        discourses = view.findViewById(R.id.discourses);
        back = getActivity().findViewById(R.id.back);
//        vedanta = findViewById(R.id.vedanta);
        content = view.findViewById(R.id.discoursesContent);
        //common_dragview = (RelativeLayout) findViewById(R.id.dragView);
        discourseView = (RecyclerView) view.findViewById(R.id.discoursesRecyclerview);
        Stetho.initializeWithDefaults(getActivity().getApplicationContext());
        content.setMaxLines(Integer.MAX_VALUE);
        homeView = (LinearLayout) view.findViewById(R.id.homeView);
        //common_dragview.setVisibility(View.VISIBLE);
//        playerLayout = findViewById(R.id.playerLayout);
        itemViewlayout = (ScrollView) view.findViewById(R.id.itemViewlayout);
        mShimmerViewContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmer();
//        playerLayout.setVisibility(View.GONE);
//
        titleView.setVisibility(View.VISIBLE);
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setText("Discourses");

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked) {
                    // content.setMaxLines(6);
                    isTextViewClicked = false;
                } else {
                    content.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                }
            }
        });


        if(db != null){
            discoursesModelsList = db.userDao().getAll();
        }

        back.setVisibility(View.GONE);
        //    setCommonProgressBar(View.VISIBLE);

          callWebservice();

     /*   if(discoursesModelsList != null && discoursesModelsList.size() > 0){
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            itemViewlayout.setVisibility(View.VISIBLE);
            discoursesModels.addAll(discoursesModelsList);
            discourseView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
            discourseView.setAdapter(new CategoryRecyclerviewAdapter(getApplicationContext(), discoursesModels,parentID,DashBoardActivity.this));

        }else{
            callWebservice();

        }*/

        //SetAboutDetail();



        return view;
    }



    private void callWebservice() {
        new WebServices(getActivity(), TAG).getCategory_list("", "","", new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                //hideCommonProgressBar();
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
                itemViewlayout.setVisibility(View.VISIBLE);
                ArrayList<DiscoursesModel> test = new ArrayList<>();
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    SpaceItemdecoration decoration = new SpaceItemdecoration(16);
                    discourseView.addItemDecoration(decoration);
                    db.userDao().getAll();
                    String jsonString = response.toString(); //http request
                    DiscoursesModel data = new DiscoursesModel();
                    Gson gson = new Gson();
                    data = gson.fromJson(jsonString, DiscoursesModel.class);
                    List<DiscoursesModel> discoursesModelsNew = TypeConvertor.stringToNestedData(response.optJSONArray("data").toString());
                    for (DiscoursesModel discoursesModel : discoursesModelsNew) {
                        db.userDao().insertAll(discoursesModel);
                    }


                    discoursesModels.addAll(discoursesModelsNew);
                    discourseView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
                    discourseView.setAdapter(new CategoryRecyclerviewAdapter(getActivity().getApplicationContext(), discoursesModels, parentID));
                    AppPreference.putLong(Constant.LAST_SYNC, new Date().getTime(), getActivity().getApplicationContext());
                } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                    CommonMethod.showSnackbar(discourseView, response, getActivity());
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });

        //   String response = CommonActivity.getPagedetails(getApplicationContext());

        new WebServices(getActivity().getApplicationContext(), TAG).getCategory_list("", "","", new VolleyResponseListerner() {
            List<VolumeModel> volumeModels = null;

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    db.volumeModel().deleteAll();
                    volumeModels = TypeConvertor.stringToNestedDataVolume(response.optJSONArray("data").toString());
                    try {
                        for (VolumeModel volumeModel : volumeModels) {
                            db.volumeModel().insertAll(volumeModel);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onError(String message, String title) {

            }
        });



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
    }



    public class CategoryRecyclerviewAdapter extends RecyclerView.Adapter<CategoryRecyclerviewAdapter.Customview> {
        Context context;
        ArrayList<DiscoursesModel> discoursesModels;
        String parentID;
        private FragmentInteractionListener mListener;

        public CategoryRecyclerviewAdapter(Context applicationContext, ArrayList<DiscoursesModel> discoursesModels, String parentID) {
            this.context = applicationContext;
            this.discoursesModels = discoursesModels;
            this.parentID = parentID;
        }

        @NonNull
        @Override
        public Customview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.items_items_dashboard, parent, false);

            return new Customview(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryRecyclerviewAdapter.Customview holder, final int position) {
            Picasso.get()
                    .load(discoursesModels.get(position).getImage_url())
                    .placeholder(R.drawable.placeholder_default)
                    .into(holder.topics_image);
            Log.d("value",discoursesModels.get(position).getName());
            String value=discoursesModels.get(position).getName();



            holder.song_title.setText(value);
            holder.topic_no.setText(discoursesModels.get( position ).getTopiccount());
            holder.volume_no.setText(discoursesModels.get(position).getTrackcount());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  if (null != mListener) {
                        Bundle profileData = new Bundle();
                    profileData.putString("data",discoursesModels.get(position).getParentid());
                    profileData.putString("data1",discoursesModels.get(position).getName());
                    profileData.putString("data3",discoursesModels.get(position).getImage_url());
                    profileData.putString("data4",discoursesModels.get(position).getDescription());
                    profileData.putString("data5",discoursesModels.get(position).getTopiccount());
                    profileData.putString("data6",discoursesModels.get(position).getVolumecount());

                    //    mListener.onFragmentInteraction(PlayerConstants.VOLUME_FRAGMENT, profileData);

                      //  CommonActivity.startNewFragment(volumePageFragment, "volumeFragment");
                    DiscoursesNewFragment fragment2 = new DiscoursesNewFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragment2.setArguments(profileData);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();
                    //    CommonActivity.startNewFragment(volumePageFragment,"home");

                        // CommonMethod.changeActivityData5(context, VolumeActivity.class, discoursesModels.get(position).getParentid(), discoursesModels.get(position).getName(),"",discoursesModels.get(position).getImage_url(),discoursesModels.get(position).getDescription());
                 //   }

                    // CommonMethod.changeActivityData5(context, VolumeActivity.class, discoursesModels.get(position).getParentid(), discoursesModels.get(position).getName(),"",discoursesModels.get(position).getImage_url(),discoursesModels.get(position).getDescription());
                    //CommonMethod.changeActivityText(context, VolumeActivity.class,discoursesModels.get(position).getParentid(),discoursesModels.get(position).getName(),"");
                }
            });
        }


        @Override
        public int getItemCount() {
            return discoursesModels.size();
        }

        public class Customview extends RecyclerView.ViewHolder {
            ImageView topics_image;
            TextView song_title;
            TextView volume_no;

            TextView topic_no;

            public Customview(View itemView) {
                super(itemView);

                topics_image = (ImageView) itemView.findViewById(R.id.topics_image);

                song_title = (TextView) itemView.findViewById(R.id.song_title);

                volume_no = (TextView) itemView.findViewById(R.id.volume_no);

                topic_no = (TextView) itemView.findViewById(R.id.topic_no);


                ButterKnife.bind(this,itemView);
            }
        }

    }


}
