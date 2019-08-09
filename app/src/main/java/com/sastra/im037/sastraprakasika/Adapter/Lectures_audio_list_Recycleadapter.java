package com.sastra.im037.sastraprakasika.Adapter;


import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sastra.im037.sastraprakasika.Fragment.NewFragments.VolumeDetailsFragment;
import com.sastra.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.sastra.im037.sastraprakasika.Model.SearchModel;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.sastra.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.sastra.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Lectures_audio_list_Recycleadapter extends RecyclerView.Adapter<Lectures_audio_list_Recycleadapter.ViewHolder> {

    ArrayList<ItemSong> mediaItems;

    Context context;
    int playerPosition;
    boolean tempallot= false;
    HashMap<String,Integer> hashMap= new HashMap<>();
    boolean isDownloading = false;
    private Lectures_audio_list_adapter.IProcessFilter mCallback;
    int lastposition = 0;
    AsyncTask asyncTask;
    long total = 0;
    boolean isDownloadUpdated = false;
    ArrayList<Integer> downloadFile = new ArrayList<>();
    DiscousesAppDatabase db;
    private ThinDownloadManager downloadManager;




    public Lectures_audio_list_Recycleadapter(ArrayList<ItemSong> media_det, Context context, Lectures_audio_list_adapter.IProcessFilter mCallback) {
        this.mediaItems = media_det;
        this.context = context;
        this.mCallback = mCallback;
        Constant.wholeMediaList.addAll(media_det);
        if (context != null){
            db = Room.databaseBuilder(context,
                    DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();
        }

        try{
            downloadManager = new ThinDownloadManager(1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.lectures_audio_list_items, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        viewHolder.downloadBtn.setTag(i);
        //     viewHolder.progressInside.setTag(i);
        viewHolder.iv_music_pause_downloads.setTag(i);
        viewHolder.circularProgressbar.setTag(i);


        if(mediaItems.get(i).getImageBig() != null && !mediaItems.get(i).getImageBig().isEmpty()){
            Picasso.get()
                    .load(mediaItems.get(i).getImageBig())
                    .placeholder(R.drawable.placeholder_default)
                    .into(viewHolder.song_img_view);
        }
        Log.d("progressss",String.valueOf(mediaItems.get(i).getProgress()));

        viewHolder.song_title_view.setText(mediaItems.get(i).getTitle());
        viewHolder.song_artist.setText(mediaItems.get(i).getClassName());
        viewHolder.song_dur.setText(mediaItems.get(i).getDuration());

        final int lastPlayed = 0;
        if (Constant.playPos    == i && Constant.isplayLectures) {
            viewHolder.textNowPlaying.setVisibility(View.GONE);
            viewHolder.nowPlaying_layout.setVisibility(View.VISIBLE);
        }else {
            viewHolder.nowPlaying_layout.setVisibility(View.GONE);
            viewHolder.textNowPlaying.setVisibility(View.VISIBLE);
        }


        if (Constant.downloadCount == 0 && Constant.downloadCompleted){
            mCallback.onProcessFilter(true,mediaItems);
            isDownloadUpdated = false;
            Constant.downloadCompleted = false;
        }
       /* if (isDownloadUpdated){
            isDownloadUpdated = false;
            mCallback.onProcessFilter(true);
        }*/



        if((Integer) viewHolder.downloadBtn.getTag() != null && (Integer) viewHolder.downloadBtn.getTag() == i && (Integer) viewHolder.iv_music_pause_downloads.getTag() != null && (Integer) viewHolder.iv_music_pause_downloads.getTag() == i  && viewHolder.downloadStatus == true){
            viewHolder.downloadBtn.setVisibility(View.GONE);
            viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
        }else if((Integer) viewHolder.downloadBtn.getTag() != null && (Integer) viewHolder.downloadBtn.getTag() == i && !mediaItems.get(i).getDownloads().isEmpty()){
            viewHolder.downloadBtn.setVisibility(View.GONE);
        }else if((Integer) viewHolder.downloadBtn.getTag() != null && (Integer) viewHolder.downloadBtn.getTag() == i  && viewHolder.isDownloadPaused) {
            viewHolder.downloadBtn.setVisibility(View.GONE);
        }else{
            if (viewHolder.isDownloadProgress){
                viewHolder.downloadBtn.setVisibility(View.GONE);
            }else {
                if (viewHolder.downloadBtn.getTag() != null ){
                    if (downloadFile != null && downloadFile.contains(i) && (Integer) viewHolder.iv_music_pause_downloads.getTag() != null && (Integer) viewHolder.iv_music_pause_downloads.getTag() == i  && viewHolder.downloadStatus == true){
                        viewHolder.downloadBtn.setVisibility(View.GONE);
                        viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                    }else if (viewHolder.isDownloadHappening){
                        //   viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                        // viewHolder.downloadBtn.setVisibility(View.GONE);
                    }else {
                        viewHolder.iv_music_pause_downloads.setVisibility(View.GONE);
                        viewHolder.downloadBtn.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        //     TextView song_start_letter = (TextView)view.findViewById(R.id.song_letter_txt);

        viewHolder.circularProgressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.isDownloadPaused){
                    viewHolder.downloadBtn.setVisibility(View.GONE);
                /*    Picasso.get()
                            .load(R.drawable.pause_orange_icon)
                            .into(viewHolder.progressInside);*/
               //     asyncTask = new Lectures_audio_list_Recycleadapter.DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus,viewHolder.isDownloadProgress,viewHolder.isDownloadHappening).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,mediaItems.get(i).getUrl(),mediaItems.get(i).getTrackId(),"false");
                    // viewHolder.progressInside.setVisibility(View.GONE);
                    viewHolder.downloadBtn.setVisibility(View.GONE);
                    viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                    viewHolder.isDownloadPaused = false;
                }else {
                    viewHolder.downloadBtn.setVisibility(View.GONE);
                    viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                    if (viewHolder.iv_music_pause_downloads.getTag() != null  && (Integer) viewHolder.iv_music_pause_downloads.getTag() == i) {
                        db.itemSongDao().updateRat("true",mediaItems.get(i).getTitle());
                    }
                    if (asyncTask != null){
                     /*   Picasso.get()
                                .load(R.drawable.resume)
                                .into(viewHolder.progressInside);*/
                        asyncTask.cancel(true);
                        //    viewHolder.progressInside.setVisibility(View.VISIBLE);
                        viewHolder.downloadBtn.setVisibility(View.GONE);
                        viewHolder.isDownloadPaused = true;
                    }
                }
            }
        });

        viewHolder.iv_music_pause_downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.iv_music_pause_downloads.getTag() != null  && (Integer) viewHolder.iv_music_pause_downloads.getTag() == i){
                    db.itemSongDao().updateRat("true",mediaItems.get(i).getTitle());
                    String path = readFilePath(mediaItems.get(i).getTitle());
                    if(path != null && path != ""){
                        File file = new File(path);
                        file.delete();
                    }
                    //    boolean status = asyncTask.cancel(true);

                    int downloadId = hashMap.get(mediaItems.get(i).getTitle());

                    downloadManager.cancel(downloadId);
                 /*   Picasso.get()
                            .load(R.drawable.pause_orange_icon)
                            .into(viewHolder.progressInside);
*/
                    viewHolder.iv_music_pause_downloads.setVisibility(View.GONE);
                    //      viewHolder.progressInside.setVisibility(View.GONE);
                    viewHolder.circularProgressbar.setVisibility(View.GONE);
                    viewHolder.downloadBtn.setVisibility(View.VISIBLE);
                    viewHolder.isDownloadProgress = false;
                    try {
                        downloadFile.remove(Integer.valueOf(i));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });


        viewHolder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMobiledata(context)){
                    new AlertDialog.Builder(context)
                            .setTitle("Mobile data alert!")
                            .setMessage("Are you sure you want to download with mobile data?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    viewHolder.downloadBtn.setVisibility(View.GONE);
                                    viewHolder.circularProgressbar.setVisibility(View.VISIBLE);
                                    viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                                    db.itemSongDao().updateProgress(1,mediaItems.get(i).getTitle());
                                    Constant.downloadPosition = i;
                                    Constant.downloadCount = Constant.downloadCount +1;
                                    viewHolder.isDownloadHappening = true;
                                    mediaItems.get(i).setTotalRate("false");
                                    //asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus,viewHolder.isDownloadProgress,viewHolder.isDownloadHappening).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle(),"false");

                                    Uri downloadUri = Uri.parse(mediaItems.get(i).getUrl());
                                    Uri destinationUri = Uri.fromFile(setFileName(mediaItems.get(i).getTrackId()));

                                    final DownloadRequest downloadRequest1 = new DownloadRequest(downloadUri).setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.LOW).setRetryPolicy(new DefaultRetryPolicy()).setDownloadContext("Download1").setDownloadListener(new DownloadStatusListener() {
                                        @Override
                                        public void onDownloadComplete(int id) {
                                            viewHolder.circularProgressbar.setVisibility(View.GONE);
                                            viewHolder.iv_music_pause_downloads.setVisibility(View.GONE);
                                            Constant.downloadCount = Constant.downloadCount -1;
                                            isDownloadUpdated = true;
                                            //Constant.downloadCompleted = true;
                                            if (Constant.downloadCount == 0 && isDownloadUpdated) {
                                                Constant.downloadCompleted = true;
                                            }

                                        }

                                        @Override
                                        public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                                            viewHolder.circularProgressbar.setVisibility(View.GONE);
                                            viewHolder.iv_music_pause_downloads.setVisibility(View.GONE);
                                            viewHolder.downloadBtn.setVisibility(View.VISIBLE);
                                            Constant.downloadCount = Constant.downloadCount -1;

                                        }

                                        @Override
                                        public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
                                            viewHolder.circularProgressbar.setVisibility(View.VISIBLE);
                                            viewHolder.circularProgressbar.setProgress(progress);
                                        }
                                    });


                                    int downloadnId = downloadManager.add(downloadRequest1);
                                    hashMap.put(mediaItems.get(i).getTitle(),downloadnId);
                                    //   asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus,viewHolder.isDownloadProgress,viewHolder.isDownloadHappening).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle(),"false");
                                /*        if(viewHolder.downloadBtn.getTag().equals(i)) {
                                            viewHolder.downloadBtn.setVisibility(View.VISIBLE);
                                            viewHolder.circularProgressbar.setVisibility(View.GONE);
                                        }*/

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                   /*     if(viewHolder.downloadBtn.getTag().equals(i)) {

                            viewHolder.downloadBtn.setVisibility(View.GONE);
                            viewHolder.circularProgressbar.setVisibility(View.VISIBLE);
                        }*/
                    viewHolder.downloadBtn.setVisibility(View.GONE);
                    viewHolder.circularProgressbar.setVisibility(View.VISIBLE);
                    viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                    //   viewHolder.progressInside.setVisibility(View.VISIBLE);
                    Constant.downloadPosition = i;
                    Constant.downloadCount = Constant.downloadCount +1;
                    viewHolder.isDownloadHappening = true;
                    mediaItems.get(i).setTotalRate("false");
                    //asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus,viewHolder.isDownloadProgress,viewHolder.isDownloadHappening).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle(),"false");
                    db.itemSongDao().updateProgress(1,mediaItems.get(i).getTitle());

                    Uri downloadUri = Uri.parse(mediaItems.get(i).getUrl());
                    Uri destinationUri = Uri.fromFile(setFileName(mediaItems.get(i).getTrackId()));

                    final DownloadRequest downloadRequest1 = new DownloadRequest(downloadUri).setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.LOW).setRetryPolicy(new DefaultRetryPolicy()).setDownloadContext("Download1").setDownloadListener(new DownloadStatusListener() {
                        @Override
                        public void onDownloadComplete(int id) {
                            viewHolder.circularProgressbar.setVisibility(View.GONE);
                            viewHolder.iv_music_pause_downloads.setVisibility(View.GONE);
                            Constant.downloadCount = Constant.downloadCount -1;
                            isDownloadUpdated = true;
                        //    Constant.downloadCompleted = true;
                            db.itemSongDao().updateProgress(0,mediaItems.get(i).getTitle());

                            if (Constant.downloadCount == 0 && isDownloadUpdated) {
                                Constant.downloadCompleted = true;
                            }

                        }

                        @Override
                        public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                            viewHolder.circularProgressbar.setVisibility(View.GONE);
                            viewHolder.iv_music_pause_downloads.setVisibility(View.GONE);
                            viewHolder.downloadBtn.setVisibility(View.VISIBLE);
                            Constant.downloadCount = Constant.downloadCount -1;
                            db.itemSongDao().updateProgress(0,mediaItems.get(i).getTitle());


                        }


                        @Override
                        public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
                            viewHolder.circularProgressbar.setVisibility(View.VISIBLE);
                            viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                            viewHolder.downloadBtn.setVisibility(View.GONE);
                            viewHolder.circularProgressbar.setProgress(progress);
                            db.itemSongDao().updateProgress(progress,mediaItems.get(i).getTitle());


                        }
                    });


                    int downloadnId = downloadManager.add(downloadRequest1);
                    hashMap.put(mediaItems.get(i).getTitle(),downloadnId);
                    //   asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus,viewHolder.isDownloadProgress,viewHolder.isDownloadHappening).execute(mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle(),"false");
                /*        if(viewHolder.downloadBtn.getTag().equals(i)) {

                            viewHolder.circularProgressbar.setVisibility(View.GONE);
                        }*/
                }

            }
        });



        //   song_img_view.setImageBitmap(image);
        viewHolder.musicPlayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.playPos = i;
                Constant.lastPosition = i;
                playerPosition = i;
                Constant.arrayList_play.clear();
                Constant.arrayList_play.addAll(Constant.arrayListLectureslineSongs);

                Constant.isOnline = false;
                Constant.isFromPage = "lecture";
                Intent intent = new Intent(context, PlayerService.class);
                intent.putExtra("from","lecture");
                Constant.isfromPlayer = "lecturer";
                Constant.isplayLectures = true;
                notifyDataSetChanged();
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

            }

        });

        // viewHolder.song_img_view.setImageResource(R.drawable.tamil);


    }

    private File setFileName(String title) {
        File folder = Environment.getExternalStoragePublicDirectory("Swamiji");
        if (!folder.exists())
            folder.mkdirs();

        File file = new File(folder, title+".swami");

        return file;
    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ablum_image_img)
        ImageView song_img_view;

        @BindView(R.id.album_title_txt)
        TextView song_title_view;

        @BindView(R.id.song_type_txt)
        TextView song_artist;

        @BindView(R.id.duration_txt)
        TextView song_dur;

        @BindView(R.id.play_icon)
        ImageView playicon_img;

        @BindView(R.id.plating_track_icon)
        ImageView playlist_track;

        @BindView(R.id.iv_music_downloads)
        ImageView downloadBtn;

        @BindView(R.id.progressStateLayout)
        RelativeLayout loadingDownload;

        @BindView(R.id.iv_music_pause_downloads)
        ImageView iv_music_pause_downloads;
        //   ImageView progressInside;
        //   MaterialProgressBar button_progress_2;
        //     CircleProgressView circleProgressView;
        @BindView(R.id.progressBar)
        ProgressBar circularProgressbar;

        @BindView(R.id.nowPlaying_layout)
        LinearLayout nowPlaying_layout;

        @BindView(R.id.textNowPlaying)
        LinearLayout textNowPlaying;

        @BindView(R.id.musicPlayLayout)
        LinearLayout musicPlayLayout;


        boolean isDownloadPaused = false;
        boolean isDownloadProgress = false;
        boolean downloadStatus = false;
        boolean isDownloadHappening = false;



        //        @BindView(R.id.search_type)
//       TextView searchType;
//       @BindView(R.id.search_duration)
//        TextView searchDuration;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static String readFilePath(String songTitle) {
        ArrayList<String> downloadSongs = new ArrayList<>();
        try {
            File folder = Environment.getExternalStoragePublicDirectory("Swamiji");
            String path = Environment.getExternalStorageDirectory().toString() + "/Swamiji";
            File directory = new File(path);
            File[] files = directory.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().contains(songTitle)) {
                        return files[i].getAbsolutePath();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isMobiledata(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            if (info.getTypeName().equalsIgnoreCase("WIFI")){
                return false;
            }else {
                return true;
            }
        }else{
            Toast.makeText(context, "Try in online!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
