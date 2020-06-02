package com.ansoft.bfit.Fragments;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.Database.SPDataManager;
import com.ansoft.bfit.Database.WorkoutID;
import com.ansoft.bfit.DiscoverWorkoutActivity;
import com.ansoft.bfit.R;
import com.ansoft.bfit.WorkoutActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestFragment extends Fragment {


    WorkoutDay workoutDay;
    AppCompatActivity activity;

    int progress;

    TextView tvNextWorkoutTitle, tvNextWorkoutRep, tvRestTimer;
    ImageView imgNextWorkout;


    Button addTimeBtn, skipBtn;


    int Seconds, Minutes;
    long MillisecondTime, endTime, TimeBuff, UpdateTime = 0L;
    Handler handler;

    AppCompatImageView closeIcon;

    public RestFragment() {
        // Required empty public constructor
    }

    public RestFragment(WorkoutDay workoutDay, AppCompatActivity activity, int progress) {
        this.workoutDay = workoutDay;
        this.activity = activity;
        this.progress = progress;
    }

    ProgressBar workoutProgressBar;
    AnimationDrawable animationDrawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rest, container, false);

        tvNextWorkoutTitle = view.findViewById(R.id.tvNextWorkoutTitle);
        closeIcon = view.findViewById(R.id.backIcon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        tvNextWorkoutRep = view.findViewById(R.id.tvNextWorkoutRep);
        tvRestTimer = view.findViewById(R.id.restTimer);
        imgNextWorkout = view.findViewById(R.id.imgNextWorkout);
        workoutProgressBar = view.findViewById(R.id.workoutProgressBar);
        workoutProgressBar.setProgress(progress);
        imgNextWorkout.setImageResource(WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getGifId());
        tvNextWorkoutTitle.setText(WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getTitle());
        String rep;
        if (workoutDay.getReptype() == 0) {
            rep = "x " + workoutDay.getRep();
        } else {
            rep = workoutDay.getRep() + " s";
        }
        animationDrawable = (AnimationDrawable) imgNextWorkout.getDrawable();
        animationDrawable.start();

        tvNextWorkoutRep.setText(rep);
        handler = new Handler();
        endTime = SystemClock.uptimeMillis() + SPDataManager.getRestTime(getActivity()) * 1000;
        handler.postDelayed(runnable, 0);
        addTimeBtn = view.findViewById(R.id.addTimeBtn);
        skipBtn = view.findViewById(R.id.skipBtn);
        addTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTime += 30 * 1000;
                Toast.makeText(getContext(), "30 seconds added to rest", Toast.LENGTH_SHORT).show();
            }
        });
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (activity instanceof WorkoutActivity) {
                    ((WorkoutActivity) activity).nextTransition();
                } else if (activity instanceof DiscoverWorkoutActivity) {
                    ((DiscoverWorkoutActivity) activity).nextTransition();
                }
            }
        });
        return view;
    }


    public Runnable runnable = new Runnable() {

        public void run() {
            MillisecondTime = endTime - SystemClock.uptimeMillis();
            if (MillisecondTime > 0) {
                UpdateTime = TimeBuff + MillisecondTime;
                Seconds = (int) (UpdateTime / 1000);
                Minutes = Seconds / 60;
                Seconds = Seconds % 60;
                tvRestTimer.setText("" + String.format("%02d", Minutes) + ":"
                        + String.format("%02d", Seconds));
                handler.postDelayed(this, 0);
            } else {
                if (activity instanceof WorkoutActivity) {
                    ((WorkoutActivity) activity).nextTransition();
                } else if (activity instanceof DiscoverWorkoutActivity) {
                    ((DiscoverWorkoutActivity) activity).nextTransition();
                }
            }
        }

    };

    @Override
    public void onPause() {
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {

        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {

        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {

        }
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {

        }
        super.onDestroyView();
    }
}
