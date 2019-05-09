package com.sastra.im037.sastraprakasika.Activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sastra.im037.sastraprakasika.Common.BackActivity;
import com.sastra.im037.sastraprakasika.Common.CommonMethod;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswd extends BackActivity {

    @BindView(R.id.emailId)
    EditText emailId;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;
    String TAG = ForgotPasswd.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_forgot_passwd, "");
        ButterKnife.bind(this);

    }

    @OnClick({R.id.emailId, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.emailId:
                emailId.setError(null);
                break;
            case R.id.send:
                if (CommonMethod.emailValidation(emailId, emailLayout, "Enter a valid email address"))
                    new WebServices(ForgotPasswd.this, TAG).forgot_passwd(emailId.getText().toString().trim(), new VolleyResponseListerner() {
                        @Override
                        public void onResponse(JSONObject response) throws JSONException {
                            if (response.optString("resultcode").equalsIgnoreCase("200")) {
                                CommonMethod.changeActivityText(ForgotPasswd.this, VerifyOtpActivity.class, "", emailId.getText().toString().trim(), "forgotPasswd");
                            }else if(response.optString("resultcode").equalsIgnoreCase("504")) {
                                CommonMethod.showSnackbar(emailId,response.optString("resultmessage"),ForgotPasswd.this);
                            }
                        }

                        @Override
                        public void onError(String message, String title) {

                        }
                    });
                break;
        }
    }
}
