package com.example.techfreelancers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.techfreelancers.R;
import com.example.techfreelancers.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private float dX, dY;
    private int lastAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        setSupportActionBar(mainBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_category, R.id.nav_message, R.id.nav_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mainBinding.navView, navController);
        // Add a listener to know when the fragment changes
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.nav_home) {
                    mainBinding.actionBarTitle.setText("Home");
                } else if (navDestination.getId() == R.id.nav_category) {
                    mainBinding.actionBarTitle.setText("Category");
                } else if (navDestination.getId() == R.id.nav_message) {
                    mainBinding.actionBarTitle.setText("Message");
                } else if (navDestination.getId() == R.id.nav_profile) {
                    mainBinding.actionBarTitle.setText("Profile");
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setX(50);
        fab.setY(2100);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.setX(event.getRawX() + dX);
                        view.setY(event.getRawY() + dY);
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN)
                            view.performClick();
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, postGigActivity.class));
            }
        });
    }
}