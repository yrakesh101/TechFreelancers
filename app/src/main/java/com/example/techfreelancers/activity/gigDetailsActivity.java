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
import com.example.techfreelancers.api.ProjectApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.model.TechProject;
import com.example.techfreelancers.databinding.ActivityGigDetailsBinding;
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

public class gigDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityGigDetailsBinding gigDetailsBinding;

    private Integer projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gigDetailsBinding = ActivityGigDetailsBinding.inflate(getLayoutInflater());
        setContentView(gigDetailsBinding.getRoot());

        setSupportActionBar(gigDetailsBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Drawable navIcon = gigDetailsBinding.toolbar.getNavigationIcon();
            if (navIcon != null) {
                @ColorInt int color = getResources().getColor(R.color.white);
                navIcon.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
        }

        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Handle the back button press
        return true;
    }

    private void init() {
        projectId = getIntent().getIntExtra("projectId", 0);
        fetchProjectInfo(projectId);
        gigDetailsBinding.upvoteButton.setOnClickListener(this);
        gigDetailsBinding.downvoteButton.setOnClickListener(this);
        gigDetailsBinding.acceptButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == gigDetailsBinding.acceptButton.getId()) {
            acceptProject();
        }
        else if (v.getId() == gigDetailsBinding.upvoteButton.getId()) {
            voteProject(1);
        }
        else if (v.getId() == gigDetailsBinding.downvoteButton.getId()) {
            voteProject(-1);

        }
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
                                .into(gigDetailsBinding.profilePic);
                        gigDetailsBinding.projectId.setText(project.getProjectId().toString());
                        gigDetailsBinding.usernames.setText(project.getPublishUserName());
                        gigDetailsBinding.datePosted.setText(project.getUpdateTime());
                        gigDetailsBinding.updateTitle.setText(project.getProjectTitle());
                        gigDetailsBinding.cost.setText(project.getProjectCost().toString());
                        gigDetailsBinding.timeSpan.setText(project.getTimeSpan());
                        gigDetailsBinding.descriptionDetails.setText(project.getProjectDetail());
                        gigDetailsBinding.voteCount.setText(project.getProjectVote().toString());
                    } else {
                        Toast.makeText(gigDetailsActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(gigDetailsActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getApplicationContext());
                            startActivity(new Intent(gigDetailsActivity.this, loginActivity.class));
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
                Toast.makeText(gigDetailsActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     *
     * @return
     */
    private void acceptProject() {
        String projectIdStr = gigDetailsBinding.projectId.getText().toString().trim();
        Integer projectId;
        if (!"".equals(projectIdStr) && projectIdStr.length() > 0) {
            projectId = Integer.valueOf(projectIdStr);
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
        Call<ResponseModel> call = retrofit.create(ProjectApi.class).acceptProject(projectId);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                // dissmiss the progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        Toast.makeText(gigDetailsActivity.this , "Accept project success.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(gigDetailsActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(gigDetailsActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getApplicationContext());
                            startActivity(new Intent(gigDetailsActivity.this, loginActivity.class));
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
                Toast.makeText(gigDetailsActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * vote project
     *
     * @return
     */
    private void voteProject(Integer voteType) {
        String projectIdStr = gigDetailsBinding.projectId.getText().toString().trim();
        Integer projectId;
        if (!"".equals(projectIdStr) && projectIdStr.length() > 0) {
            projectId = Integer.valueOf(projectIdStr);
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
        Call<ResponseModel> call = retrofit.create(ProjectApi.class).voteProject(projectId, voteType);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                // dissmiss the progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        Toast.makeText(gigDetailsActivity.this , "Vote project success.", Toast.LENGTH_SHORT).show();
                        fetchProjectInfo(projectId);
                    } else {
                        Toast.makeText(gigDetailsActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(gigDetailsActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getApplicationContext());
                            startActivity(new Intent(gigDetailsActivity.this, loginActivity.class));
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
                Toast.makeText(gigDetailsActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}