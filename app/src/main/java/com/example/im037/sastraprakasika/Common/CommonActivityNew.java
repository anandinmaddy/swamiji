package com.example.im037.sastraprakasika.Common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im037.sastraprakasika.Activity.MyLibraryActivity;
import com.example.im037.sastraprakasika.OnlinePlayer.Constant;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemMyPlayList;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;
import com.example.im037.sastraprakasika.OnlinePlayer.Methods;
import com.example.im037.sastraprakasika.OnlinePlayer.PlayerService;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.mediautil.MediaItem;
import com.example.im037.sastraprakasika.mediautil.PlayerConstants;
import com.example.im037.sastraprakasika.utils.DBHelper;
import com.example.im037.sastraprakasika.utils.GlobalBus;
import com.example.im037.sastraprakasika.utils.MessageEvent;
import com.example.im037.sastraprakasika.utils.PausableRotateAnimation;
import com.example.im037.sastraprakasika.utils.Selected;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.labo.kaji.relativepopupwindow.RelativePopupWindow;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class CommonActivityNew extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "CommonActivity";
    private FrameLayout frameLayout;
    private ActionBarDrawerToggle toggle;
    private ImageView back, notification;
    private AVLoadingIndicatorView commonProgressBar;
    private SlidingUpPanelLayout playerLayout;

    SlidingUpPanelLayout sliding_layout;
    private TextView title, songtitle;
    TextView time;
    //LinearLayout discourses, myLibrary, search, myAccount;
    ImageView discoursesImg, myLibraryImg, searchImg, myAccountImg;
    boolean doubleBackToExitPressedOnce = false;
    Selected select;
    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private final Handler handler = new Handler();
    String url = "https://imaginetventures.name/wp-content/uploads/2018/07/01-Purushartha.mp3";

    Methods methods;
    DBHelper dbHelper;
    AudioManager am;
    RelativeLayout rl_min_header;
    LinearLayout ll_max_header;
    RatingBar ratingBar;
    SeekBar seekBar_music, seekbar_min;
    View view_playlist, view_download, view_rate, view_round;
    TextView tv_min_title, tv_min_artist, tv_max_title, tv_max_artist, tv_music_title, tv_music_artist, tv_song_count,
            tv_current_time, tv_total_time;
    RoundedImageView iv_max_song, iv_min_song, imageView_pager;
    ImageView iv_music_bg, iv_min_previous, iv_min_play, iv_min_next, iv_max_fav, iv_max_option, iv_music_shuffle,
            iv_music_downloads, iv_music_previous, iv_music_next, iv_music_play,iv_music_addplaylist,imageView_heart;

    RelativeLayout rl_music_loading;
    public ViewPager viewpager;
    BottomSheetDialog dialog_desc;
    private Handler seekHandler = new Handler();
    Boolean isExpand = false, isRotateAnim = false;
    PausableRotateAnimation rotateAnimation;
    ImagePagerAdapter adapter;
    RelativeLayout include_sliding_panel_childtwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_new);
        mediaPlayer = new MediaPlayer();
        back = findViewById(R.id.back);
        include_sliding_panel_childtwo = findViewById( R.id.include_sliding_panel_childtwo);
        commonProgressBar = (AVLoadingIndicatorView) findViewById(R.id.commonProgressBar);
        seekbar_min = (SeekBar) findViewById(R.id.seekbar_min);
        songtitle = (TextView) findViewById(R.id.tv_min_title);

        Constant.isAppOpen = true;
        //checkSlidingPanelLayout(false);
        viewpager = findViewById(R.id.viewPager_song);
        rl_min_header = findViewById(R.id.rl_min_header);
        ll_max_header = findViewById(R.id.ll_max_header);
        rl_music_loading = findViewById(R.id.rl_music_loading);
        ratingBar = findViewById(R.id.rb_music);
        seekBar_music = findViewById(R.id.seekbar_music);
        seekbar_min = findViewById(R.id.seekbar_min);
        seekbar_min.setPadding(0, 0, 0, 0);
        iv_min_song = (RoundedImageView) findViewById(R.id.iv_min_song);
        time = findViewById(R.id.time);
