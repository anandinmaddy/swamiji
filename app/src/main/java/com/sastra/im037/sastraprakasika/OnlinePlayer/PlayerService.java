package com.sastra.im037.sastraprakasika.OnlinePlayer;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.sastra.im037.sastraprakasika.Activity.SplashActivity;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.utils.DBHelper;
import com.sastra.im037.sastraprakasika.utils.GlobalBus;
import com.sastra.im037.sastraprakasika.utils.MessageEvent;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class PlayerService extends IntentService implements Player.EventListener {

    public static final String ACTION_TOGGLE = "action.ACTION_TOGGLE";
    public static final String ACTION_PLAY = "action.ACTION_PLAY";
    public static final String ACTION_NEXT = "action.ACTION_NEXT";
    public static final String ACTION_PREVIOUS = "action.ACTION_PREVIOUS";
    public static final String ACTION_STOP = "action.ACTION_STOP";
    public static final String ACTION_SEEKTO = "action.ACTION_SEEKTO";

    public static ExoPlayer exoPlayer = null;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder notification;
    RemoteViews bigViews, smallViews;

    DataSource.Factory dataSourceFactory;
    ExtractorsFactory extractorsFactory;
    private String NOTIFICATION_CHANNEL_ID = "onlinemp3_ch_1";

    static PlayerService playerService;
    Methods methods;
    DBHelper dbHelper;
    Boolean isNewSong = false;
    Bitmap bitmap;
    String playerList = "";
    Boolean isExistingPlayer = false;
    public PlayerService() {
        super(null);
    }

    static public PlayerService getInstance() {
        if (playerService == null) {
            playerService = new PlayerService();
        }
        return playerService;
    }

    public static Boolean getIsPlayling() {
        return exoPlayer != null && exoPlayer.getPlayWhenReady();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String username = intent.getStringExtra("from");
        if (username.equalsIgnoreCase("lecture")){
            playerList = username;
        }else if(username.equalsIgnoreCase("download")){
            playerList = username;
        }

    }

    @Override
    public void onCreate() {
        methods = new Methods(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext());
        try {
            registerReceiver(onCallIncome, new IntentFilter("android.intent.action.PHONE_STATE"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), Util.getUserAgent(getApplicationContext(), "onlinemp3"), bandwidthMeter);
        extractorsFactory = new DefaultExtractorsFactory();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector);
        exoPlayer.addListener(this);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        String action = "";
        Bundle bundles = null;
        if(intent != null){
             action = intent.getAction();
             bundles = intent.getExtras();
        }

        switch (action) {
            case ACTION_PLAY:
                if (bundles != null && bundles.getString("from") !=null && bundles.getString("from").equalsIgnoreCase("topics")){
                    isExistingPlayer = true;
                    startNewSong(false);
                }else {
                    isExistingPlayer = false;
                    startNewSong(true);
                }
                break;
            case ACTION_TOGGLE:
                togglePlay();
                break;

            case ACTION_SEEKTO:
                seekTo(intent.getExtras().getLong("seekto"));
                break;
            case ACTION_STOP:
                stop(intent);
                break;
            case ACTION_PREVIOUS:
                if (!Constant.isOnline || methods.isNetworkAvailable()) {
                    previous();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
                break;
            case ACTION_NEXT:
                if (!Constant.isOnline || methods.isNetworkAvailable()) {
                    next();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return START_STICKY;
    }


    private void startNewSong(boolean b) {
        isNewSong = true;
        setBuffer(true);
        if (Constant.isfromPlayer.equalsIgnoreCase("topic")){
            Constant.isplayTopics = true;
            Constant.isplayLectures = false;
            Constant.isplayDownloads = false;
            Constant.isplayPlaylist = false;
            Constant.isplaySearch = false;


        }else if(Constant.isfromPlayer.equalsIgnoreCase("lecturer")){
            Constant.isplayTopics = false;
            Constant.isplayLectures = true;
            Constant.isplayDownloads = false;
            Constant.isplayPlaylist = false;
            Constant.isplaySearch = false;


        }else if(Constant.isfromPlayer.equalsIgnoreCase("download")){
            Constant.isplayTopics = false;
            Constant.isplayLectures = false;
            Constant.isplayDownloads = true;
            Constant.isplayPlaylist = false;
            Constant.isplaySearch = false;


        }else if(Constant.isfromPlayer.equalsIgnoreCase("playlist")){
            Constant.isplayTopics = false;
            Constant.isplayLectures = false;
            Constant.isplayDownloads = false;
            Constant.isplayPlaylist = true;
            Constant.isplaySearch = false;

        }else if(Constant.isfromPlayer.equalsIgnoreCase("search")){
            Constant.isplayTopics = false;
            Constant.isplayLectures = false;
            Constant.isplayDownloads = false;
            Constant.isplayPlaylist = false;
            Constant.isplaySearch = true;


        }
        Intent intent = new Intent();
        intent.putExtra("message",Constant.playPos);
        intent.setAction("com.android.activity.SEND_DATA");
        getApplicationContext().sendBroadcast(intent);
        MediaSource videoSource;
    if (!b){

            String url = Constant.arrayOfflineTopiclineSongs.get(Constant.playPos).getTopics_det_img().replace(" ", "%20");

            videoSource = new ExtractorMediaSource(Uri.parse(url),
                    dataSourceFactory, extractorsFactory, null, null);

        exoPlayer.prepare(videoSource);
        exoPlayer.setPlayWhenReady(true);
    }else {
        try {
            String url = Constant.arrayList_play.get(Constant.playPos).getUrl().replace(" ", "%20");
            String OfflineLink = Constant.arrayList_play.get(Constant.playPos).getDownloads();
            if (OfflineLink != null && !OfflineLink.equals("")){
                videoSource = new ExtractorMediaSource(Uri.parse(OfflineLink),
                        dataSourceFactory, extractorsFactory, null, null);
            }else {
                videoSource = new ExtractorMediaSource(Uri.parse(url),
                        dataSourceFactory, extractorsFactory, null, null);
            }
            exoPlayer.prepare(videoSource);
            exoPlayer.setPlayWhenReady(true);
        }catch (Exception e){
            e.printStackTrace();
        }


}

        //dbHelper.addToRecent(Constant.arrayList_play.get(Constant.playPos), Constant.isOnline);
    }

    private void togglePlay() {
        if (exoPlayer.getPlayWhenReady()) {
            exoPlayer.setPlayWhenReady(false);
            if (Constant.isfromPlayer.equalsIgnoreCase("topic")){
                Constant.isplayTopics = false;

            }else if(Constant.isfromPlayer.equalsIgnoreCase("lecturer")){
                Constant.isplayLectures = false;

            }else if(Constant.isfromPlayer.equalsIgnoreCase("download")){
                Constant.isplayDownloads = false;

            }else if(Constant.isfromPlayer.equalsIgnoreCase("playlist")){
                Constant.isplayPlaylist = false;

            }else if(Constant.isfromPlayer.equalsIgnoreCase("search")){
                Constant.isplaySearch = false;

            }
        } else {
            exoPlayer.setPlayWhenReady(true);
            if (Constant.isfromPlayer.equalsIgnoreCase("topic")){
                Constant.isplayTopics = true;
                Constant.isplayLectures = false;
                Constant.isplayDownloads = false;
                Constant.isplayPlaylist = false;
                Constant.isplaySearch = false;


            }else if(Constant.isfromPlayer.equalsIgnoreCase("lecturer")){
                Constant.isplayTopics = false;
                Constant.isplayLectures = true;
                Constant.isplayDownloads = false;
                Constant.isplayPlaylist = false;
                Constant.isplaySearch = false;


            }else if(Constant.isfromPlayer.equalsIgnoreCase("download")){
                Constant.isplayTopics = false;
                Constant.isplayLectures = false;
                Constant.isplayDownloads = true;
                Constant.isplayPlaylist = false;
                Constant.isplaySearch = false;


            }else if(Constant.isfromPlayer.equalsIgnoreCase("playlist")){
                Constant.isplayTopics = false;
                Constant.isplayLectures = false;
                Constant.isplayDownloads = false;
                Constant.isplayPlaylist = true;
                Constant.isplaySearch = false;

            }else if(Constant.isfromPlayer.equalsIgnoreCase("search")){
                Constant.isplayTopics = false;
                Constant.isplayLectures = false;
                Constant.isplayDownloads = false;
                Constant.isplayPlaylist = false;
                Constant.isplaySearch = true;


            }
        }
        changePlayPause(exoPlayer.getPlayWhenReady());
        updateNotiPlay(exoPlayer.getPlayWhenReady());
    }

    private void previous() {
        setBuffer(true);
        if (Constant.isSuffle) {
            Random rand = new Random();
            if (Constant.isFromPage.equalsIgnoreCase("topic")) {
                Constant.playPos = rand.nextInt((Constant.arrayOfflineTopiclineSongs.size() - 1) + 1);
            }else{
                Constant.playPos = rand.nextInt((Constant.arrayList_play.size() - 1) + 1);
            }
        } else {
            if (Constant.playPos > 0) {
                Constant.playPos = Constant.playPos - 1;
            } else {
                if (Constant.isFromPage.equalsIgnoreCase("topic")) {
                    Constant.playPos = Constant.arrayOfflineTopiclineSongs.size() - 1;
                }else {
                    Constant.playPos = Constant.arrayList_play.size() - 1;
                }
            }
        }
        if(Constant.isFromPage.equalsIgnoreCase("topic")){
            startNewSong(false);

        }else{
            startNewSong(true);

        }
    }

    private void next() {
        setBuffer(true);
        if (Constant.isSuffle) {
            Random rand = new Random();
            if (Constant.isFromPage.equalsIgnoreCase("topic")) {
                Constant.playPos = rand.nextInt((Constant.arrayOfflineTopiclineSongs.size() - 1) + 1);
            }else {
                Constant.playPos = rand.nextInt((Constant.arrayList_play.size() - 1) + 1);
            }
        } else {
            if (Constant.isFromPage.equalsIgnoreCase("topic")){
                if (Constant.playPos < (Constant.arrayOfflineTopiclineSongs.size() - 1)) {
                    Constant.playPos = Constant.playPos + 1;
                } else {
                    Constant.playPos = 0;
                }
            }else{
                if (Constant.isFromPage.equalsIgnoreCase("topic")) {
                    if (Constant.playPos < (Constant.arrayOfflineTopiclineSongs.size() - 1)) {
                        Constant.playPos = Constant.playPos + 1;
                    } else {
                        Constant.playPos = 0;
                    }
                }else{
                    if (Constant.playPos < (Constant.arrayList_play.size() - 1)) {
                        Constant.playPos = Constant.playPos + 1;
                    } else {
                        Constant.playPos = 0;
                    }
                }

            }

        }
        if(Constant.isFromPage.equalsIgnoreCase("topic")){
            startNewSong(false);

        }else{
            startNewSong(true);

        }
    }

    private void seekTo(long seek) {
        exoPlayer.seekTo((int) seek);
    }

    private void onCompletion() {
        if (Constant.isRepeat) {
            exoPlayer.seekTo(0);
        } else {
            if (Constant.isSuffle) {
                Random rand = new Random();
                if (Constant.isFromPage.equalsIgnoreCase("topic")) {
                    Constant.playPos = rand.nextInt((Constant.arrayOfflineTopiclineSongs.size() - 1) + 1);
                }else{
                    Constant.playPos = rand.nextInt((Constant.arrayList_play.size() - 1) + 1);

                }
            } else {
                next();
            }
        }

        startNewSong(true);
    }

    private void changePlayPause(Boolean flag) {
        changeEquilizer();
        changeImageAnimation();
        GlobalBus.getBus().postSticky(new MessageEvent(flag, "playicon"));
    }

    private void setBuffer(Boolean isBuffer) {
        if (!isBuffer) {
            changeEquilizer();
        }
        GlobalBus.getBus().postSticky(new MessageEvent(isBuffer, "buffer"));
    }

    private void changeEquilizer() {
        GlobalBus.getBus().postSticky(new ItemAlbums("", "", "", ""));
    }

    private void changeImageAnimation() {
        //((BaseActivity) Constant.context).changeImageAnimation(exoPlayer.getPlayWhenReady());
    }

    private void stop(Intent intent) {
        Constant.isPlaying = false;
        Constant.isPlayed = false;

        exoPlayer.setPlayWhenReady(false);
        changePlayPause(false);
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
        try {
            unregisterReceiver(onCallIncome);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stopService(intent);
        stopForeground(true);
    }

    private void createNoti() {
        bigViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        smallViews = new RemoteViews(getPackageName(), R.layout.layout_noti_small);

        Intent notificationIntent = new Intent(this, SplashActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.putExtra("isnoti", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent previousIntent = new Intent(this, PlayerService.class);
        previousIntent.setAction(ACTION_PREVIOUS);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, PlayerService.class);
        playIntent.setAction(ACTION_TOGGLE);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, PlayerService.class);
        nextIntent.setAction(ACTION_NEXT);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, PlayerService.class);
        closeIntent.setAction(ACTION_STOP);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        String title = "";

        if (isExistingPlayer){
            title=  Constant.arrayOfflineTopiclineSongs.get(Constant.playPos).getTopics_det_title();
        }else {
            title =  Constant.arrayList_play.get(Constant.playPos).getTitle();
        }
        notification = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(getString(R.string.app_name))
                .setPriority(Notification.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker(title)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setOnlyAlertOnce(true);

        NotificationChannel mChannel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Online Song";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_LOW;
            mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(mChannel);

            MediaSessionCompat mMediaSession;
            mMediaSession = new MediaSessionCompat(getApplicationContext(), "ONLINEMP3");
            mMediaSession.setFlags(
                    MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

            notification.setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mMediaSession.getSessionToken())
                    .setShowCancelButton(true)
                    .setShowActionsInCompactView(0, 1, 2)
                    .setCancelButtonIntent(
                            MediaButtonReceiver.buildMediaButtonPendingIntent(getApplicationContext(), PlaybackStateCompat.ACTION_STOP)))
                    .addAction(new NotificationCompat.Action(
                            R.drawable.ic_skip_previous_black_24dp, "Previous",
                            ppreviousIntent))
                    .addAction(new NotificationCompat.Action(
                            R.drawable.ic_pause_black_24dp, "Pause",
                            pplayIntent))
                    .addAction(new NotificationCompat.Action(
                            R.drawable.ic_skip_next_black_24dp, "Next",
                            pnextIntent))
                    .addAction(new NotificationCompat.Action(
                            R.drawable.ic_stop_black_24dp, "Close",
                            pcloseIntent));

            if (isExistingPlayer){
                title=  Constant.arrayOfflineTopiclineSongs.get(Constant.playPos).getTopics_det_title();
            }else {
                title =  Constant.arrayList_play.get(Constant.playPos).getTitle();
            }

            notification.setContentTitle(title);
            notification.setContentText(title);
        } else {
            if (isExistingPlayer){
                title=  Constant.arrayOfflineTopiclineSongs.get(Constant.playPos).getTopics_det_title();
            }else {
                title =  Constant.arrayList_play.get(Constant.playPos).getTitle();
            }
            bigViews.setOnClickPendingIntent(R.id.imageView_noti_play, pplayIntent);

            bigViews.setOnClickPendingIntent(R.id.imageView_noti_next, pnextIntent);

            bigViews.setOnClickPendingIntent(R.id.imageView_noti_prev, ppreviousIntent);

            bigViews.setOnClickPendingIntent(R.id.imageView_noti_close, pcloseIntent);
            smallViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);

            bigViews.setImageViewResource(R.id.imageView_noti_play, android.R.drawable.ic_media_pause);

            bigViews.setTextViewText(R.id.textView_noti_name, title);
            smallViews.setTextViewText(R.id.status_bar_track_name, title);

            bigViews.setTextViewText(R.id.textView_noti_artist, title);
            smallViews.setTextViewText(R.id.status_bar_artist_name, title);

            notification.setCustomContentView(smallViews).setCustomBigContentView(bigViews);
        }

        startForeground(101, notification.build());
        updateNotiImage();
    }

    private void updateNotiImage() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                //JsonUtils.okhttpGET(Constant.URL_SONG_1 + Constant.arrayList_play.get(Constant.playPos).getId() + Constant.URL_SONG_2 + "");
                String title = "";

                if (isExistingPlayer){
                    title=  Constant.arrayOfflineTopiclineSongs.get(Constant.playPos).getTopics_det_imgurl();
                }else {
                    title =  Constant.arrayList_play.get(Constant.playPos).getImageSmall();
                }

                getBitmapFromURL(title);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notification.setLargeIcon(bitmap);
                } else {
                    bigViews.setImageViewBitmap(R.id.imageView_noti, bitmap);
                    smallViews.setImageViewBitmap(R.id.status_bar_album_art, bitmap);
                }
                mNotificationManager.notify(101, notification.build());
                return null;
            }
        }.execute();
    }

    private void updateNoti() {
        String title = "";
        String artist = "";
        if (isExistingPlayer){
            title=  Constant.arrayOfflineTopiclineSongs.get(Constant.playPos).getTopics_det_title();
            artist = Constant.arrayOfflineTopiclineSongs.get(Constant.playPos).getTopics_det_title();

        }else {
            title =  Constant.arrayList_play.get(Constant.playPos).getTitle();
            artist = Constant.arrayList_play.get(Constant.playPos).getArtist();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setContentTitle(title);
            notification.setContentText(artist);
        } else {
            bigViews.setTextViewText(R.id.textView_noti_name, title);
            bigViews.setTextViewText(R.id.textView_noti_artist, artist);
            smallViews.setTextViewText(R.id.status_bar_artist_name, artist);
            smallViews.setTextViewText(R.id.status_bar_track_name, title);
        }
        updateNotiImage();
        updateNotiPlay(exoPlayer.getPlayWhenReady());
        changeImageAnimation();
    }

    private void updateNotiPlay(Boolean isPlay) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.mActions.remove(1);
            Intent playIntent = new Intent(this, PlayerService.class);
            playIntent.setAction(ACTION_TOGGLE);
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (isPlay) {
                notification.mActions.add(1, new NotificationCompat.Action(
                        R.drawable.ic_pause_notification, "Pause",
                        ppreviousIntent));

            } else {
                notification.mActions.add(1, new NotificationCompat.Action(
                        R.drawable.ic_play_notification, "Play",
                        ppreviousIntent));
            }
        } else {
            if (isPlay) {
                bigViews.setImageViewResource(R.id.imageView_noti_play, android.R.drawable.ic_media_pause);
            } else {
                bigViews.setImageViewResource(R.id.imageView_noti_play, android.R.drawable.ic_media_play);
            }
        }
        mNotificationManager.notify(101, notification.build());
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == Player.STATE_ENDED) {
            onCompletion();
        }
        if (playbackState == Player.STATE_READY && playWhenReady) {
            if (isNewSong) {
                isNewSong = false;
                Constant.isPlayed = true;
                setBuffer(false);
                if(isExistingPlayer){
                    GlobalBus.getBus().postSticky(Constant.arrayOfflineTopiclineSongs.get(Constant.playPos));

                }else {
                    try {
                        GlobalBus.getBus().postSticky(Constant.arrayList_play.get(Constant.playPos));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if (notification == null) {
                    createNoti();
                    changeImageAnimation();
                } else {
                    updateNoti();
                }
            } else {
                updateNotiPlay(exoPlayer.getPlayWhenReady());
            }
        }
    }

    private void getBitmapFromURL(String src) {
        try {
            if (Constant.isOnline) {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), methods.getAlbumArtUri(Integer.parseInt(src)));
                } catch (Exception e) {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_music);
                }
            }
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        exoPlayer.setPlayWhenReady(false);
        setBuffer(false);
        changePlayPause(false);
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    BroadcastReceiver onCallIncome = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String a = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (Constant.isPlaying) {
                if (a.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) || a.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    exoPlayer.setPlayWhenReady(false);
                } else if (a.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    exoPlayer.setPlayWhenReady(true);
                }
            }
        }
    };
}