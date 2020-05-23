package com.ansoft.bfit;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.Database.WorkoutID;
import com.ansoft.bfit.Model.Discover;
import com.ansoft.bfit.Util.AppConstants;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DiscoverWorkoutMoreActivity extends AppCompatActivity {

    int index;
    ArrayList<WorkoutDay> workoutDayArrayList;

    TextView tvTitle, tvDesc, tvIndex, tvIndex2;
    ImageView imgGIf, icLeft, icRight;
    AppCompatImageView backIcon;

    AnimationDrawable animationDrawable;


    String id;
    RelativeLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_more);

        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        } else {
            finish();
        }
        index = getIntent().getIntExtra(getString(R.string.index), 0);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvIndex = findViewById(R.id.tvIndex);
        tvIndex2 = findViewById(R.id.tvIndex2);
        imgGIf = findViewById(R.id.imgGIf);
        icLeft = findViewById(R.id.leftIcon);
        icRight = findViewById(R.id.rightIcon);
        loadingLayout = findViewById(R.id.loadingLayout);
        loadingLayout.setVisibility(View.VISIBLE);


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

        icRight.setClickable(false);
        icLeft.setClickable(false);
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getDiscover();

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
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }


    public void getDiscover() {
        workoutDayArrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.workout_url) + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Booking Response", response.toString());
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
                                workoutDayArrayList.add(workoutDay);
                            }

                            loadingLayout.setVisibility(View.GONE);
                            loadCurrentWorkout();
                            icRight.setClickable(true);
                            icLeft.setClickable(true);
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
}
