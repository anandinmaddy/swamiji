package com.sastra.im037.sastraprakasika.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class ListOfTopicsDetailed {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;
    @ColumnInfo(name = "title")
    String topics_det_title;
    @ColumnInfo(name = "mp3")
    String topics_det_img;
    @ColumnInfo(name = "type")
    String topics_det_type;
    @ColumnInfo(name = "post_id")
    String topics_det_post_id;
    @ColumnInfo(name = "image_url")
    String topics_det_imgurl;
    @ColumnInfo(name = "parentid")
    String topics_parentid;
    @ColumnInfo(name = "subid")
    String topics_subid;
    @ColumnInfo(name = "time")
    String topics_time;
    @ColumnInfo(name = "classname")
    String topics_classname;
    @ColumnInfo(name = "downloads")
    String downloads;
    @ColumnInfo(name = "track_id")
    String track_id;


    public ListOfTopicsDetailed(){
        return;
    }

    public ListOfTopicsDetailed(String topics_det_title, String topics_det_img, String topics_det_type, String topics_det_post_id, String topics_det_imgurl, String topics_parentid, String topics_subid, String topics_time,String top_cname) {
        this.topics_det_title = topics_det_title;
        this.topics_det_img = topics_det_img;
        this.topics_det_type = topics_det_type;
        this.topics_det_post_id = topics_det_post_id;
        this.topics_det_imgurl = topics_det_imgurl;
        this.topics_parentid = topics_parentid;
        this.topics_subid = topics_subid;
        this.topics_time = topics_time;
        this.topics_classname = top_cname;
    }

    public String getTopics_classname() {
        return topics_classname;
    }

    public void setTopics_classname(String topics_classname) {
        this.topics_classname = topics_classname;
    }

    public String getTopics_det_title() {
        return topics_det_title;
    }

    public void setTopics_det_title(String topics_det_title) {
        this.topics_det_title = topics_det_title;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public String getTopics_det_img() {
        return topics_det_img;
    }

    public void setTopics_det_img(String topics_det_img) {
        this.topics_det_img = topics_det_img;
    }

    public String getTopics_det_type() {
        return topics_det_type;
    }

    public void setTopics_det_type(String topics_det_type) {
        this.topics_det_type = topics_det_type;
    }

    public String getTopics_det_post_id() {
        return topics_det_post_id;
    }

    public void setTopics_det_post_id(String topics_det_post_id) {
        this.topics_det_post_id = topics_det_post_id;
    }

    public String getTopics_det_imgurl() {
        return topics_det_imgurl;
    }

    public void setTopics_det_imgurl(String topics_det_imgurl) {
        this.topics_det_imgurl = topics_det_imgurl;
    }

    public String getTopics_parentid() {
        return topics_parentid;
    }

    public void setTopics_parentid(String topics_parentid) {
        this.topics_parentid = topics_parentid;
    }

    public String getTopics_subid() {
        return topics_subid;
    }

    public void setTopics_subid(String topics_subid) {
        this.topics_subid = topics_subid;
    }

    public String getTopics_time() {
        return topics_time;
    }

    public void setTopics_time(String topics_time) {
        this.topics_time = topics_time;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

}
