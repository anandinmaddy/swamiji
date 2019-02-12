package com.example.im037.sastraprakasika.Model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {DiscoursesModel.class},exportSchema = false,version = 1)
public abstract class DiscousesAppDatabase extends RoomDatabase {
    public abstract DiscoursesModelDao userDao();
}