package com.sastra.im037.sastraprakasika.Adapter;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sastra.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.sastra.im037.sastraprakasika.Model.ListOfTopicsDetailed;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
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

public class TopicsDetailedAdapter extends BaseAdapter{

    ArrayList<ListOfTopicsDetailed> listOfTopicsDetaileds;
    Context context;
    ArrayList<Integer> downloadFile = new ArrayList<>();
    DiscousesAppDatabase db;
    boolean isDownloadUpdated= false;
    private ThinDownloadManager downloadManager;
    HashMap<String,Integer> hashMap= new HashMap<>();
    private int lastPosition = -1;


    public TopicsDetailedAdapter(ArrayList<ListOfTopicsDetailed> listOfTopicsDetaileds, Context context) {
        this.listOfTopicsDetaileds = listOfTopicsDetaileds;
        this.context = context;
        db = Room.databaseBuilder(context,
                DiscousesAppDatabase.class, "ListOfTopicDetailed").allowMainThreadQueries().build();
        try{
            downloadManager = new ThinDownloadManager(1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getCount() {
        return listOfTopicsDetaileds.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfTopicsDetaileds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolderItem{
        ImageView imageView_list;
        TextView  txt_title ;
        TextView txt_dur ;
        TextView song_class;
        ImageView play_icon;
        LinearLayout topicsView;
        LinearLayout nowPlaying_layout,default_layout;
        ImageView downloadBtn;
        ProgressBar circularProgressbar;
        ImageView iv_music_pause_downloads;
        boolean isDownloadProgress,isDownloadHappening,isDownloadPaused,downloadStatus = false;

    }
    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        final ViewHolderItem viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolderItem();
            convertView = LayoutInflater.from(context).
                           inflate(R.layout.topics_detailed_items_list,parent,false);
            viewHolder.imageView_list = (ImageView)convertView.findViewById(R.id.ablum_image_det_list);
            viewHolder.txt_title = (TextView)convertView.findViewById(R.id.album_title_det_list);
            viewHolder.txt_dur = (TextView)convertView.findViewById(R.id.duration_det_list);
            viewHolder.song_class = (TextView)convertView.findViewById(R.id.song_type);
            viewHolder.play_icon = (ImageView)convertView.findViewById(R.id.play_icon);
            viewHolder.topicsView = (LinearLayout) convertView.findViewById(R.id.topicsView);
            viewHolder.default_layout = (LinearLayout) convertView.findViewById(R.id.default_layout);
            viewHolder.nowPlaying_layout = (LinearLayout) convertView.findViewById(R.id.nowPlaying_layout);
            viewHolder.downloadBtn = (ImageView) convertView.findViewById(R.id.iv_music_downloads);
            viewHolder.circularProgressbar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            viewHolder.iv_music_pause_downloads = (ImageView) convertView.findViewById(R.id.iv_music_pause_downloads);
            viewHolder.isDownloadProgress = false;
            viewHolder.isDownloadHappening = false;
            viewHolder.isDownloadPaused = false;
            viewHolder.downloadStatus = false;
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        Animation animation = AnimationUtils.loadAnimation(context, (i > lastPosition) ? R.anim.up_from_bottom1 : R.anim.up_from_bottom1);
        convertView.startAnimation(animation);
        lastPosition = i;



        //     viewHolder.progressInside.setTag(i);
        viewHolder.iv_music_pause_downloads.setTag(i);
        viewHolder.circularProgressbar.setTag(i);
        viewHolder.downloadBtn.setTag(i);

        if (Constant.playPos == i && Constant.isplayTopics){
            viewHolder.default_layout.setVisibility(View.GONE);
            viewHolder.nowPlaying_layout.setVisibility(View.VISIBLE);

        }else {
            viewHolder.nowPlaying_layout.setVisibility(View.GONE);
            viewHolder.default_layout.setVisibility(View.VISIBLE);
        }

        if((Integer) viewHolder.downloadBtn.getTag() != null && (Integer) viewHolder.downloadBtn.getTag() == i && (Integer) viewHolder.iv_music_pause_downloads.getTag() != null && (Integer) viewHolder.iv_music_pause_downloads.getTag() == i  && viewHolder.downloadStatus == true){
            viewHolder.downloadBtn.setVisibility(View.GONE);
            viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
        }else if((Integer) viewHolder.downloadBtn.getTag() != null && (Integer) viewHolder.downloadBtn.getTag() == i && listOfTopicsDetaileds.get(i).getDownloads() != null && !listOfTopicsDetaileds.get(i).getDownloads().isEmpty()){
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


        viewHolder.iv_music_pause_downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.iv_music_pause_downloads.getTag() != null  && (Integer) viewHolder.iv_music_pause_downloads.getTag() == i){
                    db.itemSongDao().updateRat("true",listOfTopicsDetaileds.get(i).getTopics_det_title());

                    //    boolean status = asyncTask.cancel(true);

                    int downloadId = hashMap.get(listOfTopicsDetaileds.get(i).getTrack_id());

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
                              //      db.itemSongDao().updateProgress(1,mediaItems.get(i).getTitle());
                                    Constant.downloadPosition = i;
                                    Constant.downloadCount = Constant.downloadCount +1;
                                    viewHolder.isDownloadHappening = true;
                                  //  listOfTopicsDetaileds.get(i).setTotalRate("false");
                                    //asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus,viewHolder.isDownloadProgress,viewHolder.isDownloadHappening).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle(),"false");

                                    Uri downloadUri = Uri.parse(listOfTopicsDetaileds.get(i).getTopics_det_img());
                                    Uri destinationUri = Uri.fromFile(setFileName(listOfTopicsDetaileds.get(i).getTrack_id()));

                                    final DownloadRequest downloadRequest1 = new DownloadRequest(downloadUri).setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.LOW).setRetryPolicy(new DefaultRetryPolicy()).setDownloadContext("Download1").setDownloadListener(new DownloadStatusListener() {
                                        @Override
                                        public void onDownloadComplete(int id) {
                                            viewHolder.circularProgressbar.setVisibility(View.GONE);
                                            viewHolder.iv_music_pause_downloads.setVisibility(View.GONE);
                                            Constant.downloadCount = Constant.downloadCount -1;
                                            isDownloadUpdated = true;
                                            Constant.downloadCompleted = true;
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
                                    hashMap.put(listOfTopicsDetaileds.get(i).getTrack_id(),downloadnId);
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
                   // listOfTopicsDetaileds.get(i).setTotalRate("false");
                    //asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus,viewHolder.isDownloadProgress,viewHolder.isDownloadHappening).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,listOfTopicsDetaileds.get(i).getUrl(),listOfTopicsDetaileds.get(i).getTitle(),"false");
                //    db.listOfTopicsDetailed().updateProgress(1,listOfTopicsDetaileds.get(i).getTopics_det_title());

                    Uri downloadUri = Uri.parse(listOfTopicsDetaileds.get(i).getTopics_det_img());
                    Uri destinationUri = Uri.fromFile(setFileName(listOfTopicsDetaileds.get(i).getTrack_id()));

                    final DownloadRequest downloadRequest1 = new DownloadRequest(downloadUri).setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.LOW).setRetryPolicy(new DefaultRetryPolicy()).setDownloadContext("Download1").setDownloadListener(new DownloadStatusListener() {
                        @Override
                        public void onDownloadComplete(int id) {
                            viewHolder.circularProgressbar.setVisibility(View.GONE);
                            viewHolder.iv_music_pause_downloads.setVisibility(View.GONE);
                            Constant.downloadCount = Constant.downloadCount -1;
                            isDownloadUpdated = true;
                            Constant.downloadCompleted = true;
                      //      db.itemSongDao().updateProgress(0,listOfTopicsDetaileds.get(i).getTopics_det_title());

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
                    //        db.itemSongDao().updateProgress(0,listOfTopicsDetaileds.get(i).getTopics_det_title());


                        }

                        @Override
                        public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
                            viewHolder.circularProgressbar.setVisibility(View.VISIBLE);
                            viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                            viewHolder.downloadBtn.setVisibility(View.GONE);
                            viewHolder.circularProgressbar.setProgress(progress);
                        //    db.itemSongDao().updateProgress(progress,listOfTopicsDetaileds.get(i).getTopics_det_title());
                        }
                    });


                    int downloadnId = downloadManager.add(downloadRequest1);
                    hashMap.put(listOfTopicsDetaileds.get(i).getTrack_id(),downloadnId);
                    //   asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus,viewHolder.isDownloadProgress,viewHolder.isDownloadHappening).execute(listOfTopicsDetaileds.get(i).getUrl(),listOfTopicsDetaileds.get(i).getTitle(),"false");
                /*        if(viewHolder.downloadBtn.getTag().equals(i)) {

                            viewHolder.circularProgressbar.setVisibility(View.GONE);
                        }*/
                }

            }
        });




        ListOfTopicsDetailed topicsdet = listOfTopicsDetaileds.get(i);
        String song_title = topicsdet.getTopics_det_title();
        String song_dur = topicsdet.getTopics_time();
        String song_classname = topicsdet.getTopics_classname();

        viewHolder.song_class.setText(song_classname);
        viewHolder.txt_title.setText(song_title);
        viewHolder.txt_dur.setText(song_dur);
        Picasso.get()
                .load(listOfTopicsDetaileds.get(i).getTopics_det_imgurl())
                .into(viewHolder.imageView_list);

        viewHolder.topicsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.isOnline = false;
                Constant.playPos = i;
                Constant.isplayTopics = true;
                Constant.isFromPage = "topic";
                Bundle bundle = new Bundle();
                bundle.putString("from","topics");
                Intent intent = new Intent(context, PlayerService.class);
                intent.setAction(PlayerService.ACTION_PLAY);
                intent.putExtras(bundle);
                context.startService(intent);
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

        return convertView;
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
    private File setFileName(String title) {
        File folder = Environment.getExternalStoragePublicDirectory("Swamiji");
        if (!folder.exists())
            folder.mkdirs();

        File file = new File(folder, title+".swami");


        return file;
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
