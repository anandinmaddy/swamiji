package com.sastra.im037.sastraprakasika.Webservices;

import android.content.Context;

import com.sastra.im037.sastraprakasika.ConstantValues;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WebServices {

    private static WebServices webServices;
    private VolleyClass volleyClass;


    public WebServices(Context context, String TAG) {

        volleyClass = new VolleyClass(context, TAG);
    }

    public void login(String userName, String password, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", userName);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyClass.volleyPostData(ConstantValues.LOGIN, jsonObject, listerner);
    }

    public void socialLogin(String email, String name, String type, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("name", name);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyClass.volleyPostData(ConstantValues.SOCIAL_LOGIN, jsonObject, listerner);
    }


    public void sendOtp(String name, String email, String password, String mobile, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("name", name);
            jsonObject.put("password", password);
            jsonObject.put("mobile", mobile);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyClass.volleyPostData(ConstantValues.OTP_CODE, jsonObject, listerner);
    }


    public void confirmOtp(String email, String otpValue, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("otpcode", otpValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyClass.volleyPostData(ConstantValues.CONFIRM_OTP, jsonObject, listerner);
    }

    public void register(String email, String mobile, String password, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("mobile", mobile);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyClass.volleyPostData(ConstantValues.REGISTER, jsonObject, listerner);
    }

    public void forgot_passwd(String email, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.FORGOT_PASSWD, jsonObject, listerner);
    }

    public void set_passwd(String email, String passwd, String confirm_passwd, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("new_password", passwd);
            jsonObject.put("confirm_password", confirm_passwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.SET_PASSWD, jsonObject, listerner);
    }

    public void getCategory_list(String parentid, String subid, String catId,VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("parentid", parentid);
            jsonObject.put("subid", subid);
            jsonObject.put("catid", catId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.CATEGORY_LIST, jsonObject, listerner);
    }

    public void getCategory_listNew(String userId, String parentid, String subid, String catId,VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userId);
            jsonObject.put("parentid", parentid);
            jsonObject.put("subid", subid);
            jsonObject.put("catid", catId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.CATEGORY_LIST, jsonObject, listerner);
    }

    public void getPayment_list(String userId, String post_id,VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userId);
            jsonObject.put("post_id", post_id);
            jsonObject.put("apptype", "ANDROID");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.PAYMENT_LIST, jsonObject, listerner);
    }


    public void getPayment_verification(String userId, String post_id,VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userId);
            jsonObject.put("post_id", post_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.PAYMENTVERI_LIST, jsonObject, listerner);
    }

    public void getPlayLists(String user_id,VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.PLAY_LIST, jsonObject, listerner);
        //    volleyClass.volleyPostDataSync(ConstantValues.PLAY_LIST, jsonObject, listerner);

    }
    public void deletePlayLists(String user_id,String player_id,VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("player_id", player_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.DELETE_PLAYLIST_SONG, jsonObject, listerner);
        //    volleyClass.volleyPostDataSync(ConstantValues.PLAY_LIST, jsonObject, listerner);

    }

    public void deleteSong(String user_id,String player_id,String track_id,VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("player_id", player_id);
            jsonObject.put("track_id", track_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.DELETESONGS, jsonObject, listerner);
        //    volleyClass.volleyPostDataSync(ConstantValues.PLAY_LIST, jsonObject, listerner);

    }


    public void savePlayLists(String user_id, String player_id, String player_name, JSONArray tracks, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("player_id", player_id);
            jsonObject.put("player_name", player_name);
            jsonObject.put("tracks",tracks);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.CREATE_PLAYLIST, jsonObject, listerner);
    }


    public void addtoPlayLists(String user_id, String player_id, JSONArray tracks, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("player_id", player_id);
            jsonObject.put("tracks",tracks);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.ADD_PLAYLIST_SONG, jsonObject, listerner);
    }


    public void getPlaylistSongs(String user_id , String player_id,  VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("player_id", player_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.GET_PLAYLIST_SONG, jsonObject, listerner);
    }

    public void getlibrary(String user_id, String type, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.LIBRARY, jsonObject, listerner);
    }

    public void getTopicsDetail(String user_id, String type, String postid,VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("type", type);
            jsonObject.put( "post_id",postid );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.LIBRARY, jsonObject, listerner);
    }


    public void getAboutPageDetails(String pageid, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageid", pageid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.ABOUTSASTRA, jsonObject, listerner);
    }

    public void saveDeviceId(String user_id, String device_id, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("device_id", device_id);

        } catch (JSONException e) {
            e.printStackTrace();

        }
        volleyClass.volleyPostData(ConstantValues.DEVICE_UPDATE,jsonObject,listerner);
    }
    public void setprofileUpdate(String user_id,String name,String mobile,VolleyResponseListerner listerner){
        JSONObject jsonObject= new JSONObject();
        try{
            jsonObject.put("user_id", user_id);
            jsonObject.put("name",name);
            jsonObject.put("mobile",mobile);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.PROFILEUPDATE,jsonObject,listerner);
    }
    public void setSearchUpdate(String user_id,String type,String keyword,VolleyResponseListerner listerner){
        JSONObject jsonObject= new JSONObject();
        try{
            jsonObject.put("user_id",user_id);
            jsonObject.put("type",type);
            jsonObject.put("keyword",keyword);
            jsonObject.put( "post_id","" );

        }catch (JSONException e){
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.SEARCH,jsonObject,listerner);
    }

    public void getSearchDetail(String user_id,String type,String keyword,String postid,VolleyResponseListerner listerner){
        JSONObject jsonObject= new JSONObject();
        try{
            jsonObject.put("user_id",user_id);
            jsonObject.put("type",type);
            jsonObject.put("keyword",keyword);
            jsonObject.put( "post_id",postid );

        }catch (JSONException e){
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.SEARCH,jsonObject,listerner);
    }



}



