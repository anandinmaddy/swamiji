package com.sastra.im037.sastraprakasika.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class ListOfTopicsModels {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    @ColumnInfo(name = "image_url")
    private String song_image;
    @ColumnInfo(name = "parentname")
    private String song_title;
    @ColumnInfo(name = "volume_name")
    private String song_volume;
    @ColumnInfo(name = "description")
    private String song_description;
    @ColumnInfo(name = "count")
    private String song_count;
    @ColumnInfo(name = "parentid")
    private String song_parentid;
    @ColumnInfo(name = "post_id")
    private String song_post_id;


    public ListOfTopicsModels(){
        return;
    }


    public ListOfTopicsModels(String image_url, String name, String song_volume,String song_description,String song_count,String song_parentid,String song_post_id) {
        this.song_image = image_url;
        this.song_title = name;
        this.song_volume = song_volume;
        this.song_description=song_description;
        this.song_count=song_count;
        this.song_parentid=song_parentid;
        this.song_post_id=song_post_id;
    }

    public void setSong_image(String song_image) {
        this.song_image = song_image;
    }

    public void setSong_title(String song_title) {
        this.song_title = song_title;
    }

    public void setSong_volume(String song_volume) {
        this.song_volume = song_volume;
    }

    public String getSong_description() {
        return song_description;
    }

    public void setSong_description(String song_description) {
        this.song_description = song_description;
    }

    public String getSong_count() {
        return song_count;
    }

    public void setSong_count(String song_count) {
        this.song_count = song_count;
    }

    public String getSong_parentid() {
        return song_parentid;
    }

    public void setSong_parentid(String song_parentid) {
        this.song_parentid = song_parentid;
    }

    public String getSong_post_id() {
        return song_post_id;
    }

    public void setSong_post_id(String song_post_id) {
        this.song_post_id = song_post_id;
    }

    public String getSong_image() {
        return song_image;
    }

    public String getSong_title() {
        return song_title;
    }

    public String getSong_volume() {
        return song_volume;
    }
}
