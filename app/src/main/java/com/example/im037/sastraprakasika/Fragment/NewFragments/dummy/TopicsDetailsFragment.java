package com.example.im037.sastraprakasika.Fragment.NewFragments.dummy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im037.sastraprakasika.Activity.Topics_detailed_items;
import com.example.im037.sastraprakasika.Adapter.TopicsDetailedAdapter;
import com.example.im037.sastraprakasika.Model.ListOfTopicsDetailed;
import com.example.im037.sastraprakasika.Model.ListOfTopicsModels;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicsDetailsFragment extends Fragment {

    ArrayList<ListOfTopicsDetailed> listOfTopicsDetaileds = new ArrayList<>();
    ImageView imageView;
    ListView listView;
    ImageView img_url;
    TextView select_title;
    public static final String TAG = TopicsDetailsFragment.class.getSimpleName();


    public TopicsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.topics_detailed_items, container, false);

        imageView = (ImageView)view.findViewById(R.id.ablum_image_main);
        listView = (ListView)view.findViewById(R.id.listview_det);
        img_url = (ImageView)view.findViewById(R.id.back3);
        select_title = (TextView)view.findViewById(R.id.title3);

        img_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onBackPressed();
              //  finish();

            }
        });





        select_title.setText(getArguments().getString("data2"));

        Picasso.get()
                .load(getArguments().getString("data3"))
                .into(imageView);

        new WebServices(getContext(), TAG).getTopicsDetail(Session.getInstance(getContext(), TAG).getUserId(), "topics",getArguments().getString("data1"), new VolleyResponseListerner() {

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
                    TopicsDetailedAdapter topicsDetailedAdapter = new TopicsDetailedAdapter(listOfTopicsDetaileds,getContext());
                    listView.setAdapter(topicsDetailedAdapter);
//                    topicsRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
//                    topicsRecyclerview.setAdapter(new TopicsRecyclerviewAdapter(getActivity(), listOfTopicsModels,itopice_listener));

                } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                    Toast.makeText(getContext(),"result code not access",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String message, String title) {
                System.out.println("library error:::: "+message);
            }
        });


        return view;
    }

}
