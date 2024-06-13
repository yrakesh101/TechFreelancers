package com.example.techfreelancers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.techfreelancers.R;

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
        ImageView menuImageView = findViewById(R.id.menuImageView);
        menuImageView.setOnClickListener(v -> {
            Intent intent = new Intent(profileActivity.this, menuActivity.class);
            startActivity(intent);
        });
    }
}