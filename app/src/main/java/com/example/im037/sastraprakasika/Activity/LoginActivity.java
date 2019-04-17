package com.example.im037.sastraprakasika.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Fragment.NewFragments.DashBoardNewFragment;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {
    String TAG = "LoginActivity";
    CallbackManager mCallbackManager;
    public static GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    @BindView(R.id.userId)
    EditText userId;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.forgotPasswd)
    TextView forgotPasswd;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.facebook)
    Button facebook;
    @BindView(R.id.google)
    Button google;
    @BindView(R.id.signup)
    Button signup;
    boolean show = true;
    @BindView(R.id.userLayout)
    TextInputLayout userLayout;
    @BindView(R.id.passwdLayout)
    TextInputLayout passwdLayout;
    @BindView(R.id.shimmer_view_container)
    ProgressBar shimmerFrameLayout;
    @BindView(R.id.fullview)
    ScrollView fullview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getSupportActionBar().hide();
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
        // For facebook invalid hashkey getting
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.im037.sastraprakasika",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


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
    }


    private void googleLogin() {

        Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
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
                mGoogleApiClient.clearDefaultAccountAndReconnect();


                callSocialWebService(email_id, name, "gmail", photo);

            } else {
                Log.e(TAG, "Google not connected");
            }

        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(LoginActivity.this, "Signed out" + result, Toast.LENGTH_LONG).show();
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
                                            callSocialWebService(object.getString("email"), object.getString("name"), "facebook", profilePicUrl);
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
                        Toast.makeText(LoginActivity.this, "Err " + exception.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
    }


    private void callSocialWebService(String email, String name, String type, String photo) {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        new WebServices(LoginActivity.this, TAG).socialLogin(email, name, type, new VolleyResponseListerner() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                if (response.optString("resultcode").equalsIgnoreCase("200")) {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    mGoogleApiClient.disconnect();
                    Session.getInstance(LoginActivity.this, TAG).createSession(
                            response.getJSONObject("data").optString("first_name"),
                            response.getJSONObject("data").optString("user_email"),
                            response.getJSONObject("data").optString("token"),
                            response.getJSONObject("data").optString("ID"),"");
//                    startService(new Intent(LoginActivity.this, RegistrationIntentService.class));
                    startActivity(new Intent(LoginActivity.this, DashBoardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    finish();
                } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                    CommonMethod.showSnackbar(facebook, response, LoginActivity.this);
                }
            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }


    @OnClick({R.id.forgotPasswd, R.id.login, R.id.facebook, R.id.google, R.id.signup, R.id.userId})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userId:
                userLayout.setError(null);
                break;
            case R.id.forgotPasswd:
                if (isNetworkConnected()){
                    CommonMethod.changeActivity(LoginActivity.this, ForgotPasswd.class);
                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login:
                if (isNetworkConnected()){
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                    View view1 = LoginActivity.this.getCurrentFocus();
                    if (view1 != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    if (CommonMethod.emailValidation(userId, userLayout, "Enter a valid email address"))
                        if (CommonMethod.checkEmpty(password, passwdLayout, "Enter the password")) {
                            new WebServices(LoginActivity.this, TAG).login(userId.getText().toString().trim(), password.getText().toString().trim(), new VolleyResponseListerner() {
                                @Override
                                public void onResponse(JSONObject response) throws JSONException {
                                    if (response.optString("resultcode").equalsIgnoreCase("200")) {
                                        shimmerFrameLayout.setVisibility(View.GONE);
                                        Session.getInstance(LoginActivity.this, TAG).createSession(
                                                response.getJSONObject("data").optString("first_name"),
                                                response.getJSONObject("data").optString("user_email"),
                                                response.getJSONObject("data").optString("token"),
                                                response.getJSONObject("data").optString("ID"),"");
//                    startService(new Intent(LoginActivity.this, RegistrationIntentService.class));
                                        CommonMethod.showSnackbar(facebook, response.optString("resultmessage"), LoginActivity.this);
                                        CommonMethod.changeActivity(LoginActivity.this, DashBoardActivity.class);
                                        Fragment fragment = new DashBoardNewFragment();
                                    /*    getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fullview, fragment, fragment.getClass().getSimpleName())
                                                .commit();*/
                                        finish();

                                    } else if (response.getString("resultcode").equalsIgnoreCase("400")) {
                                        CommonMethod.showSnackbar(facebook, response.optString("resultmessage"), LoginActivity.this);
                                    }
                                }

                                @Override
                                public void onError(String message, String title) {

                                }
                            });
                        }
                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.facebook:
                if (isNetworkConnected()){
                    facebookLogin();
                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.google:
                if (isNetworkConnected()){
                    googleLogin();
                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signup:
                if (isNetworkConnected()){
                    CommonMethod.changeActivity(LoginActivity.this, SendOtpActivity.class);
                }else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.err_internet_not_conn), Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
