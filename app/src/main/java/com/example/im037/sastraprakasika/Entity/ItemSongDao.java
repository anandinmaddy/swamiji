package com.example.im037.sastraprakasika.Entity;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;

import java.util.List;

@Dao
public interface ItemSongDao {

    @Query("SELECT * FROM ItemSong")
    List<ItemSong> getAll();

    @Insert
    void insertAll(ItemSong... itemSongs);

    @Query("DELETE FROM ItemSong")
    void deleteAll();

    @Query("UPDATE ItemSong SET downloads=:download WHERE title = :title")
    void update(String download,String title);

    @Query("UPDATE ItemSong SET userRating=:download WHERE title = :title")
    void updateRat(String download,String title);

    @Query("select userRating from ItemSong WHERE title = :title")
    String getRating(String title);



}
