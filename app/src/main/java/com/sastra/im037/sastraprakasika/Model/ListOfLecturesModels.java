package com.sastra.im037.sastraprakasika.Model;

public class ListOfLecturesModels {

    private String url;
    private String ablum_image;
    private String ablum_title;
    private String duration;
    private String apla;

    public String getDuration() {
        return duration;
    }

    public String getApla() {
        return apla;
    }

    public ListOfLecturesModels(String ablum_image, String ablum_title, String url, String duration, String apla) {
        this.ablum_image = ablum_image;
        this.ablum_title = ablum_title;
        this.url=url;
        this.duration=duration;
        this.apla=apla;

    }

    public String getUrl() {
        return url;
    }

    public String getAblum_image() {
        return ablum_image;
    }

    public String getAblum_title() {
        return ablum_title;
    }
}
