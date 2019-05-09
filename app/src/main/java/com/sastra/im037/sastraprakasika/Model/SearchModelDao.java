package com.sastra.im037.sastraprakasika.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
