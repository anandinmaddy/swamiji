package com.sastra.im037.sastraprakasika.Fragment;

import android.arch.persistence.room.Room;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Activity.Itopice_listener;
import com.sastra.im037.sastraprakasika.Activity.SpaceItemdecoration;
import com.sastra.im037.sastraprakasika.Adapter.TopicsRecyclerviewAdapter;
import com.sastra.im037.sastraprakasika.Common.CommonMethod;
import com.sastra.im037.sastraprakasika.Fragment.NewFragments.MyLibraryFragment;
import com.sastra.im037.sastraprakasika.Fragment.NewFragments.dummy.TopicsDetailsFragment;
import com.sastra.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.sastra.im037.sastraprakasika.Model.ListOfTopicsModels;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;
import com.sastra.im037.sastraprakasika.mediareceiver.NetworkStateReceiverListener;
import com.sastra.im037.sastraprakasika.mediaservice.ConnectivityReceiver;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TopicsFragment extends Fragment implements Itopice_listener , NetworkStateReceiverListener {


    Unbinder unbinder;
    ArrayList<ListOfTopicsModels> listOfTopicsModels = new ArrayList<>();
    public static final String TAG = TopicsFragment.class.getSimpleName();
    ShimmerFrameLayout shimmerFrameLayout;
    TextView title;
    ImageView back;
    DiscousesAppDatabase db;
    TextView offlineLink;
    LinearLayout offlineViewer;
    LinearLayout fullview;
    NestedScrollView nestedScrollView;
    TextView noSongsView;

    List<ListOfTopicsModels> listOfTopicsOffline = new ArrayList<>();
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topics, container, false);
        unbinder = ButterKnife.bind(this, view);



        final RecyclerView topicsRecyclerview = (RecyclerView) view.findViewById(R.id.topicsRecyclerview);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                DiscousesAppDatabase.class, "ListOfTopics").allowMainThreadQueries().build();
        topicsRecyclerview.setNestedScrollingEnabled(false);

      //  title = getActivity().findViewById(R.id.title);
       // title.setText("My Library");
        shimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        shimmerFrameLayout.startShimmer();
        //  setRecyclerView();
//        mostrarDatosFactura();
        back = (ImageView) getActivity().findViewById(R.id.back);
        back.setVisibility(View.GONE);
        listOfTopicsOffline = db.listOfTopicsModels().getAll();
        offlineLink = view.findViewById(R.id.offlineLectureLink);
        offlineViewer = view.findViewById(R.id.offlineViewer);
        fullview = view.findViewById(R.id.fullview);
        noSongsView = view.findViewById(R.id.noSongs);
        nestedScrollView = view.findViewById(R.id.listDetails);
        offlineLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("offline",true);
                MyLibraryFragment fragment2 = new MyLibraryFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Constant.currentTab = 2;
                Constant.backPress = true;
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        callwebservice(topicsRecyclerview);

