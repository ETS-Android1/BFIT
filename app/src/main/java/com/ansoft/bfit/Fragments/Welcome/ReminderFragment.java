package com.ansoft.bfit.Fragments.Welcome;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.ansoft.bfit.Database.SPDataManager;
import com.ansoft.bfit.MainActivity;
import com.ansoft.bfit.R;
import com.ansoft.bfit.Util.AlarmReceiver;
import com.ansoft.bfit.Util.LocalData;
import com.ansoft.bfit.Util.NotificationScheduler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderFragment extends Fragment {



    AppCompatActivity activity;




    public ReminderFragment() {
        // Required empty public constructor
    }


    public ReminderFragment(AppCompatActivity appCompatActivity) {
        this.activity=appCompatActivity;
    }

    Button finishBtn;
    TimePicker timePicker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reminder, container, false);
        finishBtn=view.findViewById(R.id.finishBtn);
        timePicker=view.findViewById(R.id.reminderTimePicker);
        //timePicker.setIs24HourView(true);
        ImageView closeIcon;
        closeIcon=view.findViewById(R.id.backIcon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Time", timePicker.getHour()+"");



                LocalData localData;

                localData = new LocalData(getContext());

                localData.set_hour(timePicker.getHour());
                localData.set_min(timePicker.getMinute());
                NotificationScheduler.setReminder(getContext(), AlarmReceiver.class, localData.get_hour(), localData.get_min());



                SPDataManager.setReminderTime(timePicker.getHour()+":"+timePicker.getMinute(), activity);
                SPDataManager.setDataTaken(true, activity);
                activity.finish();
                Intent intent=new Intent(activity, MainActivity.class);
                activity.startActivity(intent);

            }
        });
        return view;
    }

}
