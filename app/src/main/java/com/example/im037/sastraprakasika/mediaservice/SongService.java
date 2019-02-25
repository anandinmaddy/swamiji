package com.example.im037.sastraprakasika.mediaservice;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.MetadataEditor;
import android.os.Build;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.im037.sastraprakasika.Fragment.LecturesFragment_Audioplay;
import com.example.im037.sastraprakasika.Mediaactivity.Audio_player_activity;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.mediacontrols.Controls;
import com.example.im037.sastraprakasika.mediareceiver.NotificationBroadcast;
import com.example.im037.sastraprakasika.mediautil.MediaItem;
import com.example.im037.sastraprakasika.mediautil.PlayerConstants;
import com.example.im037.sastraprakasika.mediautil.UtilFunctions;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

//import com.example.im037.sastraprakasika.Mediaactivity.Mediaplay_MainActivity;

public class SongService extends Service implements AudioManager.OnAudioFocusChangeListener {
    String LOG_CLASS = "SongService";
    private MediaPlayer mp;
    int NOTIFICATION_ID = 1111;
    public static final String NOTIFY_PREVIOUS = "com.example.im037.sastraprakasika.mediaservice.previous";
    public static final String NOTIFY_DELETE = "com.example.im037.sastraprakasika.mediaservice.delete";
    public static final String NOTIFY_PAUSE = "com.example.im037.sastraprakasika.mediaservice.pause";
    public static final String NOTIFY_PLAY = "com.example.im037.sastraprakasika.mediaservice.play";
    public static final String NOTIFY_NEXT = "com.example.im037.sastraprakasika.mediaservice.next";
    public static final String NOTIFY_CLOSE = "com.example.im037.sastraprakasika.mediaservice.close";

