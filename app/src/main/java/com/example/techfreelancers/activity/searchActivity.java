package com.example.techfreelancers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.adapter.trendingGigsAdapter;
import com.example.techfreelancers.model.trendingGigs;

import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private trendingGigsAdapter adapter;
    private List<trendingGigs> trendingGigsList;

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
    }

    private List<trendingGigs> fetchDataFromDatabase() {
        // Mock data for demonstration
        List<trendingGigs> gigsList = new ArrayList<>();
        gigsList.add(new trendingGigs("Project 1", "$1000", "2 weeks", "Description of Project 1"));
        gigsList.add(new trendingGigs("Project 2", "$2000", "3 weeks", "Description of Project 2"));
        // logic here
        return gigsList;
    }
}
