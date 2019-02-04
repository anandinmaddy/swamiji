package com.example.im037.sastraprakasika.Activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.im037.sastraprakasika.Common.BackActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPasswdActivity extends BackActivity {

    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.passLayout)
    TextInputLayout passLayout;
    @BindView(R.id.confirmPassword)
    EditText confirmPassword;
    @BindView(R.id.confirmPassLayout)
    TextInputLayout confirmPassLayout;
    @BindView(R.id.confirm)
    Button confirm;
    String TAG = SetPasswdActivity.class.getSimpleName();
    boolean show = true, show1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_set_passwd, "");
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

    @OnClick({R.id.password, R.id.confirmPassword, R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.password:
                password.setError(null);
                break;
            case R.id.confirmPassword:
                confirmPassLayout.setError(null);
                break;
            case R.id.confirm:
                if (CommonMethod.checkEmpty(password, passLayout, "Enter the password"))
                    if (CommonMethod.checkEmpty(confirmPassword, confirmPassLayout, "Enter the confirm password"))
                        if (password.getText().toString().length() >= 6) {
                            new WebServices(SetPasswdActivity.this, TAG).set_passwd(getIntent().getStringExtra("data"), password.getText().toString().trim(), confirmPassword.getText().toString(), new VolleyResponseListerner() {
                                @Override
                                public void onResponse(JSONObject response) throws JSONException {
                                    if (response.optString("resultcode").equalsIgnoreCase("200")) {
                                        Toast.makeText(SetPasswdActivity.this, response.optString("resultmessage"), Toast.LENGTH_LONG).show();
                                        CommonMethod.clearAllPreviousActivity(SetPasswdActivity.this, LoginActivity.class);
                                    }
                                }

                                @Override
                                public void onError(String message, String title) {

                                }
                            });
                        } else {
                            passLayout.setError("Password should be min six characters");
                        }
                break;
        }
    }
}
