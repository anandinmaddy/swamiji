package com.example.im037.sastraprakasika.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Common.CommonActivity;
import com.example.im037.sastraprakasika.Common.CommonMethod;
import com.example.im037.sastraprakasika.Model.ProfileModel;
import com.example.im037.sastraprakasika.R;
import com.example.im037.sastraprakasika.Session;
import com.example.im037.sastraprakasika.VolleyResponseListerner;
import com.example.im037.sastraprakasika.Webservices.WebServices;
import com.example.im037.sastraprakasika.utils.Selected;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAccountActivity extends CommonActivity {
    ArrayList<ProfileModel> profilelist = new ArrayList<>();
    ArrayList<ProfileModel.ProfileDetailsModel> profileDetaillist = new ArrayList<>();
    String TAG = MyAccountActivity.class.getSimpleName();
    ImageView account, back;
    RelativeLayout common_dragview;
    @BindView(R.id.profile_heading)
    TextView profileHeading;
    @BindView(R.id.viewId)
    View viewId;
    @BindView(R.id.name_title)
    TextView nameTitle;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.email_title)
    TextView emailTitle;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.mobile_title)
    TextView mobileTitle;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.Setting)
    TextView Setting;
    @BindView(R.id.notification_setting)
    TextView notificationSetting;
    @BindView(R.id.help_support)
    TextView helpSupport;
    @BindView(R.id.privacy_policy)
    TextView privacyPolicy;
    @BindView(R.id.logout)
    TextView logout;
    FrameLayout common_shadow;
    @BindView(R.id.name_edit)
    ImageView nameEdit;
    @BindView(R.id.email_edit)
    ImageView emailEdit;
    @BindView(R.id.mobile_edit)
    ImageView mobileEdit;
    String namechangedValue, mobileNumUpdated;
    public static boolean namechanged = false;
    EditText input, input1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setView( R.layout.activity_account, "My Account" );
        ButterKnife.bind( this );

        setSelected( Selected.MYACCOUNT );

        account = findViewById( R.id.account );
        back = findViewById( R.id.back );
        back.setVisibility( View.GONE );
        common_shadow = findViewById( R.id.shadow );
        common_shadow.setVisibility( View.VISIBLE );
        helpSupport.setText( Html.fromHtml( getResources().getString( R.string.contest_giveaways ) ) );
        //common_dragview = (RelativeLayout) findViewById(R.id.dragView);
        //common_dragview.setVisibility(View.VISIBLE);
        name.setText( Session.getInstance( MyAccountActivity.this, TAG ).getName() );
        email.setText( Session.getInstance( MyAccountActivity.this, TAG ).getEmail() );
        mobile.setText( Session.getInstance( MyAccountActivity.this, TAG ).getProfile_UpdateMobileNumber() );
        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.getInstance( getApplicationContext(), TAG ).logout();
                CommonMethod.clearAllPreviousActivity( MyAccountActivity.this, LoginActivity.class );
                finish();
            }
        } );
        nameEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Show();
            }
        } );

        emailEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Show();
            }
        } );


        mobileEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mobileshow();
            }
        } );
//        if(namechanged!=false){
//            name.setText(Session.getInstance(MyAccountActivity.this, TAG).getProfile_UpdateName());
//            mobile.setText(Session.getInstance(MyAccountActivity.this, TAG).getProfile_UpdateMobileNumber());
//
//        }else{
//            name.setText(Session.getInstance(MyAccountActivity.this, TAG).getName());
//            mobile.setText("");
//
//        }
        notificationSetting.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.changeActivity( MyAccountActivity.this, NotificationSettingActivity.class );
            }
        } );
        privacyPolicy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.changeActivity( MyAccountActivity.this, PrivacyPolicyActivity.class );
            }
        } );

        helpSupport.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.changeActivity( MyAccountActivity.this, HelpSupportActivity.class );
            }
        } );
    }

    public void Show() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Change Name" );

// Set up the input
        final EditText input = new EditText( this );
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text


// Set up the buttons
        builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                name.setText( input.getText().toString() );
                namechangedValue = name.getText().toString();
              /*  new WebServices( MyAccountActivity.this, TAG ).setprofileUpdate( Session.getInstance( MyAccountActivity.this, TAG ).getUserId(), namechangedValue, Session.getInstance( MyAccountActivity.this, TAG ).getProfile_UpdateMobileNumber(), new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        if (response.optString( "resultcode" ).equalsIgnoreCase( "200" )) {
                            Session.getInstance( MyAccountActivity.this, TAG ).createSession(
                                    response.optJSONObject( "data" ).optString( "first_name" ), Session.getInstance( MyAccountActivity.this, TAG ).getEmail(), Session.getInstance( MyAccountActivity.this, TAG ).getToken(), Session.getInstance( MyAccountActivity.this, TAG ).getUserId(), response.optJSONObject( "data" ).optString( "mobile" ) );
                            CommonMethod.showSnackbar( name, response.optString( "resultmessage" ), MyAccountActivity.this );
                        }
                    }

                    @Override
                    public void onError(String message, String title) {

                    }
                } );*/
            }
        } );
        builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        } );

        builder.show();
    }

    public void Mobileshow() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Change Mobile Number" );

// Set up the input
        final EditText input = new EditText( this );
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PHONETIC );
        builder.setView( input );

// Set up the buttons
        builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                mobile.setText( input.getText().toString() );
                dialog.cancel();
                mobileNumUpdated = mobile.getText().toString();
                new WebServices( MyAccountActivity.this, TAG ).setprofileUpdate( Session.getInstance( MyAccountActivity.this, TAG ).getUserId(), Session.getInstance( MyAccountActivity.this, TAG ).getName(), mobileNumUpdated, new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        if (response.optString( "resultcode" ).equalsIgnoreCase( "200" )) {
                            Session.getInstance( MyAccountActivity.this, TAG ).createSession(
                                    response.optJSONObject( "data" ).optString( "first_name" ), Session.getInstance( MyAccountActivity.this, TAG ).getEmail(), Session.getInstance( MyAccountActivity.this, TAG ).getToken(), Session.getInstance( MyAccountActivity.this, TAG ).getUserId(), response.optJSONObject( "data" ).optString( "mobile" ) );

                        }
                    }

                    @Override
                    public void onError(String message, String title) {

                    }
                } );
            }
        } );
        builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        } );

        builder.show();
    }

    public void setProfileUpdate() {

    }


}
