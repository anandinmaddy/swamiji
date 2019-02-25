package com.example.im037.sastraprakasika.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Adapter.Lectures_audio_list_adapter;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Mediaactivity.Audio_player_activity;
import com.example.im037.sastraprakasika.Model.DiscousesAppDatabase;
import com.example.im037.sastraprakasika.Model.ListOfLecturesListDetails;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.OnlinePlayer.Methods;
import com.example.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.mediacontrols.Controls;
import com.example.im037.sastraprakasika.mediautil.MediaItem;
import com.example.im037.sastraprakasika.mediautil.PlayerConstants;
import com.example.im037.sastraprakasika.mediautil.UtilFunctions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.view.View.VISIBLE;

public class LecturesFragment_Audioplay extends Fragment {

    Methods methods;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    String LOG_CLASS = "MainActivity";

    static TextView txt_playingSong, txt_song_title;
    Button btn_Player;
    static Button btn_Pause, btn_Play, btn_Next, btn_Previous;
    Button btn_Stop;
    LinearLayout layout_mediaLayout;
    //add new
    LinearLayout layout_desc_below;
    static LinearLayout st_linearLayoutPlayingSong;
    //    ListView mediaListView;
    //ProgressBar progressBar;
    SeekBar seekBart_aud_main;
    TextView txt_textBufferDuration, txt_textDuration;
    static ImageView img_imageViewAlbumArt;
    static Context context;
    RecyclerView recyclerView;
    Lectures_audio_list_adapter lectures_audio_list_adapter = null;
    View view;
    TextView title;
    // add new
    ArrayList<MediaItem> mediaItems = new ArrayList<>();
    List<ItemSong> itemSongList = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_MULTIPLE = 1100;

    ShimmerFrameLayout shimmerFrameLayout;
    public static final String TAG = LecturesFragment_Audioplay.class.getSimpleName();
    DiscousesAppDatabase db;

   // ProgressDialog progressDialog;


