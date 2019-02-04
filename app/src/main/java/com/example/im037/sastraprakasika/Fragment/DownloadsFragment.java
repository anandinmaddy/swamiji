package com.example.im037.sastraprakasika.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.im037.sastraprakasika.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DownloadsFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.song_letter)
    TextView songLetter;
    @BindView(R.id.ablum_image)
    ImageView ablumImage;
    @BindView(R.id.album_title)
    TextView albumTitle;
    @BindView(R.id.play_icon)
    ImageView playIcon;
    @BindView(R.id.song_type)
    TextView songType;
    @BindView(R.id.duration)
    TextView duration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloads, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
