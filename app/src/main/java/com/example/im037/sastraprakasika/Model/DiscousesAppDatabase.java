package com.example.im037.sastraprakasika.Model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.RoomDatabase;

import com.example.im037.sastraprakasika.Entity.ItemSongDao;
import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;

@Database(entities = {DiscoursesModel.class,VolumeModel.class,DiscoursesNewModel.class,ItemSong.class,ListOfTopicsModels.class,ListOfTopicsDetailed.class},exportSchema = false,version = 1)
public abstract class DiscousesAppDatabase extends RoomDatabase {
    public abstract DiscoursesModelDao userDao();

    public abstract VolumeModelDao volumeModel();

    public abstract DiscoursesNewModelDao discoursesNewModel();

    public abstract ItemSongDao itemSongDao();

    public abstract ListOfTopicsDao listOfTopicsModels();

    public abstract ListOfTopicsDetailedDao listOfTopicsDetailed();
}
