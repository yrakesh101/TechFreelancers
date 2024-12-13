package com.example.techfreelancers.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.adapter.mostVotedAdapter;
import com.example.techfreelancers.api.ProjectApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.model.TechProject;
import com.example.techfreelancers.databinding.ActivityMostVotedBinding;
import com.example.techfreelancers.utils.RetrofitClient;
import com.example.techfreelancers.utils.SessionManager;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
public class mostVotedActivity extends AppCompatActivity {

    private ActivityMostVotedBinding mostVotedBinding;
    private RecyclerView recyclerView;
    private mostVotedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mostVotedBinding = ActivityMostVotedBinding.inflate(getLayoutInflater());
        setContentView(mostVotedBinding.getRoot());

        recyclerView = findViewById(R.id.mostVotedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(mostVotedBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Drawable navIcon = mostVotedBinding.toolbar.getNavigationIcon();
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
        // get recommend project data from server to render on UI
        fetchRecommendProjects();
    }

    /**
     * get recommend project data from server to render on UI
     */
    private void fetchRecommendProjects() {
        // show progress dialog
        ProgressDialog progressDialog = new ProgressDialog(mostVotedActivity.this, 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Retrofit retrofit = RetrofitClient.getInstance(this);
        Call<ResponseModel<List<TechProject>>> call = retrofit.create(ProjectApi.class).queryMostvotedProject();
        call.enqueue(new Callback<ResponseModel<List<TechProject>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<TechProject>>> call, Response<ResponseModel<List<TechProject>>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        List<TechProject> projects = (List<TechProject>) responseModel.getData();
                        adapter = new mostVotedAdapter(projects);
                        adapter.setOnItemClickListener(new mostVotedAdapter.OnItemClickListener() {
                            @Override
                            public void onUpvoteClick(TechProject project) {
                                voteProject(project.getProjectId(), 1);
                            }

                            @Override
                            public void onDownvoteClick(TechProject project) {
                                voteProject(project.getProjectId(), -1);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(mostVotedActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(mostVotedActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ResponseModel<List<TechProject>>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mostVotedActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * vote project
     *
     * @return
     */
    private void voteProject(Integer projectId, Integer voteType) {
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
        Call<ResponseModel> call = retrofit.create(ProjectApi.class).voteProject(projectId, voteType);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                // dissmiss the progress dialog
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        Toast.makeText(mostVotedActivity.this , "Vote project success.", Toast.LENGTH_SHORT).show();
                        fetchRecommendProjects();
                    } else {
                        Toast.makeText(mostVotedActivity.this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Converter<ResponseBody, ResponseModel> converter = retrofit.responseBodyConverter(ResponseModel.class, new Annotation[0]);
                    ResponseModel errorModel = null;
                    try {
                        errorModel = converter.convert(response.errorBody());
                        Toast.makeText(mostVotedActivity.this, errorModel.getMessage(), Toast.LENGTH_SHORT).show();
                        if(401 == errorModel.getStatus()) {
                            SessionManager.clearUserSession(getApplicationContext());
                            startActivity(new Intent(mostVotedActivity.this, loginActivity.class));
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
                Toast.makeText(mostVotedActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
