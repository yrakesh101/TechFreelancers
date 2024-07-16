package com.example.techfreelancers.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techfreelancers.databinding.ActivityEditProfileBinding;

public class editProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding editProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editProfileBinding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(editProfileBinding.getRoot());

        setSupportActionBar(editProfileBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        init();
    }

    private void init() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Handle the back button press
        return true;
    }
}