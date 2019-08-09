package com.sastra.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Fragment.NewFragments.MyLectureFragment;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.sastra.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreatePlaylistAdapter extends BaseAdapter {

    ArrayList<ItemSong> mediaItems;
    Context context;
    private int lastPosition = -1;
    List<ItemSong> names ;
    public CreatePlaylistAdapter(){

    }
    public CreatePlaylistAdapter(ArrayList<ItemSong> media_det, Context context) {
        this.mediaItems = media_det;
        this.context = context;
        this.names=new ArrayList<ItemSong>();
        this.names.addAll(mediaItems);
        Constant.playListSongs1.clear();
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


        Animation animation = AnimationUtils.loadAnimation(context, (i > lastPosition) ? R.anim.up_from_bottom1 : R.anim.up_from_bottom1);
        view.startAnimation(animation);
        lastPosition = i;
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
                MyLectureFragment.showaddbt();
                if (isChecked){
                    if (viewHolder.checkbox.getTag().equals(i)){
                        viewHolder.checkbox.setButtonDrawable(R.drawable.add_orange);
                        viewHolder.checkbox.setChecked(isChecked);
                        MyLectureFragment.showaddbt();
                    }
                    Constant.playListSongs1.add(mediaItems.get(i));
                }else {
                    viewHolder.checkbox.setButtonDrawable(R.drawable.add_grey);
                    viewHolder.checkbox.setChecked(false);
                    if (Constant.playListSongs1.contains(mediaItems.get(i))){
                        Constant.playListSongs1.remove(mediaItems.get(i));
                    }
                }

                if (  Constant.playListSongs1.size()==0){
                    Log.e("playListSongs1",""+ Constant.playListSongs1.size());
                    MyLectureFragment.hideaddbt();
                }else {
                    Log.e("playListSongs1",""+ Constant.playListSongs1.size());
                    MyLectureFragment.showaddbt();
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


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ItemSong itemSong=new ItemSong();
        Log.e("getCatName",""+itemSong.getCatName());
        mediaItems.clear();
        if (charText.length() == 0) {
            Log.e("texsting","sampletest");
            mediaItems.addAll(names);

        }
        else
        {
            for ( ItemSong itemSongName : names)
            {
                String title=itemSongName.getTitle();
                String title1= Normalizer.normalize(title, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                if (title1.toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Log.e("mediaItems21",""+itemSongName.getTitle());
                    mediaItems.add(itemSongName);
                    notifyDataSetChanged();
                }
            }
        }
        notifyDataSetChanged();
    }

}
