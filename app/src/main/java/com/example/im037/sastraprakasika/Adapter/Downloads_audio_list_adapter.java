package com.example.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im037.sastraprakasika.Fragment.DownloadsFragmentNew;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class Downloads_audio_list_adapter extends BaseAdapter {

    //    ArrayList<ListOfLecturesListDetails> lect_det;
    ArrayList<ItemSong> mediaItems;
    Context context;
    private IProcessFilter mCallback;


    public Downloads_audio_list_adapter(ArrayList<ItemSong> media_det, Context context, IProcessFilter processCallback) {
        this.mediaItems = media_det;
        this.context = context;
        this.mCallback = processCallback;
    }

    static class ViewHolderItem{
        ImageView song_img_view;
        TextView song_title_view;
        TextView song_artist;
        TextView song_dur;
        ImageView playicon_img;
        ImageView delete_play;

    }

    public interface IProcessFilter {
        void onProcessFilter(String b);
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
        // inflate the layout for each list row

        ViewHolderItem viewHolder = null;

        if (view == null) {
            viewHolder = new ViewHolderItem();
            view = LayoutInflater.from(context).
                    inflate(R.layout.downloads_audio_list_items, viewGroup, false);

            viewHolder.song_img_view = (ImageView)view.findViewById(R.id.ablum_image_img);
            viewHolder.song_title_view = (TextView)view.findViewById(R.id.album_title_txt);
            viewHolder.song_artist = (TextView)view.findViewById(R.id.song_type_txt);
            viewHolder.song_dur = (TextView)view.findViewById(R.id.duration_txt);
            viewHolder.playicon_img = (ImageView)view.findViewById(R.id.play_icon_imgg);
            viewHolder.delete_play = (ImageView)view.findViewById(R.id.iv_delete_downloads);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        viewHolder.delete_play.setTag(i);

        // TextView song_start_letter = (TextView)view.findViewById(R.id.song_letter_txt);


//        ListOfLecturesListDetails details = (ListOfLecturesListDetails)lect_det.get(i);
        final ItemSong items = mediaItems.get(i);

        Picasso.get()
                .load(items.getImageSmall())
                .placeholder(R.drawable.placeholder_song)
                .into(viewHolder.song_img_view);
        //// song_img_view.setImageResource(R.drawable.vedanta);

        viewHolder.song_title_view.setText(items.getTitle());
        viewHolder.song_artist.setText(items.getClassName());
        viewHolder.song_dur.setText(items.getDuration());

        viewHolder.delete_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.arrayListOfflineSongs.size() > 0){
                    String path = Constant.arrayListOfflineSongs.get(i).getDownloads();
                    String fileName = Constant.arrayListOfflineSongs.get(i).getTitle();

                    if(path != null && path != ""){
                        File file = new File(path);
                        file.delete();
                        notifyDataSetChanged();

                        Toast.makeText(context, "Audio Deleted",Toast.LENGTH_SHORT).show();
                        mCallback.onProcessFilter(fileName);
                    }
                }

            }
        });

        //song_start_letter.setText(items.getAlpha_letter());
//        playicon_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                playicon_img.setVisibility( View.INVISIBLE );
//                playlist_track.setVisibility( View.VISIBLE );
//
//            }
//        });



//        song_title_view.setText(details.getSong_name());
//        song_artist.setText(details.getSong_artise());
//        song_dur.setText(details.getSong_duration());
//        song_img_view.setImageResource(details.getSong_imagev());
//        playicon_img.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//
//           }
//       });

//        song_img_view.setImageResource(R.drawable.tamil);

//
//        playicon_img.setImageResource(details.getSong_playicon());


        return view;
    }
}
