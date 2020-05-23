package com.ansoft.bfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.Database.WorkoutID;
import com.ansoft.bfit.Fragments.CompletedWorkoutFragment;
import com.ansoft.bfit.Fragments.FeedbackFragment;
import com.ansoft.bfit.Fragments.RestFragment;
import com.ansoft.bfit.Fragments.WorkoutFragment;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Iterator;

public class WorkoutActivity extends AppCompatActivity {

    ArrayList<WorkoutDay> workoutDays;

    int fragIndex = 0;

    public  long totalTime = 0;

    ArrayList<Fragment> scheduledFragments;
    public AnimationDrawable animationDrawable;


    RelativeLayout loadingLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        loadingLayout=findViewById(R.id.loadingLayout);
        loadingLayout.setVisibility(View.GONE);

        getData();
        scheduleFragments();
        loadCurrentFragment();
    }


    private void loadCurrentFragment() {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, scheduledFragments.get(fragIndex));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void scheduleFragments() {

        scheduledFragments = new ArrayList<>();
        for (int i = 0; i < workoutDays.size(); i++) {
            WorkoutDay workoutDay = workoutDays.get(i);
            int index;
            if (i == 0) {
                index = 0;
            } else if (i == workoutDays.size() - 1) {
                index = 2;
            } else {
                index = 1;
            }
            int progress=((i+1)*100)/(workoutDays.size());
            scheduledFragments.add(new WorkoutFragment(workoutDay, index, this, progress));
            if (i != workoutDays.size() - 1) {
                int progress2=((i+1)*100)/(workoutDays.size());
                scheduledFragments.add(new RestFragment(workoutDays.get(i+1), this, progress2));
            }
        }
        scheduledFragments.add(new FeedbackFragment(this));
        scheduledFragments.add(new CompletedWorkoutFragment(this, getIntent().getIntExtra(getString(R.string.level), 1), getIntent().getIntExtra(getString(R.string.day), 1), workoutDays.size()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(animationDrawable!=null) {
            animationDrawable.start();
        }
    }



    private void getData() {
        workoutDays = new ArrayList<>();
        Iterator<WorkoutDay> iterator = Select.from(WorkoutDay.class)
                .where(Condition.prop("day").eq(getIntent().getIntExtra(getString(R.string.day), 1)),
                        Condition.prop("level").eq(getIntent().getIntExtra(getString(R.string.level), 1)))
                .iterator();
        while (iterator.hasNext()) {
            workoutDays.add(iterator.next());
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }


    public void nextTransition() {

        if (fragIndex < scheduledFragments.size() - 1) {
            fragIndex += 1;
            loadCurrentFragment();
        } else {
            finish();
        }
    }

    public void logTime(long millis) {
        totalTime += millis;
    }

    public double calculateCaloriesBurned(){
        int totalCaloriesBurned=0;
        for(WorkoutDay workoutDay:workoutDays){
            totalCaloriesBurned+=(workoutDay.getRep()* WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getKcalBurn());
        }
        return totalCaloriesBurned;
    }

    public double getCalorie(){
        int totalCaloriesBurned=0;
        for(WorkoutDay workoutDay:workoutDays){
            totalCaloriesBurned+=(workoutDay.getRep()* WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getKcalBurn());
        }
        return totalCaloriesBurned;
    }
}
