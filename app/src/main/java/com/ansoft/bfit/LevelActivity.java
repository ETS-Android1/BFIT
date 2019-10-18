package com.ansoft.bfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ansoft.bfit.Adapter.DayAdapter;
import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.Database.SPDataManager;
import com.ansoft.bfit.Model.Day;
import com.ansoft.bfit.Util.Calculator;
import com.ansoft.bfit.Util.SpacesItemDecoration;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity {


    RecyclerView dayListView;
    ArrayList<Day> dayList;
    DayAdapter dayAdapter;
    TextView tvTitle;
    int level = 1;
    ProgressBar lvlProgressbar;
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        startFullScreen();
        getDays();
        tvTitle = findViewById(R.id.title);
        tvTitle.setText(getIntent().getStringExtra(getString(R.string.level)));
        startBtn = findViewById(R.id.startBtn);
        PushDownAnim.setPushDownAnimTo(startBtn);
        lvlProgressbar = findViewById(R.id.lvlProgressBar);

        if (getIntent().getStringExtra(getString(R.string.level)).equalsIgnoreCase(getString(R.string.lvl_easy))) {
            level = 1;
        } else if (getIntent().getStringExtra(getString(R.string.level)).equalsIgnoreCase(getString(R.string.lvl_medium))) {
            level = 2;
        } else {
            level = 3;
        }
        Log.e("LevelActivity", level + "");
        lvlProgressbar.setProgress(Calculator.calculateLevelProgress(level));
    }

    @Override
    protected void onResume() {
        getDays();
        super.onResume();
    }

    private void startFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void getDays() {
        dayListView = findViewById(R.id.dayRecyclerView);
        dayList = new ArrayList<>();
        fillDays();
        dayAdapter = new DayAdapter(dayList, getApplicationContext(), SPDataManager.getProgress(getIntent().getStringExtra(getString(R.string.level)), LevelActivity.this), level);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 4);
        dayListView.setLayoutManager(manager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        try {
            dayListView.removeItemDecorationAt(0);
        } catch (Exception e) {
        }
        dayListView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        //dayListView.addItemDecoration(new DividerItemDecoration(this, 0));
        dayListView.setAdapter(dayAdapter);
    }

    private void fillDays() {

        int currentProgress = SPDataManager.getProgress(getIntent().getStringExtra(getString(R.string.level)), LevelActivity.this);


        for (int i = 1; i < 31; i++) {
            if (i > currentProgress) {
                dayList.add(new Day(i, 0));
            } else {
                dayList.add(new Day(i, 100));
            }
        }
    }
}
