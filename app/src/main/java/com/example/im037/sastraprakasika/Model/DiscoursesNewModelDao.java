package com.example.im037.sastraprakasika.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DiscoursesNewModelDao {

    @Query("SELECT * FROM DiscoursesModel")
    List<DiscoursesModel> getAll();

    @Insert
    void insertAll(DiscoursesModel... discoursesModels);

    @Query("SELECT * FROM VolumeModel")
    List<VolumeModel> getVolumeAll();

    @Insert
    void insertVolumeAll(VolumeModel... volumeModels);




}