/*

        if (listOfTopicsOffline != null && listOfTopicsOffline.size() > 0){
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            listOfTopicsModels.clear();
            listOfTopicsModels.addAll(listOfTopicsOffline);
            topicsRecyclerview.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));

            topicsRecyclerview.setAdapter(new TopicsRecyclerviewAdapter(getContext(),listOfTopicsModels,getFragmentManager()));


        }else {
            callwebservice(topicsRecyclerview);
        }
*/

       // SetAboutDetail();

        return view;
    }

    private void callwebservice(final RecyclerView topicsRecyclerview) {
        new WebServices(getActivity(), TAG).getlibrary(Session.getInstance(getContext(), TAG).getUserId(), "topics", new VolleyResponseListerner() {
            boolean isResponse = false;
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                // hideCommonProgressBar();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                System.out.println("library respon:::: "+response);
                if (response.optString("resultcode").equalsIgnoreCase("200") && response.optJSONArray("data") !=null) {
                    isResponse = true;
                    SpaceItemdecoration decoration = new SpaceItemdecoration(16);
                    topicsRecyclerview.addItemDecoration(decoration);
                    db.listOfTopicsModels().deleteAll();
                    for (int i = 0; i < response.optJSONArray("data").length(); i++) {
                        listOfTopicsModels.add(new ListOfTopicsModels(
                                response.optJSONArray("data").optJSONObject(i).optString("image_url"),
                                response.optJSONArray("data").optJSONObject(i).optString("parentname"),
                                response.optJSONArray("data").optJSONObject(i).optString("volume_name"),
                                response.optJSONArray("data").optJSONObject(i).optString("description"),
                                response.optJSONArray("data").optJSONObject(i).optString("count"),
                                response.optJSONArray("data").optJSONObject(i).optString("parentid"),
                                response.optJSONArray("data").optJSONObject(i).optString("post_id")
 ));
                                ListOfTopicsModels listOfTopicsModels = new ListOfTopicsModels();
                                listOfTopicsModels.setSong_image(response.optJSONArray("data").optJSONObject(i).optString("image_url"));
                                listOfTopicsModels.setSong_title(response.optJSONArray("data").optJSONObject(i).optString("parentname"));
                                listOfTopicsModels.setSong_volume(response.optJSONArray("data").optJSONObject(i).optString("volume_name"));
                                listOfTopicsModels.setSong_description(response.optJSONArray("data").optJSONObject(i).optString("description"));
                                listOfTopicsModels.setSong_count(response.optJSONArray("data").optJSONObject(i).optString("count"));
                                listOfTopicsModels.setSong_parentid(response.optJSONArray("data").optJSONObject(i).optString("parentid"));
                                listOfTopicsModels.setSong_post_id(response.optJSONArray("data").optJSONObject(i).optString("post_id"));


                               db.listOfTopicsModels().insertAll(listOfTopicsModels);


                    }
                    topicsRecyclerview.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));

                    topicsRecyclerview.setAdapter(new TopicsRecyclerviewAdapter(getContext(),listOfTopicsModels,getFragmentManager()));



                } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                    nestedScrollView.setVisibility(View.GONE);
                    noSongsView.setVisibility(View.VISIBLE);
                    CommonMethod.showSnackbar(topicsRecyclerview, response, getActivity());
                }else{
                    nestedScrollView.setVisibility(View.GONE);
                    noSongsView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String message, String title) {
                nestedScrollView.setVisibility(View.GONE);
                noSongsView.setVisibility(View.VISIBLE);
                System.out.println("library error:::: "+message);
            }
        });

    }

    public void SetAboutDetail() {
        new WebServices(getActivity(), TAG).getAboutPageDetails("1872", new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("status").equalsIgnoreCase("1")) {
//                    content.setText(response.getJSONObject("data").optString("title"));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //content.setText( Html.fromHtml(response.getJSONObject("data").optString("content"), 0));
                    }

                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }

    /* private void setRecyclerView() {
         SpaceItemdecoration decoration = new SpaceItemdecoration(16);
         topicsRecyclerview.addItemDecoration(decoration);
         topicsRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));//Linear Items
         for (int i = 0; i < 3; i++) {
             listOfTopicsModels.add(new ListOfTopicsModels("","Vedenta ","Volume1"));
             listOfTopicsModels.add(new ListOfTopicsModels("","Tamil ","Volume2"));
             listOfTopicsModels.add(new ListOfTopicsModels("","Generic ","Volume3"));
             listOfTopicsModels.add(new ListOfTopicsModels("","Divyanandam ","Volume4"));
         }
         TopicsRecyclerviewAdapter adapter = new TopicsRecyclerviewAdapter(getActivity(), listOfTopicsModels);
         topicsRecyclerview.setAdapter(adapter);// set adapter on recyclerview
     }*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void mostrarDatosFactura() {

//        final LecturesFragment_Audioplay fragment = new LecturesFragment_Audioplay();
//        FragmentTransaction transaction = null;
//        transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.viewpager, fragment); //id of ViewPager
//        transaction.addToBackStack(null);
//        transaction.commit();
    }

    @Override
    public void onClickview(View view, int position) {
        //Call Start Activity Here

        Bundle profileData = new Bundle();
        profileData.putString("data",listOfTopicsModels.get(position).getSong_title());
        profileData.putString("data1",listOfTopicsModels.get(position).getSong_image());
        profileData.putString("data2",listOfTopicsModels.get(position).getSong_post_id());
        profileData.putString("data4",listOfTopicsModels.get(position).getSong_image());


        TopicsDetailsFragment fragment2 = new TopicsDetailsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragment2.setArguments(profileData);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
/*
        Intent intent = new Intent(getContext(), Topics_detailed_items.class);
        intent.putExtra("Data",listOfTopicsModels.get( position ));
        getActivity().startActivity( intent );*/

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void networkAvailable() {
      fullview.setVisibility(View.VISIBLE);
        offlineViewer.setVisibility(View.GONE);
    }

    @Override
    public void networkUnavailable() {
        fullview.setVisibility(View.VISIBLE);
        offlineViewer.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        fullview.setVisibility(View.GONE);
        try {
            ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
            connectivityReceiver.addListener(this);
            getActivity().registerReceiver(connectivityReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

//    @Override
//    public void onClickview(View view, int position) {
//        //startt activity for detail page
//       // listOfTopicsModels.get(position);
//        Toast.makeText(getContext(),"position" +listOfTopicsModels.get(position), Toast.LENGTH_SHORT).show();
//    }





}
