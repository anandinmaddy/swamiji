package com.sastra.im037.sastraprakasika.SingletonClass;

import com.sastra.im037.sastraprakasika.Model.VolumeDetailsModel;

import java.util.ArrayList;

public class GetData {
    private static final GetData ourInstance= new GetData();
    private ArrayList<VolumeDetailsModel> list = null;

    private ArrayList<VolumeDetailsModel.FileDetailsModel> subCatergory = null;

    public static GetData getInstance() {
        return ourInstance;
    }
    private GetData() {
        list = new ArrayList<VolumeDetailsModel>();
        subCatergory=new ArrayList<VolumeDetailsModel.FileDetailsModel>();
    }
    // // retrieve array from anywhere
    public ArrayList<VolumeDetailsModel> getCategorylist() {
        return list;
    }
    // //Add element to array
    public void addToCatergoryArray(VolumeDetailsModel model) {
        list.add(model);
    }

    public ArrayList<VolumeDetailsModel.FileDetailsModel> getSubCatergory() {
        return subCatergory;
    }
    public void addTosubCatergoryArray(VolumeDetailsModel.FileDetailsModel model) {
        subCatergory.add(model);
    }
    public void clearData() {
        list = new ArrayList<VolumeDetailsModel>();
        subCatergory = new ArrayList<VolumeDetailsModel.FileDetailsModel>();
    }

}
