package com.example.im037.sastraprakasika.Model;

public class ListOfTopicsDetailed {

    String topics_det_title;
    String topics_det_img;
    String topics_det_type;
    String topics_det_post_id;
    String topics_det_imgurl;
    String topics_parentid;
    String topics_subid;
    String topics_time;
    String topics_classname;


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
}
