package com.sastra.im037.sastraprakasika.Mediaactivity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Fragment.LecturesFragment_Audioplay;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.mediacontrols.Controls;
import com.sastra.im037.sastraprakasika.mediaservice.SongService;
import com.sastra.im037.sastraprakasika.mediautil.MediaItem;
import com.sastra.im037.sastraprakasika.mediautil.PlayerConstants;
import com.sastra.im037.sastraprakasika.mediautil.UtilFunctions;
import com.squareup.picasso.Picasso;


public class Audio_player_activity extends AppCompatActivity {

	static LinearLayout layoutbelow;
	static ImageView song_img_view;
	Button btnBack;
	static Button btnPause_aud;
	Button btnNext;
	static Button btnPlay_aud;
	static TextView textNowPlaying;
	static TextView textAlbumArtist;
	static TextView textComposer;
	static LinearLayout linearLayoutPlayer;
	//ProgressBar progressBar;
	SeekBar seekbar_audio;
	static Context context;
	TextView textBufferDuration, textDuration;
	 AudioManager audioManager;
	 ImageView back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.audio_player);
		context = this;
		back = findViewById(R.id.back1);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});


		init();
	}

	private void init() {
		getViews();
		setListeners();
		//seekbar_audio.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
		PlayerConstants.PROGRESSBAR_HANDLER = new Handler(){
			 @Override
		        public void handleMessage(Message msg){
				 Integer i[] = (Integer[])msg.obj;
				 textBufferDuration.setText(UtilFunctions.getDuration(i[0]));
				 textDuration.setText(UtilFunctions.getDuration(i[1]));
				 seekbar_audio.setProgress(i[2]);
		    	}
		};


		// audio vol concepts

//		 audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
//
//
//		Button upButton = (Button) findViewById(R.id.btn_upbtn);
//		upButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//
////To increase media player volume
//				audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
//			}
//		});
//
//		Button downButton = (Button) findViewById(R.id.btn_downbtn);
//		downButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
////To decrease media player volume
//
//				audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
//			}
//		});


		//this end audio volume increase and decrease concepts

//		PlayerConstants.SEEKBAR_HANDLER = new Handler(){
//			@Override
//			public void handleMessage(Message message){
//				Integer i[] = (Integer[])message.obj;
//				textBufferDuration.setText(UtilFunctions.getDuration(i[0]));
//				textDuration.setText(UtilFunctions.getDuration(i[1]));
//				seekbar_audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//					int Progresschanged = 0;
//					@Override
//					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//						Progresschanged = progress;
//						if(fromUser){
//							//add priya
//							MediaItem data = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
//							song_img_view.setImageResource(data.getAlbum_img());
//							Bitmap albumArt = UtilFunctions.getAlbumart(context, data.getAlbumId());
//							if(albumArt!= null){
//								seekbar_audio.setProgress(Integer.parseInt(data.getPath()));
//
//								}else {
//								Toast.makeText(context,"not loa",Toast.LENGTH_SHORT).show();
//							}
//
//						}else {
//							Toast.makeText(context,"Not seekbatr handler",Toast.LENGTH_SHORT).show();
//						}
//
//					}
//
//					@Override
//					public void onStartTrackingTouch(SeekBar seekBar) {
//
//					}
//
//					@Override
//					public void onStopTrackingTouch(SeekBar seekBar) {
//						Toast.makeText(getApplicationContext(), "Seek bar progress is :" + Progresschanged,
//								Toast.LENGTH_SHORT).show();
//
//					}
//				});
//
//			}
//		};


//		final Handler mHandler = new Handler();
////Make sure you update Seekbar on UI thread
//		Audio_player_activity.this.runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				//add priya
//				MediaItem data = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
//				song_img_view.setImageResource(data.getAlbum_img());
//
//				Bitmap albumArt = UtilFunctions.getAlbumart(context, data.getAlbumId());
//				if(albumArt != null){
//					seekbar_audio.setProgress(Integer.parseInt(data.getPath()));
//
//
//				}
//				mHandler.postDelayed(this, 1000);
//			}
//		});




	}

	private void setListeners() {
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Controls.previousControl(getApplicationContext());
			}
		});
		
		btnPause_aud.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Controls.pauseControl(getApplicationContext());
			}
		});
		
		btnPlay_aud.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Controls.playControl(getApplicationContext());
			}
		});
		
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Controls.nextControl(getApplicationContext());
			}
		});
	}
	
	public static void changeUI(){
		updateUI();
		changeButton();
	}
	
	private void getViews() {
		btnBack = (Button) findViewById(R.id.btnBack_audio);
		btnPause_aud = (Button) findViewById(R.id.btnPause_audio);
		btnNext = (Button) findViewById(R.id.btnNext_audio);
		btnPlay_aud = (Button) findViewById(R.id.btnPlay_audio);
		textNowPlaying = (TextView) findViewById(R.id.textNowPlaying);
		linearLayoutPlayer = (LinearLayout) findViewById(R.id.linearLayoutPlayer);
		textAlbumArtist = (TextView) findViewById(R.id.textAlbumArtist);
		textComposer = (TextView) findViewById(R.id.textComposer);
		//progressBar = (ProgressBar) findViewById(R.id.progressBar_a);
		seekbar_audio = (SeekBar)findViewById(R.id.seekbar_audio);
		seekbar_audio.setMax( 100 );
		textBufferDuration = (TextView) findViewById(R.id.textBufferDuration_a);
		textDuration = (TextView) findViewById(R.id.textDuration_a);
		textNowPlaying.setSelected(true);
		textAlbumArtist.setSelected(true);
		song_img_view = (ImageView)findViewById(R.id.song_iv);
		layoutbelow = (LinearLayout)findViewById(R.id.layout_below);


		seekbar_audio.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

				PlayerConstants.SEEKBAR_HANDLER.sendEmptyMessage(seekBar.getProgress());

				Log.e(LecturesFragment_Audioplay.class.getSimpleName(),"onStopTrackingTouch"+seekBar.getProgress());
				// PlayerConstants.PROGRESSBAR_HANDLER.sendMessage(PlayerConstants.PROGRESSBAR_HANDLER.obtainMessage(0, i));
			}
		} );
	}



	
	@Override
	protected void onResume() {
		super.onResume();
		boolean isServiceRunning = UtilFunctions.isServiceRunning(SongService.class.getName(), getApplicationContext());
		if (isServiceRunning) {
			updateUI();
		}
		changeButton();
	}
	
	public static void changeButton() {
		if(btnPause_aud!=null) {
			if (PlayerConstants.SONG_PAUSED) {
				btnPause_aud.setVisibility(View.GONE);
				btnPlay_aud.setVisibility(View.VISIBLE);
			} else {
				btnPause_aud.setVisibility(View.VISIBLE);
				btnPlay_aud.setVisibility(View.GONE);
			}
		}
	}
	
	private static void updateUI() {
		try{
			String songName = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getTitle();
			String artist = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getArtist();
			String album = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbum();
			String composer = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getComposer();
			textNowPlaying.setText(songName);
			textAlbumArtist.setText(artist + " - " + album);
			if(composer != null && composer.length() > 0){
				textComposer.setVisibility(View.VISIBLE);
				textComposer.setText(composer);
			}else{
				textComposer.setVisibility(View.GONE);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		try{

			//add priya
			MediaItem data = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
			//song_img_view.setImageResource(data.getAlbum_img());

			if(data.getAlbum_img()!=null){
				Picasso.get()
						.load(data.getAlbum_img())
						.into(song_img_view);
			}else{
				song_img_view.setBackgroundDrawable(new BitmapDrawable(UtilFunctions.getDefaultAlbumArt(context)));
			}
			layoutbelow.setVisibility(View.VISIBLE);

			//net code
//			long albumId = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbumId();
//			Bitmap albumArt = UtilFunctions.getAlbumart(context, albumId);
//			if(albumArt != null){
//				linearLayoutPlayer.setBackgroundDrawable(new BitmapDrawable(albumArt));
//			}else{
//				linearLayoutPlayer.setBackgroundDrawable(new BitmapDrawable(UtilFunctions.getDefaultAlbumArt(context)));
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
