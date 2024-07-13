package com.example.techfreelancers.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.techfreelancers.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class editProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_action_bar);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF3700B3"))); // Set your color here
            TextView title = actionBar.getCustomView().findViewById(R.id.action_bar_title);
            title.setText("Edit Profile");
        }

//        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.nav_messages) {
//                    startActivity(new Intent(editProfileActivity.this, messagesActivity.class));
//                    return true;
//                } else if (item.getItemId() == R.id.nav_home) {
//                    startActivity(new Intent(editProfileActivity.this, searchActivity.class));
//                    return true;
//                } else if (item.getItemId() == R.id.nav_search) {
//                    startActivity(new Intent(editProfileActivity.this, mostVotedActivity.class));
//                    return true;
//                } else if (item.getItemId() == R.id.nav_settings) {
//                    startActivity(new Intent(editProfileActivity.this, settingActivity.class));
//                    return true;
//                }
//                return false;
//            }
//        });


    }
}