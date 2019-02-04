package com.example.im037.sastraprakasika.Model;

import java.util.ArrayList;

public class ProfileModel {
    String name;
    int type;
    ArrayList<ProfileDetailsModel> profileDetailsModels;
    public ProfileModel(String name, int type, ArrayList<ProfileDetailsModel>profileDetailsModels) {
        this.name = name;
        this.type = type;
        this.profileDetailsModels=profileDetailsModels;
    }
    public ArrayList<ProfileDetailsModel> getProfileDetailsModels() {
        return profileDetailsModels;
    }

    public void setProfileDetailsModels(ArrayList<ProfileDetailsModel> profileDetailsModels) {
        this.profileDetailsModels = profileDetailsModels;
    }
    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static class ProfileDetailsModel {
        String data,title;

        public ProfileDetailsModel(String data, String title){
            this.data=data;
            this.title=title;
        }
        public String getData() {
            return data;
        }

        public String getTitle() {
            return title;
        }
    }

}


