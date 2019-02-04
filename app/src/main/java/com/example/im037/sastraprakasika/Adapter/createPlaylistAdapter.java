package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class createPlaylistAdapter extends BaseAdapter {

    ArrayList<ItemSong> mediaItems;
    Context context;

    public createPlaylistAdapter(ArrayList<ItemSong> media_det, Context context) {
        this.mediaItems = media_det;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mediaItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mediaItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // inflate the layout for each list row
        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.activity_create_playlist_adapter, viewGroup, false);
        }

        ImageView song_img_view = (ImageView)view.findViewById(R.id.ablum_image_img);
        TextView song_title_view = (TextView)view.findViewById(R.id.album_title_txt);
        TextView song_artist = (TextView)view.findViewById(R.id.song_type_txt);
        TextView song_dur = (TextView)view.findViewById(R.id.duration_txt);
        final ImageView playicon_img = (ImageView)view.findViewById(R.id.play_icon_imgg);
        // final ImageView playlist_track = (ImageView)view.findViewById(R.id.plating_track_icon);
        // TextView song_start_letter = (TextView)view.findViewById(R.id.song_letter_txt);




//        ListOfLecturesListDetails details = (ListOfLecturesListDetails)lect_det.get(i);
        ItemSong items = mediaItems.get(i);

        Picasso.get()
                .load(items.getImageSmall())
                .into(song_img_view);

        song_title_view.setText(items.getTitle());
        song_artist.setText(items.getClassName());
        song_dur.setText(items.getDuration());

        return view;
    }
}
