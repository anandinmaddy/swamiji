package com.example.im037.sastraprakasika.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.example.im037.sastraprakasika.Adapter.Downloads_audio_list_adapter;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Entity.ItemSongDatabase;
import com.example.im037.sastraprakasika.Entity.Lecturers;
import com.example.im037.sastraprakasika.Model.ListOfLecturesListDetails;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.OnlinePlayer.Methods;
import com.example.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.mediautil.MediaItem;
import com.example.im037.sastraprakasika.mediautil.PlayerConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DownloadsFragmentNew extends Fragment  {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    Methods methods;

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
    Downloads_audio_list_adapter downloads_audio_list_adapter = null;
    View view;

    // add new
    ArrayList<MediaItem> mediaItems = new ArrayList<>();
    public static final String TAG = LecturesFragment_Audioplay.class.getSimpleName();

    //ProgressDialog progressDialog;


    //added me
    ArrayList<ListOfLecturesListDetails> lect_det;
    ListView listView_song;
//    String img_url[] = {"https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/01-PURUSHARTHA.mp3",
//            "https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/02-SASTRAM.mp3",
//            "https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/03-VARNA-DHARMA.mp3",
//            "https://www.imaginetventures.name/swamiji/wp-content/uploads/2018/10/04-ASRAMA-DHARMA.mp3"};
//
//    int img[] = {R.drawable.intro_vedanta,R.drawable.intro_vedanta,R.drawable.general,R.drawable.tamil};
//    String song_title[] = {"An overview of Yoga","Insight into Human Pursuits(Purusartha)","Right Action & Right Attitude","Scriptures(Sastram)"};
//    String song_artist[] = {"Class1","Class2","Class3","Class4"};
//    String song_dur[] = {"56.04","55.05","57.08","58.37"};



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_downloads_main, container, false);

        context = getContext();
        init();


        //lect_det = new ArrayList<ListOfLecturesListDetails>();
//        for (int i = 0; i < img_url.length; i++) {
//            ListOfLecturesListDetails listOfLecturesListDetails = new ListOfLecturesListDetails();
//            listOfLecturesListDetails.setSong_name(song_title[i]);
//            listOfLecturesListDetails.setSong_artise(song_artist[i]);
//            listOfLecturesListDetails.setSong_imagev(img[i]);
//            listOfLecturesListDetails.setSong_duration(song_dur[i]);
//            listOfLecturesListDetails.setUrl_path(img_url[i]);
//
//            lect_det.add(listOfLecturesListDetails);
//

//            listView_song = (ListView) view.findViewById(R.id.listViewMusicSong_list);
//            Lectures_audio_list_adapter lectures_audio_list_adapter = new Lectures_audio_list_adapter(mediaItems, getActivity());
//            listView_song.setAdapter(lectures_audio_list_adapter);

        return view;


    }


    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
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


        private void init() {
            getViews();
            setListeners();
            txt_playingSong.setSelected(true);
            //progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

            if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
                if (PlayerConstants.SONGS_LIST.size() <= 0) {
                    ItemSongDatabase db = Room.databaseBuilder(getActivity(),
                            ItemSongDatabase.class, "Lecturers").allowMainThreadQueries().build();
                    ItemSong[] lecturersList =  db.userDao().loadAllUsers();
                    if(Constant.arrayListOfflineSongs.size() > 0){
                     //   Constant.arrayListOfflineSongs.add(lecturersList[0]);
                    }else{
                        callWebservice();
                    }

                    //loadUrlData();

                }

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

    private void setListeners() {
        listView_song.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                //play a song in media player priya Ramakrishanan
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
    }


        private void callWebservice() {

           /* progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading ..., Please wait");
            progressDialog.show();*/
            new WebServices(getActivity(), TAG).getlibrary(Session.getInstance(getContext(), TAG).getUserId(), "lectures", new VolleyResponseListerner() {


                @Override
                public void onResponse(final JSONObject response) throws JSONException {
                    // hideCommonProgressBar();
                  //  progressDialog.hide();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Constant.arrayListOfflineSongs.clear();
                            ItemSongDatabase db = null;

                            System.out.println("library respon:::: " + response);
                            if (response.optString("resultcode").equalsIgnoreCase("200")) {

                                try{
                                     db = Room.databaseBuilder(getActivity(),
                                             ItemSongDatabase.class, "ItemSong").allowMainThreadQueries().build();
                                    db.clearAllTables();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                try {

                                    JSONObject jsonObject1 = response.optJSONObject("data");
                                    JSONArray contentArray = jsonObject1.optJSONArray("datacontent");
                                    for (int i = 0; i < contentArray.length(); i++) {

                                        JSONObject dataConten = contentArray.getJSONObject(i);
                                        JSONArray trackArray = dataConten.optJSONArray("track");
                                        String image_url = dataConten.optString("image_url");
                                        String post_id = dataConten.optString("postid");

                                        String volume_name = dataConten.optString("volume_name");

                                        for (int j = 0; j < trackArray.length(); j++) {
                                            Lecturers lecturers = new Lecturers();
                                            JSONObject jsonObject = trackArray.optJSONObject(j);
                               /* MediaItem mediaItem = new MediaItem();
                                mediaItem.setTitle(jsonObject.optString("title"));
                                mediaItem.setPath(jsonObject.optString("mp3"));
                                mediaItem.setDuration(jsonObject.optString("time"));
                                mediaItem.setAlbum_img(image_url);
                                mediaItem.setAlbum(volume_name);*/
                                            lecturers.setMp3(jsonObject.optString("mp3"));
                                            lecturers.setTitle(jsonObject.optString("title"));
                                            lecturers.setTime(jsonObject.optString("time"));
                                            lecturers.setClassname(jsonObject.optString("classname"));
                                            lecturers.setImage_url(image_url);
                                            lecturers.setPost_id(post_id);



                                            ItemSong itemSong = new ItemSong();

                                       //     ItemSong itemSong = new ItemSong();
                                           // itemSong.setId(01);
                                            itemSong.setUrl(jsonObject.optString("mp3"));
                                            itemSong.setTitle(jsonObject.optString("title"));
                                            itemSong.setDuration(jsonObject.optString("time"));
                                            itemSong.setClassName(jsonObject.optString("classname"));
                                            itemSong.setImageBig(image_url);
                                            itemSong.setImageSmall(image_url);
                                            try{

                                                db.userDao().insertAll(itemSong);
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                            Constant.arrayListOfflineSongs.add(itemSong);


                                        }

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


                }//lectures_recycler_adapter = new Lectures_Recycler_adapter( MainActivity.this, lectures_listitems );
                //  recyclerView.setAdapter( lectures_recycler_adapter ); // set the Adapter to RecyclerView


                //Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen


                @Override
                public void onError(String message, String title) {
                    System.out.println("library error:::: " + message);
                   // progressDialog.hide();
                }
            });

        }


    private void setListItems() {
        //listView_song = (ListView) view.findViewById( R.id.listViewMusicSong_list );
        downloads_audio_list_adapter = new Downloads_audio_list_adapter(Constant.arrayListOfflineSongs, getActivity());
        listView_song.setAdapter(downloads_audio_list_adapter);

    }



}


