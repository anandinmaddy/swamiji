package com.sastra.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.sastra.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CreatePlaylistAdapter extends BaseAdapter {

    ArrayList<ItemSong> mediaItems;
    Context context;

    public CreatePlaylistAdapter(){

    }
    public CreatePlaylistAdapter(ArrayList<ItemSong> media_det, Context context) {
        this.mediaItems = media_det;
        this.context = context;
    }

    static class ViewHolderItem {
        ImageView song_img_view;
        TextView song_title_view;
        TextView song_artist;
        TextView song_dur;
        ImageView playicon_img;
        CheckBox checkbox;
        TextView className;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolderItem viewHolder;

        // inflate the layout for each list row
        if (view == null) {
            viewHolder = new ViewHolderItem();
            view = LayoutInflater.from(context).
                    inflate(R.layout.activity_create_playlist_adapter, viewGroup, false);
            viewHolder.song_img_view = (ImageView)view.findViewById(R.id.ablum_image_img);
            viewHolder.song_title_view = (TextView)view.findViewById(R.id.album_title_txt);
            viewHolder.song_artist = (TextView)view.findViewById(R.id.song_type_txt);
            viewHolder.song_dur = (TextView)view.findViewById(R.id.duration_txt);
            viewHolder.className = (TextView)view.findViewById(R.id.className);

            viewHolder.playicon_img = (ImageView)view.findViewById(R.id.play_icon_imgg);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolderItem) view.getTag();
        //    notifyDataSetChanged();
        }

        viewHolder.checkbox.setTag(i);

        // final ImageView playlist_track = (ImageView)view.findViewById(R.id.plating_track_icon);
        // TextView song_start_letter = (TextView)view.findViewById(R.id.song_letter_txt);
        if(viewHolder.checkbox.getTag().equals(i) && viewHolder.checkbox.isChecked()){
            viewHolder.checkbox.setButtonDrawable(R.drawable.add_orange);
        }else{
            viewHolder.checkbox.setButtonDrawable(R.drawable.add_grey);
        }



//        ListOfLecturesListDetails details = (ListOfLecturesListDetails)lect_det.get(i);
        ItemSong items = mediaItems.get(i);

        try {
            Picasso.get()
                    .load(items.getImageSmall())
                    .into(viewHolder.song_img_view);

        }catch (Exception e){
            e.printStackTrace();
        }




        viewHolder.song_title_view.setText(items.getTitle());
        viewHolder.song_artist.setText(items.getClassName());
        viewHolder.song_dur.setText(items.getDuration());
        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (viewHolder.checkbox.getTag().equals(i)){
                        viewHolder.checkbox.setButtonDrawable(R.drawable.add_orange);
                        viewHolder.checkbox.setChecked(isChecked);
                    }
                    Constant.playListSongs1.add(mediaItems.get(i));
                }else {
                        viewHolder.checkbox.setButtonDrawable(R.drawable.add_grey);
                        viewHolder.checkbox.setChecked(false);
                    if (Constant.playListSongs1.contains(mediaItems.get(i))){
                        Constant.playListSongs1.remove(mediaItems.get(i));
                    }
                }
            }
        });







        return view;
    }
    @Override
    public int getViewTypeCount() {

        return getCount();
    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }
}
