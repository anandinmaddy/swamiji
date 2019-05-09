package com.sastra.im037.sastraprakasika.Activity;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Adapter.Searchdetailed_adapter;
import com.sastra.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.sastra.im037.sastraprakasika.Model.SearchModel;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDetailedFragment extends Fragment {

    TextView title_view;
    ImageView backbtn;
    ImageView imageView;
    ListView listView;
    ArrayList<SearchModel> search_lists = new ArrayList<>();
    List<SearchModel> searchModels;
    public static final String TAG = SearchDetailedActivity.class.getSimpleName();
    DiscousesAppDatabase db;
    String type;
    String postId;
    String dataMsg;

    public SearchDetailedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_detail_list_items, container, false);

        listView = (ListView)view.findViewById(R.id.listview_det_search);
        //backbtn = (ImageView)getActivity().findViewById(R.id.back2);
        title_view = (TextView  )view.findViewById(R.id.title2);

        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                DiscousesAppDatabase.class, "ListOfTopics").allowMainThreadQueries().build();

        if (getArguments() !=null){
            postId = getArguments().getString("playerid");
            dataMsg = getArguments().getString("data");
        }


    /*    backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                SearchPageFragment fragment2 = new SearchPageFragment();
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });*/

        searchModels = db.searchModelDao().getAll();

        search_lists.addAll(searchModels);

      /*  SearchModel searchModel = (SearchModel) getContext().getIntent().getSerializableExtra("model" );
        type = getIntent().getStringExtra( "TYPE" );
        title_view.setText(searchModel.getTitle());
*/

        webservice();

        return view;
    }

    private void webservice() {
        new WebServices(getActivity().getApplicationContext(), TAG).getSearchDetail(Session.getInstance(getActivity().getApplicationContext(), TAG).getUserId(), type,dataMsg,postId, new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                // hideCommonProgressBar();
                System.out.println("library respon:::: "+response);

                if (response.optString("resultcode").equalsIgnoreCase("200")) {

                    for (int i = 0; i < response.optJSONObject("data").optJSONArray( "list" ).length(); i++) {

                            SearchModel searchModel = new SearchModel();
               /*         search_lists.add(new Search_list(
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("title"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("post_id"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("mp3"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("image_url"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("parentid"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("subid"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("type"),
                                response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("time")

                        ));*/

                        searchModel.setTitle(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("title"));
                        searchModel.setPost_id(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("post_id"));
                        searchModel.setMp3(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("mp3"));
                        searchModel.setImage_url(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("image_url"));
                        searchModel.setParentid(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("parentid"));
                        searchModel.setSubid(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("subid"));
                        searchModel.setType(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("type"));
                        searchModel.setTime(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("time"));
                        db.searchModelDao().insertAll(searchModel);
                    }

                    // view top image
//                    Picasso.get()
//                .load(search_lists.get(0).getImage_url())
//                .into(imageView);

                    Searchdetailed_adapter searchdetailed_adapter = new Searchdetailed_adapter(getContext(),search_lists);
                    listView.setAdapter(searchdetailed_adapter);

                } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                }
            }

            @Override
            public void onError(String message, String title) {
                System.out.println("library error:::: "+message);
            }
        });

        }
}


