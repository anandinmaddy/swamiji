package com.example.im037.sastraprakasika.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im037.sastraprakasika.Adapter.CategoryRecyclerviewAdapter;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Fragment.LecturesFragment;
import com.example.im037.sastraprakasika.Model.DiscoursesModel;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.mediautil.PlayerConstants;
import com.example.im037.sastraprakasika.utils.Selected;
import com.facebook.stetho.Stetho;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashBoardActivity extends CommonActivity {

    String parentID;
    ImageView vedanta, discourses, back;
    RecyclerView discourseView;
    TextView content;
    boolean isTextViewClicked = false;
    LinearLayout playerLayout;
    boolean doubleBackToExitPressedOnce = false;
    ArrayList<DiscoursesModel> discoursesModels = new ArrayList<>();
    RelativeLayout common_dragview;
    public static final String TAG = DashBoardActivity.class.getSimpleName();
    Handler mHandler = new Handler();
    private boolean isHomeActivityRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_dash_board, "Discourses");
        setSelected(Selected.DISCOURSES);
        isHomeActivityRunning = true;

        discourses = findViewById(R.id.discourses);
        back = findViewById(R.id.back);
//        vedanta = findViewById(R.id.vedanta);
        content = findViewById(R.id.discoursesContent);
        //common_dragview = (RelativeLayout) findViewById(R.id.dragView);
        discourseView = (RecyclerView) findViewById(R.id.discoursesRecyclerview);
        Stetho.initializeWithDefaults(this);
        //common_dragview.setVisibility(View.VISIBLE);
//        playerLayout = findViewById(R.id.playerLayout);

//        playerLayout.setVisibility(View.GONE);
//
//        vedanta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CommonMethod.changeActivity(DashBoardActivity.this, VolumeActivity.class);
//            }
//        });

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked) {
                    content.setMaxLines(6);
                    isTextViewClicked = false;
                } else {
                    content.setMaxLines(Integer.MAX_VALUE);
                    isTextViewClicked = true;
                }
            }
        });

        back.setVisibility(View.GONE);
        setCommonProgressBar(View.VISIBLE);
        new WebServices(DashBoardActivity.this, TAG).getCategory_list("", "", new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                hideCommonProgressBar();
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    SpaceItemdecoration decoration = new SpaceItemdecoration(16);
                    discourseView.addItemDecoration(decoration);
                    for (int i = 0; i < response.optJSONArray("data").length(); i++) {
                        discoursesModels.add(new DiscoursesModel(
                                response.optJSONArray("data").optJSONObject(i).optString("parentname"),
                                response.optJSONArray("data").optJSONObject(i).optString("slug"),
                                response.optJSONArray("data").optJSONObject(i).optString("parentid"),
                                response.optJSONArray("data").optJSONObject(i).optString("image_url"),
                                response.optJSONArray("data").optJSONObject(i).optString("description"),
                                response.optJSONArray( "data").optJSONObject(i).optString( "topiccount" ),
                                response.optJSONArray( "data").optJSONObject( i ).optString( "trackcount" )

                        ));
                    }
                    discourseView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
                    discourseView.setAdapter(new CategoryRecyclerviewAdapter(getApplicationContext(), discoursesModels,parentID));

                } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                    CommonMethod.showSnackbar(discourseView, response, DashBoardActivity.this);
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
        SetAboutDetail();

    }

    public void onFragmentInteraction(String id) {
        Fragment newfrag;
        if (id.equals(PlayerConstants.SETTINGS)) {
            newfrag = new LecturesFragment();
            DashBoardActivity.this.startNewFragment(newfrag, PlayerConstants.SETTINGS);
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
        if (doubleBackToExitPressedOnce) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(intent);
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
    public void startNewFragment(final Fragment frag, final String tag) {
        if (isHomeActivityRunning && frag != null && !frag.isAdded()) {
            String backStateName = frag.getClass().getName();
            FragmentManager manager = getSupportFragmentManager();
            // boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
            // if (!fragmentPopped || isFromNotification){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container, frag, tag);
            ft.addToBackStack(backStateName);
            ft.commitAllowingStateLoss();
            // }
        }
    }
    public void SetAboutDetail() {
        new WebServices(DashBoardActivity.this, TAG).getAboutPageDetails("1872", new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("status").equalsIgnoreCase("1")) {
//                    content.setText(response.getJSONObject("data").optString("title"));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        content.setText(Html.fromHtml(response.getJSONObject("data").optString("content"), 0));
                    }

                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }

}
