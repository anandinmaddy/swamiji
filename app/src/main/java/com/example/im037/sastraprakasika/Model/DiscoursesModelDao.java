package com.example.im037.sastraprakasika.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DiscoursesModelDao {

    @Query("SELECT * FROM DiscoursesModel")
    List<DiscoursesModel> getAll();

    @Insert
    void insertAll(DiscoursesModel... discoursesModels);


}