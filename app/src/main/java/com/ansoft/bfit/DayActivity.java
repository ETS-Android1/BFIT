package com.ansoft.bfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.Edits;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ansoft.bfit.Adapter.WorkoutAdapter;
import com.ansoft.bfit.DataModel.Favorite;
import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.Database.WorkoutID;
import com.ansoft.bfit.Model.Workout;
import com.ansoft.bfit.Util.Calculator;
import com.ansoft.bfit.Util.SpacesItemDecoration;
import com.ansoft.bfit.Util.SpacesItemDecorationLinear;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DayActivity extends AppCompatActivity {

    RecyclerView workoutRecyclerView;
    ArrayList<Workout> workoutArrayList;
    ArrayList<WorkoutDay> workoutData;
    WorkoutAdapter workoutAdapter;

    ImageView backIcon;
    TextView tvTitle;
    Button startBtn;

    MaterialFavoriteButton favoriteButton;

    ProgressBar dayProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        setFullscreen();
        SetupData();
        favoriteButton = findViewById(R.id.favBtn);
        if (Select.from(Favorite.class).where(Condition.prop("day").eq(getIntent().getIntExtra(getString(R.string.day), 1)), Condition.prop("level").eq(getIntent().getIntExtra(getString(R.string.level), 1))).first() == null) {
            favoriteButton.setFavorite(false);
        } else {
            favoriteButton.setFavorite(true);
        }

        dayProgressBar = findViewById(R.id.dayProgressBar);
        dayProgressBar.setProgress(Calculator.calculateDayProgress(getIntent().getIntExtra(getString(R.string.day), 1), getIntent().getIntExtra(getString(R.string.level), 1)));

        favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if (favorite) {
                    Log.e("ss", "favoried");
                    Favorite favorite1 = new Favorite(getIntent().getIntExtra(getString(R.string.day), 1), getIntent().getIntExtra(getString(R.string.level), 1));
                    favorite1.save();
                } else {
                    Log.e("ss", "unfavoried");

                    Favorite favorite1 = Select.from(Favorite.class)
                            .where(Condition.prop("day").eq(getIntent().getIntExtra(getString(R.string.day), 1)),
                                    Condition.prop("level").eq(getIntent().getIntExtra(getString(R.string.level), 1))).first();
                    if (favorite1 != null) {
                        favorite1.delete();
                    }
                }
            }
        });
        tvTitle = findViewById(R.id.title);
        tvTitle.setText("Day " + getIntent().getIntExtra(getString(R.string.day), 1));
        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(getString(R.string.level), getIntent().getIntExtra(getString(R.string.level), 1));
                intent.putExtra(getString(R.string.day), getIntent().getIntExtra(getString(R.string.day), 1));
                startActivity(intent);
            }
        });
        PushDownAnim.setPushDownAnimTo(startBtn);

    }

    private void setFullscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void SetupData() {
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        workoutArrayList = new ArrayList<>();
        getData();
        workoutAdapter = new WorkoutAdapter(workoutArrayList, DayActivity.this, getIntent().getIntExtra(getString(R.string.day), 1), getIntent().getIntExtra(getString(R.string.level), 1));
        workoutRecyclerView = findViewById(R.id.workOutRecyclerView);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        workoutRecyclerView.setLayoutManager(manager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing2);
        workoutRecyclerView.addItemDecoration(new SpacesItemDecorationLinear(spacingInPixels));
        //dayListView.addItemDecoration(new DividerItemDecoration(this, 0));
        workoutRecyclerView.setAdapter(workoutAdapter);


    }

    private void getData() {


        workoutData = new ArrayList<>();
        Iterator<WorkoutDay> iterator = Select.from(WorkoutDay.class)
                .where(Condition.prop("day").eq(getIntent().getIntExtra(getString(R.string.day), 1)),
                        Condition.prop("level").eq(getIntent().getIntExtra(getString(R.string.level), 1)))
                .iterator();

        while (iterator.hasNext()) {
            workoutData.add(iterator.next());
        }

        for (WorkoutDay workoutDay : workoutData) {
            String rep = "";
            if (workoutDay != null) {
                if (workoutDay.getReptype() == 0) {
                    rep = "x " + workoutDay.getRep();
                } else {
                    rep = workoutDay.getRep() + " s";
                }
                workoutArrayList.add(new Workout(WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getTitle(), rep, WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getGifId()));
            }
        }

    }
}
