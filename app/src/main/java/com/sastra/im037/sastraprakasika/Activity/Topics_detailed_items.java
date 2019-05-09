package com.sastra.im037.sastraprakasika.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sastra.im037.sastraprakasika.Model.ListOfTopicsDetailed;
import com.sastra.im037.sastraprakasika.Model.ListOfTopicsModels;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Topics_detailed_items extends AppCompatActivity{

    ArrayList<ListOfTopicsDetailed> listOfTopicsDetaileds = new ArrayList<>();
    ImageView imageView;
    ListView listView;
    ImageView img_url;
    TextView select_title;


    public static final String TAG = Topics_detailed_items.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topics_detailed_items);

        imageView = (ImageView)findViewById(R.id.ablum_image_main);
        listView = (ListView)findViewById(R.id.listview_det);
        img_url = (ImageView)findViewById(R.id.back3);
        select_title = (TextView)findViewById(R.id.title3);

        img_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onBackPressed();
                finish();

            }
        });




       ListOfTopicsModels  models = (ListOfTopicsModels) getIntent().getSerializableExtra( "Data" );

       select_title.setText(models.getSong_title());

        Picasso.get()
                .load(models.getSong_image())
                .into(imageView);

        new WebServices(getApplicationContext(), TAG).getTopicsDetail(Session.getInstance(getApplicationContext(), TAG).getUserId(), "topics",models.getSong_post_id(), new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                // hideCommonProgressBar();
                System.out.println("library respon:::: "+response);
                if (response.optString("resultcode").equalsIgnoreCase("200")) {

                    for (int i = 0; i < response.optJSONObject("data").optJSONArray( "list" ).length(); i++) {
                        listOfTopicsDetaileds.add(new ListOfTopicsDetailed(
                              response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("title"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("mp3"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("type"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("post_id"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("image_url"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("parentid"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("subid"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("time"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("classname")

                        ));
                    }
                  /*  TopicsDetailedAdapter topicsDetailedAdapter = new TopicsDetailedAdapter(listOfTopicsDetaileds,getApplicationContext());
                    listView.setAdapter(topicsDetailedAdapter);*/
//                    topicsRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
//                    topicsRecyclerview.setAdapter(new TopicsRecyclerviewAdapter(getActivity(), listOfTopicsModels,itopice_listener));

                } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                    Toast.makeText(getApplicationContext(),"result code not access",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String message, String title) {
                System.out.println("library error:::: "+message);
            }
        });



    }
}
