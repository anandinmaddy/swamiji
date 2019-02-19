package com.example.im037.sastraprakasika.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.Itopice_listener;
import com.example.im037.sastraprakasika.Activity.SpaceItemdecoration;
import com.example.im037.sastraprakasika.Activity.Topics_detailed_items;
import com.example.im037.sastraprakasika.Adapter.TopicsRecyclerviewAdapter;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Fragment.NewFragments.VolumeDetailsFragment;
import com.example.im037.sastraprakasika.Fragment.NewFragments.dummy.TopicsDetailsFragment;
import com.example.im037.sastraprakasika.Model.ListOfTopicsModels;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TopicsFragment extends Fragment implements Itopice_listener  {


    Unbinder unbinder;
    ArrayList<ListOfTopicsModels> listOfTopicsModels = new ArrayList<>();
    public static final String TAG = TopicsFragment.class.getSimpleName();
    ShimmerFrameLayout shimmerFrameLayout;
    TextView title;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topics, container, false);
        unbinder = ButterKnife.bind(this, view);



        final RecyclerView topicsRecyclerview = (RecyclerView) view.findViewById(R.id.topicsRecyclerview);

      //  title = getActivity().findViewById(R.id.title);
       // title.setText("My Library");
        shimmerFrameLayout = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();
        //  setRecyclerView();
//        mostrarDatosFactura();
        System.out.println("library frag:::::");
        new WebServices(getActivity(), TAG).getlibrary("52", "topics", new VolleyResponseListerner() {
            boolean isResponse = false;
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                // hideCommonProgressBar();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                System.out.println("library respon:::: "+response);
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    isResponse = true;
                    SpaceItemdecoration decoration = new SpaceItemdecoration(16);
                    topicsRecyclerview.addItemDecoration(decoration);
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
                    }
                    topicsRecyclerview.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));

                    topicsRecyclerview.setAdapter(new TopicsRecyclerviewAdapter(getContext(),listOfTopicsModels,getFragmentManager()));

                        final FragmentActivity c = getActivity();




                } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                    CommonMethod.showSnackbar(topicsRecyclerview, response, getActivity());
                }
            }

            @Override
            public void onError(String message, String title) {
                System.out.println("library error:::: "+message);
            }
        });

        SetAboutDetail();

        return view;
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
        fragmentTransaction.commit();
/*
        Intent intent = new Intent(getContext(), Topics_detailed_items.class);
        intent.putExtra("Data",listOfTopicsModels.get( position ));
        getActivity().startActivity( intent );*/

    }

//    @Override
//    public void onClickview(View view, int position) {
//        //startt activity for detail page
//       // listOfTopicsModels.get(position);
//        Toast.makeText(getContext(),"position" +listOfTopicsModels.get(position), Toast.LENGTH_SHORT).show();
//    }



}
