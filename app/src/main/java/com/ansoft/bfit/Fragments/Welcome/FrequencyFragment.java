package com.ansoft.bfit.Fragments.Welcome;


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
public class FrequencyFragment extends Fragment {

    AppCompatActivity activity;




    public FrequencyFragment() {
        // Required empty public constructor
    }


    public FrequencyFragment(AppCompatActivity appCompatActivity) {
        this.activity=appCompatActivity;
    }

    Button nextBtn;
    RadioGroup frequencyRD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_frequency, container, false);
        nextBtn=view.findViewById(R.id.nextBtn);
        frequencyRD=view.findViewById(R.id.frequencyRD);
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
                int selectedId = frequencyRD.getCheckedRadioButtonId();
                View radioButton = frequencyRD.findViewById(selectedId);
                if (radioButton == null) {
                    Toast.makeText(activity, "Please select one", Toast.LENGTH_SHORT).show();
                } else {
                    int idx = frequencyRD.indexOfChild(radioButton);
                    SPDataManager.setFrequency(idx, activity);
                    ((WelcomeActivity)activity).navigateFragment(new ActivityFragment(activity));
                }
            }
        });
        return view;
    }

}
