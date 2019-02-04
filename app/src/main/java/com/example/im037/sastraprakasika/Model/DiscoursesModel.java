package com.example.im037.sastraprakasika.Model;

public class DiscoursesModel {
    String name,image_url,slug,description;
   public String parentid;
   String topiccount,trackcount;



    public DiscoursesModel(String name,String slug,String parentid, String image_url,String desc,String topic_cnt,String track_cnt){
        this.name=name;
        this.slug=slug;
        this.parentid=parentid;
        this.image_url=image_url;
        this.description = desc;
        this.topiccount = topic_cnt;
        this.trackcount = track_cnt;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName(){
        return name;
    }
    public String getImage_url(){
        return image_url;
    }
    public String getSlug() {
        return slug;
    }

    public String getParentid() {
        return parentid;
    }

}
