package com.sastra.im037.sastraprakasika.Fragment.NewFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Activity.LoginActivity;
import com.sastra.im037.sastraprakasika.Common.CommonActivity;
import com.sastra.im037.sastraprakasika.Common.CommonMethod;
import com.sastra.im037.sastraprakasika.Fragment.HelpSupportFragment;
import com.sastra.im037.sastraprakasika.Fragment.NotificationSettingFragment;
import com.sastra.im037.sastraprakasika.Fragment.PrivacyPolicyFragment;
import com.sastra.im037.sastraprakasika.Model.ProfileModel;
import com.sastra.im037.sastraprakasika.OnlinePlayer.Constant;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.Session;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;
import com.sastra.im037.sastraprakasika.mediareceiver.NetworkStateReceiverListener;
import com.sastra.im037.sastraprakasika.mediaservice.ConnectivityReceiver;
import com.sastra.im037.sastraprakasika.utils.Selected;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAccountFragment extends Fragment implements NetworkStateReceiverListener {
    ArrayList<ProfileModel> profilelist = new ArrayList<>();
    ArrayList<ProfileModel.ProfileDetailsModel> profileDetaillist = new ArrayList<>();
    String TAG = MyAccountFragment.class.getSimpleName();
    ImageView account, back;
    RelativeLayout common_dragview;
    TextView profileHeading;

    View viewId;
    TextView name;
    TextView mobile;
    TextView Setting;
    TextView notificationSetting;
    TextView helpSupport;
    TextView privacyPolicy;
    TextView logout;
    TextView title;
    TextView email;
    FrameLayout common_shadow;
    ImageView nameEdit;
    ImageView emailEdit;
    ImageView mobileEdit;
    String namechangedValue, mobileNumUpdated;
    LinearLayout offlineViewer;
    TextView offlineLink;
    LinearLayout fullview;
    public static boolean namechanged = false;
    EditText input, input1;
    LinearLayout notificationLayout,helpSupportLayout,privacypolicyLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.activity_account, container, false);
         //   ButterKnife.bind( getActivity() );

        CommonActivity.setSelected( Selected.MYACCOUNT );


        profileHeading = (TextView) view.findViewById(R.id.profile_heading);
        title = (TextView) getActivity().findViewById(R.id.title);
        viewId = (View) view.findViewById(R.id.viewId);
        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        mobile = (TextView) view.findViewById(R.id.mobile);
        Setting = (TextView) view.findViewById(R.id.Setting);
        helpSupport = (TextView) view.findViewById(R.id.notification_setting);
        privacyPolicy = (TextView) view.findViewById(R.id.privacy_policy);
        logout = (TextView) view.findViewById(R.id.logout);
        profileHeading.setVisibility(View.VISIBLE);
        notificationLayout = view.findViewById(R.id.notificationLayout);
        offlineViewer = (LinearLayout) view.findViewById(R.id.offlineViewer);
        offlineLink = view.findViewById(R.id.offlineLectureLink);
        helpSupportLayout = view.findViewById(R.id.helpSupportLayout);
        privacypolicyLayout = view.findViewById(R.id.privacypolicyLayout);
        fullview = (LinearLayout) view.findViewById(R.id.fullview);

        notificationLayout = view.findViewById(R.id.notificationLayout);
            account = getActivity().findViewById( R.id.account );
            back = getActivity().findViewById( R.id.back );
            back.setVisibility( View.GONE );
            common_shadow = getActivity().findViewById( R.id.shadow );
            common_shadow.setVisibility( View.VISIBLE );
            nameEdit = view.findViewById(R.id.name_edit);
            emailEdit = view.findViewById(R.id.email_edit);
            mobileEdit = view.findViewById(R.id.mobile_edit);
            notificationSetting = view.findViewById(R.id.notification_setting);
            privacyPolicy= view.findViewById(R.id.privacy_policy);
            helpSupport= view.findViewById(R.id.help_support);

             title.setText( Html.fromHtml( getResources().getString( R.string.myaccount ) ) );
            helpSupport.setText( Html.fromHtml( getResources().getString( R.string.contest_giveaways ) ) );
            //common_dragview = (RelativeLayout) findViewById(R.id.dragView);
            //common_dragview.setVisibility(View.VISIBLE);
            name.setText( Session.getInstance( getActivity().getApplicationContext(), TAG ).getName() );
            email.setText( Session.getInstance( getActivity().getApplicationContext(), TAG ).getEmail() );
            mobile.setText( Session.getInstance( getActivity().getApplicationContext(), TAG ).getProfile_UpdateMobileNumber() );
            logout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonActivity.setSelected( Selected.DISCOURSES );

                    DashBoardNewFragment fragment2 = new DashBoardNewFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    back.setVisibility(View.GONE);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();
                    Session.getInstance( getActivity().getApplicationContext(), TAG ).logout();
                    CommonMethod.clearAllPreviousActivity( getActivity().getApplicationContext(), LoginActivity.class );
                   // finish();
                }
            } );

            offlineLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("offline",true);
                    MyLibraryFragment fragment2 = new MyLibraryFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    Constant.currentTab = 2;
                    Constant.backPress = true;
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });


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
        notificationLayout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    NotificationSettingFragment fragment2 = new NotificationSettingFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    back.setVisibility(View.GONE);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();

                  //  CommonMethod.changeActivity( getActivity(), NotificationSettingActivity.class );
                }
            } );
            privacypolicyLayout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrivacyPolicyFragment fragment2 = new PrivacyPolicyFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    back.setVisibility(View.GONE);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();


               //     CommonMethod.changeActivity( getActivity(), PrivacyPolicyActivity.class );
                }
            } );

            helpSupportLayout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HelpSupportFragment fragment2 = new HelpSupportFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    back.setVisibility(View.GONE);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                    fragmentTransaction.commit();

                  //  CommonMethod.changeActivity(  getActivity(), HelpSupportActivity.class );
                }
            } );

        return view;
        }


    public void Show() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this.getActivity() );
        builder.setTitle( "Change Name" );

