package com.example.im037.sastraprakasika;

import android.content.Context;
import android.content.SharedPreferences;


public class Session {
    private static final String IS_LOGIN = "IsLoggedIn";
    private static String PREF_NAME = "sastraprakasika";
    private static String email = "email";
    private static String name = "name";
    private static String token = "token";
    private static String userId = "ID";
    private static String profile_UpdateName="name";
    private static String profile_UpdateMobileNumber="mobile";
    private static String parentid="parentid";
    private static String image_url="image_url";
    private static Session session;
    private String TAG;
    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public Session(Context context, String TAG) {
        this.context = context;
        this.TAG = "Session " + TAG;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public static Session getInstance(Context context, String TAG) {
        if (session == null) {
            session = new Session(context, TAG);
        }
        return session;
    }


    public void createSession(String name, String email, String token, String userId,String mobile) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(this.email, email);
        editor.putString(this.name, name);
        editor.putString(this.token, token);
        editor.putString(this.userId,userId);
        editor.putString(this.profile_UpdateMobileNumber,mobile);
        editor.commit();
    }
//    public void profile_Update(String name, String mobile){
//        editor.putString(this.profile_UpdateName,name);
//        editor.putString(this.profile_UpdateMobileNumber,mobile);
//        editor.commit();
//
//    }

    public boolean getTermsAccepted() {
        return pref.getBoolean("terms_acceptance", false);
    }

    public String getEmail() {
        return pref.getString(email, "");
    }

    public String getName() {
        return pref.getString(name, "");
    }

    public String getToken() {
        return pref.getString(token, "");
    }

    public boolean getIsLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }
    public  String getUserId() {
        return pref.getString(userId,"");
    }
    public String getProfile_UpdateName() {
        return pref.getString(profile_UpdateName,"");
    }

    public  String getProfile_UpdateMobileNumber() {
        return pref.getString(profile_UpdateMobileNumber,"");
    }


    public void logout() {
        editor.clear();
        editor.commit();
    }

}
