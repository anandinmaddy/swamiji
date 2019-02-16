/*
package com.example.im037.sastraprakasika.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.VolumeDetailsActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Model.VolumeModel;
import com.example.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.CustomViewHolder> {
    Context context;
    ArrayList<VolumeModel> arrayList;
    String parentID;



    public VolumeAdapter(FragmentManager fm, String parentId, ArrayList<VolumeModel> arrayList) {
        super(fm);
        this.context = context;
        this.arrayList = arrayList;
        this.parentID = parentId;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.discourses_detailed_vedanta, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {

        Picasso.get().load(arrayList.get(position).getImage_url()).into(holder.image);
        holder.title.setText(arrayList.get(position).getName());
        holder.noOfVolcount.setText(arrayList.get(position).getTitlecount());
        //holder.trackcount.setText(arrayList.get(position).getTrackcount());
        holder.image.setAdjustViewBounds(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.changeActivityData5(context, VolumeDetailsActivity.class, parentID,arrayList.get(position).getSubid(), arrayList.get(position).getName(),arrayList.get(position).getImage_url(),arrayList.get(position).getDescription());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView noOfVolcount;
//        @BindView(R.id.trackcount)
//        TextView trackcount;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            noOfVolcount = (TextView) itemView.findViewById(R.id.noOfVolcount);
        }
    }
}
*/
