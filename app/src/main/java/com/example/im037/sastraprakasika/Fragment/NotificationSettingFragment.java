package com.example.im037.sastraprakasika.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.Fragment.NewFragments.MyAccountFragment;
import com.example.im037.sastraprakasika.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationSettingFragment extends Fragment {

    TextView title;
    ImageView back;
    public NotificationSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notification_setting, container, false);
        title = getActivity().findViewById(R.id.title);
        back = getActivity().findViewById(R.id.back);

        title.setText("Notification Settings");
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

        return view;
    }

}
