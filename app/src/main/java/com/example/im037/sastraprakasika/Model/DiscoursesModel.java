package com.example.im037.sastraprakasika.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DiscoursesModel {
    @PrimaryKey(autoGenerate = true)
     public int id;
    @ColumnInfo(name = "parentname")
    String parentname;

    @ColumnInfo(name = "image_url")
    String image_url;

    @ColumnInfo(name = "slug")
    String slug;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "parentid")
    String parentid;

    @ColumnInfo(name = "topiccount")
    String topiccount;

    @ColumnInfo(name = "trackcount")
    String trackcount;

    public DiscoursesModel(){
        return;
    }

    public DiscoursesModel(String name,String slug,String parentid, String image_url,String desc,String topic_cnt,String track_cnt){
        this.parentname=name;
        this.slug=slug;
        this.parentid=parentid;
        this.image_url=image_url;
        this.description = desc;
        this.topiccount = topic_cnt;
        this.trackcount = track_cnt;
      }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return parentname;
    }

    public void setName(String name) {
        this.parentname = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getTopiccount() {
        return topiccount;
    }

    public void setTopiccount(String topiccount) {
        this.topiccount = topiccount;
    }

    public String getTrackcount() {
        return trackcount;
    }

    public void setTrackcount(String trackcount) {
        this.trackcount = trackcount;
    }

}
