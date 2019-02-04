package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Model.ListOfTopicsDetailed;
import com.example.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopicsDetailedAdapter extends BaseAdapter{

    ArrayList<ListOfTopicsDetailed> listOfTopicsDetaileds;
    Context context;


    public TopicsDetailedAdapter(ArrayList<ListOfTopicsDetailed> listOfTopicsDetaileds, Context ctx) {
        this.listOfTopicsDetaileds = listOfTopicsDetaileds;
        this.context = ctx;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).
                           inflate(R.layout.topics_detailed_items_list,parent,false);
        }
        ImageView imageView_list = (ImageView)convertView.findViewById(R.id.ablum_image_det_list);
        TextView  txt_title = (TextView)convertView.findViewById(R.id.album_title_det_list);
        TextView txt_dur = (TextView)convertView.findViewById(R.id.duration_det_list);
        TextView song_class = (TextView)convertView.findViewById(R.id.song_type);

        ListOfTopicsDetailed topicsdet = listOfTopicsDetaileds.get(position);
        String song_title = topicsdet.getTopics_det_title();
        String song_dur = topicsdet.getTopics_time();
        String song_classname = topicsdet.getTopics_classname();

        song_class.setText(song_classname);
        txt_title.setText(song_title);
        txt_dur.setText(song_dur);
        Picasso.get()
                .load(listOfTopicsDetaileds.get(position).getTopics_det_imgurl())
                .into(imageView_list);


        return convertView;
    }
}
