package com.ansoft.bfit.Fragments.Welcome;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ansoft.bfit.Database.SPDataManager;
import com.ansoft.bfit.R;
import com.ansoft.bfit.Util.ScheduleMaker;
import com.ansoft.bfit.WelcomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePlanFragment extends Fragment {


    AppCompatActivity activity;


    public CreatePlanFragment() {
        // Required empty public constructor
    }


    public CreatePlanFragment(AppCompatActivity appCompatActivity) {
        this.activity = appCompatActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_plan, container, false);
        ImageView closeIcon;
        closeIcon = view.findViewById(R.id.backIcon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        new MyAsyncTask().execute();

        return view;
    }

    public class MyAsyncTask extends AsyncTask<Integer, Void, String> {


        @Override
        protected String doInBackground(Integer... params) {

            double difficultyIndex1, difficultyIndex2, difficultyIndex3;


            int gender = SPDataManager.getGender(activity);
            int mactivity = SPDataManager.getActivity(activity);
            int frequency = SPDataManager.getFrequency(activity);
            int pushup = SPDataManager.getPushup(activity);

            int addINdex = 10;
            double mulIndex = 0.02;
            double genderIndex = 0;

            if (gender == 0) {
                genderIndex += 0.2;
            } else {
                genderIndex += 0.4;
            }


            genderIndex += ((mactivity + addINdex) * mulIndex);
            genderIndex += ((frequency + addINdex) * mulIndex);
            genderIndex += ((pushup + addINdex) * mulIndex);


            difficultyIndex1 = genderIndex * 0.8;
            difficultyIndex2 = genderIndex * 1;
            difficultyIndex3 = genderIndex * 1.2;


            ScheduleMaker.makeSchedule(activity, difficultyIndex1, 1, difficultyIndex2, 2, difficultyIndex3, 3);

            return "OK";
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(activity, "We have created a perfect plan for you!", Toast.LENGTH_LONG).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((WelcomeActivity) activity).navigateFragment(new ReminderFragment(activity));
                }
            }, 2000);
        }
    }

}
