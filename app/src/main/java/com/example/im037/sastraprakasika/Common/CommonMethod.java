package com.example.im037.sastraprakasika.Common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.im037.sastraprakasika.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;


/**
 * Created by admin on 2/20/2018.
 */

public class CommonMethod {

    private static int count = 0;
    private static Timer timer;

    public static void clearAllPreviousActivity(Context context, Class<?> c) {
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        context.startActivity(i);
    }

//    public static void changeActivity(Context context, Class<?> c) {
//        Intent i = new Intent(context, c);
//        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
//    }

    public static void changeActivity(Activity context, Class<?> c) {
        Intent i = new Intent(context, c);
//        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        context.overridePendingTransition( R.anim.fade_in, R.anim.fade_out);

    }
    public static void changeActivityData(Context context,Class<?>c,String data){
        Intent i=new Intent(context,c);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("data", data);
        context.startActivity(i);
    }

    public static void changeActivityText(Context context, Class<?> c, String data, String data1, String data2) {
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("data", data);
        i.putExtra("data1", data1);
        i.putExtra("data2", data2);
        context.startActivity(i);
    }
    public static void changeActivityData5(Context context, Class<?> c, String data, String data1, String data2,String data3,String data4) {
        Intent i = new Intent(context, c);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("data", data);
        i.putExtra("data1", data1);
        i.putExtra("data2", data2);
        i.putExtra("data3", data3);
        i.putExtra("data4", data4);
        context.startActivity(i);
    }
    public static void showSnackbar(View view, String message, Activity activity) {
        try {
            hideKeyboard(activity);
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSnackbar(View view, JSONObject message, Activity activity) {
        try {
            hideKeyboard(activity);
            Snackbar snackbar = Snackbar.make(view, message.optString("message"), Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void hideKeyboardNew(Context context, View v) {
        InputMethodManager input = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static boolean checkTokenStauts(JSONObject jsonObject, Activity activity) {
        try {
            if (jsonObject.has("token_status"))
                if (!jsonObject.getString("token_status").equalsIgnoreCase("0")) {
                    return true;
                } else {
//                    Session.getInstance(activity, "CommonMethod").logout();
//                    clearAllPreviousActivity(activity, Dashboard.class);
                    activity.finish();
                    return false;
                }
            else
                return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean checkEmpty(EditText editText, TextInputLayout textInputLayout, String error) {
        try {
            if (!editText.getText().toString().equalsIgnoreCase("")) {
                textInputLayout.setError(null);
                return true;
            } else {
                textInputLayout.setError(error);
                editText.requestFocus();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean emailValidation(EditText editText, TextInputLayout textInputLayout, String error) {
        if (Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()) {
            textInputLayout.setError(null);
            return true;
        } else {
            textInputLayout.setError(error);
            editText.requestFocus();
            return false;
        }
    }


}
