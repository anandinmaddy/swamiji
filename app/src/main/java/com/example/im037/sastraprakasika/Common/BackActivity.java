package com.example.im037.sastraprakasika.Common;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.R;


public class BackActivity extends AppCompatActivity {
    private static String TAG = BackActivity.class.getSimpleName();
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private ImageView back;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            // edited here
//            getWindow().setStatusBarColor(Color.WHITE);
//        }
        setContentView(R.layout.common_back_activity);
//        toolbar = (Toolbar) findViewById(R.id.backcommonActivityToolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        back = (ImageView) findViewById(R.id.backCommonActivityBackImageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_out);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_out);
    }

    public void setView(int viewLayout, String title) {
//        if (title.equalsIgnoreCase("NEW CUSTOMER"))
//        toolbar.setBackgroundColor(getResources().getColor(R.color.grayColor1));
//        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        frameLayout = (FrameLayout) findViewById(R.id.bcakcommonActivityFrameLayout);
        this.title = (TextView) findViewById(R.id.backCommonActivityTitle);
        this.title.setText(title);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(viewLayout, null, false);
        frameLayout.addView(activityView);
    }

}