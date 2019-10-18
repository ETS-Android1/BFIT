package com.ansoft.bfit.Fragments;


import android.animation.Animator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ansoft.bfit.DataModel.ReportData;
import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.Database.WorkoutID;
import com.ansoft.bfit.R;
import com.ansoft.bfit.Util.SquareImageView;
import com.ansoft.bfit.WorkoutActivity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutFragment extends Fragment {


    WorkoutDay workoutDay;
    int index;
    AppCompatActivity activity;
    int progress;


    public WorkoutFragment() {
        // Required empty public constructor
    }


    public WorkoutFragment(WorkoutDay workoutDay, int index, AppCompatActivity activity, int progress) {
        this.workoutDay = workoutDay;
        this.index = index;
        this.activity = activity;
        this.progress = progress;
    }

    SquareImageView workoutProfile;
    TextView tvWorkoutTitle, tvWorkoutRep, tvTimer, tvCountDownTimer;
    FloatingActionButton doneBtn;

    int Seconds, Minutes;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;

    int countDown = 5;

    AppCompatImageView backIcon;
    Runnable countDownrunnable;
    Handler countdownHandler;


    ProgressBar workoutProgressBar;

    AnimationDrawable animationDrawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        workoutProfile = view.findViewById(R.id.workoutProfile);
        tvWorkoutTitle = view.findViewById(R.id.workoutTitle);
        tvTimer = view.findViewById(R.id.timer);
        doneBtn = view.findViewById(R.id.doneBtn);
        backIcon = view.findViewById(R.id.backIcon);
        workoutProgressBar=view.findViewById(R.id.workoutProgressBar);
        workoutProgressBar.setProgress(progress);


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutDay.setCompleted(true);
                Log.e("Saved", workoutDay.save()+"");
                ((WorkoutActivity) activity).logTime(MillisecondTime);


                Calendar cal1 = Calendar.getInstance();
                int currentDaycmp = cal1.get(Calendar.DAY_OF_MONTH);
                int currentMonthcmp = cal1.get(Calendar.MONTH);
                int currentYearcmp = cal1.get(Calendar.YEAR);


                ReportData reportData = Select.from(ReportData.class)
                        .where(Condition.prop("year").eq(currentYearcmp),
                                Condition.prop("month").eq(currentMonthcmp),
                                Condition.prop("day").eq(currentDaycmp)).first();

                if (reportData != null) {
                    reportData.addTotalSeconds(Seconds);
                    reportData.addTotalCalorieBurned((workoutDay.getRep()* WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getKcalBurn()));
                    reportData.addTotalCompleted(1);
                    reportData.save();
                } else {
                    reportData = new ReportData(currentYearcmp, currentMonthcmp, currentDaycmp);
                    reportData.addTotalSeconds(Seconds);
                    reportData.addTotalCalorieBurned((workoutDay.getRep()* WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getKcalBurn()));
                    reportData.addTotalCompleted(1);
                    reportData.save();
                }

                ((WorkoutActivity) activity).nextTransition();
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        workoutProfile.setImageResource(WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getGifId());
        animationDrawable=(AnimationDrawable)workoutProfile.getDrawable();

//        Glide.with(getContext())
//
//                .load(WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getGifId())
//
//                .into(workoutProfile);

        tvWorkoutTitle.setText(WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getTitle());

        String rep;
        if (workoutDay.getReptype() == 0) {
            rep = "x " + workoutDay.getRep();
        } else {
            rep = workoutDay.getRep() + " s";
        }

        tvWorkoutRep = view.findViewById(R.id.tvWorkoutRep);
        tvWorkoutRep.setText(rep);

        tvCountDownTimer = view.findViewById(R.id.tvCountDownTimer);


        startCountDown();

        return view;
    }






    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;


            tvTimer.setText("" + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }

    };

    public void startCountDown() {

        countdownHandler = new Handler();
        countDownrunnable = new Runnable() {
            public void run() {
                countDown -= 1;
                tvCountDownTimer.setText(countDown + "");
                if (countDown > 0) {


                    YoYo.with(Techniques.Tada)
                            .duration(1000)
                            .playOn(tvCountDownTimer);
                    try {
                        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.td_countdown);
                        mp.start();
                    }catch (Exception e){

                    }
                    countdownHandler.postDelayed(this, 1000);
                } else {


                    try {
                        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.td_whistle);
                        mp.start();
                    }catch (Exception e){

                    }
                    animationDrawable.start();
                    handler = new Handler();
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    tvCountDownTimer.setVisibility(View.GONE);
                    doneBtn.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.Tada)
                            .duration(1000)
                            .playOn(doneBtn);

                }
            }

        };
        countdownHandler.postDelayed(countDownrunnable, 1000);
        ;
    }


    @Override
    public void onPause() {

        try {
            handler.removeCallbacks(runnable);
            countdownHandler.removeCallbacks(countDownrunnable);
        }catch (Exception e){
            Log.e("Handler not initiated", "true");
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {

        try {
            handler.removeCallbacks(runnable);
            countdownHandler.removeCallbacks(countDownrunnable);
        }catch (Exception e){
            Log.e("Handler not initiated", "true");
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {

        try {
            handler.removeCallbacks(runnable);
            countdownHandler.removeCallbacks(countDownrunnable);
        }catch (Exception e){
            Log.e("Handler not initiated", "true");
        }
        super.onDetach();
    }

    @Override
    public void onDestroyView() {

        try {
            handler.removeCallbacks(runnable);
            countdownHandler.removeCallbacks(countDownrunnable);
        }catch (Exception e){
            Log.e("Handler not initiated", "true");
        }
        super.onDestroyView();

    }


}
