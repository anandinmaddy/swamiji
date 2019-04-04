package com.example.im037.sastraprakasika.Fragment.NewFragments.dummy;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im037.sastraprakasika.Activity.Topics_detailed_items;
import com.example.im037.sastraprakasika.Adapter.TopicsDetailedAdapter;
import com.example.im037.sastraprakasika.Fragment.NewFragments.MyLibraryFragment;
import com.example.im037.sastraprakasika.Fragment.TopicsFragment;
import com.example.im037.sastraprakasika.Model.DiscoursesModel;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.Model.ListOfTopicsDetailed;
import com.example.im037.sastraprakasika.Model.ListOfTopicsModels;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.utils.TypeConvertor;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicsDetailsFragment extends Fragment {

    ArrayList<ListOfTopicsDetailed> listOfTopicsDetaileds = new ArrayList<>();
    ImageView imageView;
    ListView listView;
    ImageView img_url,back;
    TextView select_title;
    public static final String TAG = TopicsDetailsFragment.class.getSimpleName();
    ShimmerFrameLayout shimmerFrameLayout;
    DiscousesAppDatabase db;
    String post_id;
    List<ListOfTopicsDetailed> listOfTopicsDetailedsList;
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
        select_title = (TextView)view.findViewById(R.id.titleview);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        back = getActivity().findViewById(R.id.back);
        shimmerFrameLayout.startShimmer();
        img_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onBackPressed();
              //  finish();

            }
        });
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                DiscousesAppDatabase.class, "ListOfTopicDetailed").allowMainThreadQueries().build();
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.GONE);
                MyLibraryFragment fragment2 = new MyLibraryFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Constant.currentTab = 0;
                Constant.backPress = true;
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if(getArguments() != null){
            select_title.setText(getArguments().getString("data"));
            post_id = getArguments().getString("data2");
        }

        Picasso.get()
                .load(getArguments().getString("data4"))
                .placeholder(R.drawable.placeholder_song)
                .into(imageView);

        listOfTopicsDetailedsList = db.listOfTopicsDetailed().getAll();



        if(listOfTopicsDetailedsList.size() > 0){
            Constant.arrayOfflineTopiclineSongs.clear();
            Constant.arrayOfflineTopiclineSongs.addAll(listOfTopicsDetailedsList);
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            TopicsDetailedAdapter topicsDetailedAdapter = new TopicsDetailedAdapter(Constant.arrayOfflineTopiclineSongs,getActivity());
            listView.setAdapter(topicsDetailedAdapter);
        }else {
            callwebservice();
        }

        return view;
    }

    private void callwebservice() {
        final ArrayList<ListOfTopicsDetailed> listOfTopicsJsonResponse = new ArrayList<>();

        new WebServices(getContext(), TAG).getTopicsDetail(Session.getInstance(getContext(), TAG).getUserId(), "topics",post_id, new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                // hideCommonProgressBar();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                db.listOfTopicsDetailed().deleteAll();
                System.out.println("library respon:::: "+response);
                if (response.optString("resultcode").equalsIgnoreCase("200")) {

     /*               List<ListOfTopicsDetailed> topicsDetailsFragments = TypeConvertor.stringToNestedTopicDetail(response.optJSONObject("data").optJSONArray("list").toString());
                    for (ListOfTopicsDetailed topicsDetailsFragment : topicsDetailsFragments){
                        db.listOfTopicsDetailed().insertAll(topicsDetailsFragment);
                    }*/


                    for (int i = 0; i < response.optJSONObject("data").optJSONArray( "list" ).length(); i++) {
                        ListOfTopicsDetailed listOfTopicsDetailed = new ListOfTopicsDetailed();

                        listOfTopicsJsonResponse.add(new ListOfTopicsDetailed(
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
                        listOfTopicsDetailed.setTopics_det_title(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("title"));
                        listOfTopicsDetailed.setTopics_det_img(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("mp3"));
                        listOfTopicsDetailed.setTopics_det_type(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("type"));
                        listOfTopicsDetailed.setTopics_det_post_id(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("post_id"));
                        listOfTopicsDetailed.setTopics_det_imgurl(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("image_url"));
                        listOfTopicsDetailed.setTopics_parentid(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("parentid"));
                        listOfTopicsDetailed.setTopics_subid(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("subid"));
                        listOfTopicsDetailed.setTopics_time(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("time"));
                        listOfTopicsDetailed.setTopics_classname(response.optJSONObject("data").optJSONArray("list").optJSONObject(i).optString("classname"));
                        db.listOfTopicsDetailed().insertAll(listOfTopicsDetailed);
                        Constant.arrayTopiclineSongs.add(listOfTopicsDetailed);
                    }
                    Constant.arrayOfflineTopiclineSongs.clear();
                    Constant.arrayOfflineTopiclineSongs.addAll(Constant.arrayTopiclineSongs);

                    TopicsDetailedAdapter topicsDetailedAdapter = new TopicsDetailedAdapter(Constant.arrayOfflineTopiclineSongs,getActivity());
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

    }

}
