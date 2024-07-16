package com.example.techfreelancers.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techfreelancers.databinding.ActivitySearchResultBinding;


public class searchResultActivity extends AppCompatActivity {

    private ActivitySearchResultBinding searchResultBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchResultBinding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(searchResultBinding.getRoot());

        setSupportActionBar(searchResultBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Handle the back button press
        return true;
    }
}