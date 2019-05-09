package com.sastra.im037.sastraprakasika.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class VolumeModel {

    @PrimaryKey(autoGenerate = true)
    public int idno;

    @ColumnInfo(name = "id")
    private String id;


    @ColumnInfo(name = "name")
    private   String  name;

    @ColumnInfo(name = "slug")
    private   String  slug;

    @ColumnInfo(name = "subid")
    String subid;

    @ColumnInfo(name = "topiccount")
    private   String  topiccount;

    @ColumnInfo(name = "trackcount")
    private   String  trackcount;


    @ColumnInfo(name = "description")
    private   String  description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getTrackcount() {
        return trackcount;
    }

    @ColumnInfo(name = "image_url")



    private   String  image_url;

    public int getIdno() {
        return idno;
    }

    public void setIdno(int idno) {
        this.idno = idno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getTopiccount() {
        return topiccount;
    }

    public void setTopiccount(String topiccount) {
        this.topiccount = topiccount;
    }

    public String getTitlecount() {
        return trackcount;
    }

    public void setTrackcount(String trackcount) {
        this.trackcount = trackcount;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
