package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter_Playlist_Next extends RecyclerView.Adapter<Adapter_Playlist_Next.Custom_view> {

    ArrayList title_images;
    ArrayList imageview_titles;
    ArrayList class_no;
    ArrayList duration;
    Context context;

    public Adapter_Playlist_Next(ArrayList title_image, ArrayList imageview_title, ArrayList class_no, ArrayList duration, Context context) {
        this.title_images = title_image;
        this.imageview_titles = imageview_title;
        this.class_no = class_no;
        this.duration = duration;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_Playlist_Next.Custom_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_Playlist_Next.Custom_view(LayoutInflater.from(context).inflate(R.layout.playlist_detailed_listitems_nexts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Playlist_Next.Custom_view holder, int position) {

        holder.title_imge.setText((CharSequence) title_images.get(position));
        holder.song_imge.setImageResource((Integer) imageview_titles.get(position));
        holder.song_class.setText((CharSequence) class_no.get(position));
        holder.song_dur.setText((CharSequence) duration.get(position));



    }

    @Override
    public int getItemCount() {
        return title_images.size();
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

        public Custom_view(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
