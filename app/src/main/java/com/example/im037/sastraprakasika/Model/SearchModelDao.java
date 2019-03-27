package com.example.im037.sastraprakasika.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.example.im037.sastraprakasika.OnlinePlayer.ItemSong;

import java.util.List;

@Dao
public interface SearchModelDao {

    @Query("SELECT * FROM SearchModel")
    List<SearchModel> getAll();

    @Insert
    void insertAll(SearchModel... searchModels);

    @Query("DELETE FROM SearchModel")
    void deleteAll();





}
