package com.example.im037.sastraprakasika.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.im037.sastraprakasika.Adapter.LecturesRecyclerviewAdapter;
import com.example.im037.sastraprakasika.Model.ListOfLecturesModels;
import com.example.im037.sastraprakasika.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LecturesFragment extends Fragment {


    @BindView(R.id.lectures_recyclerview)
    RecyclerView lecturesRecyclerview;


    ArrayList<ListOfLecturesModels> listOfLecturesModels = new ArrayList<>();
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lectures, container, false);
        unbinder = ButterKnife.bind(this, view);
        for (int i = 0; i < 4; i++) {
            listOfLecturesModels.add(new ListOfLecturesModels("", "An overview of Yoga", "", "", "A"));
            listOfLecturesModels.add(new ListOfLecturesModels("", "Insight into Human Pursuits(Purusartha)", "", "", "I"));
            listOfLecturesModels.add(new ListOfLecturesModels("", "Right Action & Right Attitude", "", "", "R"));
            listOfLecturesModels.add(new ListOfLecturesModels("", "Scriptures(Sastram)", "", "", "S"));
        }
        LecturesRecyclerviewAdapter adapter = new LecturesRecyclerviewAdapter(getActivity(), listOfLecturesModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lecturesRecyclerview.setLayoutManager(linearLayoutManager);
        lecturesRecyclerview.setItemAnimator(new DefaultItemAnimator());
        lecturesRecyclerview.setAdapter(adapter);


        return view;
    }
}