//        myAccount = findViewById(R.id.accountLayout);
//        discourses = findViewById(R.id.discoursesLayout);
//        myLibrary = findViewById(R.id.myLibraryLayout);
        rl_min_header = findViewById(R.id.rl_min_header);
        ll_max_header = findViewById(R.id.ll_max_header);
       // search = findViewById(R.id.searchLayout);
        myAccountImg = findViewById(R.id.account);
        discoursesImg = findViewById(R.id.discourses);
        myLibraryImg = findViewById(R.id.myLibrary);
        searchImg = findViewById(R.id.search);
        seekBar_music = findViewById(R.id.seekbar_music);
        iv_music_addplaylist = findViewById( R.id.iv_music_add2playlist );
        seekbar_min = findViewById(R.id.seekbar_min);
        seekbar_min.setPadding(0, 0, 0, 0);

        methods = new Methods(this);
        dbHelper = new DBHelper(this);



        iv_music_bg = findViewById(R.id.iv_music_bg);
        iv_music_play = findViewById(R.id.iv_music_play);
        iv_music_next = findViewById(R.id.iv_music_next);
        iv_music_previous = findViewById(R.id.iv_music_previous);
        //add me
        iv_music_shuffle = findViewById(R.id.iv_music_add2playlist);
        iv_music_downloads = findViewById(R.id.iv_music_downloads);
        view_round = findViewById(R.id.vBgLike);

        iv_min_song = findViewById(R.id.iv_min_song);
        iv_max_song = findViewById(R.id.iv_max_song);
        iv_min_previous = findViewById(R.id.iv_min_previous);
        iv_min_play = findViewById(R.id.iv_min_play);
        iv_min_next = findViewById(R.id.iv_min_next);
        iv_max_fav = findViewById(R.id.iv_max_fav);
        iv_max_option = findViewById(R.id.iv_max_option);
        imageView_heart = findViewById(R.id.ivLike);

        tv_current_time = findViewById(R.id.tv_music_time);
        tv_total_time = findViewById(R.id.tv_music_total_time);
        tv_song_count = findViewById(R.id.tv_music_song_count);
        tv_music_title = findViewById(R.id.tv_music_title);
        tv_music_artist = findViewById(R.id.tv_music_artist);
        tv_min_title = findViewById(R.id.tv_min_title);
        tv_min_artist = findViewById(R.id.tv_min_artist);
        tv_max_title = findViewById(R.id.tv_max_title);
        tv_max_artist = findViewById(R.id.tv_max_artist);
        sliding_layout = findViewById(R.id.sliding_layout);

        iv_max_option.setColorFilter(Color.BLACK);

        iv_max_fav.setOnClickListener(this);
        iv_max_option.setOnClickListener(this);

        iv_min_play.setOnClickListener(this);
        iv_min_next.setOnClickListener(this);
        iv_min_previous.setOnClickListener(this);

        iv_music_play.setOnClickListener(this);
        iv_music_next.setOnClickListener(this);
        iv_music_previous.setOnClickListener(this);
        iv_music_shuffle.setOnClickListener(this);
        iv_music_downloads.setOnClickListener(this);
        sliding_layout.setOnClickListener(this);







        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        iv_music_downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommonActivityNew.this,MyLibraryActivity.class );
                intent.putExtra( "from","player" );
                startActivity( intent );
            }
        });

        iv_music_addplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommonActivityNew.this,MyLibraryActivity.class );
                intent.putExtra( "from","playlist" );
                startActivity( intent );
            }
        });




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        seekBar_music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                try {
                    Intent intent = new Intent(CommonActivityNew.this, PlayerService.class);
                    intent.setAction(PlayerService.ACTION_SEEKTO);
                    intent.putExtra("seekto", methods.getSeekFromPercentage(progress, methods.calculateTime(Constant.arrayList_play.get(Constant.playPos).getDuration())));
                    startService(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




        //add me new
        sliding_layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset == 0.0f) {
                    isExpand = false;

                    rl_min_header.setVisibility(View.VISIBLE);
                    ll_max_header.setVisibility(View.INVISIBLE);
                } else if (slideOffset > 0.0f && slideOffset < 1.0f) {
                    rl_min_header.setVisibility(View.VISIBLE);
                    ll_max_header.setVisibility(View.VISIBLE);

                    if (isExpand) {
                        rl_min_header.setAlpha(1.0f - slideOffset);
                        ll_max_header.setAlpha(0.0f + slideOffset);
                    } else {
                        rl_min_header.setAlpha(1.0f - slideOffset);
                        ll_max_header.setAlpha(slideOffset);
                    }
                } else {
                    isExpand = true;

                    rl_min_header.setVisibility(View.INVISIBLE);
                    ll_max_header.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });


