package com.ansoft.bfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.Database.WorkoutID;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Iterator;

public class WorkoutMoreActivity extends AppCompatActivity {

    int index, day, level;
    ArrayList<WorkoutDay> workoutDayArrayList;

    TextView tvTitle, tvDesc, tvIndex, tvIndex2;
    ImageView imgGIf, icLeft, icRight;
    AppCompatImageView backIcon;

    AnimationDrawable animationDrawable;
    RelativeLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_more);

        index = getIntent().getIntExtra(getString(R.string.index), 0);
        day = getIntent().getIntExtra(getString(R.string.day), 1);
        level = getIntent().getIntExtra(getString(R.string.level), 1);
        workoutDayArrayList = new ArrayList<>();
        Iterator<WorkoutDay> iterator = Select.from(WorkoutDay.class)
                .where(Condition.prop("day").eq(getIntent().getIntExtra(getString(R.string.day), 1)),
                        Condition.prop("level").eq(getIntent().getIntExtra(getString(R.string.level), 1)))
                .iterator();

        while (iterator.hasNext()) {
            workoutDayArrayList.add(iterator.next());
        }
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvIndex = findViewById(R.id.tvIndex);
        tvIndex2 = findViewById(R.id.tvIndex2);
        imgGIf = findViewById(R.id.imgGIf);
        icLeft = findViewById(R.id.leftIcon);
        icRight = findViewById(R.id.rightIcon);
        loadingLayout=findViewById(R.id.loadingLayout);
        loadingLayout.setVisibility(View.GONE);


        loadCurrentWorkout();

        icRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < workoutDayArrayList.size() - 1) {
                    index++;
                    loadCurrentWorkout();
                }
            }
        });


        icLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index > 0) {
                    index--;
                    loadCurrentWorkout();
                }
            }
        });

        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



    private void loadCurrentWorkout() {
        tvTitle.setText(WorkoutID.getWorkoutFromID(workoutDayArrayList.get(index).getWorkoutid()).getTitle());
        tvDesc.setText(getString(WorkoutID.getWorkoutFromID(workoutDayArrayList.get(index).getWorkoutid()).getDescription()));


        imgGIf.setImageResource(WorkoutID.getWorkoutFromID(workoutDayArrayList.get(index).getWorkoutid()).getGifId());
        animationDrawable = (AnimationDrawable) imgGIf.getDrawable();
        if (!animationDrawable.isRunning()) {
            onWindowFocusChanged(true);
        }
//        Glide.with(getApplicationContext())
//                .load(WorkoutID.getWorkoutFromID(workoutDayArrayList.get(index).getWorkoutid()).getGifId())
//                .into(imgGIf);

        tvIndex.setText((index + 1) + "");
        tvIndex2.setText("/" + workoutDayArrayList.size());

        if (index == workoutDayArrayList.size() - 1) {
            icRight.setVisibility(View.INVISIBLE);
        } else {
            icRight.setVisibility(View.VISIBLE);
        }

        if (index == 0) {
            icLeft.setVisibility(View.INVISIBLE);
        } else {
            icLeft.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animationDrawable.start();
    }
}
