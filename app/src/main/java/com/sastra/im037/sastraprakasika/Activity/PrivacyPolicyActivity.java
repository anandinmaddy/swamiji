package com.sastra.im037.sastraprakasika.Activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Common.CommonActivity;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

public class PrivacyPolicyActivity extends CommonActivity {
    RelativeLayout common_dragview;
    TextView webview;
    public static final String TAG = PrivacyPolicyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_privacy_policy, "Privacy Policy");
        //common_dragview = (RelativeLayout) findViewById(R.id.dragView);
        //common_dragview.setVisibility(View.GONE);
        webview=(TextView)findViewById(R.id.webview);
        SetAboutDetail();
    }

    public void SetAboutDetail() {
        new WebServices(PrivacyPolicyActivity.this, TAG).getAboutPageDetails("1905", new VolleyResponseListerner() {

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("status").equalsIgnoreCase("1")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        webview.setText(Html.fromHtml(response.getJSONObject("data").optString("content"), 0));
                    }


                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }
}
