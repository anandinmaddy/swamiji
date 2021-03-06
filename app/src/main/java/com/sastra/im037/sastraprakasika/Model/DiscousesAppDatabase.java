package com.sastra.im037.sastraprakasika.Model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sastra.im037.sastraprakasika.Entity.ItemSongDao;
import com.sastra.im037.sastraprakasika.OnlinePlayer.ItemSong;

@Database(entities = {DiscoursesModel.class,VolumeModel.class,DiscoursesNewModel.class,ItemSong.class,ListOfTopicsModels.class,ListOfTopicsDetailed.class,PlayList.class,SearchModel.class},exportSchema = true,version = 1)
public abstract class DiscousesAppDatabase extends RoomDatabase {

    public abstract DiscoursesModelDao userDao();

    public abstract VolumeModelDao volumeModel();

    public abstract DiscoursesNewModelDao discoursesNewModel();

    public abstract ItemSongDao itemSongDao();

    public abstract ListOfTopicsDao listOfTopicsModels();

    public abstract ListOfTopicsDetailedDao listOfTopicsDetailed();

    public abstract PlayListDao playListDao();

    public abstract SearchModelDao searchModelDao();

}
