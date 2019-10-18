package com.ansoft.bfit.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ansoft.bfit.R;
import com.ansoft.bfit.WorkoutActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {

    int level, day;

    AppCompatActivity activity;

    public FeedbackFragment(int level, int day, AppCompatActivity activity) {
        this.level = level;
        this.day = day;
        this.activity = activity;
    }

    Button fdbEasyBtn, fdbOkayBtn, fdbHardBtn;

    AppCompatImageView closeIcon;
    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        fdbEasyBtn=view.findViewById(R.id.fdbEasyBtn);
        fdbOkayBtn =view.findViewById(R.id.fdbOkayBtn);
        fdbHardBtn=view.findViewById(R.id.fdbHardBtn);
        closeIcon=view.findViewById(R.id.backIcon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((WorkoutActivity)activity).nextTransition();
            }
        });

        fdbEasyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((WorkoutActivity)activity).nextTransition();
            }
        });


        fdbOkayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((WorkoutActivity)activity).nextTransition();
            }
        });


        fdbHardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((WorkoutActivity)activity).nextTransition();
            }
        });
        return view;
    }

}