    private ComponentName remoteComponentName;
    private RemoteControlClient remoteControlClient;
    AudioManager audioManager;
    Bitmap mDummyAlbumArt;
    private static Timer timer;
    private static boolean currentVersionSupportBigNotification = false;
    private static boolean currentVersionSupportLockScreenControls = false;
    private static boolean isPLaying = false;
    NotificationBroadcast notificationBroadcast;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mp = new MediaPlayer();
        audioManager = (AudioManager) getSystemService( AUDIO_SERVICE );
        notificationBroadcast = new NotificationBroadcast();
        currentVersionSupportBigNotification = UtilFunctions.currentVersionSupportBigNotification();
        currentVersionSupportLockScreenControls = UtilFunctions.currentVersionSupportLockScreenControls();
        timer = new Timer();
        mp.setOnCompletionListener( new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Controls.nextControl( getApplicationContext() );
            }
        } );

        TelephonyManager mgr = (TelephonyManager) getSystemService( TELEPHONY_SERVICE );
        if (mgr != null) {
            mgr.listen( phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE );
        }
        super.onCreate();
    }


    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {

                //Incoming call: Pause music

                if (mp != null) {
                    if (mp.isPlaying()) {
                        isPLaying = true;
                        PlayerConstants.PLAY_PAUSE_HANDLER.sendMessage( PlayerConstants.PLAY_PAUSE_HANDLER.obtainMessage( 0, getApplicationContext().getResources().getString( R.string.pause ) ) );
                    }
                }

                Log.e( SongService.class.getSimpleName(), "RINGING" );
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                //Not in call: Play music
                if (mp != null) {
                    if (isPLaying) {
                        isPLaying = false;
                        PlayerConstants.PLAY_PAUSE_HANDLER.sendMessage( PlayerConstants.PLAY_PAUSE_HANDLER.obtainMessage( 0, getApplicationContext().getResources().getString( R.string.play ) ) );
                    }
                }

                Log.e( SongService.class.getSimpleName(), "CALL_STATE_IDLE" );
            } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                //A call is dialing, active or on hold

                if (mp != null) {
                    if (mp.isPlaying()) {
                        isPLaying = true;
                        PlayerConstants.PLAY_PAUSE_HANDLER.sendMessage( PlayerConstants.PLAY_PAUSE_HANDLER.obtainMessage( 0, getApplicationContext().getResources().getString( R.string.pause ) ) );
                    }
                }

                Log.e( SongService.class.getSimpleName(), "CALL_STATE_OFFHOOK" );
            }
            super.onCallStateChanged( state, incomingNumber );
        }
    };


    /**
     * Send message from timer
     *
     * @author jonty.ankit
     */
    private class MainTask extends TimerTask {
        public void run() {
            handler.sendEmptyMessage( 0 );
        }
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mp != null && mp.getCurrentPosition() > 0) {
                int progress = (mp.getCurrentPosition() * 100) / mp.getDuration();
                Integer i[] = new Integer[3];
                i[0] = mp.getCurrentPosition();
                i[1] = mp.getDuration();
                i[2] = progress;
                try {
                    PlayerConstants.PROGRESSBAR_HANDLER.sendMessage( PlayerConstants.PROGRESSBAR_HANDLER.obtainMessage( 0, i ) );
                } catch (Exception e) {
                }
            }
        }
    };

    @SuppressLint("NewApi")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {

            String action = intent.getAction();
            switch (action) {

                case NOTIFY_PLAY:

                    break;
                case NOTIFY_CLOSE:
                    stopService(intent);
                    stopForeground(true);
                    break;
                case NOTIFY_NEXT:
                    Controls.nextControl(getApplicationContext());
                    break;
                case NOTIFY_PREVIOUS:
                    Controls.previousControl(getApplicationContext());
                    break;

            }

            if (PlayerConstants.SONGS_LIST.size() <= 0) {
                PlayerConstants.SONGS_LIST = UtilFunctions.listOfSongs( getApplicationContext() );
            }
            MediaItem data = PlayerConstants.SONGS_LIST.get( PlayerConstants.SONG_NUMBER );
            if (currentVersionSupportLockScreenControls) {
                RegisterRemoteClient();
            }
            String songPath = data.getPath();
            playSong( songPath, data );
            newNotification();

            PlayerConstants.SONG_CHANGE_HANDLER = new Handler( new Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    MediaItem data = PlayerConstants.SONGS_LIST.get( PlayerConstants.SONG_NUMBER );
                    String songPath = data.getPath();
                    newNotification();
                    try {
                        playSong( songPath, data );
//						Mediaplay_MainActivity.changeUI();
                        LecturesFragment_Audioplay.changeUI();
                        Audio_player_activity.changeUI();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            } );


            PlayerConstants.SEEKBAR_HANDLER = new Handler( new Callback() {
                @Override
                public boolean handleMessage(Message message) {
                    Log.e( SongService.class.getSimpleName(), "" + message.what );
                    int currentposition = (message.what * mp.getDuration()) / 100;
                    if (mp != null) {
                        mp.seekTo( currentposition );
                    }
                    return false;
                }
            } );

            PlayerConstants.PLAY_PAUSE_HANDLER = new Handler( new Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    String message = (String) msg.obj;
                    if (mp == null)
                        return false;
                    if (message.equalsIgnoreCase( getResources().getString( R.string.play ) )) {
                        PlayerConstants.SONG_PAUSED = false;
                        if (currentVersionSupportLockScreenControls) {
                            remoteControlClient.setPlaybackState( RemoteControlClient.PLAYSTATE_PLAYING );
                        }
                        mp.start();
                    } else if (message.equalsIgnoreCase( getResources().getString( R.string.pause ) )) {
                        PlayerConstants.SONG_PAUSED = true;
                        if (currentVersionSupportLockScreenControls) {
                            remoteControlClient.setPlaybackState( RemoteControlClient.PLAYSTATE_PAUSED );
                        }
                        mp.pause();
                    }
                    newNotification();
                    try {
//						Mediaplay_MainActivity.changeButton();
                        Audio_player_activity.changeButton();
                        LecturesFragment_Audioplay.changeButton();
                    } catch (Exception e) {
                    }
                    Log.d( "TAG", "TAG Pressed: " + message );
                    return false;
                }
            } );
            IntentFilter filter = new IntentFilter();
            filter.addAction( "android.intent.action.MEDIA_BUTTON" );
            registerReceiver( notificationBroadcast, filter );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService( Context.NOTIFICATION_SERVICE );


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "default";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel( CHANNEL_ID, name, importance );
            mChannel.setDescription( Description );
            mChannel.enableLights( true );
            mChannel.setLightColor( Color.RED );
            mChannel.enableVibration( true );
            mChannel.setVibrationPattern( new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400} );
            mChannel.setShowBadge( false );
            notificationManager.createNotificationChannel( mChannel );
        }


    }

    /**
     * Notification
     * Custom Bignotification is available from API 16
     */
    @SuppressLint("NewApi")
    private void newNotification() {

        createNotificationChannel();
        String songName = PlayerConstants.SONGS_LIST.get( PlayerConstants.SONG_NUMBER ).getTitle();
        String albumName = PlayerConstants.SONGS_LIST.get( PlayerConstants.SONG_NUMBER ).getAlbum();

        RemoteViews simpleContentView = new RemoteViews( getApplicationContext().getPackageName(), R.layout.customise_notification );
        RemoteViews expandedView = new RemoteViews( getApplicationContext().getPackageName(), R.layout.bignotification );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "default");
        setListeners( simpleContentView,notificationBuilder);
        setListeners( expandedView ,notificationBuilder);

       Notification notification = notificationBuilder
                .setSmallIcon( R.drawable.ic_music )
                .setContentTitle( songName ).build();



        notification.contentView = simpleContentView;
        if (currentVersionSupportBigNotification) {
            notification.bigContentView = expandedView;
        }

        try {
            long albumId = PlayerConstants.SONGS_LIST.get( PlayerConstants.SONG_NUMBER ).getAlbumId();
            Bitmap albumArt = UtilFunctions.getAlbumart( getApplicationContext(), albumId );
            if (albumArt != null) {

                notification.contentView.setImageViewBitmap( R.id.imageViewAlbumArt, albumArt );
                if (currentVersionSupportBigNotification) {
                    notification.bigContentView.setImageViewBitmap( R.id.imageViewAlbumArt, albumArt );
                }
            } else {
                notification.contentView.setImageViewResource( R.id.imageViewAlbumArt, R.drawable.default_album_art );
                if (currentVersionSupportBigNotification) {
                    notification.bigContentView.setImageViewResource( R.id.imageViewAlbumArt, R.drawable.default_album_art );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (PlayerConstants.SONG_PAUSED) {
            notification.contentView.setViewVisibility( R.id.btnPause, View.GONE );
            notification.contentView.setViewVisibility( R.id.btnPlay, View.VISIBLE );

            if (currentVersionSupportBigNotification) {
                notification.bigContentView.setViewVisibility( R.id.btnPause, View.GONE );
                notification.bigContentView.setViewVisibility( R.id.btnPlay, View.VISIBLE );
            }
        } else {
            notification.contentView.setViewVisibility( R.id.btnPause, View.VISIBLE );
            notification.contentView.setViewVisibility( R.id.btnPlay, View.GONE );

            if (currentVersionSupportBigNotification) {
                notification.bigContentView.setViewVisibility( R.id.btnPause, View.VISIBLE );
                notification.bigContentView.setViewVisibility( R.id.btnPlay, View.GONE );
            }
        }

        notification.contentView.setTextViewText( R.id.textSongName, songName );
        notification.contentView.setTextViewText( R.id.textAlbumName, albumName );
        if (currentVersionSupportBigNotification) {
            notification.bigContentView.setTextViewText( R.id.textSongName, songName );
            notification.bigContentView.setTextViewText( R.id.textAlbumName, albumName );
        }
        notification.flags |= Notification.FLAG_ONGOING_EVENT;






        startForeground( NOTIFICATION_ID, notification );
    }




    /**
     * Notification click listeners
     *
     * @param view
     */
    public void setListeners(RemoteViews view, NotificationCompat.Builder notification) {
        Intent previous = new Intent( NOTIFY_PREVIOUS );
        Intent delete = new Intent( NOTIFY_DELETE );
        Intent pause = new Intent( NOTIFY_PAUSE );
        Intent next = new Intent( NOTIFY_NEXT );
        Intent play = new Intent( NOTIFY_PLAY );

        Intent previousIntent = new Intent(this, SongService.class);
        previousIntent.setAction(NOTIFY_PREVIOUS);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, SongService.class);
        playIntent.setAction(NOTIFY_PLAY);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, SongService.class);
        nextIntent.setAction(NOTIFY_NEXT);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, SongService.class);
        closeIntent.setAction(NOTIFY_CLOSE);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, PendingIntent.FLAG_CANCEL_CURRENT);


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
                        R.mipmap.ic_noti_pause, "Previous",
                        ppreviousIntent))
                .addAction(new NotificationCompat.Action(
                        R.mipmap.ic_noti_pause, "Pause",
                        pplayIntent))
                .addAction(new NotificationCompat.Action(
                        R.mipmap.ic_noti_next, "Next",
                        pnextIntent))
                .addAction(new NotificationCompat.Action(
                        R.mipmap.ic_noti_close, "Close",
                        pcloseIntent));

      /*  PendingIntent pPrevious = PendingIntent.getBroadcast( getApplicationContext(), 0, previous, PendingIntent.FLAG_UPDATE_CURRENT );
        view.setOnClickPendingIntent( R.id.btnPrevious, pPrevious );

        PendingIntent pDelete = PendingIntent.getBroadcast( getApplicationContext(), 0, delete, PendingIntent.FLAG_UPDATE_CURRENT );
        view.setOnClickPendingIntent( R.id.btnDelete, pDelete );

        PendingIntent pPause = PendingIntent.getBroadcast( getApplicationContext(), 0, pause, PendingIntent.FLAG_UPDATE_CURRENT );

        view.setOnClickPendingIntent( R.id.btnPause, pPause );

        PendingIntent pNext = PendingIntent.getBroadcast( getApplicationContext(), 0, next, PendingIntent.FLAG_UPDATE_CURRENT );
        view.setOnClickPendingIntent( R.id.btnNext, pNext );

        PendingIntent pPlay = PendingIntent.getBroadcast( getApplicationContext(), 0, play, PendingIntent.FLAG_UPDATE_CURRENT );
        view.setOnClickPendingIntent( R.id.btnPlay, pPlay );*/

    }

    @Override
    public void onDestroy() {
        if (mp != null) {
            mp.stop();
            mp = null;
        }
        unregisterReceiver( notificationBroadcast );
        super.onDestroy();
    }

    /**
     * Play song, Update Lockscreen fields
     *
     * @param songPath
     * @param data
     */
    @SuppressLint("NewApi")
    private void playSong(String songPath, MediaItem data) {
        try {

            if (currentVersionSupportLockScreenControls) {
                UpdateMetadata( data );
                remoteControlClient.setPlaybackState( RemoteControlClient.PLAYSTATE_PLAYING );
            }
            mp.reset();
            mp.setDataSource( songPath );
            mp.prepare();
            mp.start();
            timer.scheduleAtFixedRate( new MainTask(), 0, 100 );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private void RegisterRemoteClient() {
        remoteComponentName = new ComponentName( getApplicationContext(), new NotificationBroadcast().ComponentName() );
        try {
            if (remoteControlClient == null) {
                audioManager.registerMediaButtonEventReceiver( remoteComponentName );
                Intent mediaButtonIntent = new Intent( Intent.ACTION_MEDIA_BUTTON );
                mediaButtonIntent.setComponent( remoteComponentName );
                PendingIntent mediaPendingIntent = PendingIntent.getBroadcast( this, 0, mediaButtonIntent, 0 );
                remoteControlClient = new RemoteControlClient( mediaPendingIntent );
                audioManager.registerRemoteControlClient( remoteControlClient );
            }
            remoteControlClient.setTransportControlFlags(
                    RemoteControlClient.FLAG_KEY_MEDIA_PLAY |
                            RemoteControlClient.FLAG_KEY_MEDIA_PAUSE |
                            RemoteControlClient.FLAG_KEY_MEDIA_PLAY_PAUSE |
                            RemoteControlClient.FLAG_KEY_MEDIA_STOP |
                            RemoteControlClient.FLAG_KEY_MEDIA_PREVIOUS |
                            RemoteControlClient.FLAG_KEY_MEDIA_NEXT );
        } catch (Exception ex) {
        }
    }

    @SuppressLint("NewApi")
    private void UpdateMetadata(MediaItem data) {
        if (remoteControlClient == null)
            return;
        MetadataEditor metadataEditor = remoteControlClient.editMetadata( true );
        metadataEditor.putString( MediaMetadataRetriever.METADATA_KEY_ALBUM, data.getAlbum() );
        metadataEditor.putString( MediaMetadataRetriever.METADATA_KEY_ARTIST, data.getArtist() );
        metadataEditor.putString( MediaMetadataRetriever.METADATA_KEY_TITLE, data.getTitle() );
        mDummyAlbumArt = UtilFunctions.getAlbumart( getApplicationContext(), data.getAlbumId() );
        if (mDummyAlbumArt == null) {
            mDummyAlbumArt = BitmapFactory.decodeResource( getResources(), R.drawable.default_album_art );
        }
        metadataEditor.putBitmap( MetadataEditor.BITMAP_KEY_ARTWORK, mDummyAlbumArt );
        metadataEditor.apply();
        audioManager.requestAudioFocus( this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN );
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
    }
}