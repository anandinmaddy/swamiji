package com.sastra.im037.sastraprakasika.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sastra.im037.sastraprakasika.Common.CommonMethod;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendOtpActivity extends AppCompatActivity {
    CallbackManager mCallbackManager;
    public static GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.emailId)
    EditText emailId;
    @BindView(R.id.createAcc)
    Button createAcc;
    @BindView(R.id.nameLayout)
    TextInputLayout nameLayout;
    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.passLayout)
    TextInputLayout passLayout;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.mobileLayout)
    TextInputLayout mobileLayout;
    @BindView(R.id.confirmPassword)
    EditText confirmPassword;
    @BindView(R.id.confirmPassLayout)
    TextInputLayout confirmPassLayout;
    @BindView(R.id.facebooksign)
    Button facebooksign;
    @BindView(R.id.googlesign)
    Button googlesign;


    boolean show = true, show1 = true;
    String TAG = SendOtpActivity.class.getSimpleName(), otpValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        ButterKnife.bind(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();


        ((EditText) findViewById(R.id.emailId)).setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE ||
                                event != null &&
                                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed()) {
                                // the user is done typing.
                                View view = SendOtpActivity.this.getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }

                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                }
        );
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
    private void googleLogin() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK && data != null) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        } else if (resultCode == RESULT_OK && data != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            if (mGoogleApiClient.hasConnectedApi(Auth.GOOGLE_SIGN_IN_API)) {
                final GoogleSignInAccount acct = result.getSignInAccount();
                String name = acct.getDisplayName();
                String email_id = acct.getEmail();
                String photo = null;
                if (acct.getPhotoUrl() != null) {
                    Uri uri = acct.getPhotoUrl();
                    photo = uri.toString();
                }

                callSocialWebService(email_id, name, "gmail user", photo);

            } else {
                Log.e(TAG, "Google not connected");
            }

        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(SendOtpActivity.this, "Signed out" + result, Toast.LENGTH_LONG).show();
        }
    }
    private void facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e(TAG, String.valueOf(loginResult.getAccessToken()));
                        Log.e(TAG, String.valueOf(loginResult.getRecentlyDeniedPermissions()));

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.d("TEST", response.toString());
                                        try {
                                            String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                            callSocialWebService(object.getString("email"), object.getString("name"), "facebook user", profilePicUrl);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("Facebook", exception.toString());
                        Toast.makeText(SendOtpActivity.this, "Err " + exception.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
    }
    private void callSocialWebService(String email, String name, String type, String photo) {
        new WebServices(SendOtpActivity.this, TAG).socialLogin(name, email, type, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    Session.getInstance(SendOtpActivity.this, TAG).createSession(
                            response.getJSONObject("data").optString("first_name"),
                            response.getJSONObject("data").optString("user_email"),
                            response.getJSONObject("data").optString("token"),
                            response.getJSONObject("data").optString("ID"),"");
//                    startService(new Intent(LoginActivity.this, RegistrationIntentService.class));
                    CommonMethod.changeActivity(SendOtpActivity.this, DashBoardActivity.class);
                    finish();

                } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                    CommonMethod.showSnackbar(facebooksign, response, SendOtpActivity.this);
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }


    @OnClick({R.id.createAcc, R.id.name, R.id.emailId, R.id.login,R.id.googlesign,R.id.facebooksign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                CommonMethod.clearAllPreviousActivity(SendOtpActivity.this, LoginActivity.class);
                break;
            case R.id.name:
                nameLayout.setError(null);
                break;

            case R.id.emailId:
                emailLayout.setError(null);

                break;
            case R.id.createAcc:
                if (CommonMethod.checkEmpty(name, nameLayout, "Enter your name"))
                    if (CommonMethod.emailValidation(emailId, emailLayout, "Enter a valid email address"))
                        if (CommonMethod.checkEmpty(mobile, mobileLayout, "Enter your mobile number"))
                            if (mobile.getText().toString().length() == 10){
                                if (CommonMethod.checkEmpty(password, passLayout, "Enter the password"))
                                    if (CommonMethod.checkEmpty(confirmPassword, confirmPassLayout, "Enter the confirm password"))
                                        if (password.getText().toString().length() >= 6){
                                            new WebServices(SendOtpActivity.this, TAG).sendOtp(name.getText().toString().trim(), emailId.getText().toString().trim(),password.getText().toString().trim(),mobile.getText().toString().trim(), new VolleyResponseListerner() {
                                                @Override
                                                public void onResponse(JSONObject response) throws JSONException {
                                                    if (response.optString("resultcode").equalsIgnoreCase("200")) {
                                                        Session.getInstance(SendOtpActivity.this, TAG).createSession(
                                                                response.getJSONObject("data").optString("first_name"),
                                                                response.getJSONObject("data").optString("user_email"),
                                                                response.getJSONObject("data").optString("token"),
                                                                response.getJSONObject("data").optString("ID"),"");
                                                        CommonMethod.showSnackbar(createAcc, response.optString("resultmessage"), SendOtpActivity.this);
                                                        CommonMethod.changeActivityText(SendOtpActivity.this, VerifyOtpActivity.class, name.getText().toString().trim(), emailId.getText().toString().trim(), "");

                                                    } else if (response.optString("resultcode").equalsIgnoreCase("504")) {
                                                        CommonMethod.showSnackbar(createAcc, response.optString("resultmessage"), SendOtpActivity.this);
                                                    }
                                                }

                                                @Override
                                                public void onError(String message, String title) {

                                                }
                                            });
                                        }
                                else{
                                            passLayout.setError("Password should be min six characters");
                                }
                            }
                        else{
                                mobileLayout.setError("Enter a valid mobile number");
                            }



                break;
            case R.id.googlesign:
                googleLogin();
                break;
            case R.id.facebooksign:
                facebookLogin();
                break;
        }
    }


}
