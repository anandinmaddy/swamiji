package com.example.im037.sastraprakasika.Model;

public class VolumeModel {
  private   String  name,slug,subid,titlecount,trackcount,image_url,Description;




    public VolumeModel(String name, String slug, String subid, String titlecount, String trackcount, String image_url, String description ) {
        this.name = name;
        this.slug = slug;
        this.subid = subid;
        this.titlecount=titlecount;
        this.trackcount=trackcount;
        this.image_url=image_url;
        this.Description=description;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getSubid() {
        return subid;
    }

    public String getTitlecount() {
        return titlecount;
    }

    public String getTrackcount() {
        return trackcount;
    }

    public String getImage_url() {
        return image_url;
    }
    public String getDescription() {
        return Description;
    }




}
