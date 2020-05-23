package com.ansoft.bfit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ansoft.bfit.Adapter.DiscoverAdapter;
import com.ansoft.bfit.Adapter.WorkoutAdapter;
import com.ansoft.bfit.DataModel.Favorite;
import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.Database.WorkoutID;
import com.ansoft.bfit.Model.Discover;
import com.ansoft.bfit.Model.Workout;
import com.ansoft.bfit.Util.AppConstants;
import com.ansoft.bfit.Util.SpacesItemDecorationLinear;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.thekhaeng.pushdownanim.PushDownAnim;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DiscoverActivity extends AppCompatActivity {

    RecyclerView workoutRecyclerView;
    ArrayList<Workout> workoutArrayList;
    WorkoutAdapter workoutAdapter;

    ImageView backIcon;
    TextView tvTitle;
    Button startBtn;

    String id;

    RelativeLayout loadingLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        } else {
            finish();
        }

        workoutArrayList = new ArrayList<>();
        tvTitle = findViewById(R.id.title);
        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), DiscoverWorkoutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        PushDownAnim.setPushDownAnimTo(startBtn);

        SetupData();
        loadingLayout=findViewById(R.id.loadingLayout);
        loadingLayout.setVisibility(View.VISIBLE);
    }


    private void SetupData() {
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getDiscover();

    }


    public void getDiscover() {
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


                            tvTitle.setText(discover.getName());
                            for (int k = 0; k < workoutArray.length(); k++) {
                                JSONObject workoutJsonObject = workoutArray.getJSONObject(k);

                                String rep = "";
                                int reptype=workoutJsonObject.getInt("reptype");

                                if (reptype == 0) {
                                    rep = "x " + workoutJsonObject.getInt("rep");
                                } else {
                                    rep = workoutJsonObject.getInt("rep") + " s";
                                }


                                discover.addWorkout(workoutJsonObject.getInt("id"), workoutJsonObject.getInt("rep"));
                                workoutArrayList.add(new Workout(WorkoutID.getWorkoutFromID(workoutJsonObject.getInt("id")).getTitle(), rep, WorkoutID.getWorkoutFromID(workoutJsonObject.getInt("id")).getGifId()));

                            }
                            loadingLayout.setVisibility(View.GONE);
                            workoutAdapter = new WorkoutAdapter(workoutArrayList, DiscoverActivity.this, getIntent().getIntExtra(getString(R.string.day), 1), getIntent().getIntExtra(getString(R.string.level), 1), id);
                            workoutRecyclerView = findViewById(R.id.workOutRecyclerView);
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
                            workoutRecyclerView.setLayoutManager(manager);
                            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing2);
                            workoutRecyclerView.addItemDecoration(new SpacesItemDecorationLinear(spacingInPixels));
                            workoutRecyclerView.setAdapter(workoutAdapter);

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
