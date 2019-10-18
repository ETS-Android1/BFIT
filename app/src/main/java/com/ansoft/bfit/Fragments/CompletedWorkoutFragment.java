package com.ansoft.bfit.Fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ansoft.bfit.DataModel.ReportData;
import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.Database.SPDataManager;
import com.ansoft.bfit.Database.WorkoutID;
import com.ansoft.bfit.R;
import com.ansoft.bfit.WorkoutActivity;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Calendar;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedWorkoutFragment extends Fragment {


    AppCompatActivity activity;


    int day;
    String level;
    int totalWorkout;


    public CompletedWorkoutFragment(AppCompatActivity activity, int lvl, int day, int totalWorkout) {
        this.activity = activity;
        if (lvl == 1) {
            level = activity.getString(R.string.lvl_easy);
        } else if (lvl == 2) {
            level = activity.getString(R.string.lvl_medium);
        } else {
            level = activity.getString(R.string.lvl_hard);
        }
        this.day = day;
        this.totalWorkout = totalWorkout;
    }

    public CompletedWorkoutFragment() {
        // Required empty public constructor
    }

    TextView tvTotalTime, tvCalorieBurned;
    Button finishBtn;

    AppCompatImageView closeIcon;
    KonfettiView konfettiView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_workout, container, false);
        SPDataManager.setProgress(level, day, activity);


        tvTotalTime = view.findViewById(R.id.tvTotalTime);
        tvCalorieBurned = view.findViewById(R.id.tvCalorieBurned);
        finishBtn = view.findViewById(R.id.finishBtn);
        closeIcon = view.findViewById(R.id.backIcon);
        konfettiView = view.findViewById(R.id.viewKonfetti);

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        konfettiView.build()
                .addColors(getContext().getColor(R.color.colorAccent), getContext().getColor(android.R.color.white), Color.YELLOW)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 25000L);

        int Seconds, Minutes;
        Seconds = (int) (((WorkoutActivity) activity).totalTime / 1000);

        Minutes = Seconds / 60;

        Seconds = Seconds % 60;





        Calendar cal1 = Calendar.getInstance();
        int currentDaycmp = cal1.get(Calendar.DAY_OF_MONTH);
        int currentMonthcmp = cal1.get(Calendar.MONTH);
        int currentYearcmp = cal1.get(Calendar.YEAR);


        ReportData reportData = Select.from(ReportData.class)
                .where(Condition.prop("year").eq(currentYearcmp),
                        Condition.prop("month").eq(currentMonthcmp),
                        Condition.prop("day").eq(currentDaycmp)).first();

        if (reportData != null) {
            reportData.setCompleted(true);
            reportData.save();
        }

        tvCalorieBurned.setText("" + String.format("%.2f", ((WorkoutActivity) activity).calculateCaloriesBurned()) + "KCAL");


        tvTotalTime.setText(String.format("%02d", Minutes) + ":"
                + String.format("%02d", Seconds));
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        return view;
    }

}
