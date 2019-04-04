package com.example.im037.sastraprakasika.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
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

import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.example.im037.sastraprakasika.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class Lectures_audio_list_adapter extends BaseAdapter {

    //    ArrayList<ListOfLecturesListDetails> lect_det;
    // List<ItemSong> mediaItems;
    ArrayList<ItemSong> mediaItems;

    Context context;
    int playerPosition;
    int downloadCount = 0;
    HashMap<String,Integer> hashMap= new HashMap<>();
    boolean isDownloading = false;
    private IProcessFilter mCallback;
    int lastposition = 0;
    AsyncTask asyncTask;
    long total = 0;


    public interface IProcessFilter {
        void onProcessFilter(boolean b);
    }


    public Lectures_audio_list_adapter(ArrayList<ItemSong> media_det, Context context,IProcessFilter mCallback) {
        this.mediaItems = media_det;
        this.context = context;
        this.mCallback = mCallback;

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
        //   MaterialProgressBar button_progress_2;
        //     CircleProgressView circleProgressView;
        ProgressBar circularProgressbar;
        boolean isDownloadPaused = false;
        LinearLayout nowPlaying_layout,textNowPlaying,musicPlayLayout;
        boolean downloadStatus = false;


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

            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolderItem) view.getTag();
            notifyDataSetChanged();
        }

        viewHolder.downloadBtn.setTag(i);


        if((Integer) viewHolder.downloadBtn.getTag() != null && (Integer) viewHolder.downloadBtn.getTag() == i  && viewHolder.downloadStatus == true){
            viewHolder.downloadBtn.setVisibility(View.GONE);
            viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
        }else if((Integer) viewHolder.downloadBtn.getTag() != null && (Integer) viewHolder.downloadBtn.getTag() == i && !mediaItems.get(i).getDownloads().isEmpty()){
            viewHolder.downloadBtn.setVisibility(View.GONE);
        }else if((Integer) viewHolder.downloadBtn.getTag() != null && (Integer) viewHolder.downloadBtn.getTag() == i  && viewHolder.isDownloadPaused) {
            viewHolder.downloadBtn.setVisibility(View.GONE);
        }else{
            viewHolder.iv_music_pause_downloads.setVisibility(View.GONE);
            viewHolder.downloadBtn.setVisibility(View.VISIBLE);
        }
        //     TextView song_start_letter = (TextView)view.findViewById(R.id.song_letter_txt);

        viewHolder.circularProgressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.isDownloadPaused){
                    viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                    viewHolder.downloadBtn.setVisibility(View.GONE);
                    asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus).execute(mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle(),String.valueOf(i));
                    viewHolder.downloadBtn.setVisibility(View.GONE);
                    viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                    viewHolder.isDownloadPaused = false;
                }else {
                    viewHolder.downloadBtn.setVisibility(View.GONE);
                    viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                    if (asyncTask != null){
                        viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                        asyncTask.cancel(true);
                        viewHolder.downloadBtn.setVisibility(View.GONE);
                        viewHolder.iv_music_pause_downloads.setVisibility(View.VISIBLE);
                        viewHolder.isDownloadPaused = true;
                    }
                }
                notifyDataSetChanged();
            }
        });

        viewHolder.iv_music_pause_downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTask.cancel(true);
                String path = readFilePath(mediaItems.get(i).getTitle());

                if(path != null && path != ""){
                    File file = new File(path);
                    file.delete();
                }

                viewHolder.circularProgressbar.setVisibility(View.GONE);
                viewHolder.iv_music_pause_downloads.setVisibility(View.GONE);
                viewHolder.downloadBtn.setVisibility(View.VISIBLE);
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
                //     viewHolder.downloadBtn.setTag(i);
                //   viewHolder.circularProgressbar.setTag(i);
                boolean isDownloaded = readFileNames(mediaItems.get(i).getTitle().toString());
                if (isDownloaded){
                    Toast.makeText(context, "Audio already downloaded, kindly check Downloads", Toast.LENGTH_SHORT).show();

                }else {
                    if(isMobiledata(context)){
                        new AlertDialog.Builder(context)
                                .setTitle("Mobile data alert!")
                                .setMessage("Are you sure you want to download with mobile data?")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                  /*      if(viewHolder.downloadBtn.getTag().equals(i)){
                                            viewHolder.downloadBtn.setVisibility(View.GONE);
                                            viewHolder.circularProgressbar.setVisibility(View.VISIBLE);
                                        }*/
                                        //   viewHolder.circularProgressbar.setIndeterminateDrawable(new IndeterminateCircularProgressDrawable(context));

                                        viewHolder.downloadBtn.setVisibility(View.GONE);
                                        viewHolder.circularProgressbar.setVisibility(View.VISIBLE);
                                        Constant.downloadPosition = i;
                                        downloadCount = downloadCount +1;


                                        asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus).execute(mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle(),String.valueOf(i));
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
                        Constant.downloadPosition = i;
                        downloadCount = downloadCount +1;

                        asyncTask = new DownloadFileAsync(viewHolder.circularProgressbar,viewHolder.downloadBtn,i,viewHolder.iv_music_pause_downloads,viewHolder.downloadStatus).execute(mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle(),String.valueOf(i));
                /*        if(viewHolder.downloadBtn.getTag().equals(i)) {

                            viewHolder.downloadBtn.setVisibility(View.VISIBLE);
                            viewHolder.circularProgressbar.setVisibility(View.GONE);
                        }*/
                    }
                }


            }
        });

        if(mediaItems.get(i).getBitmap() != null && !mediaItems.get(i).getBitmap().isEmpty()){
            Picasso.get()
                    .load(mediaItems.get(i).getBitmap())
                    .into(viewHolder.song_img_view);
        }


        //  Glide.with(context).asGif().load(R.drawable.playing).into(playlist_track);

     /*   if (PlayerService.getIsPlayling() && Constant.playPos == i){
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.playicon_img.setImageResource(android.R.color.transparent);
                viewHolder.playicon_img.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.play_arrow));
            } else {
                viewHolder.playicon_img.setImageResource(android.R.color.transparent);
                viewHolder.playicon_img.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_pause_grey_web));
            }

            //  viewHolder.playlist_track.setVisibility( View.VISIBLE );
        }else{
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.playicon_img.setImageResource(android.R.color.transparent);
                viewHolder.playicon_img.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.play_arrow));
            } else {
                viewHolder.playicon_img.setImageResource(android.R.color.transparent);
                viewHolder.playicon_img.setBackground(ContextCompat.getDrawable(context, R.drawable.play_arrow));
            }
        }*/


      /*  Picasso.get()
                .load(mediaItems.get(i).getBitmap())
                .into(playicon_img);
*/
        viewHolder.song_title_view.setText(mediaItems.get(i).getTitle());
        viewHolder.song_artist.setText(mediaItems.get(i).getClassName());
        viewHolder.song_dur.setText(mediaItems.get(i).getDuration());
        //song_start_letter.setText(items.getAlpha_letter());

      /*  Bitmap image = null;
        try {
            URL url = new URL(mediaItems.get(i).getImageSmall());
             image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(IOException e) {
            System.out.println(e);
        }*/

        final int lastPlayed = 0;
        if (Constant.playPos == i && Constant.isplayLectures) {
            viewHolder.textNowPlaying.setVisibility(View.GONE);
            viewHolder.nowPlaying_layout.setVisibility(View.VISIBLE);
        }else {
            viewHolder.nowPlaying_layout.setVisibility(View.GONE);
            viewHolder.textNowPlaying.setVisibility(View.VISIBLE);
        }


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

        // viewHolder.song_img_view.setImageResource(R.drawable.tamil);




        return view;
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
        Long downloadedSize;

        public DownloadFileAsync(ProgressBar circularProgressbar,ImageView downloadBtn,int progress, ImageView pauseBtn,boolean downloadStatus) {
            this.progressBar = circularProgressbar;
            this.downloadbtn = downloadBtn;
            this.progressVal = progress;
            this.pauseBtn = pauseBtn;
            this.downloadstatus = downloadStatus;
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        //  private ProgressDialog mProgressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // if (downloadbtn.getTag().equals(progressVal)){
            downloadbtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            pauseBtn.setVisibility(View.VISIBLE);
            downloadstatus = true;

            //   }
            //viewHolder.circularProgressbar.setVisibility(View.VISIBLE);
            //  onCreateDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            long deneme = 0;
            try {

                URL url = new URL(aurl[0]);
                String title = aurl[1];
                URLConnection connection = url.openConnection();
                if (setFileName(title).exists())
                {
                    deneme = setFileName(title).length();

                    connection.setAllowUserInteraction(true);
                    connection.setRequestProperty("Range", "bytes=" + deneme + "-");
                }else{
                    connection.setRequestProperty("Range", "bytes=" + deneme + "-");
                }

                connection.connect();

                String connectionField = connection.getHeaderField("content-range");

                if (connectionField !=null){
                    String[] connectionRanges = connectionField.substring("bytes=".length()).split("-");
                    deneme = Long.valueOf(connectionRanges[0]);
                }

                if (connectionField == null && connectionField.isEmpty() && setFileName(title).exists()){
                    setFileName(title).delete();
                }

                long lenghtOfFile = connection.getContentLength() + deneme;
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
                InputStream input = new BufferedInputStream(
                        url.openStream(), 8192);
                OutputStream output = new FileOutputStream(setFileName(title));
                int lastcount = 0;
                total = deneme;
                byte data[] = new byte[4096];
                    while ((count = input.read(data)) != -1) {
                    total += count;
                        int progBarCount = (int) ((total * 100) / lenghtOfFile);
                            publishProgress(""+progBarCount);
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
                Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                //  mProgressDialog.dismiss();

            }
            return "done";
        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC",progress[0]);
            if (progress.length > 1){
                Log.d("ANDRO_ASYNC_____",progress[1]);
            }
            isDownloading = true;

            super.onProgressUpdate();
            if (downloadbtn.getTag().equals(progressVal)) {
                progressBar.setProgress(Integer.parseInt(progress[0]));
                pauseBtn.setVisibility(View.VISIBLE);
                downloadbtn.setVisibility(View.GONE);
            }

        }



        @Override
        protected void onPostExecute(String unused) {

            downloadCount = downloadCount -1 ;
            isDownloading = false;
            downloadstatus = false;
            if (downloadCount == 0){
                mCallback.onProcessFilter(true);
            }
            //
            if (downloadbtn.getTag().equals(progressVal)){
                downloadbtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.GONE);
            }
            pauseBtn.setVisibility(View.GONE);


            //   mProgressDialog.dismiss();
        }



        protected Dialog onCreateDialog(int id) {
            switch (id) {
                case DIALOG_DOWNLOAD_PROGRESS:
            /*        mProgressDialog = new ProgressDialog(context);
                    mProgressDialog.setMessage("Download in progress..");
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    return mProgressDialog;*/
                default:
                    return null;
            }
        }

        private File setFileName(String title) {
            File folder = Environment.getExternalStoragePublicDirectory("Swamiji");
            if (!folder.exists())
                folder.mkdirs();

            File file = new File(folder, title+".swami");


            return file;
        }



    }

    private void setValues(String[] progress) {

    }


}
