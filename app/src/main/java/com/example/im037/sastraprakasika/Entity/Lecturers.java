package com.example.im037.sastraprakasika.Entity;

import android.graphics.Bitmap;

import com.orm.SugarRecord;

public class Lecturers extends SugarRecord {

    String post_id = "";
    String title = "";
    String mp3 = "";
    String classname = "";
    String custom = "";
    String type = "";
    String cpost_id="";
    String time = "";
    String image_url;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCpost_id() {
        return cpost_id;
    }

    public void setCpost_id(String cpost_id) {
        this.cpost_id = cpost_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
