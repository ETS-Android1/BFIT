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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ansoft.bfit.Adapter.ExpertAdapter;
import com.ansoft.bfit.App;
import com.ansoft.bfit.Model.ExpertModel;
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
public class ExpertFragment extends Fragment {


    ArrayList<ExpertModel> expertArrayList;
    RecyclerView expertRecyclerView;
    AVLoadingIndicatorView loadingProgressBar;

    public ExpertFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert, container, false);
        expertRecyclerView = view.findViewById(R.id.expertRecyclerView);
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar);
        getExperts();
        return view;
    }


    public void getExperts() {

        loadingProgressBar.setVisibility(View.VISIBLE);
        expertRecyclerView.setVisibility(View.GONE);
        expertArrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConstants.EXPERT_APP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            JSONArray dataArray = dataObject.getJSONArray("docs");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject eachDataJsonObject = dataArray.getJSONObject(i);
                                ExpertModel expertModel = new ExpertModel();
                                expertModel.setName(eachDataJsonObject.getString("name"));
                                expertModel.setLink(eachDataJsonObject.getString("photoUrl"));
                                expertModel.setInstagram(eachDataJsonObject.getString("instagram"));
                                expertModel.setWebsite(eachDataJsonObject.getString("website"));
                                expertModel.setPhone(eachDataJsonObject.getString("phonenumber"));
                                expertArrayList.add(expertModel);
                            }

                            loadingProgressBar.setVisibility(View.GONE);
                            if (expertArrayList.size() > 0) {
                                ExpertAdapter discoverAdapter = new ExpertAdapter(expertArrayList, (AppCompatActivity) getActivity());
                                RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
                                expertRecyclerView.setLayoutManager(manager);
                                expertRecyclerView.setNestedScrollingEnabled(true);
                                int spacingInPixels = 2;
                                expertRecyclerView.addItemDecoration(new SpacesItemDecorationLinear(spacingInPixels));
                                expertRecyclerView.setAdapter(discoverAdapter);
                                expertRecyclerView.setVisibility(View.VISIBLE);
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
