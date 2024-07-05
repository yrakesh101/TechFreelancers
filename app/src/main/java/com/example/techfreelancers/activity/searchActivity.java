package com.example.techfreelancers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.adapter.trendingGigsAdapter;
import com.example.techfreelancers.model.trendingGigs;
import com.example.techfreelancers.adapter.categoryAdapter;
import com.example.techfreelancers.model.category;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private trendingGigsAdapter adapter;
    private List<trendingGigs> trendingGigsList;

    private RecyclerView categoryRecyclerView;
    private categoryAdapter categoryAdapter;
    private List<category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.trendingsPostsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data from your database
        trendingGigsList = fetchDataFromDatabase();

        adapter = new trendingGigsAdapter(trendingGigsList);
        recyclerView.setAdapter(adapter);

        categoryRecyclerView = findViewById(R.id.categoryView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        categoryList = fetchCategoriesFromDatabase();
        categoryAdapter = new categoryAdapter(categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    startActivity(new Intent(searchActivity.this, searchActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_search) {
                    startActivity(new Intent(searchActivity.this, searchActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.nav_settings) {
                    startActivity(new Intent(searchActivity.this, settingActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    private List<trendingGigs> fetchDataFromDatabase() {
        // Mock data for demonstration
        List<trendingGigs> gigsList = new ArrayList<>();
        gigsList.add(new trendingGigs("Project 1", "$1000", "2 weeks", "Description of Project 1"));
        gigsList.add(new trendingGigs("Project 2", "$2000", "3 weeks", "Description of Project 2"));
        gigsList.add(new trendingGigs("Project 3", "$1000", "2 weeks", "Description of Project 1"));
        gigsList.add(new trendingGigs("Project 4", "$2000", "3 weeks", "Description of Project 2"));
        // logic here
        return gigsList;
    }

    private List<category> fetchCategoriesFromDatabase() {
        // Mock data for demonstration
        List<category> categories = new ArrayList<>();
        categories.add(new category("Category 1"));
        categories.add(new category("Category 2"));
        categories.add(new category("Category 3"));
        categories.add(new category("Category 4"));
        categories.add(new category("Category 5"));
        categories.add(new category("Category 6"));
        // Add more data fetching logic here
        return categories;
    }
}
