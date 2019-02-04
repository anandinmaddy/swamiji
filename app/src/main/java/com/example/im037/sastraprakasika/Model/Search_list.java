package com.example.im037.sastraprakasika.Model;

public class Search_list {

    String title;
    String post_id;
    String mp3;
    String image_url;
    String parentid;
    String subid;
    String type;
    String time;

    public Search_list(String title, String post_id, String mp3, String image_url, String parentid, String subid, String type, String time) {
        this.title = title;
        this.post_id = post_id;
        this.mp3 = mp3;
        this.image_url = image_url;
        this.parentid = parentid;
        this.subid = subid;
        this.type = type;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
