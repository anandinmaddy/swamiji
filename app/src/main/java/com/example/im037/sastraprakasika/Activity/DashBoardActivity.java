package com.example.im037.sastraprakasika.Activity;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.DiscoursesNewFragment;
import com.example.im037.sastraprakasika.Fragment.AboutDetailFragment;
import com.example.im037.sastraprakasika.Fragment.LecturesFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.DashBoardNewFragment;
import com.example.im037.sastraprakasika.Fragment.VolumePageFragment;
import com.example.im037.sastraprakasika.Model.DiscoursesModel;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.Model.ItemOffSet;
import com.example.im037.sastraprakasika.Model.PlayList;
import com.example.im037.sastraprakasika.Model.VolumeModel;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.mediautil.PlayerConstants;
import com.example.im037.sastraprakasika.utils.AppPreference;
import com.example.im037.sastraprakasika.utils.Selected;
import com.example.im037.sastraprakasika.utils.TypeConvertor;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.facebook.stetho.Stetho;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardActivity extends CommonActivity implements FragmentInteractionListener {

    String parentID;
    ImageView vedanta, discourses, back;
    RecyclerView discourseView;
    TextView content;
    boolean isTextViewClicked = false;
    LinearLayout playerLayout;
    boolean doubleBackToExitPressedOnce = false;
    ArrayList<DiscoursesModel> discoursesModels = new ArrayList<>();
    ArrayList<VolumeModel> volumeArrayList = new ArrayList<>();
    TextView discoursesTitle;
    RelativeLayout common_dragview;
    public static final String TAG = DashBoardActivity.class.getSimpleName();
    Handler mHandler = new Handler();
    private static boolean isHomeActivityRunning;
    List<DiscoursesModel> discoursesModelsList;
    DiscousesAppDatabase db;
    LinearLayout homeView;
    ShimmerFrameLayout mShimmerViewContainer;
    NestedScrollView itemViewlayout;
    private FirebaseAnalytics mFirebaseAnalytics;
    TextView knowMoreTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_dash_board, "Discourses");
        setSelected(Selected.DISCOURSES);
        isHomeActivityRunning = true;
        db = Room.databaseBuilder(getApplicationContext(),
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();
        discoursesTitle = findViewById(R.id.discoursesTitle);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle newBundle = new Bundle();
        newBundle.putString("eee","3232");
        mFirebaseAnalytics.logEvent("testng",newBundle);
        discourses = findViewById(R.id.discourses);
        back = findViewById(R.id.back);
//        vedanta = findViewById(R.id.vedanta);
        content = findViewById(R.id.discoursesContent);
        //common_dragview = (RelativeLayout) findViewById(R.id.dragView);
        discourseView = (RecyclerView) findViewById(R.id.discoursesRecyclerview);
        Stetho.initializeWithDefaults(this);
        content.setMaxLines(Integer.MAX_VALUE);
        homeView = (LinearLayout) findViewById(R.id.homeView);
        knowMoreTxt = (TextView) findViewById(R.id.knowMoreTxt);
        //common_dragview.setVisibility(View.VISIBLE);
//        playerLayout = findViewById(R.id.playerLayout);
        itemViewlayout = (NestedScrollView) findViewById(R.id.itemViewlayout);


        mShimmerViewContainer = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmer();
//        playerLayout.setVisibility(View.GONE);
//
//        vedanta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CommonMethod.changeActivity(DashBoardActivity.this, VolumeActivity.class);
//            }
//        });

        DashBoardNewFragment discoursesNewFragment = new DashBoardNewFragment();
        startNewFragment(discoursesNewFragment,"home");
/*
        CommonActivity.lecturesAPICALL(this);
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

        knowMoreTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle profileData = new Bundle();
                AboutDetailFragment aboutDetailFragment = new AboutDetailFragment();
                aboutDetailFragment.setArguments(profileData);
                startNewFragment(aboutDetailFragment,"home");
            }
        });
        if(db != null){
            discoursesModelsList = db.userDao().getAll();
        }

        back.setVisibility(View.GONE);
        //    setCommonProgressBar(View.VISIBLE);

      //  callWebservice();

    *//*    if(discoursesModelsList != null && discoursesModelsList.size() > 0){
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            itemViewlayout.setVisibility(View.VISIBLE);
            discoursesModels.addAll(discoursesModelsList);
            discourseView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
            discourseView.setAdapter(new CategoryRecyclerviewAdapter(getApplicationContext(), discoursesModels,parentID,DashBoardActivity.this));

        }else{
            callWebservice();


        }*//*
        callPlayList();

        SetAboutDetail();
        DashBoardNewFragment discoursesNewFragment = new DashBoardNewFragment();
        startNewFragment(discoursesNewFragment,"home");*/
    }

    private void callPlayList() {

            new WebServices(this.getApplicationContext(), TAG).getPlayLists(Session.getInstance(this.getApplicationContext(), TAG).getUserId(),  new VolleyResponseListerner() {
                List<PlayList> playLists ;

                @Override
                public void onResponse(JSONObject response) throws JSONException {
                    if (response.optString("resultcode").equalsIgnoreCase("200")) {
                        db.playListDao().deleteAll();
                        playLists = TypeConvertor.stringToNestedDataPlayList(response.optJSONArray("data").toString());
                        try {
                            for (PlayList playList : playLists) {
                                db.playListDao().insertAll(playList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onError(String message, String title) {          }
            });


    }

    private void callWebservice() {
        new WebServices(DashBoardActivity.this, TAG).getCategory_list("", "","", new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                //hideCommonProgressBar();
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
                itemViewlayout.setVisibility(View.VISIBLE);
                ArrayList<DiscoursesModel> test = new ArrayList<>();
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    db.userDao().deleteAll();
                    SpaceItemdecoration decoration = new SpaceItemdecoration(16);
                    discourseView.addItemDecoration(decoration);
                    db.userDao().getAll();
                    List<DiscoursesModel> discoursesModelsNew = TypeConvertor.stringToNestedData(response.optJSONArray("data").toString());
                    for (DiscoursesModel discoursesModel : discoursesModelsNew){
                        db.userDao().insertAll(discoursesModel);
                    }

                    discoursesModels.clear();
                    discoursesModels.addAll(discoursesModelsNew);
                    discourseView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
                    ItemOffSet itemDecoration = new ItemOffSet(getApplicationContext(), R.dimen._4sdp);
                    discourseView.addItemDecoration(itemDecoration);
                    discourseView.setAdapter(new CategoryRecyclerviewAdapter(getApplicationContext(), discoursesModels,parentID,DashBoardActivity.this));
                    AppPreference.putLong(Constant.LAST_SYNC,new Date().getTime(),getApplicationContext());
                } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                    CommonMethod.showSnackbar(discourseView, response, DashBoardActivity.this);
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });

     //   String response = CommonActivity.getPagedetails(getApplicationContext());

        new WebServices(getApplicationContext(), VolumeActivity.class.getSimpleName()).getCategory_list(getIntent().getStringExtra("data"), "","", new VolleyResponseListerner() {
            List<VolumeModel> volumeModels = null;
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if(response.optString("resultcode").equalsIgnoreCase("200")){

                    volumeModels = TypeConvertor.stringToNestedDataVolume(response.optJSONArray("data").toString());
                    try {
                        for (VolumeModel volumeModel : volumeModels){
                            db.volumeModel().insertAll(volumeModel);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                 }

            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(String id, Object data) {
        Fragment newfrag;
        Bundle bundle;
        if (id.equals(PlayerConstants.VOLUME_FRAGMENT)) {
            newfrag = new LecturesFragment();
            if(data != null){
                bundle = (Bundle) data;
                newfrag.setArguments(bundle);
            }

            startNewFragment(newfrag, PlayerConstants.VOLUME_FRAGMENT);
        }
    }

    @Override
    public void onFragmentInteraction(String id) {
        Fragment newfrag;
        if (id.equals(PlayerConstants.VOLUME_FRAGMENT)) {
            newfrag = new VolumePageFragment();
            startNewFragment(newfrag, PlayerConstants.VOLUME_FRAGMENT);
        }
    }


    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        isHomeActivityRunning = false;

    }

    @Override
    public void onStart() {
        try{
            super.onStart();
            isHomeActivityRunning = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
    public void startNewFragment(final Fragment frag, final String tag) {
        if ( frag != null && !frag.isAdded()) {
            ((FrameLayout)findViewById(R.id.commonActivityFrameLayout)).removeAllViews();
            String backStateName = frag.getClass().getName();
            FragmentManager manager = getSupportFragmentManager();
            // boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
            // if (!fragmentPopped || isFromNotification){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.commonActivityFrameLayout, frag, tag);
            ft.addToBackStack(null);
            ft.commit();
            // }
        }
    }

    public void popBackStack() {
        final FragmentManager fm = this.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            try {
                fm.popBackStack();
            } catch (IllegalStateException ignored) {
                ignored.printStackTrace();
                // There's no way to avoid getting this if saveInstanceState has already been called.
            }
        }
    }



    public void SetAboutDetail() {
        new WebServices(DashBoardActivity.this, TAG).getAboutPageDetails("1872", new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("status").equalsIgnoreCase("1")) {
//                    content.setText(response.getJSONObject("data").optString("title"));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // content.setText(Html.fromHtml(response.getJSONObject("data").optString("content"), 0));
                    }

                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }

    public class CategoryRecyclerviewAdapter extends RecyclerView.Adapter<CategoryRecyclerviewAdapter.Customview> {
        Context context;
        ArrayList<DiscoursesModel> discoursesModels;
        String parentID;
        private FragmentInteractionListener mListener;

        public CategoryRecyclerviewAdapter(Context applicationContext, ArrayList<DiscoursesModel> discoursesModels, String parentID, DashBoardActivity dashBoardActivity) {
            this.context = applicationContext;
            this.discoursesModels = discoursesModels;
            this.parentID = parentID;
            this.mListener = dashBoardActivity;
        }

        @NonNull
        @Override
        public Customview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.items_items_dashboard, parent, false);

            return new Customview(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Customview holder, final int position) {
            Picasso.get()
                    .load(discoursesModels.get(position).getImage_url())
                    .placeholder(R.drawable.placeholder_default)
                    .into(holder.topics_image);
            Log.d("value",discoursesModels.get(position).getName());
            String value=discoursesModels.get(position).getName();



            holder.songTitle.setText(value);
            holder.topics_number.setText(discoursesModels.get( position ).getTopiccount());
            holder.volumeNo.setText(discoursesModels.get(position).getTrackcount());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        Bundle profileData = new Bundle();
                        Log.d("parentId",discoursesModels.get(position).getParentid());
                        profileData.putString("data",discoursesModels.get(position).getParentid());
                        profileData.putString("data1",discoursesModels.get(position).getName());
                        profileData.putString("data3",discoursesModels.get(position).getImage_url());
                        profileData.putString("data4",discoursesModels.get(position).getDescription());
                        profileData.putString("data5",discoursesModels.get(position).getTopiccount());
                        profileData.putString("data6",discoursesModels.get(position).getVolumecount());
                        //    mListener.onFragmentInteraction(PlayerConstants.VOLUME_FRAGMENT, profileData);
                        DiscoursesNewFragment discoursesNewFragment = new DiscoursesNewFragment();
                        discoursesNewFragment.setArguments(profileData);
                        startNewFragment(discoursesNewFragment,"home");

                      /*  VolumePageFragment volumePageFragment = new VolumePageFragment();
                        volumePageFragment.setArguments(profileData);
                        startNewFragment(volumePageFragment,"home");*/
                        // CommonMethod.changeActivityData5(context, VolumeActivity.class, discoursesModels.get(position).getParentid(), discoursesModels.get(position).getName(),"",discoursesModels.get(position).getImage_url(),discoursesModels.get(position).getDescription());
                    }

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
            @BindView(R.id.topics_image)
            ImageView topics_image;
            @BindView(R.id.song_title)
            TextView songTitle;
            @BindView(R.id.volume_no)
            TextView volumeNo;

            @BindView( R.id.topic_no )
            TextView topics_number;


            public Customview(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }

    }
}
