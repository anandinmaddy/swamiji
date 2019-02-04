package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Activity.Playlist_detailed_Activity;
import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter_playlist extends RecyclerView.Adapter<Adapter_playlist.Customview> {

    ArrayList title_image;
    ArrayList imageview_title;
    Context context;

    public Adapter_playlist(ArrayList title_image, ArrayList imageview_title, Context context) {
        this.title_image = title_image;
        this.imageview_title = imageview_title;
        this.context = context;
    }


    @NonNull
    @Override
    public Adapter_playlist.Customview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Adapter_playlist.Customview(LayoutInflater.from(context).inflate(R.layout.fragment_playlist_items, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull final Adapter_playlist.Customview holder, final int position) {

       holder.title_img.setText((CharSequence) title_image.get(position));
       holder.song_img.setImageResource((Integer) imageview_title.get(position));







        holder.img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Playlist_detailed_Activity.class);
                 //intent.putExtra("title",holder.title_img.setText((CharSequence) title_image.get(position)));
                context.startActivity(intent);

            }
        });

        }

    @Override
    public int getItemCount() {
        return title_image.size();
    }



    public class Customview extends RecyclerView.ViewHolder {
        @BindView(R.id.album_image_title)
        TextView title_img;
        @BindView(R.id.album_image_playlist)
        ImageView song_img;
        @BindView(R.id.arrowicon)
        ImageView img_arrow;
        //        @BindView(R.id.volume_no)
//        TextView volumeNo;
        public Customview(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}

