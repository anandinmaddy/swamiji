package com.example.im037.sastraprakasika.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im037.sastraprakasika.Adapter.Searchdetailed_adapter;
import com.example.im037.sastraprakasika.Model.SearchModel;
import com.example.im037.sastraprakasika.Model.Search_list;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchDetailedActivity extends Activity {

    TextView title_view;
    ImageView backbtn;
    ImageView imageView;
    ListView listView;
    ArrayList<Search_list> search_lists = new ArrayList<>();
    public static final String TAG = SearchDetailedActivity.class.getSimpleName();

    String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detailed_listitem);

        //imageView = (ImageView)findViewById(R.id.ablum_image_main_search);
        listView = (ListView)findViewById(R.id.listview_det_search);
        backbtn = (ImageView)findViewById(R.id.back2);
        title_view = (TextView)findViewById(R.id.title2);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // finish();
                Intent intent = new Intent(SearchDetailedActivity.this,SearchActivity.class);
                startActivity( intent );
                finish();
            }
        });


       SearchModel searchModel = (SearchModel) getIntent().getSerializableExtra("model" );
       type = getIntent().getStringExtra( "TYPE" );
       title_view.setText(searchModel.getTitle());





//        Picasso.get()
//                .load(search_lists.get(position).getImage_url())
//                .into(imageView);

        new WebServices(getApplicationContext(), TAG).getSearchDetail(Session.getInstance(getApplicationContext(), TAG).getUserId(), type,searchModel.getTitle(),searchModel.getPost_id(), new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                // hideCommonProgressBar();
                System.out.println("library respon:::: "+response);

                if (response.optString("resultcode").equalsIgnoreCase("200")) {

                    for (int i = 0; i < response.optJSONObject("data").optJSONArray( "list" ).length(); i++) {


                        search_lists.add(new Search_list(
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("title"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("post_id"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("mp3"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("image_url"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("parentid"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("subid"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("type"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("time")

                        ));
                    }

                    // view top image
//                    Picasso.get()
//                .load(search_lists.get(0).getImage_url())
//                .into(imageView);
                    Searchdetailed_adapter searchdetailed_adapter = new Searchdetailed_adapter(getApplicationContext(),search_lists);
                    listView.setAdapter(searchdetailed_adapter);

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
