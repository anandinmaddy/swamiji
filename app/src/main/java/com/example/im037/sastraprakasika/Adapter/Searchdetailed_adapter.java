package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Model.SearchModel;
import com.example.im037.sastraprakasika.Model.Search_list;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.example.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Searchdetailed_adapter extends BaseAdapter{

    Context context;
    ArrayList<SearchModel> search_lists;

    public Searchdetailed_adapter(Context ctx,ArrayList<SearchModel> searchlist) {
        this.context = ctx;
        this.search_lists = searchlist;
    }

    @Override
    public int getCount() {
       return search_lists.size();
    }

    @Override
    public Object getItem(int position) {
        return search_lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.search_detail_list_items,parent,false);
        }
        ImageView imageView_list = (ImageView)convertView.findViewById(R.id.ablum_image_det_list_search);
        TextView txt_title = (TextView)convertView.findViewById(R.id.album_title_det_list_search);
        TextView txt_dur = (TextView)convertView.findViewById(R.id.duration_det_list_seacrh);
        TextView txt_cname = (TextView)convertView.findViewById(R.id.song_type);


        SearchModel searchlistitems = search_lists.get(position);
        String song_title = searchlistitems.getTitle();
        String song_dur = searchlistitems.getTime();


        txt_title.setText(song_title);
        txt_dur.setText(song_dur);
        Picasso.get()
                .load(search_lists.get(position).getImage_url())
                .into(imageView_list);
        txt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.playPos = position;
                Constant.lastPosition = position;
                //    viewHolder.playicon_img.setVisibility( View.GONE );
                //  viewHolder.playlist_track.setVisibility( View.VISIBLE );
                Constant.arrayList_play.clear();
                Constant.arrayList_play.addAll(Constant.arrayListLectureslineSongs);

                Constant.isOnline = false;
                Constant.isFromPage = "lecture";
                Intent intent = new Intent(context, PlayerService.class);
                intent.putExtra("from","lecture");
                // intent.setAction(PlayerService.ACTION_PLAY);
                //    context.getApplicationContext().startService(intent);

                if (Constant.isPlayed && Constant.lastPlayed == position) {
                    intent.setAction(PlayerService.ACTION_TOGGLE);
                    context.getApplicationContext().startService(intent);
                } else {
                    if (!Constant.isOnline) {
                        Constant.lastPlayed = position;
                        intent.setAction(PlayerService.ACTION_PLAY);
                        context.getApplicationContext().startService(intent);
                    }
                }

            }
        });


        return convertView;
    }
}
