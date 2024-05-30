package com.example.techfreelancers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.techfreelancers.databinding.ActivitySignUpBinding;

public class signupActivity extends welcomeActivity {
    ActivitySignUpBinding signUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
        setVariable();
    }

    private void setVariable() {
        signUpBinding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= signUpBinding.emailEditText.getText().toString();
                String password=signUpBinding.passwordEditText.getText().toString();

                if (password.length()<6){
                    Toast.makeText(signupActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}