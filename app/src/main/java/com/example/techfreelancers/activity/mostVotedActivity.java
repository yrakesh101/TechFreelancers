package com.example.techfreelancers.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techfreelancers.R;
import com.example.techfreelancers.adapter.trendingGigsAdapter;
import com.example.techfreelancers.api.ProjectApi;
import com.example.techfreelancers.api.ResponseModel;
import com.example.techfreelancers.api.model.TechProject;
import com.example.techfreelancers.utils.RetrofitClient;

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
    private RecyclerView recyclerView;
    private trendingGigsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_voted);

        recyclerView = findViewById(R.id.PostsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.nav_messages) {
//                    startActivity(new Intent(mostVotedActivity.this, messagesActivity.class));
//                    return true;
//                }
//                else if (item.getItemId() == R.id.nav_home) {
//                    startActivity(new Intent(mostVotedActivity.this, searchActivity.class));
//                    return true;
//                } else if (item.getItemId() == R.id.nav_search) {
//                    startActivity(new Intent(mostVotedActivity.this, mostVotedActivity.class));
//                    return true;
//                } else if (item.getItemId() == R.id.nav_settings) {
//                    startActivity(new Intent(mostVotedActivity.this, settingActivity.class));
//                    return true;
//                }
//                return false;
//            }
//        });

        init();

    }

    private void init() {
        // show progress dialog
        ProgressDialog progressDialog = new ProgressDialog(mostVotedActivity.this, 1);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // get recommend project data from server to render on UI
        fetchRecommendProjects();
        // get category data from server to render on UI

        // dissmiss the progress dialog
        progressDialog.dismiss();
    }

    /**
     * get recommend project data from server to render on UI
     */
    private void fetchRecommendProjects() {
        Retrofit retrofit = RetrofitClient.getInstance(this);
        Call<ResponseModel<List<TechProject>>> call = retrofit.create(ProjectApi.class).queryRecommendProject();
        call.enqueue(new Callback<ResponseModel<List<TechProject>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<TechProject>>> call, Response<ResponseModel<List<TechProject>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getSuccess() && responseModel.getStatus() == 200) {
                        List<TechProject> projects = (List<TechProject>) responseModel.getData();
                        adapter = new trendingGigsAdapter(projects);
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<TechProject>>> call, Throwable t) {
                Toast.makeText(mostVotedActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
