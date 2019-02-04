package com.example.im037.sastraprakasika.Webservices;

import android.content.Context;

import com.example.im037.sastraprakasika.ConstantValues;
import com.example.im037.sastraprakasika.VolleyResponseListerner;

import org.json.JSONException;
import org.json.JSONObject;


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

    public void getCategory_list(String parentid, String subid, VolleyResponseListerner listerner) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("parentid", parentid);
            jsonObject.put("subid", subid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volleyClass.volleyPostData(ConstantValues.CATEGORY_LIST, jsonObject, listerner);
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



