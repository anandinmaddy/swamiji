package com.sastra.im037.sastraprakasika.Adapter;


import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Fragment.NewFragments.VolumeDetailsFragment;
import com.sastra.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.sastra.im037.sastraprakasika.Model.SearchModel;
import com.sastra.im037.sastraprakasika.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<SearchModel> arrayList;
    String type;
    DiscousesAppDatabase db;
    FragmentManager fragmentManager;


    public SongRecyclerViewAdapter(Activity context, ArrayList<SearchModel> arrayList,String type, FragmentManager fragmentManager) {
        this.context = context;
        this.arrayList = arrayList;
        this.type = type;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_row_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        db = Room.databaseBuilder(context,
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();

        holder.searchTitle.setText(arrayList.get(position).getTitle());
        holder.imageicon.setImageResource(R.drawable.righticonsearch);
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                SearchModel search_det = arrayList.get(position);
                Intent intent = new Intent(context, SearchDetailedActivity.class);
                intent.putExtra( "model",search_det );
                intent.putExtra( "TYPE",type );
                context.startActivity( intent );*/

                Bundle profileData = new Bundle();
                profileData.putString("data",arrayList.get(position).getParentid());
                profileData.putString("data1",arrayList.get(position).getSubid());
                profileData.putString("data2",arrayList.get(position).getTitle());
                profileData.putString("data3",arrayList.get(position).getImage_url());
                profileData.putString("data4",arrayList.get(position).getDescription());
                //    mListener.onFragmentInteraction(PlayerConstants.VOLUME_FRAGMENT, profileData);

                //  CommonActivity.startNewFragment(volumePageFragment, "volumeFragment");
                VolumeDetailsFragment fragment2 = new VolumeDetailsFragment();
                fragment2.setArguments(profileData);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                /*
                Bundle bundle = new Bundle();
                bundle.putString("from","search");
                SearchDetailedFragment fragment2 = new SearchDetailedFragment();
                fragment2.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();
*/
            }
        } );

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.search_image)
//        ImageView searchImage;
        @BindView(R.id.search_title)
        TextView searchTitle;
        @BindView(R.id.img1)
        ImageView imageicon;

//        @BindView(R.id.search_type)
//       TextView searchType;
//       @BindView(R.id.search_duration)
//        TextView searchDuration;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
