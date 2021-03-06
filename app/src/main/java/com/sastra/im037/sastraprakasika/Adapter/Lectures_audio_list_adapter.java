package com.sastra.im037.sastraprakasika.Adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sastra.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.sastra.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.sastra.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Lectures_audio_list_adapter extends BaseAdapter {

    //    ArrayList<ListOfLecturesListDetails> lect_det;
    // List<ItemSong> mediaItems;
    ArrayList<ItemSong> mediaItems;

    Context context;
    int playerPosition;
    boolean tempallot= false;
    HashMap<String,Integer> hashMap= new HashMap<>();
    boolean isDownloading = false;
    private IProcessFilter mCallback;
    int lastposition = 0;
    AsyncTask asyncTask;
    long total = 0;
    boolean isDownloadUpdated = false;
    ArrayList<Integer> downloadFile = new ArrayList<>();
    DiscousesAppDatabase db;
    private ThinDownloadManager downloadManager;


    public interface IProcessFilter {
        void onProcessFilter(boolean b, ArrayList<ItemSong> mediaItems);
    }


    public Lectures_audio_list_adapter(ArrayList<ItemSong> media_det, Context context,IProcessFilter mCallback) {
        this.mediaItems = media_det;
        this.context = context;
        this.mCallback = mCallback;
        Constant.wholeMediaList.addAll(media_det);
        db = Room.databaseBuilder(context,
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();
        try{
            downloadManager = new ThinDownloadManager(1);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


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


    static class ViewHolderItem {
        ImageView song_img_view;
        TextView song_title_view;
        TextView song_artist;
        TextView song_dur;
        ImageView playicon_img;
        ImageView playlist_track;
        ImageView downloadBtn;
        RelativeLayout loadingDownload;
        ImageView iv_music_pause_downloads;
        //   ImageView progressInside;
        //   MaterialProgressBar button_progress_2;
        //     CircleProgressView circleProgressView;
        ProgressBar circularProgressbar;
        boolean isDownloadPaused = false;
        boolean isDownloadProgress = false;
        LinearLayout nowPlaying_layout,textNowPlaying,musicPlayLayout;
        boolean downloadStatus = false;
        RelativeLayout progressStateLayout;
        boolean isDownloadHappening = false;



    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        // inflate the layout for each list row
        final ViewHolderItem viewHolder;


        if (view == null) {
            viewHolder = new ViewHolderItem();

            view = LayoutInflater.from(context).
                    inflate(R.layout.lectures_audio_list_items, viewGroup, false);

            viewHolder.song_img_view = (ImageView)view.findViewById(R.id.ablum_image_img);
            viewHolder.song_title_view = (TextView)view.findViewById(R.id.album_title_txt);
            viewHolder.song_artist = (TextView)view.findViewById(R.id.song_type_txt);
            viewHolder.song_dur = (TextView)view.findViewById(R.id.duration_txt);
            viewHolder.playicon_img = (ImageView)view.findViewById(R.id.play_icon);
            viewHolder.playlist_track = (ImageView)view.findViewById(R.id.plating_track_icon);
            viewHolder.downloadBtn = (ImageView) view.findViewById(R.id.iv_music_downloads);
            viewHolder.musicPlayLayout = (LinearLayout) view.findViewById(R.id.musicPlayLayout);
            // viewHolder.circleProgressView = (CircleProgressView) view.findViewById(R.id.circleView);
            //   viewHolder.button_progress_2 = (MaterialProgressBar) view.findViewById(R.id.downloadProgressBar);
            viewHolder.loadingDownload = (RelativeLayout) view.findViewById(R.id.rl_music_loading);
            viewHolder.circularProgressbar = (ProgressBar) view.findViewById(R.id.progressBar);
            viewHolder.iv_music_pause_downloads = (ImageView) view.findViewById(R.id.iv_music_pause_downloads);
            viewHolder.textNowPlaying = (LinearLayout) view.findViewById(R.id.textNowPlaying);
            viewHolder.nowPlaying_layout = (LinearLayout) view.findViewById(R.id.nowPlaying_layout);
            //      viewHolder.progressInside = (ImageView) view.findViewById(R.id.progressInside);
            viewHolder.progressStateLayout = (RelativeLayout) view.findViewById(R.id.progressStateLayout);
            viewHolder.isDownloadProgress = false;
            viewHolder.isDownloadPaused = false;
            viewHolder.isDownloadHappening = false;
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) view.getTag();
            //  notifyDataSetChanged();
        }

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


        if (Constant.downloadCount == 0 && isDownloadUpdated){
            mCallback.onProcessFilter(true,mediaItems);
            isDownloadUpdated = false;
            notifyDataSetChanged();
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
                    asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus,viewHolder.isDownloadProgress,viewHolder.isDownloadHappening).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,mediaItems.get(i).getUrl(),mediaItems.get(i).getTrackId(),"false");
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


    /*    viewHolder.iv_music_pause_downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDownloadPaused){
                    asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads).execute(mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle(),String.valueOf(i));
                    Picasso.get()
                            .load(R.drawable.pause2_blackicon_new)
                            .into(viewHolder.iv_music_pause_downloads);
                    isDownloadPaused = false;
                }else {
                    if (asyncTask != null){
                        asyncTask.cancel(true);

                        Picasso.get()
                                .load(R.drawable.play2_blackicon_new)
                                .into(viewHolder.iv_music_pause_downloads);
                        isDownloadPaused = true;
                    }
                }

            }
        });*/


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
                                            Constant.downloadCompleted = true;
                                            if (Constant.downloadCount == 0 && isDownloadUpdated) {
                                                Constant.downloadCompleted = true;
                                                notifyDataSetChanged();
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
                            Constant.downloadCompleted = true;
                            db.itemSongDao().updateProgress(0,mediaItems.get(i).getTitle());

                            if (Constant.downloadCount == 0 && isDownloadUpdated) {
                                Constant.downloadCompleted = true;
                                notifyDataSetChanged();

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
                            Log.d("progress",String.valueOf(progress));
                            Log.d("title",mediaItems.get(i).getTitle());


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




        return view;
    }

    private File setFileName(String title) {
        File folder = Environment.getExternalStoragePublicDirectory("Swamiji");
        if (!folder.exists())
            folder.mkdirs();

        File file = new File(folder, title+".swami");


        return file;
    }

    public static boolean readFileNames(String songTitle) {
        ArrayList<String> downloadSongs = new ArrayList<>();
        try {
            File folder = Environment.getExternalStoragePublicDirectory("Swamiji");
            String path = Environment.getExternalStorageDirectory().toString() + "/Swamiji";
            File directory = new File(path);
            File[] files = directory.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    String result = files[i].getName().replace(".swami","");
                    downloadSongs.add(result);
                }
            }
            if (downloadSongs != null && downloadSongs.size() > 0){
                if (downloadSongs.contains(songTitle)){
                    return true;
                }else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    private void downloadMp3(String url, String title) {

    }
    @Override
    public int getViewTypeCount() {

        return getCount();
    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }



    class DownloadFileAsync extends AsyncTask<String, String, String> {
        public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
        ProgressBar progressBar;
        ImageView downloadbtn;
        ImageView pauseBtn;
        int progressVal;
        boolean downloadstatus;
        //  ImageView progressInside;
        Long downloadedSize;
        boolean isDownloadProgress;
        HttpURLConnection connection;
        boolean isDownloadHappening;
        public DownloadFileAsync(ProgressBar circularProgressbar,ImageView downloadBtn,int progress, ImageView pauseBtn,boolean downloadStatus,boolean isDownloadProgress, boolean isDownloadHappening) {
            this.progressBar = circularProgressbar;
            this.downloadbtn = downloadBtn;
            this.progressVal = progress;
            this.pauseBtn = pauseBtn;
            this.downloadstatus = downloadStatus;
            //     this.progressInside = progressInside;
            this.isDownloadProgress = isDownloadProgress;
            this.isDownloadHappening = isDownloadHappening;

        }


        //  private ProgressDialog mProgressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // if (downloadbtn.getTag().equals(progressVal)){
            //  progressBar.setVisibility(View.VISIBLE);

            downloadstatus = true;
            if (downloadbtn.getTag().equals(progressVal)){
                isDownloadProgress = true;
            }
            downloadFile.add(progressVal);
            //   }
            //viewHolder.circularProgressbar.setVisibility(View.VISIBLE);
            //  onCreateD ialog(DIALOG_DOWNLOAD_PROGRESS);
        }



        @Override
        protected String doInBackground(String... aurl) {
            int count;
            long deneme = 0;
            String title = aurl[1];

            String status =db.itemSongDao().getRating(title);
            if (!status.isEmpty() && status.equalsIgnoreCase("true")){
                Constant.downloadCount = Constant.downloadCount -1 ;
                db.itemSongDao().updateRat("false",title);
                if (Constant.downloadCount == 0 && isDownloadUpdated) {
                    Constant.downloadCompleted = true;
                }

                return "cancel";
            }else {

                try {


                    URL url = new URL(aurl[0]);
                    String check = aurl[2];
                    int progBarCount = 0;
                    if (check.equalsIgnoreCase("false")) {

                        connection = (HttpURLConnection) url
                                .openConnection();
                        if (setFileName(title).exists()) {
                            deneme = setFileName(title).length();
                            connection.setAllowUserInteraction(true);
                            connection.setRequestProperty("Range", "bytes=" + deneme + "-");
                        } else {
                            connection.setRequestProperty("Range", "bytes=" + deneme + "-");
                        }

                        connection.connect();

                        String connectionField = connection.getHeaderField("content-range");

                        if (connectionField != null) {
                            String[] connectionRanges = connectionField.substring("bytes=".length()).split("-");
                            deneme = Long.valueOf(connectionRanges[0]);
                        }

                        if (connectionField == null && connectionField.isEmpty() && setFileName(title).exists()) {
                            setFileName(title).delete();
                        }

                        long lenghtOfFile = connection.getContentLength() + deneme;
                        InputStream input = new BufferedInputStream(
                                url.openStream(), 8192);
                        OutputStream output = new FileOutputStream(setFileName(title));
                        int lastcount = 0;
                        total = deneme;
                        byte data[] = new byte[4096];
                        while ((count = input.read(data)) != -1) {
                            total += count;
                            progBarCount = (int) ((total * 100) / lenghtOfFile);
                            publishProgress("" + progBarCount);
                            output.write(data, 0, count);
                        }

                        output.flush();
                        output.close();
                        input.close();
                        Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
                    } else {
                        //no
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //  mProgressDialog.dismiss();

                }
                return "done";
            }
        }

        protected void onProgressUpdate(String... progress) {
            isDownloading = true;
            super.onProgressUpdate();
            if (downloadbtn.getTag().equals(progressVal)) {
                progressBar.setProgress(Integer.parseInt(progress[0]));

            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

        }




        @Override
        protected void onPostExecute(String unused) {

            Log.d("asas",unused);
            Constant.downloadCount = Constant.downloadCount -1 ;
            isDownloading = false;
            downloadstatus = false;
            /*if (downloadCount == 0){
                mCallback.onProcessFilter(true);
            }*/
            //


            if (downloadbtn.getTag().equals(progressVal)){
                isDownloadProgress = false;

            }

            mediaItems.get(progressVal).setTotalRate("false");

            //Constant.downloadCompleted = true;
            isDownloadUpdated = true;
            this.isDownloadHappening = true;

            if (Constant.downloadCount == 0 && isDownloadUpdated) {
                Constant.downloadCompleted = true;
            }
            try {
                //downloadbtn.setVisibility(View.GONE);
                //   progressInside.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.GONE);
                //    progressInside.setVisibility(View.GONE);
                if (downloadFile!= null && downloadFile.size() > 0 && downloadFile.contains(progressVal)){
                    downloadFile.remove(progressVal);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            //   notifyDataSetChanged();
            //   mProgressDialog.dismiss();
        }


        private File setFileName(String title) {
            File folder = Environment.getExternalStoragePublicDirectory("Swamiji");
            if (!folder.exists())
                folder.mkdirs();

            File file = new File(folder, title+".swami");


            return file;
        }



    }
}
