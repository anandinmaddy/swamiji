package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.example.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter_Playlist_Next extends RecyclerView.Adapter<Adapter_Playlist_Next.Custom_view> {
/*
    ArrayList title_images;
    ArrayList imageview_titles;
    ArrayList class_no;
    ArrayList duration;*/
    ArrayList<ItemSong> arrayListSong = new ArrayList();
    Context context;

    public Adapter_Playlist_Next(ArrayList<ItemSong> arrayListSong,Context context) {
        this.arrayListSong = arrayListSong;
     //   this.imageview_titles = imageview_title;
     /*   this.class_no = class_no;
        this.duration = duration;*/
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_Playlist_Next.Custom_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_Playlist_Next.Custom_view(LayoutInflater.from(context).inflate(R.layout.playlist_detailed_listitems_nexts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_Playlist_Next.Custom_view holder, final int position) {


        holder.title_imge.setText(arrayListSong.get(position).getTitle());
        Picasso.get().load(arrayListSong.get(position).getImageBig()).placeholder(R.drawable.placeholder_default)
                .into(holder.song_imge);
       // holder.song_class.setText(arrayListSong.get(position).getCatName());
        holder.song_dur.setText(arrayListSong.get(position).getDuration());


        if (arrayListSong.size() <=0){

        }


        if (PlayerService.getIsPlayling() && Constant.playPos == position){
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.play_icon_next.setImageResource(android.R.color.transparent);
                holder.play_icon_next.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pause_grey_web));
            } else {
                holder.play_icon_next.setImageResource(android.R.color.transparent);
                holder.play_icon_next.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_pause_grey_web));
            }

            //  viewHolder.playlist_track.setVisibility( View.VISIBLE );
        }else{
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.play_icon_next.setImageResource(android.R.color.transparent);
                holder.play_icon_next.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play_grey_web));
            } else {
                holder.play_icon_next.setImageResource(android.R.color.transparent);
                holder.play_icon_next.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_play_grey_web));
            }
        }


        holder.songView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.playPos = position;
                Constant.lastPosition = position;
                //    viewHolder.play_icon_next.setVisibility( View.GONE );
                //  viewHolder.playlist_track.setVisibility( View.VISIBLE );
               // playerPosition = position;
                Constant.arrayList_play.clear();
                Constant.arrayList_play.addAll(arrayListSong);

                Constant.isOnline = false;
               // mCallback.onProcessFilter(false);
                Constant.isFromPage = "lecture";
                Intent intent = new Intent(context, PlayerService.class);
                intent.putExtra("from","lecture");
                // intent.setAction(PlayerService.ACTION_PLAY);
                //    context.getApplicationContext().startService(intent);

                if (Constant.isPlayed && Constant.lastPlayed == position) {
                    holder.play_icon_next.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play_grey_web));
                    intent.setAction(PlayerService.ACTION_TOGGLE);
                    context.getApplicationContext().startService(intent);
                } else {
                    if (!Constant.isOnline) {
                        Constant.lastPlayed = position;
                        holder.play_icon_next.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_pause_grey_web));
                        intent.setAction(PlayerService.ACTION_PLAY);
                        context.getApplicationContext().startService(intent);
                    }
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListSong.size();
    }



    public class Custom_view extends RecyclerView.ViewHolder {

        @BindView(R.id.album_title_det_list_next)
        TextView title_imge;
        @BindView(R.id.ablum_image_det_list_next)
        ImageView song_imge;
        @BindView(R.id.song_type_next)
        TextView song_class;
        @BindView(R.id.duration_det_list_next)
        TextView song_dur;
        @BindView(R.id.play_icon_next)
        ImageView play_icon_next;
        @BindView(R.id.songView)
        LinearLayout songView;

        public Custom_view(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
