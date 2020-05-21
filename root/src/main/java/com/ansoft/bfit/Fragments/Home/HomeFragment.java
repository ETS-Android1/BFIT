package com.ansoft.bfit.Fragments.Home;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.HomeActivity;
import com.ansoft.bfit.LevelActivity;
import com.ansoft.bfit.R;
import com.ansoft.bfit.Util.Calculator;
import com.ansoft.bfit.WelcomeActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    RelativeLayout lvlEasy, lvlMedium, lvlHard, btnDiscover, btnWhatsNew;
    ProgressBar levelProgressEasy, levelProgressMedium, levelProgressHard;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        createNotificationChannel();


        if (WorkoutDay.count(WorkoutDay.class) < 1) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), WelcomeActivity.class));
            //return;
        }
        levelProgressEasy = view.findViewById(R.id.levelProgressEasy);
        levelProgressMedium = view.findViewById(R.id.levelProgressMedium);
        levelProgressHard = view.findViewById(R.id.levelProgressHard);
        btnDiscover = view.findViewById(R.id.btnDiscover);
        btnWhatsNew = view.findViewById(R.id.btnWhatsNew);

        levelProgressEasy.setProgress(Calculator.calculateLevelProgress(1));
        levelProgressMedium.setProgress(Calculator.calculateLevelProgress(2));
        levelProgressHard.setProgress(Calculator.calculateLevelProgress(3));


        lvlEasy = view.findViewById(R.id.lvlEasy);
        lvlMedium = view.findViewById(R.id.lvlMedium);
        lvlHard = view.findViewById(R.id.lvlHard);

        lvlEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LevelActivity.class);
                intent.putExtra(getString(R.string.level), getString(R.string.lvl_easy));
                startActivity(intent);
            }
        });

        lvlMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LevelActivity.class);
                intent.putExtra(getString(R.string.level), getString(R.string.lvl_medium));
                startActivity(intent);
            }
        });

        lvlHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LevelActivity.class);
                intent.putExtra(getString(R.string.level), getString(R.string.lvl_hard));
                startActivity(intent);
            }
        });

        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).navigateFragment(R.id.nav_disover);
            }
        });

        btnWhatsNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).navigateFragment(R.id.nav_whatsnew);
            }
        });

        PushDownAnim.setPushDownAnimTo(lvlEasy);
        PushDownAnim.setPushDownAnimTo(lvlMedium);
        PushDownAnim.setPushDownAnimTo(lvlHard);
        PushDownAnim.setPushDownAnimTo(btnDiscover);
        PushDownAnim.setPushDownAnimTo(btnWhatsNew);

        return view;
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Bfit";
            String description = "Bfit";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("98251", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