    //added me
    ArrayList<ItemSong> lect_det;
    ListView listView_song;
    String img_url[] = {"https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/01-PURUSHARTHA.mp3",
            "https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/02-SASTRAM.mp3",
            "https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/03-VARNA-DHARMA.mp3",
           "https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/04-ASRAMA-DHARMA.mp3"};
    int img[] = {R.drawable.intro_vedanta,R.drawable.intro_vedanta,R.drawable.general,R.drawable.tamil};
   String song_title[] = {"An overview of Yoga","Insight into Human Pursuits(Purusartha)","Right Action & Right Attitude","Scriptures(Sastram)"};
    String song_artist[] = {"Class1","Class2","Class3","Class4"};
    String song_dur[] = {"56.04","55.05","57.08","58.37"};



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_audioplay_main, container, false);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                DiscousesAppDatabase.class, "DiscoursesModel").allowMainThreadQueries().build();

        context = getContext();
        init();
        shimmerFrameLayout.startShimmer();
        title = getActivity().findViewById(R.id.title);
        title.setText("My Library");

       title.setTextColor(getResources().getColor(R.color.black));
        lect_det = new ArrayList<ItemSong>();
        for (int i = 0; i < img_url.length; i++) {
            ItemSong listOfLecturesListDetails = new ItemSong();
            listOfLecturesListDetails.setArtist(song_title[i]);
            listOfLecturesListDetails.setArtist(song_artist[i]);
          //  listOfLecturesListDetails.setImage(img[i]);
            listOfLecturesListDetails.setDuration(song_dur[i]);
            listOfLecturesListDetails.setUrl(img_url[i]);
            lect_det.add(listOfLecturesListDetails);

        }


            itemSongList = db.itemSongDao().getAll();
            listView_song = (ListView) view.findViewById(R.id.listViewMusicSong_list);
        Constant.arrayList_play.clear();
        Constant.arrayList_play.addAll(itemSongList);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);

        if (itemSongList != null && itemSongList.size() > 0){
            final Lectures_audio_list_adapter lectures_audio_list_adapter = new Lectures_audio_list_adapter(itemSongList, getActivity());
            listView_song.setAdapter(lectures_audio_list_adapter);
        }else {
            final Lectures_audio_list_adapter lectures_audio_list_adapter = new Lectures_audio_list_adapter(lect_det, getActivity());
            listView_song.setAdapter(lectures_audio_list_adapter);
        }


        listView_song.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lectures_audio_list_adapter.notifyDataSetChanged();
            }
        });

        return view;


    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {

        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)  && (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
                return true;
        }else {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(((Activity) context),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_MULTIPLE);

            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
          //  if (PlayerConstants.SONGS_LIST.size() <= 0) {
                callWebservice();
           // }
            setListItems();
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


    //

    private void init() {
        getViews();
        setListeners();
        txt_playingSong.setSelected(true);
        //progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
          //  callWebservice();

            if(db.itemSongDao().getAll().size() > 0){
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }else {
                callWebservice();
            }

            /*
            if (PlayerConstants.SONGS_LIST.size() <= 0) {
                callWebservice();

                //loadUrlData();

            }*/

        }
    }

    private boolean checkPermissionWRITE_EXTERNAL_STORAGE(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions( (Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


    private void setListItems() {
        //listView_song = (ListView) view.findViewById( R.id.listViewMusicSong_list );
        lectures_audio_list_adapter = new Lectures_audio_list_adapter(itemSongList, getActivity());
        listView_song.setAdapter(lectures_audio_list_adapter);

    }


    private void loadStaticSongs() {


        String img_url[] = {"https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/01-PURUSHARTHA.mp3",
                "https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/02-SASTRAM.mp3",
                "https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/03-VARNA-DHARMA.mp3",
                "https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/04-ASRAMA-DHARMA.mp3"};

        int img[] = {R.drawable.intro_vedanta, R.drawable.intro_vedanta, R.drawable.general, R.drawable.tamil};
        String song_title[] = {"An overview of Yoga", "Insight into Human Pursuits(Purusartha)", "Right Action & Right Attitude", "Scriptures(Sastram)"};
        String song_artist[] = {"Class1", "Class2", "Class3", "Class4"};
        String song_dur[] = {"56.04", "55.05", "57.08", "58.37"};
        String url_song_title[] = {"Introduction to Purushastra", "Introduction to Sastram", "Introduction to Varma_dharma", "Introduction to Asrama_dharma"};

        String song_start_letter[] = {"A", "I", "R", "S"};
        for (int i = 0; i < img_url.length; i++) {


        }


    }

    private void getViews() {
        txt_playingSong = (TextView) view.findViewById(R.id.textNowPlaying_txt);
        txt_song_title = (TextView) view.findViewById(R.id.textSongTitle_txt);
//        btn_Player = (Button) view.findViewById(R.id.btnMusicPlayer_btn);
        listView_song = (ListView) view.findViewById(R.id.listViewMusicSong_list);
        layout_mediaLayout = (LinearLayout) view.findViewById(R.id.linearLayoutMusicList_layout);
        btn_Pause = (Button) view.findViewById(R.id.btnPause_btn);
        btn_Play = (Button) view.findViewById(R.id.btnPlay_btn);
        st_linearLayoutPlayingSong = (LinearLayout) view.findViewById(R.id.linearLayoutPlayingSong_layout);
        seekBart_aud_main = (SeekBar) view.findViewById(R.id.seekbar_audio_main);
        seekBart_aud_main.setMax(100);
        //progressBar = (ProgressBar) view.findViewById(R.id.progressbar_c);
//        btn_Stop = (Button) view.findViewById(R.id.btnStop_btn);
        txt_textBufferDuration = (TextView) view.findViewById(R.id.textBufferDuration_txt);
        txt_textDuration = (TextView) view.findViewById(R.id.textDuration_txt);
        img_imageViewAlbumArt = (ImageView) view.findViewById(R.id.imageViewAlbumArt_img);
        btn_Next = (Button) view.findViewById(R.id.btnNext_btn);
        btn_Previous = (Button) view.findViewById(R.id.btnPrevious_btn);
        layout_desc_below = (LinearLayout) view.findViewById(R.id.layoutdesc);


    }


    private void lictclick(){

    }

    private void setListeners() {
        listView_song.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Constant.isOnline = false;
                Constant.arrayList_play.clear();
                Constant.arrayList_play.addAll(Constant.arrayListOfflineSongs);
                Constant.playPos = position;
                Intent intent = new Intent(getActivity(), PlayerService.class);
                intent.setAction(PlayerService.ACTION_PLAY);
                getActivity().startService(intent);
                //( (CommonActivity) getActivity()).checkSlidingPanelLayout( true );
                //play a song in media player
            }
        });


        seekBart_aud_main.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                try {
                    Intent intent = new Intent(getActivity(), PlayerService.class);
                    intent.setAction(PlayerService.ACTION_SEEKTO);
                    intent.putExtra("seekto", methods.getSeekFromPercentage(progress, methods.calculateTime(Constant.arrayList_play.get(Constant.playPos).getDuration())));
                    context.startService(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//
//                PlayerConstants.SEEKBAR_HANDLER.sendEmptyMessage(seekBar.getProgress());
//
//                Log.e(LecturesFragment_Audioplay.class.getSimpleName(), "onStopTrackingTouch" + seekBar.getProgress());

            }
        });

        btn_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Controls.playControl(getActivity());

            }
        });
        btn_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Controls.pauseControl(getActivity());


            }
        });
        btn_Next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Controls.nextControl(getActivity());
            }
        });
        btn_Previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Controls.previousControl(getActivity());
            }
        });
