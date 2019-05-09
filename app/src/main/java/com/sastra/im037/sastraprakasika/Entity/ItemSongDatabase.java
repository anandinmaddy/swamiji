package com.sastra.im037.sastraprakasika.Entity;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sastra.im037.sastraprakasika.OnlinePlayer.ItemSong;

@Database(entities = {ItemSong.class}, version = 1,exportSchema = false)
public abstract class ItemSongDatabase extends RoomDatabase {
    public abstract ItemSongDao userDao();
}
