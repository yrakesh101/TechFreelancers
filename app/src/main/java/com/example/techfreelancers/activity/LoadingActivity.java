package com.example.techfreelancers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techfreelancers.R;
import com.example.techfreelancers.utils.ConfigUtil;

import java.util.Properties;

public class LoadingActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    // Total time for the countdown (3 seconds)
    private static int TOTAL_TIME = 3000;
    // Interval for updating the progress
    private static final int INTERVAL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        progressBar = findViewById(R.id.progressBar);

        // load loadingtime from config file
        Properties config = ConfigUtil.loadConfig(getApplicationContext());
        if (config != null) {
            TOTAL_TIME = Integer.parseInt(config.getProperty("loadingtime"));
        }
        // Start the countdown
        startCountdown();
    }

    private void startCountdown() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            // Start with 100%
            int progress = 100;
            int timeElapsed = 0;

            @Override
            public void run() {
                if (timeElapsed < TOTAL_TIME) {
                    timeElapsed += INTERVAL;
                    progress = 100 - (timeElapsed * 100 / TOTAL_TIME);
                    progressBar.setProgress(progress);
                    handler.postDelayed(this, INTERVAL);
                } else {
                    // Transition to main content after countdown
                    Intent intent = new Intent(LoadingActivity.this, loginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
        handler.post(runnable);
    }
}