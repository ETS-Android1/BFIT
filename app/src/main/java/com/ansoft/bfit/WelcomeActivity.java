package com.ansoft.bfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.ansoft.bfit.Fragments.GiveupFragment;
import com.ansoft.bfit.Fragments.Welcome.ActivityFragment;
import com.ansoft.bfit.Fragments.Welcome.CreatePlanFragment;
import com.ansoft.bfit.Fragments.Welcome.FrequencyFragment;
import com.ansoft.bfit.Fragments.Welcome.GenderFragment;
import com.ansoft.bfit.Fragments.Welcome.PushupFragment;
import com.ansoft.bfit.Fragments.Welcome.ReminderFragment;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        navigateFragment(new GenderFragment(this));
    }

    public void navigateFragment(Fragment fragment) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
