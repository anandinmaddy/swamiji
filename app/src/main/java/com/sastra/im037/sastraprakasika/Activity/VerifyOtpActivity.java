package com.sastra.im037.sastraprakasika.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Common.BackActivity;
import com.sastra.im037.sastraprakasika.Common.CommonMethod;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyOtpActivity extends BackActivity implements TextWatcher {


    String TAG = VerifyOtpActivity.class.getSimpleName(), otpValue;
    @BindView(R.id.txt1)
    AppCompatEditText txt1;
    @BindView(R.id.txt2)
    AppCompatEditText txt2;
    @BindView(R.id.txt3)
    AppCompatEditText txt3;
    @BindView(R.id.txt4)
    AppCompatEditText txt4;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.changeMail)
    TextView changeMail;
    @BindView(R.id.resendCode)
    TextView resendCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_signup1, "");
        ButterKnife.bind(this);

        txt1.addTextChangedListener(this);
        txt2.addTextChangedListener(this);
        txt3.addTextChangedListener(this);
        txt4.addTextChangedListener(this);

        String email = "<b>" + getIntent().getStringExtra("data1") + "</b>";
        info.setText(Html.fromHtml("Please enter the OTP you received in \n" + email));

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (txt1.getText().length() == 1) {
            txt2.requestFocus();
            if (txt2.getText().length() == 1) {
                txt3.requestFocus();
                if (txt3.getText().length() == 1) {
                    txt4.requestFocus();
                    if (txt4.getText().length() == 1) {

                        otpValue = txt1.getText().toString() + txt2.getText().toString() + txt3.getText().toString() + txt4.getText().toString();

                        new WebServices(VerifyOtpActivity.this, TAG).confirmOtp(getIntent().getStringExtra("data1"), otpValue, new VolleyResponseListerner() {
                            @Override
                            public void onResponse(JSONObject response) throws JSONException {
                                View view = VerifyOtpActivity.this.getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }

                                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                                    Session.getInstance(VerifyOtpActivity.this, TAG).createSession(
                                            response.getJSONObject("data").optString("first_name"),
                                            response.getJSONObject("data").optString("user_email"),
                                            response.getJSONObject("data").optString("token"),
                                            response.getJSONObject("data").optString("ID"),response.getJSONObject("data").optString("mobile"));
                                    CommonMethod.showSnackbar(txt4, response.optString("resultmessage"), VerifyOtpActivity.this);
                                    if (getIntent().getStringExtra("data2").equalsIgnoreCase("forgotPasswd")) {
                                        CommonMethod.changeActivityText(VerifyOtpActivity.this, SetPasswdActivity.class, getIntent().getStringExtra("data1"), "", "");
                                    } else {

                                        CommonMethod.changeActivity(VerifyOtpActivity.this, DashBoardActivity.class);
                                    }
                                    finish();
                                } else if (response.optString("resultcode").equalsIgnoreCase("400")) {
                                    CommonMethod.showSnackbar(txt4, response.optString("resultmessage"), VerifyOtpActivity.this);
                                }
                            }

                            @Override
                            public void onError(String message, String title) {

                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @OnClick({R.id.changeMail, R.id.resendCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.changeMail:
//                CommonMethod.changeActivity(VerifyOtpActivity.this, SendOtpActivity.class);
                finish();
                break;
            case R.id.resendCode:
                new WebServices(VerifyOtpActivity.this, TAG).sendOtp(getIntent().getStringExtra("data"), getIntent().getStringExtra("data1"),"","", new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        if (response.optString("resultcode").equalsIgnoreCase("200")) {
                            CommonMethod.showSnackbar(resendCode, response.optString("resultmessage"), VerifyOtpActivity.this);
                        } else if (response.optString("resultcode").equalsIgnoreCase("504")) {
                            CommonMethod.showSnackbar(resendCode, response.optString("resultmessage"), VerifyOtpActivity.this);
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
