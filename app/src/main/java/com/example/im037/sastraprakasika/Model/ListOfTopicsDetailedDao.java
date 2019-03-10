package com.example.im037.sastraprakasika.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ListOfTopicsDetailedDao {

    @Query("SELECT * FROM ListOfTopicsDetailed")
    List<ListOfTopicsDetailed> getAll();

    @Insert
    void insertAll(ListOfTopicsDetailed... listOfTopicsDetaileds);

    @Query("SELECT * FROM ListOfTopicsDetailed")
    List<ListOfTopicsDetailed> getVolumeAll();

    @Insert
    void insertVolumeAll(ListOfTopicsDetailed... listOfTopicsDetaileds);


    @Query("DELETE FROM ListOfTopicsDetailed")
    void deleteAll();


}
