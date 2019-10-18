package com.ansoft.bfit.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ansoft.bfit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiveupFragment extends Fragment {


    public GiveupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giveup, container, false);
        return view;
    }

}
