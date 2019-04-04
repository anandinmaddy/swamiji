package com.example.im037.sastraprakasika.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Fragment.NewFragments.dummy.TopicsDetailsFragment;
import com.example.im037.sastraprakasika.Model.ListOfTopicsDetailed;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.example.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopicsDetailedAdapter extends BaseAdapter{

    ArrayList<ListOfTopicsDetailed> listOfTopicsDetaileds;
    Context context;
    Activity activity;


    public TopicsDetailedAdapter(ArrayList<ListOfTopicsDetailed> listOfTopicsDetaileds, Activity activity) {
        this.listOfTopicsDetaileds = listOfTopicsDetaileds;
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listOfTopicsDetaileds.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfTopicsDetaileds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolderItem{
        ImageView imageView_list;
        TextView  txt_title ;
        TextView txt_dur ;
        TextView song_class;
        ImageView play_icon;
        LinearLayout topicsView;
        LinearLayout nowPlaying_layout,default_layout;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolderItem viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolderItem();
            convertView = LayoutInflater.from(context).
                           inflate(R.layout.topics_detailed_items_list,parent,false);
            viewHolder.imageView_list = (ImageView)convertView.findViewById(R.id.ablum_image_det_list);
            viewHolder.txt_title = (TextView)convertView.findViewById(R.id.album_title_det_list);
            viewHolder.txt_dur = (TextView)convertView.findViewById(R.id.duration_det_list);
            viewHolder.song_class = (TextView)convertView.findViewById(R.id.song_type);
            viewHolder.play_icon = (ImageView)convertView.findViewById(R.id.play_icon);
            viewHolder.topicsView = (LinearLayout) convertView.findViewById(R.id.topicsView);
            viewHolder.default_layout = (LinearLayout) convertView.findViewById(R.id.default_layout);
            viewHolder.nowPlaying_layout = (LinearLayout) convertView.findViewById(R.id.nowPlaying_layout);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolderItem) convertView.getTag();
            notifyDataSetChanged();
        }

        if (Constant.playPos == position && Constant.isplayTopics){
            viewHolder.default_layout.setVisibility(View.GONE);
            viewHolder.nowPlaying_layout.setVisibility(View.VISIBLE);

        }else {
            viewHolder.nowPlaying_layout.setVisibility(View.GONE);
            viewHolder.default_layout.setVisibility(View.VISIBLE);
        }
        ListOfTopicsDetailed topicsdet = listOfTopicsDetaileds.get(position);
        String song_title = topicsdet.getTopics_det_title();
        String song_dur = topicsdet.getTopics_time();
        String song_classname = topicsdet.getTopics_classname();

        viewHolder.song_class.setText(song_classname);
        viewHolder.txt_title.setText(song_title);
        viewHolder.txt_dur.setText(song_dur);
        Picasso.get()
                .load(listOfTopicsDetaileds.get(position).getTopics_det_imgurl())
                .into(viewHolder.imageView_list);

        viewHolder.topicsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.isOnline = false;
                Constant.playPos = position;
                Constant.isplayTopics = true;
                Constant.isFromPage = "topic";

                Bundle bundle = new Bundle();
                bundle.putString("from","topics");
                Constant.isFromPage = "topic";
                Intent intent = new Intent(activity, PlayerService.class);
                intent.setAction(PlayerService.ACTION_PLAY);
                intent.putExtras(bundle);
                activity.startService(intent);
                notifyDataSetChanged();


            }
        });

        return convertView;
    }
}
