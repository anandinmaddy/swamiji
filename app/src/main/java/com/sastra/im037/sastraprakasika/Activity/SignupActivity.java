package com.sastra.im037.sastraprakasika.Activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class SignupActivity extends BackActivity {


    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirmPassword)
    EditText confirmPassword;
    @BindView(R.id.createAcc)
    Button createAcc;
    @BindView(R.id.passLayout)
    TextInputLayout passLayout;
    @BindView(R.id.confirmPassLayout)
    TextInputLayout confirmPassLayout;

    boolean show = true, show1 = true;
    String TAG = SignupActivity.class.getSimpleName(), otpValue;

    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.mobileLayout)
    TextInputLayout mobileLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_signup2, "");
        ButterKnife.bind(this);

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passwd = password.getText().toString().trim();
                if (!s.toString().equalsIgnoreCase(""))
                    if (passwd.length() > start) {
                        if (String.valueOf(s.charAt(start)).equalsIgnoreCase(String.valueOf(passwd.charAt(start)))) {
                            confirmPassLayout.setError(null);
                        } else {
                            confirmPassLayout.setError("password mismatch");
                            confirmPassword.setText("");
                        }
                    } else {
                        confirmPassLayout.setError("password mismatch");
                        confirmPassword.setText("");
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (show) {
                            password.setTransformationMethod(null);
                            show = false;
                            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                        } else {
                            password.setTransformationMethod(new PasswordTransformationMethod());
                            show = true;
                            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_close, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        confirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (confirmPassword.getRight() - confirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (show1) {
                            confirmPassword.setTransformationMethod(null);
                            show1 = false;
                            confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                        } else {
                            confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                            show1 = true;
                            confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_close, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.createAcc, R.id.password, R.id.confirmPassword, R.id.mobile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mobile:
                mobileLayout.setError(null);
                break;
            case R.id.password:
                passLayout.setError(null);
                break;
            case R.id.confirmPassword:
                confirmPassLayout.setError(null);
                break;
            case R.id.createAcc:
                if (CommonMethod.checkEmpty(mobile, mobileLayout, "Enter your mobile number"))
                    if (mobile.getText().toString().length() == 10) {
                        if (CommonMethod.checkEmpty(password, passLayout, "Enter the password"))
                            if (CommonMethod.checkEmpty(confirmPassword, confirmPassLayout, "Enter the confirm password"))
                                if (password.getText().toString().length() >= 6) {
                                    new WebServices(SignupActivity.this, TAG).register(getIntent().getStringExtra("data"), mobile.getText().toString().trim(), confirmPassword.getText().toString().trim(), new VolleyResponseListerner() {
                                        @Override
                                        public void onResponse(JSONObject response) throws JSONException {
                                            if (response.optString("resultcode").equalsIgnoreCase("200")) {
                                                Session.getInstance(SignupActivity.this, TAG).createSession(
                                                        response.getJSONObject("data").optString("first_name"),
                                                        response.getJSONObject("data").optString("user_email"),
                                                        response.getJSONObject("data").optString("token"),
                                                        response.optJSONObject("data").optString("ID"),"");
                                                CommonMethod.showSnackbar(createAcc, response.optString("resultmessage"), SignupActivity.this);
                                                CommonMethod.clearAllPreviousActivity(SignupActivity.this, DashBoardActivity.class);
                                                finish();
                                            } else if (response.optString("resultcode").equalsIgnoreCase("504")) {
                                                CommonMethod.showSnackbar(createAcc, response.optString("resultmessage"), SignupActivity.this);
                                            }
                                        }

                                        @Override
                                        public void onError(String message, String title) {

                                        }
                                    });
                                } else {
                                    passLayout.setError("Password should be min six characters");
                                }
                    } else {
                        mobileLayout.setError("Enter a valid mobile number");
                    }
                break;
        }
    }


}
