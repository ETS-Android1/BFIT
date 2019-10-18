package com.ansoft.bfit.Fragments.Home;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.ansoft.bfit.LevelActivity;
import com.ansoft.bfit.R;
import com.ansoft.bfit.Util.Calculator;
import com.thekhaeng.pushdownanim.PushDownAnim;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    RelativeLayout lvlEasy, lvlMedium, lvlHard;
    ProgressBar levelProgressEasy, levelProgressMedium, levelProgressHard;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_main, container, false);

        levelProgressEasy=view.findViewById(R.id.levelProgressEasy);
        levelProgressMedium=view.findViewById(R.id.levelProgressMedium);
        levelProgressHard=view.findViewById(R.id.levelProgressHard);

        levelProgressEasy.setProgress(Calculator.calculateLevelProgress(1));
        levelProgressMedium.setProgress(Calculator.calculateLevelProgress(2));
        levelProgressHard.setProgress(Calculator.calculateLevelProgress(3));


        lvlEasy=view.findViewById(R.id.lvlEasy);
        lvlMedium=view.findViewById(R.id.lvlMedium);
        lvlHard=view.findViewById(R.id.lvlHard);

        lvlEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LevelActivity.class);
                intent.putExtra(getString(R.string.level), getString(R.string.lvl_easy));
                startActivity(intent);
            }
        });

        lvlMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LevelActivity.class);
                intent.putExtra(getString(R.string.level), getString(R.string.lvl_medium));
                startActivity(intent);
            }
        });

        lvlHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LevelActivity.class);
                intent.putExtra(getString(R.string.level), getString(R.string.lvl_hard));
                startActivity(intent);
            }
        });
        PushDownAnim.setPushDownAnimTo(lvlEasy);
        PushDownAnim.setPushDownAnimTo(lvlMedium);
        PushDownAnim.setPushDownAnimTo(lvlHard);
        return view;
    }

}