//        btn_Stop.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent i = new Intent(getApplicationContext(), SongService.class);
//            stopService();
//            st_linearLayoutPlayingSong.setVisibility(View.GONE);
//        }
//
//        private void stopService() {
//        }
//    });

        img_imageViewAlbumArt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Audio_player_activity.class);
                startActivity(i);
            }
        });

        layout_desc_below.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Audio_player_activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        /*try {
            boolean isServiceRunning = UtilFunctions.isServiceRunning( SongService.class.getName(), getApplicationContext() );
            if (isServiceRunning) {
                updateUI();
            } else {
                st_linearLayoutPlayingSong.setVisibility( View.GONE );
            }
            changeButton();

            //before code progress
            PlayerConstants.PROGRESSBAR_HANDLER = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Integer i[] = (Integer[]) msg.obj;
                    txt_textBufferDuration.setText( UtilFunctions.getDuration( i[0] ) );
                    txt_textDuration.setText( UtilFunctions.getDuration( i[1] ) );
                    seekBart_aud_main.setProgress( i[2] );
                }
            };
*/

//            PlayerConstants.SEEKBAR_HANDLER = new Handler(){
//                @Override
//                public void handleMessage(Message message){
//                    Integer i[] = (Integer[])message.obj;
//                    txt_textBufferDuration.setText(UtilFunctions.getDuration(i[0]));
//                    txt_textDuration.setText(UtilFunctions.getDuration(i[1]));
//                    seekBart_aud_main.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                        int Progresschanged = 0;
//                        @Override
//                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                            Progresschanged = progress;
//                            mediaPlayer.seekTo();
//
//                        }
//
//                        @Override
//                        public void onStartTrackingTouch(SeekBar seekBar) {
//
//                        }
//
//                        @Override
//                        public void onStopTrackingTouch(SeekBar seekBar) {
//                            Toast.makeText(getActivity(), "Seek bar progress is :" + Progresschanged,
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//                }
//            };
        /*} catch (Exception e) {
        }*/
    }


    public static void updateUI() {
        try {
            MediaItem data = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
            txt_playingSong.setText(data.getTitle() + " " + data.getArtist() + "-" + data.getAlbum());
            //add priya
            txt_song_title.setText(data.getTitle());
            //txt_playingSong.setText( data.getAlbum() );
            // img_imageViewAlbumArt.setImageResource( R.drawable.vedanta );


            if (data.getAlbum_img() != null) {

//                img_imageViewAlbumArt.setBackgroundDrawable(new BitmapDrawable(albumArt));
                //add priya
                // img_imageViewAlbumArt.setImageResource( data.getAlbum_img() );
                Picasso.get()
                        .load(data.getAlbum_img())
                        .placeholder(R.drawable.placeholder_song)
                        .into(img_imageViewAlbumArt);
            } else {
                img_imageViewAlbumArt.setBackgroundDrawable(new BitmapDrawable(UtilFunctions.getDefaultAlbumArt(context)));
            }
            st_linearLayoutPlayingSong.setVisibility( VISIBLE);
        } catch (Exception e) {
        }
    }

    public static void changeButton() {
        if (PlayerConstants.SONG_PAUSED) {
            btn_Pause.setVisibility(View.GONE);
            btn_Play.setVisibility( VISIBLE);
        } else {
            btn_Pause.setVisibility( VISIBLE);
            btn_Play.setVisibility(View.GONE);
        }
    }

    public static void changeUI() {
        updateUI();
        changeButton();
    }


    private void callWebservice() {

      /*  progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading ..., Please wait");
        progressDialog.show();
*/
        new WebServices(getActivity(), TAG).getlibrary(Session.getInstance(getContext(), TAG).getUserId(), "lectures", new VolleyResponseListerner() {


            @Override
            public void onResponse(final JSONObject response) throws JSONException {
                // hideCommonProgressBar();
             //   progressDialog.hide();
//
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        Constant.arrayListOfflineSongs.clear();
                        System.out.println("library respon:::: " + response);
                        if (response.optString("resultcode").equalsIgnoreCase("200")) {


                            try {
                                db.itemSongDao().deleteAll();
                                JSONObject jsonObject1 = response.optJSONObject("data");
                                JSONArray contentArray = jsonObject1.optJSONArray("datacontent");
                                for (int i = 0; i < contentArray.length(); i++) {

                                    JSONObject dataConten = contentArray.getJSONObject(i);
                                    JSONArray trackArray = dataConten.optJSONArray("track");
                                    String image_url = dataConten.optString("image_url");
                                    String volume_name = dataConten.optString("volume_name");

                                    for (int j = 0; j < trackArray.length(); j++) {
                                        JSONObject jsonObject = trackArray.optJSONObject(j);
                                        MediaItem mediaItem = new MediaItem();
                                        mediaItem.setTitle(jsonObject.optString("title"));
                                        mediaItem.setPath(jsonObject.optString("mp3"));
                                        mediaItem.setDuration(jsonObject.optString("time"));
                                        mediaItem.setAlbum_img(image_url);
                                        mediaItem.setAlbum(volume_name);
                                        ItemSong itemSong = new ItemSong();

                                        itemSong.setUrl(jsonObject.optString("mp3"));
                                        itemSong.setTitle(jsonObject.optString("title"));
                                        itemSong.setDuration(jsonObject.optString("time"));
                                        itemSong.setClassName(jsonObject.optString("classname"));
                                        itemSong.setImageBig(image_url);
                                        itemSong.setImageSmall(image_url);
                                         String isOfflinevideo = readFileNames(jsonObject.optString("title"));
                                         if (isOfflinevideo != null && isOfflinevideo != ""){
                                             itemSong.setDownloads(isOfflinevideo);
                                         }



                                        db.itemSongDao().insertAll(itemSong);
                                        Constant.arrayListLectureslineSongs.add(itemSong);


                                    }

                                    Constant.arrayList_play.clear();
                                    Constant.arrayList_play.addAll(Constant.arrayListLectureslineSongs);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            try {
                                if (response.getString("resultcode").equalsIgnoreCase("400")) {
                                    CommonMethod.showSnackbar(listView_song, response, getActivity());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setListItems();
                    }

                });


            }

            //lectures_recycler_adapter = new Lectures_Recycler_adapter( MainActivity.this, lectures_listitems );
            //  recyclerView.setAdapter( lectures_recycler_adapter ); // set the Adapter to RecyclerView


            //Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen


            @Override
            public void onError(String message, String title) {
                System.out.println("library error:::: " + message);
                //progressDialog.hide();
            }
        });

    }



    private String readFileNames(String title) {
            HashMap<String,String> downloadSongs = new HashMap<String, String>();
            try {
                File folder = Environment.getExternalStoragePublicDirectory("Swamiji");
                String path = Environment.getExternalStorageDirectory().toString() + "/Swamiji";
                File directory = new File(path);
                File[] files = directory.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        String result = files[i].getName().replace(".mp3","");
                        downloadSongs.put(result,files[i].toString());
                    }
                }

                if (downloadSongs != null && downloadSongs.size() > 0){
                    Iterator myVeryOwnIterator = downloadSongs.keySet().iterator();
                    while(myVeryOwnIterator.hasNext()) {
                        String key=(String)myVeryOwnIterator.next();
                        if (key.equalsIgnoreCase(title)){
                            return (String)downloadSongs.get(key);
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

}
















