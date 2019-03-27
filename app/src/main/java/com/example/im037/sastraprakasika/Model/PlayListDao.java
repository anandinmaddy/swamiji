package com.example.im037.sastraprakasika.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PlayListDao {


    @Query("SELECT * FROM PlayList")
    List<PlayList> getAll();

    @Insert
    void insertAll(PlayList... playLists);

    @Query("SELECT * FROM PlayList")
    List<PlayList> getVolumeAll();

    @Insert
    void insertVolumeAll(PlayList... playLists);


    @Query("DELETE FROM PlayList")
    void deleteAll();

}
