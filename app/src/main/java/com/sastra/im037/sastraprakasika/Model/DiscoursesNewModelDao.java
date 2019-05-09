package com.sastra.im037.sastraprakasika.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DiscoursesNewModelDao {

    @Query("SELECT * FROM DiscoursesNewModel")
    List<DiscoursesNewModel> getAll();

    @Insert
    void insertAll(DiscoursesNewModel... discoursesNewModels);

    @Query("SELECT * FROM DiscoursesNewModel")
    List<DiscoursesNewModel> getVolumeAll();

    @Query("DELETE FROM DiscoursesNewModel")
    void deleteAll();





}
