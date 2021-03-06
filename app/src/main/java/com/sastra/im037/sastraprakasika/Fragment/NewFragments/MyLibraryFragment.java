package com.sastra.im037.sastraprakasika.Fragment.NewFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sastra.im037.sastraprakasika.Adapter.FragmentAdapter;
import com.sastra.im037.sastraprakasika.Fragment.DownloadsFragmentNew;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.R;
import com.facebook.shimmer.ShimmerFrameLayout;


public class MyLibraryFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView back;
    LinearLayout commonactivity_linearlayout;
    //TextView commonactivity_titleText;
    private TabLayout tablayout;
    private ViewPager viewpager;
    ShimmerFrameLayout shimmerFrameLayout;
    private  String title[]={"Topics","Lectures","Downloads","Playlists"};
 //   private TextView titleView;
    private FragmentAdapter adapter;
    private String TAG="";
    RelativeLayout common_dragview;
    View common_view;
    FrameLayout common_shadow;
    TextView titleView;
    Activity activity;
    Context context;
        String passvalue = "";
    //add me0
    protected View mView;
    public MyLibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        titleView = getActivity().findViewById(R.id.title);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mylibrary, container, false);
        this.mView = view;
        setRetainInstance(true);

        titleView = getActivity().findViewById(R.id.title);
        if(getArguments() != null){
            passvalue = getArguments().getString( "from" );
        }


        shimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();
        back=(ImageView)getActivity().findViewById(R.id.back);
        tablayout = view.findViewById(R.id.tablayout);
      //  common_view=(View)view.findViewById(R.id.viewId);
      //  common_shadow=(FrameLayout)view.findViewById(R.id.shadow);
        //common_shadow.setVisibility(View.GONE);
//      /  common_view.setVisibility(View.VISIBLE);
       // commonactivity_titleText=(TextView)view.findViewById(R.id.title) ;
        commonactivity_linearlayout=(LinearLayout)getActivity().findViewById(R.id.ss);
     //   titleView.setTextColor(getResources().getColor(R.color.black));
       // titleView.setTypeface(null, Typeface.BOLD);
        viewpager = view.findViewById(R.id.viewpager);
        back.setVisibility(View.VISIBLE);
        viewpager.setOffscreenPageLimit(4);
        adapter=new FragmentAdapter(getFragmentManager(),title);
        titleView.setText("My Library");
        titleView.setTextColor(getResources().getColor(R.color.white));
        viewpager.setAdapter(adapter);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);

        if(Constant.currentTab == 0 && Constant.backPress) {
            Constant.backPress = false;
            viewpager.setCurrentItem( 0 );
        }else if(Constant.currentTab == 1 && Constant.backPress){
            Constant.backPress = false;
            viewpager.setCurrentItem( 1 );
        }else if(Constant.currentTab == 2 && Constant.backPress){
            Constant.backPress = false;
            viewpager.setCurrentItem( 2 );
        }else if(Constant.currentTab == 3 && Constant.backPress){
            Constant.backPress = false;
            viewpager.setCurrentItem( 3 );
        }
        tablayout.setupWithViewPager(viewpager);
        viewpager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.GONE);
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                }
            }
        });
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Constant.currentTab = tab.getPosition();
                if (Constant.currentTab == 2 && Constant.downloadCompleted && Constant.downloadCount < 1){
                    Constant.downloadCompleted = false;
                    MyLibraryFragment fragment2 = new MyLibraryFragment();
                    Bundle bundle = new Bundle();
                    Constant.currentTab = 2;
                    Constant.backPress = true;
                    bundle.putString("from","lecture");
                    FragmentManager fragmentManager = getFragmentManager();
                    fragment2.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();
                }
           /*     if (Constant.currentTab == 2 && Constant.downloadCount == 0){
               //     Toast.makeText(getContext(), "Download on progress. Please wait to complete the downloads.", Toast.LENGTH_SHORT).show();
                    Constant.downloadCompleted = false;
                    MyLibraryFragment fragment2 = new MyLibraryFragment();
                    Bundle bundle = new Bundle();
                    Constant.currentTab = 2;
                    Constant.backPress = true;
                    bundle.putString("from","lecture");
                    FragmentManager fragmentManager = getFragmentManager();
                    fragment2.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();
                    Constant.isFirstTime = false;
                }*/
            /*    if (Constant.currentTab == 1 && Constant.downloadCount > 0){
                    DownloadsFragmentNew fragment2 = new DownloadsFragmentNew();
                    Constant.currentTab = 2;
                    Constant.backPress = true;
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();                }*/
                if (Constant.currentTab == 2 && Constant.isFirstTime){
                    Constant.downloadCompleted = false;
                    MyLibraryFragment fragment2 = new MyLibraryFragment();
                    Bundle bundle = new Bundle();
                    Constant.currentTab = 2;
                    Constant.backPress = true;
                    bundle.putString("from","lecture");
                    FragmentManager fragmentManager = getFragmentManager();
                    fragment2.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();
                    Constant.isFirstTime = false;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            //    Constant.currentTab = tab.getPosition();
            }
        });




        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
       // commonactivity_linearlayout.setBackgroundColor(getResources().getColor(R.color.orange));
       // titleView.setTextColor(getResources().getColor(R.color.white));

    }


}
