package com.example.im037.sastraprakasika.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Model.ListOfLecturesListDetails;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.example.im037.sastraprakasika.R;
import com.google.android.gms.common.internal.service.Common;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class Lectures_audio_list_adapter extends BaseAdapter {

//    ArrayList<ListOfLecturesListDetails> lect_det;
    List<ItemSong> mediaItems;
    Context context;
    int playerPosition;

    public Lectures_audio_list_adapter(List<ItemSong> media_det, Context context) {
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


    static class ViewHolderItem {
        ImageView song_img_view;
        TextView song_title_view;
        TextView song_artist;
        TextView song_dur;
        ImageView playicon_img;
        ImageView playlist_track;
        ImageView downloadBtn;
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
            viewHolder.playicon_img.setVisibility( View.VISIBLE );
            viewHolder.playlist_track.setVisibility( View.GONE );

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem) view.getTag();
        }



   //     TextView song_start_letter = (TextView)view.findViewById(R.id.song_letter_txt);



        viewHolder.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        new DownloadFileAsync().execute(mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle());
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }else{
                        new DownloadFileAsync().execute(mediaItems.get(i).getUrl(),mediaItems.get(i).getTitle());

                    }
                }


            }
        });

        if(mediaItems.get(i).getBitmap() != null && !mediaItems.get(i).getBitmap().isEmpty()){
            Picasso.get()
                    .load(mediaItems.get(i).getBitmap())
                    .into(viewHolder.song_img_view);
        }


        viewHolder.playicon_img.setVisibility( View.VISIBLE );
        viewHolder.playlist_track.setVisibility( View.GONE );

      //  Glide.with(context).asGif().load(R.drawable.playing).into(playlist_track);

        if (PlayerService.getIsPlayling() && Constant.playPos == i){
            viewHolder.playicon_img.setVisibility( View.GONE );
            viewHolder.playlist_track.setVisibility( View.VISIBLE );
        }


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

    //   song_img_view.setImageBitmap(image);
        viewHolder.playicon_img.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Constant.playPos = i;

                  viewHolder.playicon_img.setVisibility( View.GONE );
                  viewHolder.playlist_track.setVisibility( View.VISIBLE );
                  playerPosition = i;

              Constant.isOnline = false;

              Intent intent = new Intent(context, PlayerService.class);
              intent.putExtra("from","lecture");
              intent.setAction(PlayerService.ACTION_PLAY);
              context.getApplicationContext().startService(intent);
          }

       });

        viewHolder.song_img_view.setImageResource(R.drawable.tamil);




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
                    String result = files[i].getName().replace(".mp3","");
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


    private boolean isMobiledata(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info.getTypeName().equalsIgnoreCase("WIFI")){
            return false;
        }else {
            return true;
        }
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
        private ProgressDialog mProgressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onCreateDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL(aurl[0]);
                String title = aurl[1];
                URLConnection conexion = url.openConnection();
                conexion.connect();
                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(setFileName(title));
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
                mProgressDialog.dismiss();

            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC",progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();

            mProgressDialog.dismiss();
        }



        protected Dialog onCreateDialog(int id) {
            switch (id) {
                case DIALOG_DOWNLOAD_PROGRESS:
                    mProgressDialog = new ProgressDialog(context);
                    mProgressDialog.setMessage("Download in progress..");
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    return mProgressDialog;
                default:
                    return null;
            }
        }

        private File setFileName(String title) {
                File folder = Environment.getExternalStoragePublicDirectory("Swamiji");
                if (!folder.exists())
                    folder.mkdirs();

            File file = new File(folder, title+".mp3");


            return file;
        }



    }



}
