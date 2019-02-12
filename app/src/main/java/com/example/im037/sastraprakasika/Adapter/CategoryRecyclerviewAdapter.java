package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.DashBoardActivity;
import com.example.im037.sastraprakasika.Activity.FragmentInteractionListener;
import com.example.im037.sastraprakasika.Model.DiscoursesModel;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.mediautil.PlayerConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryRecyclerviewAdapter extends RecyclerView.Adapter<CategoryRecyclerviewAdapter.Customview> {
    Context context;
    ArrayList<DiscoursesModel> discoursesModels;
    String parentID;
    private FragmentInteractionListener mListener;

    public CategoryRecyclerviewAdapter(Context context, ArrayList<DiscoursesModel> discoursesModels,String parentId,DashBoardActivity dashBoardActivity) {
        this.context = context;
        this.discoursesModels = discoursesModels;
        this.parentID = parentId;
        this.mListener = dashBoardActivity;
    }

    @NonNull
    @Override
    public Customview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //mListener.onFragmentInteraction(Constants.SIGN_UP_EMAIL_FRAGMENT, null);

        return new Customview(LayoutInflater.from(context).inflate(R.layout.items_items_dashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Customview holder, final int position) {

        Picasso.get()
                .load(discoursesModels.get(position).getImage_url())
                .into(holder.topics_image);
        Log.d("value",discoursesModels.get(position).getName());
        String value=discoursesModels.get(position).getName();



        holder.songTitle.setText(value);
        holder.topics_number.setText(discoursesModels.get( position ).getTopiccount());
       holder.volumeNo.setText(discoursesModels.get(position).getTrackcount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Bundle profileData = new Bundle();
                    profileData.putString("data",discoursesModels.get(position).getParentid());
                    profileData.putString("data1",discoursesModels.get(position).getName());
                    profileData.putString("data3",discoursesModels.get(position).getImage_url());
                    profileData.putString("data4",discoursesModels.get(position).getDescription());
                    mListener.onFragmentInteraction(PlayerConstants.VOLUME_FRAGMENT, profileData);
                }

               // CommonMethod.changeActivityData5(context, VolumeActivity.class, discoursesModels.get(position).getParentid(), discoursesModels.get(position).getName(),"",discoursesModels.get(position).getImage_url(),discoursesModels.get(position).getDescription());
                //CommonMethod.changeActivityText(context, VolumeActivity.class,discoursesModels.get(position).getParentid(),discoursesModels.get(position).getName(),"");
            }
        });

    }

    @Override
    public int getItemCount() {
        return discoursesModels.size();
    }

    public class Customview extends RecyclerView.ViewHolder {
        @BindView(R.id.topics_image)
        ImageView topics_image;
        @BindView(R.id.song_title)
        TextView songTitle;
        @BindView(R.id.volume_no)
        TextView volumeNo;

        @BindView( R.id.topic_no )
        TextView topics_number;


        public Customview(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
