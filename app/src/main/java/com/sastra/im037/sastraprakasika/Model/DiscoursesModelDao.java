package com.sastra.im037.sastraprakasika.Model;

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

    @Query("SELECT * FROM DiscoursesModel")
    List<DiscoursesModel> getVolumeAll();

    @Insert
    void insertVolumeAll(DiscoursesModel... discoursesModels);

    @Query("DELETE FROM DiscoursesModel")
    void deleteAll();



}
