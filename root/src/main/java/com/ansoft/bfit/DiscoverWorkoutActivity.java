package com.ansoft.bfit;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ansoft.bfit.Adapter.WorkoutAdapter;
import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.Database.WorkoutID;
import com.ansoft.bfit.Fragments.CompletedWorkoutFragment;
import com.ansoft.bfit.Fragments.FeedbackFragment;
import com.ansoft.bfit.Fragments.RestFragment;
import com.ansoft.bfit.Fragments.WorkoutFragment;
import com.ansoft.bfit.Model.Discover;
import com.ansoft.bfit.Model.Workout;
import com.ansoft.bfit.Util.AppConstants;
import com.ansoft.bfit.Util.SpacesItemDecorationLinear;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DiscoverWorkoutActivity extends AppCompatActivity {

    ArrayList<WorkoutDay> workoutDays;

    int fragIndex = 0;

    public long totalTime = 0;

    ArrayList<Fragment> scheduledFragments;
    public AnimationDrawable animationDrawable;


    String id;
    RelativeLayout loadingLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        } else {
            finish();
        }
        getDiscover();
        loadingLayout = findViewById(R.id.loadingLayout);
        loadingLayout.setVisibility(View.VISIBLE);
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
            int progress = ((i + 1) * 100) / (workoutDays.size());
            scheduledFragments.add(new WorkoutFragment(workoutDay, index, this, progress));
            if (i != workoutDays.size() - 1) {
                int progress2 = ((i + 1) * 100) / (workoutDays.size());
                scheduledFragments.add(new RestFragment(workoutDays.get(i + 1), this, progress2));
            }
        }
        scheduledFragments.add(new FeedbackFragment(this));
        scheduledFragments.add(new CompletedWorkoutFragment(this, getIntent().getIntExtra(getString(R.string.level), 1), getIntent().getIntExtra(getString(R.string.day), 1), workoutDays.size()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }


    public void getDiscover() {
        workoutDays = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.workout_url) + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject eachDataJsonObject = jsonObject.getJSONObject("data");
                            Discover discover = new Discover();
                            discover.setName(eachDataJsonObject.getString("name"));
                            discover.setDescription(eachDataJsonObject.getString("description"));
                            discover.setDifficulty(eachDataJsonObject.getInt("difficulty"));
                            discover.setThumbnailLink(eachDataJsonObject.getString("thumbnail"));
                            JSONArray workoutArray = eachDataJsonObject.getJSONArray("workouts");
                            for (int k = 0; k < workoutArray.length(); k++) {
                                JSONObject workoutJsonObject = workoutArray.getJSONObject(k);
                                WorkoutDay workoutDay = new WorkoutDay();
                                workoutDay.setWorkoutid(workoutJsonObject.getInt("id"));
                                workoutDay.setRep(workoutJsonObject.getInt("rep"));
                                workoutDay.setReptype(workoutJsonObject.getInt("reptype"));
                                workoutDays.add(workoutDay);
                            }

                            loadingLayout.setVisibility(View.GONE);
                            scheduleFragments();
                            loadCurrentFragment();
                        } catch (JSONException e) {
                            Log.e("Error", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        finish();
                        AppConstants.showVolleyError(error, getApplicationContext());
                    }
                });
        App.getInstance().addToRequestQueue(stringRequest, "asa");

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

    public double calculateCaloriesBurned() {
        int totalCaloriesBurned = 0;
        for (WorkoutDay workoutDay : workoutDays) {
            totalCaloriesBurned += (workoutDay.getRep() * WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getKcalBurn());
        }
        return totalCaloriesBurned;
    }

    public double getCalorie() {
        int totalCaloriesBurned = 0;
        for (WorkoutDay workoutDay : workoutDays) {
            totalCaloriesBurned += (workoutDay.getRep() * WorkoutID.getWorkoutFromID(workoutDay.getWorkoutid()).getKcalBurn());
        }
        return totalCaloriesBurned;
    }
}
