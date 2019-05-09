package com.sastra.im037.sastraprakasika.utils;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class AppPreference {
    private static SharedPreferences sharedPreferences = null;
    private static final String PREF_NAME = "SwamijiAppPref";

    public static SharedPreferences getSharedPrefernces(Context context) {
        if (context != null) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences(PREF_NAME,
                        Context.MODE_PRIVATE);
            }
        }
        return sharedPreferences;
    }

    public static void putString(String key, String value, Context context) {
        getSharedPrefernces(context).edit().putString(key, value).apply();
    }

    public static String getString(String key, Context context) {
        return getSharedPrefernces(context).getString(key, null);
    }

    public static void putLong(String key, Long value, Context context) {
        getSharedPrefernces(context).edit().putLong(key, value).apply();
    }

    public static Long getLong(String key, Context context) {
        return getSharedPrefernces(context).getLong(key, (long) 0.0);
    }

    public static void putFloat(String key, Float value, Context context) {
        getSharedPrefernces(context).edit().putFloat(key, value).apply();
    }

    public static Float getFloat(String key, Context context) {
        return getSharedPrefernces(context).getFloat(key, (float) 0.0);
    }


    public static void putBoolean(String key, boolean value, Context context) {
        getSharedPrefernces(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, Context context) {
        return getSharedPrefernces(context).getBoolean(key, false);
    }


    public static void putInt(String key, int value, Context context) {
        getSharedPrefernces(context).edit().putInt(key, value).apply();
    }

    public static int getInt(String key, Context context) {
        return getSharedPrefernces(context).getInt(key, -1);
    }

    public static void clearData(Context context) {
        getSharedPrefernces(context).edit().clear().apply();
    }

    public static void clearKeyData(String key, Context context) {
        getSharedPrefernces(context).edit().remove(key).apply();

    }

    public static void putStringSet(String key, Set<String> value, Context context) {
        getSharedPrefernces(context).edit().putStringSet(key, value).apply();

    }

    public static Set<String> getStringSet(String key, Context context) {
        return getSharedPrefernces(context).getStringSet(key, null);
    }


}
