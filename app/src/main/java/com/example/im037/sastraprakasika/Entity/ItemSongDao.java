package com.example.im037.sastraprakasika.Entity;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;

import java.util.List;

@Dao
public interface ItemSongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ItemSong... itemSongs);

    @Delete
    void delete(ItemSong itemSongs);

    @Query("SELECT * FROM itemSong")
    public ItemSong[] loadAllUsers();





}
