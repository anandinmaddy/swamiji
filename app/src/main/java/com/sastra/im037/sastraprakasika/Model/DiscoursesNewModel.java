package com.sastra.im037.sastraprakasika.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DiscoursesNewModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "image_url")
    String image_url;

    @ColumnInfo(name = "slug")
    String slug;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "catid")
    String catid;

    @ColumnInfo(name = "subid")
    String subid;

    @ColumnInfo(name = "titlecount")
    String titlecount;

    @ColumnInfo(name = "trackcount")
    String trackcount;


    public DiscoursesNewModel(){
        return;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getTitlecount() {
        return titlecount;
    }

    public void setTitlecount(String titlecount) {
        this.titlecount = titlecount;
    }

    public String getTrackcount() {
        return trackcount;
    }

    public void setTrackcount(String trackcount) {
        this.trackcount = trackcount;
    }
}
