package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Model.ListOfLecturesModels;
import com.example.im037.sastraprakasika.Model.ListOfTopicsModels;
import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LecturesRecyclerviewAdapter extends RecyclerView.Adapter<LecturesRecyclerviewAdapter.LectureHolder> {

    Context context;
    ArrayList<ListOfLecturesModels> listOfLecturesModels;
    public LecturesRecyclerviewAdapter(Context context, ArrayList<ListOfLecturesModels> listOfLecturesModels) {
        this.context = context;
        this.listOfLecturesModels = listOfLecturesModels;
    }

    @NonNull
    @Override
    public LectureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LectureHolder(LayoutInflater.from(context).inflate(R.layout.item_lectures, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LectureHolder holder, int position) {
        String value=listOfLecturesModels.get(position).getAblum_title();
        if(value.contains("Scriptures(Sastram)")){
            holder.ablumImage.setImageResource(R.drawable.tamil);
        }else if(value.contains("Right Action & Right Attitude")){
            holder.ablumImage.setImageResource(R.drawable.general);
        }else if(value.contains("An overview of Yoga")){
            holder.ablumImage.setImageResource(R.drawable.intro_vedanta);
        }else if(value.contains("Insight into Human Pursuits(Purusartha)")){
            holder.ablumImage.setImageResource(R.drawable.intro_vedanta);
        }else
        {
            holder.ablumImage.setImageResource(R.drawable.vedanta_icon);
        }
        holder.albumTitle.setText(listOfLecturesModels.get(position).getAblum_title());
        holder.songLetter.setText(listOfLecturesModels.get(position).getApla());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return listOfLecturesModels.size();
    }

    public class LectureHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.song_letter)
        TextView songLetter;
        @BindView(R.id.ablum_image)
        ImageView ablumImage;
        @BindView(R.id.album_title)
        TextView albumTitle;
        @BindView(R.id.play_icon)
        ImageView playIcon;
        @BindView(R.id.download_img)
        ImageView downloadImg;
        public LectureHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
