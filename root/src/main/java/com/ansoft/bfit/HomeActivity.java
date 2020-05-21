package com.ansoft.bfit;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    AppCompatImageView icMenu;
    TextView tvTitle;
    DrawerLayout drawer;
    NavigationView navigationView;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        icMenu = findViewById(R.id.icMenu);
        tvTitle = findViewById(R.id.tvTitle);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_disover, R.id.nav_whatsnew,
                R.id.nav_report, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        tvTitle.setText("Butt Workout");
                        break;

                    case R.id.nav_disover:
                        tvTitle.setText("Discover");
                        break;

                    case R.id.nav_whatsnew:
                        tvTitle.setText("Whats New");
                        break;

                    case R.id.nav_report:
                        tvTitle.setText("Report");
                        break;


                    case R.id.nav_fitness_expert:
                        tvTitle.setText("Meet the experts");
                        break;

                    case R.id.nav_settings:
                        tvTitle.setText("Settings");
                        break;

                    default:
                        tvTitle.setText("Butt Workout");
                        break;

                }
                NavigationUI.onNavDestinationSelected(menuItem, navController);
                drawer.closeDrawer(Gravity.LEFT);
                return true;
            }
        });

        icMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppBarConfiguration.getDrawerLayout().openDrawer(Gravity.LEFT);
            }
        });
    }


    public void navigateFragment(int id) {
        System.out.println("Discover");
        navController.navigate(id);
        navigationView.setCheckedItem(id);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
