package com.example.techfreelancers.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.techfreelancers.R;
import com.example.techfreelancers.api.EvaluateApi;
import com.example.techfreelancers.api.ProjectApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.form.EvaluateForm;
import com.example.techfreelancers.api.model.TechProject;
import com.example.techfreelancers.databinding.ActivityRateAccepterBinding;
import com.example.techfreelancers.utils.RetrofitClient;
import com.example.techfreelancers.utils.SessionManager;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RateAccepterActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityRateAccepterBinding rateAccepterBinding;

    private Integer projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rateAccepterBinding = ActivityRateAccepterBinding.inflate(getLayoutInflater());
        setContentView(rateAccepterBinding.getRoot());

        setSupportActionBar(rateAccepterBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Drawable navIcon = rateAccepterBinding.toolbar.getNavigationIcon();
            if (navIcon != null) {
                @ColorInt int color = getResources().getColor(R.color.white);
                navIcon.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
        }

        init();
    }

    private void init() {
        projectId = getIntent().getIntExtra("projectId", 0);
        rateAccepterBinding.saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == rateAccepterBinding.saveButton.getId()) {
            postEvaluation();
        }
    }

    private void postEvaluation() {
        EvaluateForm evaluation = new EvaluateForm();
        String rateContent = rateAccepterBinding.commentEditText.getText().toString().trim();
        String rateStar = String.valueOf(rateAccepterBinding.ratingBar.getRating());
        if (projectId != null) {
            evaluation.setProjectId(projectId);
        } else {
            Toast.makeText(this, "Project id cannot be null.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!"".equals(rateContent) && rateContent.length() > 0) {
            evaluation.setEvaluationDetail(rateContent);
        } else {
            Toast.makeText(this, "Comment cannot be null.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!"".equals(rateStar) && rateStar.length() > 0) {
            evaluation.setRateStar(Float.valueOf(rateAccepterBinding.ratingBar.getRating()).intValue());
        } else {
            evaluation.setRateStar(0);
        }
        Retrofit retrofit = RetrofitClient.getInstance(this);
        ProgressDialog progressDialog = new ProgressDialog(this, 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResponseModel> call = retrofit.create(EvaluateApi.class).evaluateAccepter(evaluation);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    Toast.makeText(RateAccepterActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(RateAccepterActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getApplicationContext());
                            startActivity(new Intent(getApplicationContext(), loginActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RateAccepterActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Handle the back button press
        return true;
    }
}