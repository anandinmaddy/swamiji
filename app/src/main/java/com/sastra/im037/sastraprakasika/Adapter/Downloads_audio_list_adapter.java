package com.sastra.im037.sastraprakasika.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.sastra.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.sastra.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class Downloads_audio_list_adapter extends BaseAdapter {

    //    ArrayList<ListOfLecturesListDetails> lect_det;
    ArrayList<ItemSong> mediaItems;
    Context context;
    private IProcessFilter mCallback;
    private int lastPosition = -1;



    public Downloads_audio_list_adapter(ArrayList<ItemSong> media_det, Context context, IProcessFilter processCallback) {
        this.mediaItems = media_det;
        this.context = context;
        this.mCallback = processCallback;
        Log.e("demodownload","demodownload");
    }

    static class ViewHolderItem{
        ImageView song_img_view;
        TextView song_title_view;
        TextView song_artist;
        TextView song_dur;
        ImageView playicon_img;
        ImageView delete_play;
        LinearLayout nowPlaying_layout,textNowPlaying,musicPlayLayout;

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
            viewHolder.nowPlaying_layout = (LinearLayout) view.findViewById(R.id.nowPlaying_layout);
            viewHolder.textNowPlaying = (LinearLayout) view.findViewById(R.id.textNowPlaying);
            viewHolder.musicPlayLayout = (LinearLayout) view.findViewById(R.id.musicPlayLayout);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        viewHolder.delete_play.setTag(i);

        // TextView song_start_letter = (TextView)view.findViewById(R.id.song_letter_txt);
      /*  final Animation animation = AnimationUtils.loadAnimation(context, (i > lastPosition) ? R.anim.up_from_bottom1 : R.anim.up_from_bottom1);
        view.startAnimation(animation);*/
        lastPosition = i;




        if (Constant.playPos == i && Constant.isplayDownloads) {
            viewHolder.textNowPlaying.setVisibility(View.GONE);
            viewHolder.nowPlaying_layout.setVisibility(View.VISIBLE);
        }else {
            viewHolder.nowPlaying_layout.setVisibility(View.GONE);
            viewHolder.textNowPlaying.setVisibility(View.VISIBLE);
        }


//        ListOfLecturesListDetails details = (ListOfLecturesListDetails)lect_det.get(i);
        final ItemSong items = mediaItems.get(i);

        try {
            Picasso.get()
                    .load(items.getImageSmall())
                    .placeholder(R.drawable.placeholder_default)
                    .into(viewHolder.song_img_view);

        }catch (Exception e){
            e.printStackTrace();
        }
        //// song_img_view.setImageResource(R.drawable.vedanta);

        viewHolder.song_title_view.setText(items.getTitle());
        viewHolder.song_artist.setText(items.getClassName());
        viewHolder.song_dur.setText(items.getDuration());

        final View finalView = view;
        final ViewHolderItem finalViewHolder = viewHolder;
        viewHolder.delete_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.arrayListOfflineSongs.size() > 0){
                    final String path = Constant.arrayListOfflineSongs.get(i).getDownloads();
                    final String fileName = Constant.arrayListOfflineSongs.get(i).getTitle();
                    Constant.isplayLectures = true;

                    if(path != null && path != ""){
                        Animation anim = AnimationUtils.loadAnimation(context,
                                android.R.anim.slide_out_right);
                        anim.setDuration(500);
                        finalViewHolder.musicPlayLayout.startAnimation(anim);
                        notifyDataSetChanged();

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                File file = new File(path);
                                file.delete();
                                Toast.makeText(context, "Audio Deleted",Toast.LENGTH_SHORT).show();
                                mCallback.onProcessFilter(fileName);
                                /*Constant.playListSongSync.remove(arrayListSong.get(position)); //Remove the current content from the array
                                notifyDataSetChanged();*/ //Refresh list
                            }

                        }, anim.getDuration());


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


        viewHolder.musicPlayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.playPos = i;
                Constant.lastPosition = i;
                Constant.arrayList_play.clear();
                Constant.arrayList_play.addAll(Constant.arrayListOfflineSongs);
                Constant.isfromPlayer = "download";

                Constant.isOnline = false;
                Intent intent = new Intent(context, PlayerService.class);
                intent.putExtra("from","lecture");
                Constant.isplayDownloads = true;

                if (Constant.isPlayed && Constant.lastPlayed == i) {
                    intent.setAction(PlayerService.ACTION_TOGGLE);
                    context.getApplicationContext().startService(intent);
                } else {
                    if (!Constant.isOnline) {
                        Constant.lastPlayed = i;
                        intent.setAction(PlayerService.ACTION_PLAY);
                        context.getApplicationContext().startService(intent);
                    }
                }
                notifyDataSetChanged();
            }
        });
        return view;
    }

}