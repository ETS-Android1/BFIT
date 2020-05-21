package com.ansoft.bfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ansoft.bfit.Adapter.DayAdapter;
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
    AppCompatImageView actionMenuRestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        getDays();
        tvTitle = findViewById(R.id.title);
        tvTitle.setText(getIntent().getStringExtra(getString(R.string.level)));
        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LevelActivity.this, DayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(getString(R.string.level), level);
                intent.putExtra(getString(R.string.day), SPDataManager.getProgress(getIntent().getStringExtra(getString(R.string.level)), LevelActivity.this)+1);
                startActivity(intent);
            }
        });
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

        actionMenuRestore = findViewById(R.id.menuActionRestore);
        actionMenuRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(LevelActivity.this, actionMenuRestore);
                popup.getMenuInflater().inflate(R.menu.restore_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menuRestore) {
                            SPDataManager.setProgress(getIntent().getStringExtra(getString(R.string.level)), 0, LevelActivity.this);
                            getDays();
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
    }

    @Override
    protected void onResume() {
        getDays();
        super.onResume();
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
