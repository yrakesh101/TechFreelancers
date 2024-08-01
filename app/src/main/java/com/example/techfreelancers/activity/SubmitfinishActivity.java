package com.example.techfreelancers.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.example.techfreelancers.api.ProjectApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.model.TechProject;
import com.example.techfreelancers.databinding.ActivitySubmitfinishBinding;
import com.example.techfreelancers.utils.RetrofitClient;
import com.example.techfreelancers.utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubmitfinishActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySubmitfinishBinding submitfinishBinding;

    private Integer projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        submitfinishBinding = ActivitySubmitfinishBinding.inflate(getLayoutInflater());
        setContentView(submitfinishBinding.getRoot());

        setSupportActionBar(submitfinishBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Drawable navIcon = submitfinishBinding.toolbar.getNavigationIcon();
            if (navIcon != null) {
                @ColorInt int color = getResources().getColor(R.color.white);
                navIcon.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
        }

        init();
    }

    private void init() {
        projectId = getIntent().getIntExtra("projectId", 0);
        fetchProjectInfo(projectId);
        submitfinishBinding.finishButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == submitfinishBinding.finishButton.getId()) {
            submitProject();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Handle the back button press
        return true;
    }

    /**
     * get category data from server to render on UI
     *
     * @return
     */
    private void fetchProjectInfo(Integer projectId) {
        if (projectId != null) {

        } else {
            Toast.makeText(this, "Project id cannot be null.", Toast.LENGTH_SHORT).show();
            return;
        }
        // show progress dialog
        ProgressDialog progressDialog = new ProgressDialog(this, 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Retrofit retrofit = RetrofitClient.getInstance(this);
        Call<ResponseModel<TechProject>> call = retrofit.create(ProjectApi.class).queryProjectById(projectId);
        call.enqueue(new Callback<ResponseModel<TechProject>>() {
            @Override
            public void onResponse(Call<ResponseModel<TechProject>> call, Response<ResponseModel<TechProject>> response) {
                // dissmiss the progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        TechProject project = (TechProject) responseModel.getData();
                        String imageUrl = "https://picsum.photos/id/"+ project.getPublishUserId() +"/200/200";
                        Picasso.get()
                                .load(imageUrl)
                                .placeholder(R.drawable.log) // Optional placeholder image
                                .error(R.drawable.error) // Optional error image
                                .into(submitfinishBinding.profilePic);
                        submitfinishBinding.projectId.setText(project.getProjectId().toString());
                        submitfinishBinding.usernames.setText(project.getPublishUserName());
                        submitfinishBinding.datePosted.setText(project.getUpdateTime());
                        submitfinishBinding.updateTitle.setText(project.getProjectTitle());
                        submitfinishBinding.cost.setText(project.getProjectCost().toString());
                        submitfinishBinding.timeSpan.setText(project.getTimeSpan());
                        submitfinishBinding.descriptionDetails.setText(project.getProjectDetail());
                    } else {
                        Toast.makeText(SubmitfinishActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(SubmitfinishActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getApplicationContext());
                            startActivity(new Intent(SubmitfinishActivity.this, loginActivity.class));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<TechProject>> call, Throwable t) {
                // dissmiss the progress dialog
                progressDialog.dismiss();
                Toast.makeText(SubmitfinishActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     *
     * @return
     */
    private void submitProject() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the dialog title and message
        builder.setMessage("Do you really finish this project?");
        // Add a button to the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Handle the button click (if needed)

                String projectIdStr = submitfinishBinding.projectId.getText().toString().trim();
                Integer projectId;
                if (!"".equals(projectIdStr) && projectIdStr.length() > 0) {
                    projectId = Integer.valueOf(projectIdStr);
                } else {
                    Toast.makeText(getApplicationContext(), "Project id cannot be null.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // show progress dialog
                ProgressDialog progressDialog = new ProgressDialog(SubmitfinishActivity.this, 1);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Retrofit retrofit = RetrofitClient.getInstance(getApplicationContext());
                Call<ResponseModel> call = retrofit.create(ProjectApi.class).submitProject(projectId);
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        // dissmiss the progress dialog
                        progressDialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            ResponseModel responseModel = response.body();
                            if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                                Toast.makeText(SubmitfinishActivity.this , "Submit project success.", Toast.LENGTH_SHORT).show();
                                onSupportNavigateUp();
                            } else {
                                Toast.makeText(SubmitfinishActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                            ResponseModel errorModel = null;
                            try {
                                errorModel = converter.convert(response.errorBody());
                                Toast.makeText(SubmitfinishActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                                if(401 == errorModel.getStatus()) {
                                    SessionManager.clearUserSession(getApplicationContext());
                                    startActivity(new Intent(SubmitfinishActivity.this, loginActivity.class));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        // dissmiss the progress dialog
                        progressDialog.dismiss();
                        Toast.makeText(SubmitfinishActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Handle the button click (if needed)
                dialog.dismiss(); // Close the dialog
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}