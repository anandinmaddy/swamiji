package com.example.im037.sastraprakasika.MusicPlayer;


public class PlaylistItem {

    public String videoId;
    public String youtubeId;
    public String title;
    public String uploader;

    public PlaylistItem(String videoId, String youtubeId, String title, String uploader) {
        this.videoId = videoId;
        this.youtubeId = youtubeId;
        this.title = title;
        this.uploader = uploader;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
}
