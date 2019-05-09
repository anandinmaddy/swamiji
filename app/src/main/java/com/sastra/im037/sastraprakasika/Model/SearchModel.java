package com.sastra.im037.sastraprakasika.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class SearchModel{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    @ColumnInfo(name = "parentid")
    private String parentid;
    @ColumnInfo(name = "image_url")
    private String image_url;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "subid")
    private String subid;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "time")
    private String time;
    @ColumnInfo(name = "post_id")
    private String post_id;
    @ColumnInfo(name = "mp3")
    private String mp3;



    @ColumnInfo(name = "description")
    private String description;


    public SearchModel() {

    }


    public SearchModel(String parentid, String title, String subid, String type, String time, String image_url, String post_id) {
        this.parentid = parentid;
        this.title = title;
        this.subid = subid;
        this.type = type;
        this.time = time;
        this.image_url=image_url;
        this.post_id=post_id;


    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }


    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }
    public String getParentid() {
        return parentid;
    }

    public String getTitle() {
        return title;
    }

    public String getSubid() {
        return subid;
    }

    public String getType() {
        return type;
    }
    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public String getTime() {
        return time;
    }
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
