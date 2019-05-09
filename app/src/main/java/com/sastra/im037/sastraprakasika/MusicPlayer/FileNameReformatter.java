package com.sastra.im037.sastraprakasika.MusicPlayer;

import android.content.Context;

public class FileNameReformatter {

    private static Context context;
    private static FileNameReformatter mInstance;

    public FileNameReformatter(Context context) {
        FileNameReformatter.context = context;
    }

    public static FileNameReformatter getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FileNameReformatter(context);
        }
        return mInstance;
    }

    public String getFormattedName(String oldName){

        String newName = "";
        // remove '|'
        newName += oldName.replaceAll("\\|", " ");
        newName = newName.replaceAll("\\,","");
        newName = newName.replaceAll("\\-","");

        return newName;
    }



}
