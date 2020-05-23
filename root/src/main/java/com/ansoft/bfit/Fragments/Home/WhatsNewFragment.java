package com.ansoft.bfit.Fragments.Home;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ansoft.bfit.Adapter.DiscoverAdapter;
import com.ansoft.bfit.App;
import com.ansoft.bfit.Model.Discover;
import com.ansoft.bfit.R;
import com.ansoft.bfit.Util.AppConstants;
import com.ansoft.bfit.Util.SpacesItemDecorationLinear;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WhatsNewFragment extends Fragment {


    ArrayList<Discover> whatsNewArrayList;

    RecyclerView whatsnewRecyclerView;

    AVLoadingIndicatorView loadingProgressBar;

    public WhatsNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_whats_new, container, false);
        whatsnewRecyclerView = view.findViewById(R.id.whatsnewRecyclerView);
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar);
        getTrendings();
        return view;
    }


    public void getTrendings() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        whatsnewRecyclerView.setVisibility(View.GONE);
        whatsNewArrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.whatsnew_url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Trending Response", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            JSONArray dataArray = dataObject.getJSONArray("docs");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject eachDataJsonObject = dataArray.getJSONObject(i);
                                Discover discover = new Discover();
                                discover.setId(eachDataJsonObject.getString("_id"));
                                discover.setName(eachDataJsonObject.getString("name"));
                                discover.setDescription(eachDataJsonObject.getString("description"));
                                discover.setDifficulty(eachDataJsonObject.getInt("difficulty"));
                                discover.setThumbnailLink(eachDataJsonObject.getString("thumbnail"));


                                JSONArray workoutArray = eachDataJsonObject.getJSONArray("workouts");

                                for (int k = 0; k < workoutArray.length(); k++) {
                                    JSONObject workoutJsonObject = workoutArray.getJSONObject(k);
                                    discover.addWorkout(workoutJsonObject.getInt("id"), workoutJsonObject.getInt("rep"));
                                }
                                whatsNewArrayList.add(discover);
                            }

                            loadingProgressBar.setVisibility(View.GONE);
                            if (whatsNewArrayList.size() > 0) {
                                DiscoverAdapter discoverAdapter = new DiscoverAdapter(whatsNewArrayList, (AppCompatActivity) getActivity());

                                RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
                                whatsnewRecyclerView.setLayoutManager(manager);
                                whatsnewRecyclerView.setNestedScrollingEnabled(true);
                                int spacingInPixels = 2;
                                whatsnewRecyclerView.addItemDecoration(new SpacesItemDecorationLinear(spacingInPixels));
                                whatsnewRecyclerView.setAdapter(discoverAdapter);
                                whatsnewRecyclerView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            Log.e("Error", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        App.getInstance().addToRequestQueue(stringRequest, "asa");

    }


}
