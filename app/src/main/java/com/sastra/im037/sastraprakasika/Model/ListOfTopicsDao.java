package com.sastra.im037.sastraprakasika.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ListOfTopicsDao {

    @Query("SELECT * FROM ListOfTopicsModels")
    List<ListOfTopicsModels> getAll();

    @Insert
    void insertAll(ListOfTopicsModels... listOfTopicsModels);

    @Query("SELECT * FROM ListOfTopicsModels")
    List<ListOfTopicsModels> getVolumeAll();

    @Insert
    void insertVolumeAll(ListOfTopicsModels... listOfTopicsModels);


    @Query("DELETE FROM ListOfTopicsModels")
    void deleteAll();


}
