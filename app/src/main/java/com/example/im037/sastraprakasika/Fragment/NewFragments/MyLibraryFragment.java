package com.example.im037.sastraprakasika.Fragment.NewFragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Adapter.FragmentAdapter;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.utils.Selected;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;


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
    private  String title[]={"Topics","Lectures","Download","Playlists"};
 //   private TextView titleView;
    private FragmentAdapter adapter;
    private String TAG="";
    RelativeLayout common_dragview;
    View common_view;
    FrameLayout common_shadow;
    ShimmerFrameLayout shimmerFrameLayout;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mylibrary, container, false);
        this.mView = view;

        titleView = getActivity().findViewById(R.id.title);
//       / passvalue = getIntent().getStringExtra( "from" );

        back=(ImageView)getActivity().findViewById(R.id.back);
        tablayout = view.findViewById(R.id.tablayout);
      //  common_view=(View)view.findViewById(R.id.viewId);
      //  common_shadow=(FrameLayout)view.findViewById(R.id.shadow);
        //common_shadow.setVisibility(View.GONE);
//      /  common_view.setVisibility(View.VISIBLE);
       // commonactivity_titleText=(TextView)view.findViewById(R.id.title) ;
        commonactivity_linearlayout=(LinearLayout)getActivity().findViewById(R.id.ss);
        titleView.setTextColor(getResources().getColor(R.color.black));
        titleView.setTypeface(null, Typeface.BOLD);
        viewpager = view.findViewById(R.id.viewpager);
        back.setVisibility(View.GONE);
        viewpager.setOffscreenPageLimit(4);
        adapter=new FragmentAdapter(getFragmentManager(),title);
        titleView.setText("My Library");
        commonactivity_linearlayout.setBackgroundColor(getResources().getColor(R.color.white));
        viewpager.setAdapter(adapter);
        if("player".equalsIgnoreCase( passvalue) ){
            viewpager.setCurrentItem( 2 );
        }else if("playlist".equalsIgnoreCase( passvalue ))
        {
            viewpager.setCurrentItem( 3 );
        }else {
            viewpager.setCurrentItem( 0 );
        }
        tablayout.setupWithViewPager(viewpager);
        viewpager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));




        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        commonactivity_linearlayout.setBackgroundColor(getResources().getColor(R.color.orange));
        titleView.setTextColor(getResources().getColor(R.color.white));

    }
}
