package com.sastra.im037.sastraprakasika.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface VolumeModelDao {

    @Query("SELECT * FROM VolumeModel")
    List<VolumeModel> getAll();

    @Insert
    void insertAll(VolumeModel... volumeModels);

    @Query("DELETE FROM VolumeModel")
    void deleteAll();


}
