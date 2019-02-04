package com.example.im037.sastraprakasika.Model;

public class Topics_empty_postdet {

    String parentname;
    String volume_name;
    String post_id;
    String parentid;
    String count;
    String description;
    String image_url;

    public Topics_empty_postdet(String parentname, String volume_name, String post_id, String parentid, String count, String description, String image_url) {
        this.parentname = parentname;
        this.volume_name = volume_name;
        this.post_id = post_id;
        this.parentid = parentid;
        this.count = count;
        this.description = description;
        this.image_url = image_url;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getVolume_name() {
        return volume_name;
    }

    public void setVolume_name(String volume_name) {
        this.volume_name = volume_name;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
