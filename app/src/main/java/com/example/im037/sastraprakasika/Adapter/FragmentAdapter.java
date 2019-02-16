package com.example.im037.sastraprakasika.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.im037.sastraprakasika.Fragment.DownloadsFragment;
import com.example.im037.sastraprakasika.Fragment.DownloadsFragmentNew;
import com.example.im037.sastraprakasika.Fragment.LecturesFragment_Audioplay;
import com.example.im037.sastraprakasika.Fragment.PlaylistsFragment;
import com.example.im037.sastraprakasika.Fragment.TopicsFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private String[]title;
    private Map<Integer,String>mFragmentTags;
    private Activity activity;
    //added me
    private Context context;

    public FragmentAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title=title;
        mFragmentTags=new HashMap<Integer, String>();
//        this.activity = activity1;

    }

    @Override
    public Fragment getItem(int position) {

        switch (title[position]) {
            case "Topics":
                return new TopicsFragment();
//            case "Lectures":
//                return new LecturesFragment();
            case "Lectures":
//                Intent intent = new Intent(context,Mediaplay_MainActivity.class);
//                context.startActivity(intent);

//                activity.startActivity(new Intent(activity, Mediaplay_MainActivity.class));

//                return new LecturesFragmentNew();
//
                //return new LecturesFragment_Audioplay();
                return new LecturesFragment_Audioplay();
//                return new LecturesFragment_new();

            case "Download":
              //  return new LecturesFragment_Audioplay();
            return new DownloadsFragment();

//                return new DownloadsFragment();
               // return new DownloadsFragmentNew();
            case "Playlists":

              return new PlaylistsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            String tag = fragment.getTag();
            mFragmentTags.put(position, tag);
        }
        return object;
    }
}
