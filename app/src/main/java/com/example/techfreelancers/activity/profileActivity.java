package com.example.techfreelancers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.techfreelancers.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView editIcon = findViewById(R.id.editIcon);
        editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(profileActivity.this, editProfileActivity.class);
            startActivity(intent);
        });


        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_messages) {
                    startActivity(new Intent(profileActivity.this, messagesActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_home) {
                    startActivity(new Intent(profileActivity.this, searchActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_search) {
                    startActivity(new Intent(profileActivity.this, viewAllActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_settings) {
                    startActivity(new Intent(profileActivity.this, settingActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}