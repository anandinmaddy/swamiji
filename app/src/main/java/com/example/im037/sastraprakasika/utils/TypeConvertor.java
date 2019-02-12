package com.example.im037.sastraprakasika.utils;

import android.arch.persistence.room.TypeConverter;

import com.example.im037.sastraprakasika.Model.DiscoursesModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TypeConvertor {
    private static Gson gson = new Gson();
    private static Type type = new TypeToken<List<DiscoursesModel>>(){}.getType();

    @TypeConverter
    public static List<DiscoursesModel> stringToNestedData(String json) {
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String nestedDataToString(List<DiscoursesModel> nestedData) {
        return gson.toJson(nestedData, type);
    }
}