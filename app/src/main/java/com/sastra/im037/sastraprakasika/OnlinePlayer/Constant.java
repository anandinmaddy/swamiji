package com.sastra.im037.sastraprakasika.OnlinePlayer;

import android.content.Context;

import com.sastra.im037.sastraprakasika.Model.ListOfTopicsDetailed;
import com.sastra.im037.sastraprakasika.Model.PlayList;
import com.sastra.im037.sastraprakasika.Model.VolumeDetailsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Constant {

    public static final String LAST_SYNC = "LAST_SYNC";
    private static final long serialVersionUID = 1L;
    public static String isFromPage = "lecture";
    public static int lastPlayed = 0;
    public static boolean backPress = false;
    public static boolean isplayTopics = false;
    public static boolean isplayLectures = false;
    public static boolean isplayDownloads = false;
    public static String isfromPlayer = "";
    public static boolean isplayPlaylist = false;
    public static boolean isplaySearch = false;
    public static boolean refreshLecture = false;
    public static boolean downloadCompleted = false;
    public static boolean isFirstTime = false;
    public static ArrayList<ItemSong> wholeMediaList = new ArrayList<>();
    public static int downloadCount = 0;
    public static boolean refreshed = false;
    public static int purchaseKey = 0;
    public static ArrayList<VolumeDetailsModel> arrayListPurchase;

    private static String SERVER_URL = "";

    public static final String URL_HOME = SERVER_URL + "api.php?home";
    public static final String URL_LATEST = SERVER_URL + "api.php?latest&page=";
    public static final String URL_ARTIST = SERVER_URL + "api.php?artist_list&page=";
    public static final String URL_ALBUMS = SERVER_URL + "api.php?album_list&page=";
    public static final String URL_PLAYLIST = SERVER_URL + "api.php?playlist&page=";
    public static final String URL_CAT = SERVER_URL + "api.php?cat_list&page=";
    public static final String URL_DOWNLOAD_COUNT = SERVER_URL + "api.php?song_download_id=";
    public static final String URL_ALL_SONG = SERVER_URL + "api.php?all_songs&page=";
    public static final String URL_SONG_BY_CAT = SERVER_URL + "api.php?cat_id=";
    public static final String URL_SONG_BY_ARTIST = SERVER_URL + "api.php?artist_name=";
    public static final String URL_SONG_BY_ALBUMS = SERVER_URL + "api.php?album_id=";
    public static final String URL_SONG_BY_PLAYLIST = SERVER_URL + "api.php?playlist_id=";
    public static final String URL_SONG_1 = SERVER_URL + "api.php?mp3_id=";
    public static final String URL_SONG_2 = "&device_id=";
    public static final String URL_SEARCH = SERVER_URL + "api.php?search_text=";
    public static final String URL_RATING_1 = SERVER_URL + "api_rating.php?post_id=";
    public static final String URL_RATING_2 = "&device_id=";
    public static final String URL_RATING_3 = "&rate=";

    public static final String URL_APP_DETAILS = SERVER_URL + "api.php";
    public static final String URL_ABOUT_US_LOGO = SERVER_URL + "images/";

    public static final String TAG_ROOT = "ONLINE_MP3";
    public static final String TAG_ROOT_1 = "EBOOK_APP";

    public static final String TAG_ID = "id";
    public static final String TAG_CAT_ID = "cat_id";
    public static final String TAG_CAT_NAME = "category_name";
    public static final String TAG_CAT_IMAGE = "category_image";
    public static final String TAG_MP3_URL = "mp3_url";
    public static final String TAG_DURATION = "mp3_duration";
    public static final String TAG_SONG_NAME = "mp3_title";
    public static final String TAG_DESC = "mp3_description";
    public static final String TAG_THUMB_B = "mp3_thumbnail_b";
    public static final String TAG_THUMB_S = "mp3_thumbnail_s";
    public static final String TAG_ARTIST = "mp3_artist";
    public static final String TAG_TOTAL_RATE = "total_rate";
    public static final String TAG_AVG_RATE = "rate_avg";
    public static final String TAG_USER_RATE = "user_rate";
    public static final String TAG_VIEWS = "total_views";
    public static final String TAG_DOWNLOADS = "total_download";

    public static final String TAG_CID = "cid";
    public static final String TAG_AID = "aid";
    public static final String TAG_PID = "pid";
    public static final String TAG_BID = "bid";

    public static final String TAG_BANNER_TITLE = "banner_title";
    public static final String TAG_BANNER_DESC = "banner_sort_info";
    public static final String TAG_BANNER_IMAGE = "banner_image";
    public static final String TAG_BANNER_TOTAL = "total_songs";

    public static final String TAG_ARTIST_NAME = "artist_name";
    public static final String TAG_ARTIST_IMAGE = "artist_image";
    public static final String TAG_ARTIST_THUMB = "artist_image_thumb";

    public static final String TAG_ALBUM_NAME = "album_name";
    public static final String TAG_ALBUM_IMAGE = "album_image";
    public static final String TAG_ALBUM_THUMB = "album_image_thumb";

    public static final String TAG_PLAYLIST_NAME = "playlist_name";
    public static final String TAG_PLAYLIST_IMAGE = "playlist_image";
    public static final String TAG_PLAYLIST_THUMB = "playlist_image_thumb";

    //public static ItemAbout itemAbout;

    public static int playPos = 0;
    public static ArrayList<ItemSong> arrayList_play = new ArrayList<>();
    public static ArrayList<ItemSong> onlyOffline = new ArrayList<>();
    public static ArrayList<ItemSong> playListSongSync = new ArrayList<>();

    public static ArrayList<ItemSong> arrayListOfflineSongs = new ArrayList<>();
    public static ArrayList<ItemSong> playListSongsList = new ArrayList<>();
    public static ArrayList<ItemSong> removedSOngsList = new ArrayList<>();

    public static ArrayList<Integer> trackList = new ArrayList<>();

    public static ArrayList<ItemSong> playListSongs1 = new ArrayList<>();
    public static Map<String, ArrayList<ItemSong>> playListMap = new <String, ArrayList<ItemSong>>HashMap();

    public static String titleName = "";
    public static int downloadPosition = 0;
    public static ArrayList<ItemSong> arrayListLectureslineSongs = new ArrayList<>();

    public static ArrayList<ListOfTopicsDetailed> arrayTopiclineSongs = new ArrayList<>();
    public static ArrayList<ListOfTopicsDetailed> arrayOfflineTopiclineSongs = new ArrayList<>();
    public static int currentTab = 2;
    public static ArrayList<PlayList> playListArray = new ArrayList<>();

    public static int lastPosition = 0;
    //public static ArrayList<ItemAlbums> arrayListOfflineAlbums = new ArrayList<>();
    //public static ArrayList<ItemArtist> arrayListOfflineArtist = new ArrayList<>();

    public static Boolean isRepeat = false, isSuffle = false, isPlaying = false,
            isPlayed = false, isFromNoti = false, isFromPush = false, isAppOpen = false, isOnline = true, isBannerAd = true,
            isInterAd = true, isSongDownload = false;
    public static Context context;
    public static int volume = 25;
    public static String pushSID = "0", pushCID = "0", pushCName = "", pushArtID = "0", pushArtNAME = "", pushAlbID = "0", pushAlbNAME = "", search_item = "";

    public static int rotateSpeed = 25000; //in milli seconds

    public static int bannerAdShowTime = 7000; //in milli seconds

    public static int adCount = 0;
    public static int adDisplay = 5;

    public static String ad_publisher_id = "";
    public static String ad_banner_id = "";
    public static String ad_inter_id = "";
    public static String jsonReturn = "";


}