//        myLibrary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CommonMethod.changeActivity(CommonActivity.this, MyLibraryActivity.class);
//            }
//        });
//
//        discourses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CommonMethod.changeActivity(CommonActivity.this, DashBoardActivity.class);
//            }
//        });
//
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CommonMethod.changeActivity(CommonActivity.this, SearchActivity.class);
//            }
//        });
//
//        myAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CommonMethod.changeActivity(CommonActivity.this, MyAccountActivity.class);
//            }
//        });


    }

    public void checkSlidingPanelLayout(boolean visibile) {
        if(visibile){
            include_sliding_panel_childtwo.setVisibility(View.VISIBLE);
        }else{
            include_sliding_panel_childtwo.setVisibility(View.GONE);

        }

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        seekHandler.removeCallbacks(run);
        super.onPause();
    }

    public void setView(int viewLayout, String title) {
        this.title = findViewById(R.id.title);
        this.title.setText(title);

        frameLayout = (FrameLayout) findViewById(R.id.commonActivityFrameLayout);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(viewLayout, null, false);
        frameLayout.addView(activityView);

    }

    public void setCommonProgressBar(int frameVisible) {
        commonProgressBar.setVisibility(View.VISIBLE);
        commonProgressBar.show();
        frameLayout.setVisibility(frameVisible);
    }

    public void hideCommonProgressBar() {
        commonProgressBar.setVisibility(View.GONE);
        commonProgressBar.hide();
        frameLayout.setVisibility(View.VISIBLE);
    }





    public void setSelected(Selected select) {
        this.select = select;
        int textColor;
        switch (select) {
            case MYLIBRARY:
                myLibraryImg.setImageResource(R.drawable.mylibrary_orange);
                break;
            case DISCOURSES:
                discoursesImg.setImageResource(R.drawable.discourse_orange);
                break;
            case SEARCH:
                searchImg.setImageResource(R.drawable.search_orange);
                break;
            case MYACCOUNT:
                myAccountImg.setImageResource(R.drawable.account_orange);
                break;

        }
    }






    private void updateUI() {
        MediaItem data = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
        songtitle.setText(data.getTitle());
        Picasso.get()
                .load(data.getAlbum_img())
                .placeholder(R.drawable.ic_music)
                .into(iv_min_song);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_min_play:
                playPause();
                break;
            case R.id.iv_music_play:
                playPause();
                break;
            case R.id.iv_min_next:
                next();
                break;
            case R.id.iv_music_next:
                next();
                break;
            case R.id.iv_min_previous:
                previous();
                break;
            case R.id.iv_music_previous:
                previous();
                break;
            case R.id.iv_music_add2playlist:
                setShuffle();
                break;
            case R.id.iv_music_downloads:
                setRepeat();
                break;
            case R.id.iv_max_option:
                if (Constant.arrayList_play.size() > 0) {
                    showBottomSheetDialog();
                }
                break;
            case R.id.iv_max_fav:
                if (Constant.arrayList_play.size() > 0) {
                    if (Constant.isOnline) {
                        methods.animateHeartButton(view);
//                        methods.animatePhotoLike(view_round, imageView_heart);
                        view.setSelected(!view.isSelected());
                        findViewById(R.id.ivLike).setSelected(view.isSelected());
                        fav();
                    }
                } else {
                    Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
                }
                break;

            /*case R.id.iv_music_add2playlist:
                if (Constant.arrayList_play.size() > 0) {
                    methods.openPlaylists(Constant.arrayList_play.get(viewpager.getCurrentItem()), Constant.isOnline);
                } else {
                    Toast.makeText(BaseActivity.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_music_download:
                if (checkPer()) {
                    download();
                } else {
                    checkPer();
                }
                break;
            case R.id.iv_music_rate:
                if (Constant.arrayList_play.size() > 0) {
                    openRateDialog();
                }
                break;
            case R.id.iv_music_volume:
                changeVolume();
                break;*/
        }

    }

    public void showBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.layout_desc, null);

        dialog_desc = new BottomSheetDialog(this);
        dialog_desc.setContentView(view);
        dialog_desc.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        dialog_desc.show();

        AppCompatButton button = dialog_desc.findViewById(R.id.button_detail_close);
        TextView textView = dialog_desc.findViewById(R.id.tv_desc_title);
        textView.setText(Constant.arrayList_play.get(Constant.playPos).getTitle());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_desc.dismiss();
            }
        });

        WebView webview_song_desc = dialog_desc.findViewById(R.id.webView_bottom);
        String mimeType = "text/html;charset=UTF-8";
        String encoding = "utf-8";
        String text = "<html><head>"
                + "<style> body{color: #000 !important;text-align:left}"
                + "</style></head>"
                + "<body>"
                + Constant.arrayList_play.get(Constant.playPos).getDescription()
                + "</body></html>";

        webview_song_desc.loadData(text, mimeType, encoding);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    public void seekUpdation() {
        try {
            seekbar_min.setProgress(methods.getProgressPercentage(PlayerService.exoPlayer.getCurrentPosition(), methods.calculateTime(Constant.arrayList_play.get(Constant.playPos).getDuration())));
            seekBar_music.setProgress(methods.getProgressPercentage(PlayerService.exoPlayer.getCurrentPosition(), methods.calculateTime(Constant.arrayList_play.get(Constant.playPos).getDuration())));
            tv_current_time.setText(methods.milliSecondsToTimer(PlayerService.exoPlayer.getCurrentPosition()));
            seekBar_music.setSecondaryProgress(PlayerService.exoPlayer.getBufferedPercentage());
            if (PlayerService.exoPlayer.getPlayWhenReady() && Constant.isAppOpen) {
                seekHandler.removeCallbacks(run);
                seekHandler.postDelayed(run, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playPause() {
        if (Constant.arrayList_play.size() > 0) {
            //checkSlidingPanelLayout(true);
            Intent intent = new Intent(CommonActivityNew.this, PlayerService.class);
            if (Constant.isPlayed) {
                intent.setAction(PlayerService.ACTION_TOGGLE);
                startService(intent);
            } else {
                if (!Constant.isOnline || methods.isNetworkAvailable()) {
                    intent.setAction(PlayerService.ACTION_PLAY);
                    startService(intent);
                } else {
                    Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void next() {
        if (Constant.arrayList_play.size() > 0) {
            if (!Constant.isOnline || methods.isNetworkAvailable()) {
                isRotateAnim = false;
                Intent intent = new Intent(CommonActivityNew.this, PlayerService.class);
                intent.setAction(PlayerService.ACTION_NEXT);
                startService(intent);
            } else {
                Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void previous() {
        if (Constant.arrayList_play.size() > 0) {
            if (!Constant.isOnline || methods.isNetworkAvailable()) {
                isRotateAnim = false;
                Intent intent = new Intent(CommonActivityNew.this, PlayerService.class);
                intent.setAction(PlayerService.ACTION_PREVIOUS);
                startService(intent);
            } else {
                Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void setRepeat() {
        if (Constant.isRepeat) {
            Constant.isRepeat = false;
            iv_music_downloads.setImageDrawable(getResources().getDrawable(R.drawable.download_new));
        } else {
            Constant.isRepeat = true;
            iv_music_downloads.setImageDrawable(getResources().getDrawable(R.drawable.download_new));
        }
    }

    public void setShuffle() {
        if (Constant.isSuffle) {
            Constant.isSuffle = false;
            iv_music_shuffle.setColorFilter(ContextCompat.getColor(CommonActivityNew.this, R.color.grey));
        } else {
            Constant.isSuffle = true;
            iv_music_shuffle.setColorFilter(ContextCompat.getColor(CommonActivityNew.this, R.color.grey));
        }
    }

    public Boolean checkPer() {
        if ((ContextCompat.checkSelfPermission(CommonActivityNew.this, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE"}, 1);
            }
            return false;
        } else {
            return true;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSongChange(ItemSong itemSong) {
        changeText(itemSong, "home");
        Constant.context = CommonActivityNew.this;
        changeImageAnimation(PlayerService.getInstance().getIsPlayling());
//        GlobalBus.getBus().removeStickyEvent(itemSong);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onBufferChange(MessageEvent messageEvent) {

        if (messageEvent.message.equals("buffer")) {
            isBuffering(messageEvent.flag);
        } else {
            changePlayPauseIcon(messageEvent.flag);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onViewPagerChanged(ItemMyPlayList itemMyPlayList) {
        adapter.notifyDataSetChanged();
        GlobalBus.getBus().removeStickyEvent(itemMyPlayList);
    }

    @Override
    public void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        GlobalBus.getBus().unregister(this);
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        boolean canUseExternalStorage = false;

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    canUseExternalStorage = true;
                }

                if (!canUseExternalStorage) {
                    Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.err_cannot_use_features), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void shareSong() {
        if (Constant.arrayList_play.size() > 0) {

            if (Constant.isOnline) {
                Intent sharingIntent = new Intent( Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra( Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_song));
                sharingIntent.putExtra( Intent.EXTRA_TEXT, getResources().getString(R.string.listening) + " - " + Constant.arrayList_play.get(viewpager.getCurrentItem()).getTitle() + "\n\nvia " + getResources().getString(R.string.app_name) + " - http://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_song)));
            } else {
                if (checkPer()) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("audio/mp3");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse(Constant.arrayList_play.get(viewpager.getCurrentItem()).getUrl()));
                    share.putExtra( Intent.EXTRA_TEXT, getResources().getString(R.string.listening) + " - " + Constant.arrayList_play.get(viewpager.getCurrentItem()).getTitle() + "\n\nvia " + getResources().getString(R.string.app_name) + " - http://play.google.com/store/apps/details?id=" + getPackageName());
                    startActivity(Intent.createChooser(share, getResources().getString(R.string.share_song)));
                }
            }
        } else {
            Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }

    public void fav() {
        if (dbHelper.checkFav(Constant.arrayList_play.get(Constant.playPos).getId())) {
            dbHelper.removeFromFav(Constant.arrayList_play.get(Constant.playPos).getId());
            Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.removed_fav), Toast.LENGTH_SHORT).show();
            changeFav(false);
        } else {
            dbHelper.addToFav(Constant.arrayList_play.get(Constant.playPos));
            Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.added_fav), Toast.LENGTH_SHORT).show();
            changeFav(true);
        }
    }

    public void changeFav(Boolean isFav) {
        if (isFav) {
            //iv_max_fav.setImageDrawable(getResources().getDrawable(R.mipmap.ic_fav_hover));
        } else {
            //iv_max_fav.setImageDrawable(getResources().getDrawable(R.mipmap.ic_fav));
        }
    }

    private void openRateDialog() {
        /*dialog_rate = new Dialog(CommonActivity.this);
        dialog_rate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_rate.setContentView(R.layout.layout_review);

        final ImageView iv_close = dialog_rate.findViewById(R.id.iv_rate_close);
        final TextView textView = dialog_rate.findViewById(R.id.tv_rate);
        final RatingBar ratingBar = dialog_rate.findViewById(R.id.rb_add);
        final Button button = dialog_rate.findViewById(R.id.button_submit_rating);
        final Button button_later = dialog_rate.findViewById(R.id.button_later_rating);

        ratingBar.setStepSize(Float.parseFloat("1"));

        if (Constant.arrayList_play.get(viewpager.getCurrentItem()).getUserRating().equals("")) {
            new GetRating(new RatingListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onEnd(String success, String message, int rating) {
                    if (rating > 0) {
                        ratingBar.setRating(rating);
                        textView.setText(getString(R.string.thanks_for_rating));
                    } else {
                        ratingBar.setRating(1);
                    }
                    Constant.arrayList_play.get(viewpager.getCurrentItem()).setUserRating(String.valueOf(rating));

                }
            }).execute(Constant.URL_SONG_1 + Constant.arrayList_play.get(viewpager.getCurrentItem()).getId() + Constant.URL_SONG_2 + deviceId);
        } else {
            if (Integer.parseInt(Constant.arrayList_play.get(viewpager.getCurrentItem()).getUserRating()) != 0 && !Constant.arrayList_play.get(viewpager.getCurrentItem()).getUserRating().equals("")) {
                textView.setText(getString(R.string.thanks_for_rating));
                ratingBar.setRating(Integer.parseInt(Constant.arrayList_play.get(viewpager.getCurrentItem()).getUserRating()));
            } else {
                ratingBar.setRating(1);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBar.getRating() != 0) {
                    if (methods.isNetworkAvailable()) {
                        loadRatingApi(String.valueOf((int) ratingBar.getRating()));
                    } else {
                        Toast.makeText(CommonActivity.this, getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CommonActivity.this, getString(R.string.select_rating), Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_rate.dismiss();
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_rate.dismiss();
            }
        });

        dialog_rate.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog_rate.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog_rate.show();
        Window window = dialog_rate.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        */
    }

/*    private void loadRatingApi(final String rate) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CommonActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        LoadRating loadRating = new LoadRating(new RatingListener() {
            @Override
            public void onStart() {
                progressDialog.show();
            }

            @Override
            public void onEnd(String success, String message, int rating) {

                if (success.equals("true")) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(CommonActivity.this, message, Toast.LENGTH_SHORT).show();

                    if (!message.contains("already")) {
                        Constant.arrayList_play.get(viewpager.getCurrentItem()).setAverageRating(String.valueOf(rating));
                        Constant.arrayList_play.get(viewpager.getCurrentItem()).setTotalRate(String.valueOf(Integer.parseInt(Constant.arrayList_play.get(viewpager.getCurrentItem()).getTotalRate() + 1)));
                        Constant.arrayList_play.get(viewpager.getCurrentItem()).setUserRating(String.valueOf(rate));
                        ratingBar.setRating(rating);
//                        changeRating();
                    }
                } else {
                    Toast.makeText(CommonActivity.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
                dialog_rate.dismiss();
            }
        });
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        loadRating.execute(Constant.URL_RATING_1 + Constant.arrayList_play.get(viewpager.getCurrentItem()).getId() + Constant.URL_RATING_2 + deviceId + Constant.URL_RATING_3 + rate);
    }*/

    private void changeVolume() {

        final RelativePopupWindow popupWindow = new RelativePopupWindow(this);

        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.layout_dailog_volume, null);
        ImageView imageView1 = view.findViewById(R.id.iv1);
        ImageView imageView2 = view.findViewById(R.id.iv2);
        imageView1.setColorFilter(Color.BLACK);
        imageView2.setColorFilter(Color.BLACK);

        VerticalSeekBar seekBar = view.findViewById(R.id.seekbar_volume);
        seekBar.getThumb().setColorFilter(ContextCompat.getColor(CommonActivityNew.this, R.color.orange), PorterDuff.Mode.SRC_IN);
        seekBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(CommonActivityNew.this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        final AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        seekBar.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        int volume_level = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar.setProgress(volume_level);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                am.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));
        popupWindow.setContentView(view);
        //popupWindow.showOnAnchor(iv_music_volume, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.CENTER);
    }

   /* private void download() {
        if (Constant.arrayList_play.size() > 0) {

            File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getString(R.string.app_name));
            if (!root.exists()) {
                root.mkdirs();
            }

            File file = new File(root, Constant.arrayList_play.get(viewpager.getCurrentItem()).getTitle() + ".mp3");

            if (!file.exists()) {

                String url = Constant.arrayList_play.get(viewpager.getCurrentItem()).getUrl();

                if (!DownloadService.getInstance().isDownloading()) {
                    Intent serviceIntent = new Intent(CommonActivity.this, DownloadService.class);
                    serviceIntent.setAction(DownloadService.ACTION_START);
                    serviceIntent.putExtra("downloadUrl", url);
                    serviceIntent.putExtra("file_path", root.toString());
                    serviceIntent.putExtra("file_name", file.getName());
                    startService(serviceIntent);
                } else {
                    Intent serviceIntent = new Intent(CommonActivity.this, DownloadService.class);
                    serviceIntent.setAction(DownloadService.ACTION_ADD);
                    serviceIntent.putExtra("downloadUrl", url);
                    serviceIntent.putExtra("file_path", root.toString());
                    serviceIntent.putExtra("file_name", file.getName());
                    startService(serviceIntent);
                }

                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        String json = JsonUtils.okhttpGET(Constant.URL_DOWNLOAD_COUNT + Constant.arrayList_play.get(viewpager.getCurrentItem()).getId());
                        return null;
                    }
                }.execute();
            } else {
                Toast.makeText(CommonActivity.this, getResources().getString(R.string.already_download), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CommonActivity.this, getResources().getString(R.string.err_no_songs_selected), Toast.LENGTH_SHORT).show();
        }
    }*/

    public void newRotateAnim() {
        if (rotateAnimation != null) {
            rotateAnimation.cancel();
        }
        rotateAnimation = new PausableRotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(Constant.rotateSpeed);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());
    }

    public void changeImageAnimation(Boolean isPlay) {
        try {
            if (!isPlay) {
                rotateAnimation.pause();
            } else {
                if (!isRotateAnim) {
                    isRotateAnim = true;
                    if (imageView_pager != null) {

                        // imageView_pager.setAnimation(null);

                    }
                    View view_pager = viewpager.findViewWithTag("myview" + Constant.playPos);
                    newRotateAnim();
                    imageView_pager = view_pager.findViewById(R.id.image);
                    imageView_pager.startAnimation(rotateAnimation);
                } else {
                    rotateAnimation.resume();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeTextPager(ItemSong itemSong) {
        ratingBar.setRating(Integer.parseInt(itemSong.getAverageRating()));

        tv_music_artist.setText(itemSong.getArtist());
        tv_music_title.setText(itemSong.getTitle());
        tv_song_count.setText((viewpager.getCurrentItem() + 1) + "/" + Constant.arrayList_play.size());
    }

    public void changeText(final ItemSong itemSong, final String page) {

        tv_min_title.setText(itemSong.getTitle());
        tv_min_artist.setText(itemSong.getArtist());

        tv_max_title.setText(itemSong.getTitle());
        tv_max_artist.setText(itemSong.getArtist());

        ratingBar.setRating(Integer.parseInt(itemSong.getAverageRating()));
        tv_music_title.setText(itemSong.getTitle());
        tv_music_artist.setText(itemSong.getArtist());

        tv_song_count.setText(Constant.playPos + 1 + "/" + Constant.arrayList_play.size());
        tv_total_time.setText(itemSong.getDuration());

        changeFav(dbHelper.checkFav(itemSong.getId()));

        if (Constant.isOnline) {
            Picasso.get()
                    .load(itemSong.getImageSmall())
                    .placeholder(R.drawable.ic_music)
                    .into(iv_min_song);

            Picasso.get()
                    .load(itemSong.getImageSmall())
                    .placeholder(R.drawable.ic_music)
                    .into(iv_max_song);


            if (ratingBar.getVisibility() == View.GONE) {
                ratingBar.setVisibility(View.VISIBLE);
                iv_max_fav.setVisibility(View.VISIBLE);

                //iv_music_rate.setVisibility(View.VISIBLE);
                view_rate.setVisibility(View.VISIBLE);
            }

            if (Constant.isSongDownload) {
                //iv_music_download.setVisibility(View.VISIBLE);
                view_download.setVisibility(View.VISIBLE);
            } else {
                //iv_music_download.setVisibility(View.GONE);
                view_download.setVisibility(View.GONE);
            }
        } else {
            Picasso.get()
                    .load((itemSong.getImageSmall()))
                    .placeholder(R.drawable.ic_music)
                    .into(iv_min_song);

            Picasso.get()
                    .load((itemSong.getImageSmall()))
                    .placeholder(R.drawable.ic_music)
                    .into(iv_max_song);

            if (ratingBar.getVisibility() == View.VISIBLE) {
                ratingBar.setVisibility(View.GONE);
                iv_max_fav.setVisibility(View.GONE);

                //iv_music_rate.setVisibility(View.GONE);
                view_rate.setVisibility(View.GONE);

                //iv_music_download.setVisibility(View.GONE);
                view_download.setVisibility(View.GONE);
            }
        }

        if (!page.equals("")) {
            viewpager.setAdapter(adapter);
            viewpager.setOffscreenPageLimit(Constant.arrayList_play.size());
        }
        viewpager.setCurrentItem(Constant.playPos);
    }

    public void changePlayPauseIcon(Boolean isPlay) {
        if (!isPlay) {
            iv_music_play.setImageDrawable(getResources().getDrawable(R.drawable.play_orange));
            iv_min_play.setImageDrawable(getResources().getDrawable(R.drawable.play_orange));
        } else {
            iv_music_play.setImageDrawable(getResources().getDrawable(R.drawable.pause_orange_icon));
            iv_min_play.setImageDrawable(getResources().getDrawable(R.drawable.pause_orange_icon));
        }
        seekUpdation();
    }

    public void isBuffering(Boolean isBuffer) {
        Constant.isPlaying = !isBuffer;
        if (isBuffer) {
            rl_music_loading.setVisibility(View.VISIBLE);
            iv_music_play.setVisibility(View.INVISIBLE);
        } else {
            rl_music_loading.setVisibility(View.INVISIBLE);
            iv_music_play.setVisibility(View.VISIBLE);
            changePlayPauseIcon(!isBuffer);
            //seekUpdation();
        }
        iv_music_next.setEnabled(!isBuffer);
        iv_music_previous.setEnabled(!isBuffer);
        iv_min_next.setEnabled(!isBuffer);
        iv_min_previous.setEnabled(!isBuffer);
        // iv_music_download.setEnabled(!isBuffer);
        iv_min_play.setEnabled(!isBuffer);
        seekBar_music.setEnabled(!isBuffer);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        private ImagePagerAdapter() {
            inflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return Constant.arrayList_play.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View imageLayout = inflater.inflate(R.layout.layout_viewpager, container, false);
            assert imageLayout != null;
            RoundedImageView imageView = imageLayout.findViewById(R.id.image);
            final ImageView imageView_play = imageLayout.findViewById(R.id.iv_vp_play);
            final ProgressBar spinner = imageLayout.findViewById(R.id.loading);

            if (Constant.playPos == position) {
                imageView_play.setVisibility(View.GONE);
            }

            if (Constant.isOnline) {
                Picasso.get()
                        .load(Constant.arrayList_play.get(position).getImageBig())
                        .resize(300, 300)
                        .placeholder(R.drawable.ic_music)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                spinner.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
                                spinner.setVisibility(View.GONE);
                            }
                        });
            } else {
                Picasso.get()
                        .load(methods.getAlbumArtUri(Integer.parseInt(Constant.arrayList_play.get(position).getImageBig())))
                        .placeholder(R.drawable.ic_music)
                        .into(imageView);

                spinner.setVisibility(View.GONE);
            }

            imageView_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.playPos = viewpager.getCurrentItem();
                    isRotateAnim = false;
                    if (!Constant.isOnline || methods.isNetworkAvailable()) {
                        Intent intent = new Intent(CommonActivityNew.this, PlayerService.class);
                        intent.setAction(PlayerService.ACTION_PLAY);
                        startService(intent);
                        imageView_play.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(CommonActivityNew.this, getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if (position == 0) {
                isRotateAnim = false;
                imageView_pager = imageView;
            }

            imageLayout.setTag("myview" + position);
            container.addView(imageLayout, 0);
            return imageLayout;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
