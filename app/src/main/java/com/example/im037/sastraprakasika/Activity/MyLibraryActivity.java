package com.example.im037.sastraprakasika.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Adapter.FragmentAdapter;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.utils.Selected;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLibraryActivity extends CommonActivity  {
    ImageView back;
    LinearLayout commonactivity_linearlayout;
    TextView commonactivity_titleText;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private  String title[]={"Topics","Lectures","Downloads","Playlists"};
    private FragmentAdapter adapter;
    private String TAG="";
    RelativeLayout common_dragview;
    View common_view;
    FrameLayout common_shadow;

    Activity activity;
    Context context;
    String passvalue = "";
    //add me0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = MyLibraryActivity.this;
        setView(R.layout.activity_mylibrary, "My Library");
        ButterKnife.bind(this);
        setSelected(Selected.MYLIBRARY);


        passvalue = getIntent().getStringExtra( "from" );

        back=(ImageView)findViewById(R.id.back);
        common_view=(View)findViewById(R.id.viewId);
        common_shadow=(FrameLayout)findViewById(R.id.shadow);
        common_shadow.setVisibility(View.GONE);
        common_view.setVisibility(View.VISIBLE);
        commonactivity_titleText=(TextView)findViewById(R.id.title) ;
        commonactivity_linearlayout=(LinearLayout)findViewById(R.id.ss);
        back.setVisibility(View.GONE);
        //common_dragview=(RelativeLayout)findViewById(R.id.dragView);
        //common_dragview.setVisibility(View.VISIBLE);
        //commonactivity_linearlayout.setBackgroundColor(getResources().getColor(R.color.white));
       // commonactivity_titleText.setTextColor(getResources().getColor(R.color.black));
//        adapter=new FragmentAdapter(getSupportFragmentManager(),title,activity);
        viewpager.setOffscreenPageLimit(4);
        adapter=new FragmentAdapter(getSupportFragmentManager(),title);

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
    }



    public void setPosition(int position) {
        viewpager.setCurrentItem(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
}
