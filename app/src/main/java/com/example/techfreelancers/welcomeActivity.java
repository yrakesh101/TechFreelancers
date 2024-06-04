package com.example.techfreelancers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class welcomeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_acitivity); // Ensure the layout file name is correct
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = this.getSharedPreferences("login_user_info", Context.MODE_PRIVATE);

        Button signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(view -> {
            Intent intent = new Intent(welcomeActivity.this, signupActivity.class);
            startActivity(intent);
        });

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(welcomeActivity.this, loginActivity.class);
            startActivity(intent);
        });
    }
}
