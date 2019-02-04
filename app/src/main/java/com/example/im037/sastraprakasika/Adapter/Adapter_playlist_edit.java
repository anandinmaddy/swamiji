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

public class Adapter_playlist_edit  extends RecyclerView.Adapter<Adapter_playlist_edit.Edit_view>{

    ArrayList title_images;
    ArrayList imageview_titles;
    ArrayList class_no;
    ArrayList duration;
    Context context;

    public Adapter_playlist_edit(ArrayList title_images, ArrayList imageview_titles, ArrayList class_no, ArrayList duration, Context context) {
        this.title_images = title_images;
        this.imageview_titles = imageview_titles;
        this.class_no = class_no;
        this.duration = duration;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_playlist_edit.Edit_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adapter_playlist_edit.Edit_view(LayoutInflater.from(context).inflate(R.layout.playlist_detailed_edit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Edit_view holder, int position) {

        holder.title_imge_edit.setText((CharSequence) title_images.get(position));
        holder.song_imge_edit.setImageResource((Integer) imageview_titles.get(position));
        holder.song_class_edit.setText((CharSequence) class_no.get(position));
        holder.song_dur_edit.setText((CharSequence) duration.get(position));

    }

    @Override
    public int getItemCount() {
        return title_images.size();
    }

    public class Edit_view extends RecyclerView.ViewHolder {
        @BindView(R.id.album_title_det_list_next_edit)
        TextView title_imge_edit;
        @BindView(R.id.ablum_image_det_list_next_edit)
        ImageView song_imge_edit;
        @BindView(R.id.song_type_next_edit)
        TextView song_class_edit;
        @BindView(R.id.duration_det_list_next_edit)
        TextView song_dur_edit;
        public Edit_view(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
