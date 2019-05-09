package com.sastra.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Model.VolumeDetailsModel;
import com.sastra.im037.sastraprakasika.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolumeDetailsAdapter extends RecyclerView.Adapter<VolumeDetailsAdapter.CustomViewHolder> {
    Context context;
    ArrayList<VolumeDetailsModel> arrayList;



    public VolumeDetailsAdapter(Context context, ArrayList<VolumeDetailsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.volume_details_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        //holder.volumevalues.setText(arrayList.get(position).getVolume());
        holder.classes.setText(arrayList.get(position).getClasses());
        holder.price.setText(arrayList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.classes)
        TextView classes;
        @BindView(R.id.price)
        Button price;
//        @BindView(R.id.volumevalues)
//        TextView volumevalues;
        @BindView(R.id.classesvalue)
        TextView classesvalue;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
