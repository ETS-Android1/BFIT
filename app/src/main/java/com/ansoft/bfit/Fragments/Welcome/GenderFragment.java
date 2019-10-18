package com.ansoft.bfit.Fragments.Welcome;


import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ansoft.bfit.Database.SPDataManager;
import com.ansoft.bfit.R;
import com.ansoft.bfit.WelcomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenderFragment extends Fragment {

    AppCompatActivity activity;


    public GenderFragment() {
        // Required empty public constructor
    }


    public GenderFragment(AppCompatActivity appCompatActivity) {
        this.activity = appCompatActivity;
    }

    Button nextBtn;
    RadioGroup genderRD;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gender, container, false);
        nextBtn = view.findViewById(R.id.nextBtn);
        genderRD = view.findViewById(R.id.genderRD);
        ImageView closeIcon;
        closeIcon=view.findViewById(R.id.backIcon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = genderRD.getCheckedRadioButtonId();


                View radioButton = genderRD.findViewById(selectedId);
                if (radioButton == null) {
                    Toast.makeText(activity, "Please select the gender", Toast.LENGTH_SHORT).show();
                } else {
                    int idx = genderRD.indexOfChild(radioButton);
                    SPDataManager.setGender(idx, activity);
                    ((WelcomeActivity) activity).navigateFragment(new FrequencyFragment(activity));
                }
            }
        });
        return view;
    }

}
