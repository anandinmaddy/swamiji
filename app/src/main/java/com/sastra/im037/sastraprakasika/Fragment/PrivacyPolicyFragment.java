package com.sastra.im037.sastraprakasika.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sastra.im037.sastraprakasika.Fragment.NewFragments.MyAccountFragment;
import com.sastra.im037.sastraprakasika.R;
import com.sastra.im037.sastraprakasika.VolleyResponseListerner;
import com.sastra.im037.sastraprakasika.Webservices.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyPolicyFragment extends Fragment {

    RelativeLayout common_dragview;
    TextView webview;
    ImageView back;
    TextView title;
    public static final String TAG = PrivacyPolicyFragment.class.getSimpleName();


    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_privacy_policy, container, false);
        webview=(TextView)view.findViewById(R.id.webview);

         title = getActivity().findViewById(R.id.title);
         back = getActivity().findViewById(R.id.back);

        title.setText("Privacy Policy");
        back.setVisibility(View.VISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAccountFragment fragment2 = new MyAccountFragment();
                FragmentManager fragmentManager = getFragmentManager();
                back.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonActivityFrameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });


        SetAboutDetail();

        return view;
    }

    private void SetAboutDetail() {
        new WebServices(getContext(), TAG).getAboutPageDetails("1905", new VolleyResponseListerner() {

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