// Set up the input
        final EditText input = new EditText( this.getActivity() );
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text

        input.setInputType( InputType.TYPE_CLASS_TEXT  );
        builder.setView( input );

// Set up the buttons
        builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InputMethodManager im = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(input.getWindowToken(), 0);
                name.setText( input.getText().toString() );
                namechangedValue = name.getText().toString();
                new WebServices( getContext(), TAG ).setprofileUpdate( Session.getInstance( getContext(), TAG ).getUserId(), namechangedValue, Session.getInstance( getContext(), TAG ).getProfile_UpdateMobileNumber(), new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        if (response.optString( "resultcode" ).equalsIgnoreCase( "200" )) {
                            Session.getInstance( getContext(), TAG ).createSession(
                                    response.optJSONObject( "data" ).optString( "first_name" ), Session.getInstance( getContext(), TAG ).getEmail(), Session.getInstance( getContext(), TAG ).getToken(), Session.getInstance( getContext(), TAG ).getUserId(), response.optJSONObject( "data" ).optString( "mobile" ) );
                            CommonMethod.showSnackbar( name, response.optString( "resultmessage" ), getActivity() );
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
                InputMethodManager im = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(input.getWindowToken(), 0);
                dialog.cancel();

            }
        } );
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        builder.show();
    }


    public void Mobileshow() {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setTitle( "Change Mobile Number" );

// Set up the input
        final EditText input = new EditText( getActivity() );
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType( InputType.TYPE_CLASS_NUMBER  );
        builder.setView( input );

// Set up the buttons
        builder.setPositiveButton( "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                InputMethodManager im = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(input.getWindowToken(), 0);
                mobile.setText( input.getText().toString() );
                dialog.cancel();
                mobileNumUpdated = mobile.getText().toString();
                new WebServices(  getActivity(), TAG ).setprofileUpdate( Session.getInstance( getActivity(), TAG ).getUserId(), Session.getInstance( getActivity(), TAG ).getName(), mobileNumUpdated, new VolleyResponseListerner() {
                    @Override
                    public void onResponse(JSONObject response) throws JSONException {
                        if (response.optString( "resultcode" ).equalsIgnoreCase( "200" )) {
                            Session.getInstance(  getActivity(), TAG ).createSession(
                                    response.optJSONObject( "data" ).optString( "first_name" ), Session.getInstance( getActivity(), TAG ).getEmail(), Session.getInstance( getActivity(), TAG ).getToken(), Session.getInstance( getActivity(), TAG ).getUserId(), response.optJSONObject( "data" ).optString( "mobile" ) );

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
                InputMethodManager im = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(input.getWindowToken(), 0);
                dialog.cancel();
            }
        } );
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        builder.show();
    }

    public void setProfileUpdate() {

    }

    @Override
    public void networkAvailable() {
        fullview.setVisibility(View.VISIBLE);
        offlineViewer.setVisibility(View.GONE);
    }

    @Override
    public void networkUnavailable() {
        fullview.setVisibility(View.VISIBLE);
        offlineViewer.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        fullview.setVisibility(View.GONE);
        try {
            ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
            connectivityReceiver.addListener(this);
            getActivity().registerReceiver(connectivityReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
