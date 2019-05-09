package com.sastra.im037.sastraprakasika.Model;

import java.util.ArrayList;

public class VolumeDetailsModel {
    String title, volume, classes, price,postid,medium_image,thumb_image;
    ArrayList<FileDetailsModel> fileDetailsModels;
    boolean purchase;


    public VolumeDetailsModel(String postid, String title, String vome, String classes, String price, boolean purchase, ArrayList<FileDetailsModel> fileDetailsModels) {
        this.postid=postid;
        this.title = title;
        this.volume = vome;
        this.classes = classes;
        this.price = price;
        this.purchase = purchase;
        this.fileDetailsModels = fileDetailsModels;
    }

    public ArrayList<FileDetailsModel> getFileDetailsModels() {
        return fileDetailsModels;
    }

    public void setFileDetailsModels(ArrayList<FileDetailsModel> fileDetailsModels) {
        this.fileDetailsModels = fileDetailsModels;
    }


    public boolean isPurchase() {
        return purchase;
    }

    public void setPurchase(boolean purchase) {
        this.purchase = purchase;
    }


    public String getTitle() {
        return title;
    }

    public String getVolume() {
        return volume;
    }

    public String getClasses() {
        return classes;
    }

    public String getPrice() {
        return price;
    }
    public String getPostid() {
        return postid;
    }

    public static class FileDetailsModel {
     private String fileName, className, length;

        public FileDetailsModel(String fileName, String className, String length) {
            this.fileName = fileName;
            this.className = className;
            this.length = length;
        }

        public String getFileName() {
            return fileName;
        }

        public String getClassName() {
            return className;
        }

        public String getLength() {
            return length;
        }
    }
}
